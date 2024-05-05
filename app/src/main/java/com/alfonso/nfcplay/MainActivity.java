package com.alfonso.nfcplay;

import android.app.AlertDialog;
import android.os.Bundle;

import com.alfonso.nfcplay.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_games, R.id.navigation_read, R.id.navigation_write, R.id.navigation_otros)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    @Override
    public void onBackPressed() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        if (navController.getCurrentDestination() == navController.getGraph().findNode(R.id.navigation_games)) {
            super.onBackPressed(); // Permite el comportamiento predeterminado de onBackPressed
        } else {
            mostrarCuadroAlertaCerrarApp();
        }
    }

    private void mostrarCuadroAlertaCerrarApp() {
        new AlertDialog.Builder(this)
                .setTitle("¿Quieres salir de la aplicación?")
                .setMessage("¿Estás seguro de que quieres cerrar la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }


}