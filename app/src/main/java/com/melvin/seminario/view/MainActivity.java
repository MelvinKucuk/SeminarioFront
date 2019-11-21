package com.melvin.seminario.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.util.ResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_USER = "USER";
    public static final String KEY_PASS = "PASS";
    public static final String USER_PREFERENCES = "UserPreferences";

    @BindView(R.id.editTextMail)
    EditText editTextMail;
    @BindView(R.id.editTextConstraseña)
    EditText editTextContrasena;
    @BindView(R.id.buttonIngresar)
    Button botonIngresar;
    @BindView(R.id.buttonCrearUsuario)
    Button botonCrearUsuario;
    @BindView(R.id.checkboxRecordar)
    CheckBox checkBoxRecordar;
    @BindView(R.id.botonIngresarInvitado)
    Button botonInvitado;
    private ProgressDialog progressDialog;

    private String user;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        user = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).getString(KEY_USER, "");
        pass = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).getString(KEY_PASS, "");

        botonCrearUsuario.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CrearUsuarioActivity.class));
        });

        botonInvitado.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            Bundle datos = new Bundle();
            datos.putBoolean(MenuActivity.KEY_INVITADO, true);
            intent.putExtras(datos);
            startActivity(intent);
        });

        // Recordar usaurio
        if (!user.isEmpty()){
            if (!pass.isEmpty()) {
                checkBoxRecordar.setChecked(true);
                editTextMail.setText(user);
                editTextContrasena.setText(pass);
                progressDialog.setMessage("Validando Usuario");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                new UsuarioController().verificarUsuario(user, pass, new ResultListener<Boolean>() {
                    @Override
                    public void finish(Boolean resultado) {
                        if (resultado) {
                            progressDialog.dismiss();
                            getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).edit().putString(KEY_USER, user).apply();
                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        botonIngresar.setOnClickListener(v -> {

                    String username = editTextMail.getText().toString();
                    String password = editTextContrasena.getText().toString();
                    progressDialog.setMessage("Validando Usuario");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    new UsuarioController().verificarUsuario(username, password, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                progressDialog.dismiss();
                                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).edit().putString(KEY_USER, username).apply();
                                if (checkBoxRecordar.isChecked())
                                    getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).edit().putString(KEY_PASS, password).apply();
                                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        );



    }
}
