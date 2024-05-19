package com.alfonso.nfcplay.ui.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alfonso.nfcplay.R;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] gameTitles;

    public CustomArrayAdapter(Context context, String[] gameTitles) {
        super(context, R.layout.list_item_game, gameTitles);
        this.context = context;
        this.gameTitles = gameTitles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_game, parent, false);

        TextView titleTextView = rowView.findViewById(R.id.text_game_title);
        ImageView imageView = rowView.findViewById(R.id.image_game);

        titleTextView.setText(gameTitles[position]);
        imageView.setImageResource(getImageResourceForGame(gameTitles[position]));

        return rowView;
    }

    private int getImageResourceForGame(String gameTitle) {

        return R.drawable.ic_games_black;
    }
}