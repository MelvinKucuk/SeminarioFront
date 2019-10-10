package com.melvin.seminario.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.melvin.seminario.R;
import com.melvin.seminario.Util.ResultListener;
import com.melvin.seminario.dao.DaoInternetPosts;
import com.melvin.seminario.model.Post;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView botonIngresar = findViewById(R.id.cardViewIngresar);
        botonIngresar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SeleccionarSiniestroActivity.class)));


        new DaoInternetPosts().obtenerPosts(resultado -> {
            String respuesta = resultado.get(0).getDescription();
        });
    }
}
