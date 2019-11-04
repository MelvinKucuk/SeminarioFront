package com.melvin.seminario.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.melvin.seminario.R;

public class CrearUsuarioActivity extends AppCompatActivity implements
                                CrearUsuarioFinalizarFragment.OnFragmentInteractionListener,
                                CrearUsuarioMailFragment.OnFragmentInteractionListener{

    private String mail;
    private String contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        CrearUsuarioMailFragment fragment = new CrearUsuarioMailFragment();
        cargarFragment(fragment);
    }

    private void cargarFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enDatosCompletados(String mail, String contrasena) {
        this.mail = mail;
        this.contrasena = contrasena;
        CrearUsuarioFinalizarFragment fragment = new CrearUsuarioFinalizarFragment();
        cargarFragment(fragment);
    }
}
