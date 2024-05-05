package com.alfonso.nfcplay.ui.write;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.IOException;

public class NfcUtils {

    public static NdefMessage createNdefMessage(String content) {
        NdefMessage message = null;
        if (content != null && !content.isEmpty()) {
            NdefRecord record = NdefRecord.createMime("text/plain", content.getBytes());
            message = new NdefMessage(new NdefRecord[]{record});
        }
        return message;
    }

    public static void writeNfcTag(Context context, Tag tag, NdefMessage message) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not available on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tag != null) {
            try {
                Ndef ndef = Ndef.get(tag);
                if (ndef != null) {
                    ndef.connect();
                    ndef.writeNdefMessage(message);
                    Toast.makeText(context, "NFC Tag written successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "NFC Tag does not support NDEF", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException | IllegalStateException | NullPointerException | FormatException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error writing NFC Tag", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e){
                e.printStackTrace();
                Toast.makeText(context, "Error: Tag indetectable", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Error getting NFC Tag", Toast.LENGTH_SHORT).show();
        }
    }

    public static String readNfcTag(Intent intent) {
        String tagContent = null;
        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage message = (NdefMessage) rawMessages[0];
            if (message != null) {
                NdefRecord record = message.getRecords()[0];
                if (record != null) {
                    tagContent = new String(record.getPayload());
                }
            }
        }
        return tagContent;
    }
}
