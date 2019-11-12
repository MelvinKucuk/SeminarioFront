package com.melvin.seminario.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.model.User;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    private void cargarFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enDatosCompletados(String mail, String contrasena) {

        new UsuarioController().verificarMail(mail,
                esNuevoUusario -> {
                    if (esNuevoUusario){
                        this.mail = mail;
                        this.contrasena = contrasena;
                        CrearUsuarioFinalizarFragment fragment = new CrearUsuarioFinalizarFragment();
                        cargarFragment(fragment);
                    } else {
                        Toast.makeText(this, "El mail ya esta en uso", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void terminarCreacion(String nombre, String apellido, String dni, String fecha, String pais, String domicilio) {
        User usuario = new User(this.mail, this.contrasena);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setFechaNacimeinto(fecha);
        usuario.setDomicilio(domicilio);
        usuario.setPais(pais);
        new UsuarioController().crearUsuario(usuario,
                seCreo -> {
                    if (seCreo) {
                        Toast.makeText(this, "El usuario se creo exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Hubo un error en la creacion", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
