package com.alfonso.nfcplay;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.alfonso.nfcplay.databinding.ActivityMainBinding;
import com.alfonso.nfcplay.servicios.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.alfonso.nfcplay.servicios.AjustesActivity;
import com.alfonso.nfcplay.servicios.AboutNfcPlayActivity;


//https://www.canva.com/design/DAGFmY1QLnY/YV2n5XgXINCahvGB1vaOqg/edit?utm_content=DAGFmY1QLnY&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.alfonso.nfcplay.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
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
            super.onBackPressed();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);


        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_ajustes) {
            Intent intent = new Intent(this, AjustesActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about_nfcplay) {
            Intent intent = new Intent(this, AboutNfcPlayActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}