package com.alfonso.nfcplay.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private final String[] gameTitles = {"La búsqueda del tesoro", "La búsqueda del tesoro TEAMS", "Scape Room", "Tradución de objetos", "Datos Curiosos"};
    private final String[] gameInstructions = {
            "En la búsqueda del tesoro el organizador dará una pista inicial y esconderá el resto de pasos hasta el tesoro en diferentes sitios." +
                    " \n\nEjemplo, Pista 1: debajo de la piedra de la entrada de casa, Pista 2: donde duerme el perro ... \n\n" +
                    "Cada pista deberá ser guardada en una tarjeta NFC en la que se escribira: Pista x: y la descripción de la pista. Así no se perderá el orden de descubrimiento de pistas",
            "EN la búsqueda del tesoro por equipos las reglas son muy similares a la individual. \n\nSin embargo, cada equipo sumará 5 puntos si son los primeros" +
                    "en encontrar la etiqueta, 4 puntos si son los segundos, y un punto menos por equipos siguientes, dependiendo de cuantos equipos sean\n\n" +
                    "Además, el primer equipo en encontrar la tarjeta nfc puede reescreibirla modificando ligeramente la descripción de la siguiente pista para " +
                    "entorpecer a los demás equipos, pero solo ligeramnete. Es decir, borrar una palabra, cambiar alguna letra...",
            "En Scape Room se pondrán diferentes pistas escritas en diferentes etiquetas NFC para lograr escapar de una habitación.\n\n" +
                    "La dificultad del juego dependerá únicamente de la preparación e intensidad que le quiera dar el creador del juego.",
            "Traducción de objetos más que un juego es una actividad didactica, donde se pegarán etiquetas NFC debajo de todos los objetos de una habitación y se guardará " +
                    "el nombre de ese objeto en el idioma que deseemos que aprendan los niños.\n\n" +
                    "Irán escanenado los diferentes objetos del cuarto para así ampliar su vovabulario en inglés, poor ejemplo.",
            "Datos Curiosos sigue la estela del juego anterior, pero en vez de traducciones de objetos, se añadirá información curiosa" +
                    "de un objeto al que se le pegará una tarjeta NFC con los datos curiosos."
    };

    public String[] getGameTitles() {
        return gameTitles;
    }

    public String getGameInstructions(int index) {
        return gameInstructions[index];
    }
}