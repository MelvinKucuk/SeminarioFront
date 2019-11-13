package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CrearUsuarioFinalizarFragment extends Fragment {

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
    @BindView(R.id.buttonFinalizar)
    Button botonFinalizar;

    public final Calendar c = Calendar.getInstance();

    private final int dia = c.get(Calendar.DAY_OF_MONTH);
    private final int mes = c.get(Calendar.MONTH);
    private final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";

    private OnFragmentInteractionListener mListener;

    public CrearUsuarioFinalizarFragment() {
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_usuario_finalizar, container, false);
        ButterKnife.bind(this, view);

        editTextFechaNacimiento.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

        botonFinalizar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String apellido = editTextApellido.getText().toString();
            String dni = editTextDni.getText().toString();
            String domicilio = editTextDomicilio.getText().toString();
            String fecha = editTextFechaNacimiento.getText().toString();
            String pais = editTextPais.getText().toString();

            if (!nombre.isEmpty()){
                if (!apellido.isEmpty()){
                    if (!dni.isEmpty()){
                        if (!fecha.isEmpty()){
                            if (!pais.isEmpty()){
                                if (!domicilio.isEmpty()){
                                    mListener.terminarCreacion(nombre, apellido, dni, fecha, pais, domicilio);
                                } else {
                                    Toast.makeText(getActivity(), "Falta ingreasr el domicilio", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Falta ingresar el Pais", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Falta ingresar la Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Falta ingresar el DNI", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Falta ingresar el Apellido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Falta ingresar el Nombre", Toast.LENGTH_SHORT).show();
            }

        });
        botonFinalizar.setOnTouchListener(
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

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void terminarCreacion(String nombre, String apellido, String dni, String fecha, String pais, String domicilio);
    }
}
