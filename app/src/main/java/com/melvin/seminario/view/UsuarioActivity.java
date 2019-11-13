package com.melvin.seminario.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;

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

    public final Calendar c = Calendar.getInstance();

    private final int dia = c.get(Calendar.DAY_OF_MONTH);
    private final int mes = c.get(Calendar.MONTH);
    private final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
                        editTextNombre.setText(usuario.getNombre());
                        editTextApellido.setText(usuario.getApellido());
                        editTextDni.setText(usuario.getDni());
                        editTextFechaNacimiento.setText(usuario.getFechaNacimeinto());
                        editTextPais.setText(usuario.getPais());
                        editTextMail.setText(usuario.getUsername());
                        editTextDomicilio.setText(usuario.getDomicilio());
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
