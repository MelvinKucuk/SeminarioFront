package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.model.User;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsuarioActivity extends AppCompatActivity {

    private String user;

    @BindView(R.id.editTextNombre)
    EditText editTextNombre;
    @BindView(R.id.editTextApellido)
    EditText editTextApellido;
    @BindView(R.id.editTextDni)
    EditText editTextDni;
    @BindView(R.id.editTextDomicilio)
    EditText editTextDomicilio;
    @BindView(R.id.editTextFecha)
    EditText editTextFechaNacimiento;
    @BindView(R.id.editTextPais)
    EditText editTextPais;
    @BindView(R.id.editTextMail)
    EditText editTextMail;
    @BindView(R.id.toolbarUsuarios)
    Toolbar toolbar;
    @BindView(R.id.buttonGuardar)
    Button botonGuardar;

    private String username;

    public final Calendar c = Calendar.getInstance();

    private final int dia = c.get(Calendar.DAY_OF_MONTH);
    private final int mes = c.get(Calendar.MONTH);
    private final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        botonGuardar.setOnTouchListener(
                (v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            v.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                            ((Button) v).setTextColor(getResources().getColor(R.color.white));
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            v.getBackground().clearColorFilter();
                            ((Button) v).setTextColor(getResources().getColor(R.color.primaryText));
                            v.invalidate();
                            break;
                        }
                    }
                    return false;
                });

        editTextFechaNacimiento.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month+1;
                    String diaFormateado = (dayOfMonth < 10)? CERO + dayOfMonth : String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + mesActual : String.valueOf(mesActual);
                    String fecha = diaFormateado + BARRA + mesFormateado + BARRA + year;
                    editTextFechaNacimiento.setText(fecha);

                }
            }, anio, mes, dia);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        user = getSharedPreferences(MainActivity.USER_PREFERENCES, MODE_PRIVATE).getString(MainActivity.KEY_USER, "");

        new UsuarioController().recuperarUsuario(user,
                usuario -> {
                    if (usuario != null){
                        username = usuario.getUsername();
                        editTextNombre.setText(usuario.getNombre());
                        editTextApellido.setText(usuario.getApellido());
                        editTextDni.setText(usuario.getDni());
                        editTextFechaNacimiento.setText(usuario.getFechaNacimeinto());
                        editTextPais.setText(usuario.getPais());
                        editTextMail.setText(usuario.getUsername());
                        editTextDomicilio.setText(usuario.getDomicilio());
                    }
                });
        botonGuardar.setOnClickListener(v -> {
            User usuario = new User(username);
            usuario.setNombre(editTextNombre.getText().toString());
            usuario.setApellido(editTextApellido.getText().toString());
            usuario.setDni(editTextDni.getText().toString());
            usuario.setFechaNacimeinto(editTextFechaNacimiento.getText().toString());
            usuario.setPais(editTextPais.getText().toString());
            usuario.setDomicilio(editTextDomicilio.getText().toString());
            new UsuarioController().actualizarUsuario(usuario,
                        guardadoExitoso -> {
                            if (guardadoExitoso){
                                Toast.makeText(this, "Se guardaron los datos", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
