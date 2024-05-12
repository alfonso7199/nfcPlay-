package com.alfonso.nfcplay.ui.read;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.nfcplay.R;
import com.alfonso.nfcplay.databinding.FragmentReadBinding;
import com.alfonso.nfcplay.ui.read.NFCReader;

public class ReadFragment extends Fragment {

    NfcAdapter nfcAdapter;
    FragmentReadBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NfcManager nfcManager = (NfcManager) requireContext().getSystemService(Context.NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                Toast.makeText(requireContext(), "Por favor, activa el NFC para leer tarjetas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Este dispositivo no soporta NFC", Toast.LENGTH_SHORT).show();
        }

        ReadViewModel readViewModel = new ViewModelProvider(this).get(ReadViewModel.class);

        final Button readButton = binding.readNfcButton;
        readButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NFCReader.class);
            startActivity(intent);
        });

        final TextView textView = binding.readNfcButton;
        readViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
