package com.melvin.seminario.view;

import android.content.Intent;
import android.net.Uri;
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

    public static final String KEY_INVITADO = "invitado";

    @BindView(R.id.toolbarSiniestros)
    Toolbar toolbar;
    @BindView(R.id.botonConsulta)
    ImageView botonConsulta;
    @BindView(R.id.textViewBienvenida)
    TextView textViewBienveida;
    @BindView(R.id.botonPolicia)
    ImageView botonPolicia;
    @BindView(R.id.textViewConsulta)
    TextView textViewConsulta;

    private String user;
    private Boolean esInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            esInvitado = datos.getBoolean(KEY_INVITADO);
            if (esInvitado) {
                textViewBienveida.setText("Hola Invitado");
                textViewBienveida.setVisibility(View.VISIBLE);
                botonConsulta.setImageResource(R.drawable.ic_estado_deshabilitado);
                textViewConsulta.setTextColor(getResources().getColor(R.color.disabledText));
            }

        } else {
            user = getSharedPreferences(MainActivity.USER_PREFERENCES, MainActivity.MODE_PRIVATE).getString(MainActivity.KEY_USER, "");
            new UsuarioController().recuperarUsuario(user,
                    usuario -> {
                        textViewBienveida.setText(String.format("Hola %s %s", usuario.getNombre(), usuario.getApellido()));
                        textViewBienveida.setVisibility(View.VISIBLE);
                    });
            botonConsulta.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, DenunciasActivity.class)));
        }




        ImageView botonSiniestro = findViewById(R.id.botonSiniestros);
        botonSiniestro.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, FlujoAccidenteActivity.class);
            Bundle bundle = new Bundle();
            if (esInvitado != null)
                bundle.putBoolean(FlujoAccidenteActivity.KEY_INVITADO, true);
            else
                bundle.putBoolean(FlujoAccidenteActivity.KEY_INVITADO, false);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        botonPolicia.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:107"));
            startActivity(intent);
        });
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
            if (!esInvitado)
                startActivity(new Intent(MenuActivity.this, UsuarioActivity.class));
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
