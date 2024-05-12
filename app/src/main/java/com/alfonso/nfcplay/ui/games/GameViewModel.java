package com.alfonso.nfcplay.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private final String[] gameTitles = {"La búsqueda del tesoro", "La búsqueda del tesoro TEAMS", "Juego 3"};
    private final String[] gameInstructions = {
            "En la búsqueda del tesoro el organizador dará una pista inicial y esconderá el resto de pasos hasta el tesoro en diferentes sitios." +
                    " \n\nEjemplo, Pista 1: debajo de la piedra de la entrada de casa, Pista 2: donde duerme el perro ... \n\n" +
                    "Cada pista deberá ser guardada en una tarjeta NFC en la que se escribira: Pista x: y la descripción de la pista. Así no se perderá el orden de descubrimiento de pistas",
            "EN la búsqueda del tesoro por equipos las reglas son muy similares a la individual. \n\nSin embargo, cada equipo sumará 5 puntos si son los primeros" +
                    "en encontrar la etiqueta, 4 puntos si son los segundos, y un punto menos por equipos siguientes, dependiendo de cuantos equipos sean\n\n" +
                    "Además, el primer equipo en encontrar la tarjeta nfc puede reescreibirla modificando ligeramente la descripción de la siguiente pista para " +
                    "entorpecer a los demás equipos, pero solo ligeramnete. Es decir, borrar una palabra, cambiar alguna letra...",
            "Instrucciones para el Juego 3..."
    };

    public String[] getGameTitles() {
        return gameTitles;
    }

    public String getGameInstructions(int index) {
        return gameInstructions[index];
    }
}