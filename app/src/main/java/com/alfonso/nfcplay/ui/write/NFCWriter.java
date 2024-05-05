package com.alfonso.nfcplay.ui.write;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.alfonso.nfcplay.R;
import com.google.android.material.textfield.TextInputEditText;

public class NFCWriter extends AppCompatActivity {
    private NdefMessage nfcMessage;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextInputEditText editTextText, editUrl;
    private Button btnWriteText, btnWriteUrl;
    private Tag tag; // Agregamos un miembro de clase para almacenar la referencia al Tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_writer);

        editTextText = findViewById(R.id.editTextText);
        editUrl = findViewById(R.id.editUrl);
        btnWriteText = findViewById(R.id.btnWriteText);
        btnWriteUrl = findViewById(R.id.btnWriteUrl);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC not available on this device", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create a PendingIntent object so the Android system can populate it with the details
        // of the tag when it is scanned.
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);

        btnWriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamamos al método writeNfcTag pasando el contenido del texto y el objeto Tag
                writeNfcTag(editTextText.getText().toString(), tag);
            }
        });

        btnWriteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editUrl.getText().toString();
                if (isUrlValid(url)) {
                    writeNfcTag(url, tag); // También pasamos el objeto Tag aquí
                } else {
                    Toast.makeText(NFCWriter.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
    private NdefMessage createNdefMessage(String content) {
        NdefRecord record = NdefRecord.createMime("text/plain", content.getBytes());
        return new NdefMessage(new NdefRecord[]{record});
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() != null && intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); // Get the Tag
            if (tag != null) {
                // Here you have the tag object
                NfcUtils.writeNfcTag(this, tag, nfcMessage);
            } else {
                Toast.makeText(this, "Error: Tag is null", Toast.LENGTH_SHORT).show();
            }
            // For simplicity, let's just show a toast with the content
            String tagContent = NfcUtils.readNfcTag(intent);
            Toast.makeText(this, "NFC Tag Content: " + tagContent, Toast.LENGTH_SHORT).show();
        }
    }



    private void writeNfcTag(String content, Tag tag) {
        NdefMessage message = NfcUtils.createNdefMessage(content);
        if (message != null) {
            // Pasamos el objeto Tag al método writeNfcTag en NfcUtils
            NfcUtils.writeNfcTag(this,tag, message);
        } else {
            Toast.makeText(this, "Failed to create NFC message", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUrlValid(String url) {
        // A simple URL validation
        return android.util.Patterns.WEB_URL.matcher(url).matches();
    }
}
