package com.melvin.seminario.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.util.ResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextMail)
    EditText editTextMail;
    @BindView(R.id.editTextConstraseña)
    EditText editTextContraseña;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        CardView botonIngresar = findViewById(R.id.cardViewIngresar);
        botonIngresar.setOnClickListener(v -> {

                    String username = editTextMail.getText().toString();
                    String password = editTextContraseña.getText().toString();
                    progressDialog.setMessage("Validando Usuario");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    new UsuarioController().verificarUsuario(username, password, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                progressDialog.dismiss();
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
