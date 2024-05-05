package com.alfonso.nfcplay.ui.games;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.nfcplay.R;
import com.alfonso.nfcplay.databinding.FragmentGamesBinding;

public class GameFragment extends Fragment {

    private GameViewModel gameViewModel;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_games, container, false);

        listView = root.findViewById(R.id.list_games);
        CustomArrayAdapter adapter = new CustomArrayAdapter(requireContext(), gameViewModel.getGameTitles());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInstructionsDialog(gameViewModel.getGameInstructions(position));
            }
        });

        return root;
    }

    private void showInstructionsDialog(String instructions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Cómo Jugar");
        builder.setMessage(instructions);
        builder.setPositiveButton("Cerrar", null);
        builder.show();
    }
}