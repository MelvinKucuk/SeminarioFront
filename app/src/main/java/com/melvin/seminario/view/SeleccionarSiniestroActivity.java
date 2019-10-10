package com.melvin.seminario.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.melvin.seminario.R;

public class SeleccionarSiniestroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_siniestro);

        ImageView botonSiniestro = findViewById(R.id.botonSiniestros);
        botonSiniestro.setOnClickListener(v ->
                startActivity(new Intent(SeleccionarSiniestroActivity.this, FlujoAccidenteActivity.class)));
    }
}
