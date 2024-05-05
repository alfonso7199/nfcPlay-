package com.alfonso.nfcplay.ui.write;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.nfcplay.R;
import com.alfonso.nfcplay.databinding.FragmentWriteBinding;

public class WriteFragment extends Fragment {

    private FragmentWriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WriteViewModel writeViewModel = new ViewModelProvider(this).get(WriteViewModel.class);

        binding = FragmentWriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.write;
        writeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button writeNfcButton = binding.writeNfcButton;
        writeNfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NFCWriter.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
