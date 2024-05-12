package com.alfonso.nfcplay.ui.select;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.nfcplay.databinding.FragmentOtrosBinding;


public class OtroFragment extends Fragment {
    private FragmentOtrosBinding binding;
    private NfcAdapter nfcAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OtroViewModel otroViewModel = new ViewModelProvider(this).get(OtroViewModel.class);

        binding = FragmentOtrosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.otros;
        otroViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btnnfc = binding.btnnfc;
        btnnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableNfc();
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireActivity());
        if (nfcAdapter == null) {
            Toast.makeText(requireActivity(), "NFC no está disponible en este dispositivo", Toast.LENGTH_SHORT).show();
            return root;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(requireActivity(), "Por favor, habilite NFC en la configuración", Toast.LENGTH_SHORT).show();
            Intent nfcSettings = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(nfcSettings);
        }

        return root;
    }

    private void enableNfc() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(requireActivity());
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(requireActivity(), "NFC habilitado", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
