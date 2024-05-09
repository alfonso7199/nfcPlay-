package com.alfonso.nfcplay.ui.write;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alfonso.nfcplay.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
public class NFCWriter extends AppCompatActivity {
    private static final String ERROR_DETECTED = "No NFC tag detected!";
    private static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    private static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private Tag myTag;

    private TextView tvNFCContent;
    private TextView message;
    private Button btnWrite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_writer);

        tvNFCContent = findViewById(R.id.nfcContents);
        message = findViewById(R.id.editMessage);
        btnWrite = findViewById(R.id.button);

        btnWrite.setOnClickListener(view -> {
            try {
                if (myTag == null) {
                    Toast.makeText(NFCWriter.this, ERROR_DETECTED, Toast.LENGTH_LONG).show();
                } else {
                    write(message.getText().toString(), myTag);
                    Toast.makeText(NFCWriter.this, WRITE_SUCCESS, Toast.LENGTH_LONG).show();
                }
            } catch (IOException | FormatException e) {
                Toast.makeText(NFCWriter.this, WRITE_ERROR, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // For when the activity is launched by the intent-filter for android.nfc.action.NDEF_DISCOVERE
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_MUTABLE
        );
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        IntentFilter[] writeTagFilters = new IntentFilter[]{tagDetected};
    }

    /******************************************************************************
     * Read From NFC Tag
     *****************************************************************************/
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                buildTagViews(msgs);
            }
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;
        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        Charset textEncoding = ((payload[0] & 128) == 0) ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        int languageCodeLength = payload[0] & 0063;
        text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        tvNFCContent.setText("Message read from NFC Tag:\n" + text);
    }
    /******************************************************************************
     * Write to NFC Tag
     *****************************************************************************/
    private void write(String text, Tag tag) throws IOException, FormatException {
        try {
            NdefRecord[] records = {createRecord(text)};
            NdefMessage message = new NdefMessage(records);
            // Get an instance of Ndef for the tag.
            Ndef ndef = Ndef.get(tag);
            // Enable I/O
            ndef.connect();
            // Write the message
            ndef.writeNdefMessage(message);
            // Close the connection
            ndef.close();
        } catch (IOException | FormatException e) {
            // Manejar IOException y FormatException
            e.printStackTrace();
            // Mostrar un mensaje de error al usuario
            Toast.makeText(this, "Error writing to NFC tag", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            // Manejar SecurityException
            e.printStackTrace();
            // Mostrar un mensaje de error al usuario
            Toast.makeText(this, "SecurityException: Tag is out of date", Toast.LENGTH_SHORT).show();
        }
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // Set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // Copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
    }

    /**
     * For reading the NFC when the app is already launched
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WriteModeOn();
    }

    /******************************************************************************
     * Enable Write and foreground dispatch to prevent intent-filter to launch the app again
     *****************************************************************************/
    private void WriteModeOn() {
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    /******************************************************************************
     * Disable Write and foreground dispatch to allow intent-filter to launch the app
     *****************************************************************************/
    private void WriteModeOff() {
        nfcAdapter.disableForegroundDispatch(this);
    }
}