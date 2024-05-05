package com.alfonso.nfcplay.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private final String[] gameTitles = {"Juego 1", "Juego 2", "Juego 3"};
    private final String[] gameInstructions = {
            "Instrucciones para el Juego 1... ",
            "Instrucciones para el Juego 2...",
            "Instrucciones para el Juego 3..."
    };

    public String[] getGameTitles() {
        return gameTitles;
    }

    public String getGameInstructions(int index) {
        return gameInstructions[index];
    }
}