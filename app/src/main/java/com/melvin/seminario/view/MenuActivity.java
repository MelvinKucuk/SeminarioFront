package com.melvin.seminario.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbarSiniestros)
    Toolbar toolbar;
    @BindView(R.id.botonConsulta)
    ImageView botonConsulta;
    @BindView(R.id.textViewBienvenida)
    TextView textViewBienveida;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        user = getSharedPreferences(MainActivity.USER_PREFERENCES, MainActivity.MODE_PRIVATE).getString(MainActivity.KEY_USER, "");
        new UsuarioController().recuperarUsuario(user,
                usuario -> {
                    textViewBienveida.setText(String.format("Hola %s %s", usuario.getNombre(), usuario.getApellido()));
                    textViewBienveida.setVisibility(View.VISIBLE);
                });

        botonConsulta.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, DenunciasActivity.class)));


        ImageView botonSiniestro = findViewById(R.id.botonSiniestros);
        botonSiniestro.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, FlujoAccidenteActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.datos){
            startActivity(new Intent(MenuActivity.this, UsuarioActivity.class));
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
