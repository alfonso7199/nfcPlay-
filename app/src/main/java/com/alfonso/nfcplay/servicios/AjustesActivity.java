package com.alfonso.nfcplay.servicios;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.alfonso.nfcplay.R;

public class AjustesActivity extends AppCompatActivity {

    private SwitchCompat switchNFC;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        SwitchCompat switchTema = findViewById(R.id.switch_tema);
        switchNFC = findViewById(R.id.switch_nfc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        switchTema.setChecked(sharedPreferences.getBoolean("modoOscuro", false));
        switchNFC.setChecked(sharedPreferences.getBoolean("nfcActivado", false));

        Button btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());

        switchTema.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Guardar preferencia
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("modoOscuro", isChecked);
                editor.apply();

                // Cambiar el tema de la aplicación
                if (isChecked) {
                    setTheme(R.style.Theme_NFCPlay);
                } else {
                    setTheme(R.style.Theme_NFCPlay);
                }

                recreate();
            }
        });

        switchNFC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // NFC está activado
                    if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
                        Intent nfcIntent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(nfcIntent);
                    } else if (nfcAdapter == null) {
                        Toast.makeText(AjustesActivity.this, "El dispositivo no es compatible con NFC", Toast.LENGTH_SHORT).show();
                        switchNFC.setChecked(false);
                    }
                } else {
                    if (nfcAdapter != null && nfcAdapter.isEnabled()) {
                        Intent nfcIntent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(nfcIntent);
                    }
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("nfcActivado", isChecked);
                editor.apply();
            }
        });
    }
    private void cerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
