package com.melvin.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SeleccionarSiniestroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_siniestro);

        ImageView botonSiniestro = findViewById(R.id.botonSiniestros);
        botonSiniestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionarSiniestroActivity.this, FlujoAccidenteActivity.class));
            }
        });
    }
}
