package com.melvin.seminario.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UbicacionManualFragment extends Fragment {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    public final Calendar c = Calendar.getInstance();

    private final int dia = c.get(Calendar.DAY_OF_MONTH);
    private final int mes = c.get(Calendar.MONTH);
    private final int anio = c.get(Calendar.YEAR);
    private final int horaActual = c.get(Calendar.HOUR_OF_DAY);
    private final int minutosActual = c.get(Calendar.MINUTE);

    private String fecha;
    private String hora;
    private String direccion;

    private int minutosIngresado;
    private int horaIngresada;
    private int diaIngresado;
    private int mesIngresado;
    private int anioIngresado;

    @BindView(R.id.editTextCalle)
    EditText editTextDireccion;
    @BindView(R.id.editTextHora)
    EditText editTextHora;
    @BindView(R.id.editTextFecha)
    EditText editTextFecha;
    @BindView(R.id.cardViewSiguiente)
    CardView botonSiguiente;

    private TimePickerDialog timePickerDialog;


    private OnFragmentInteractionListener mListener;

    public UbicacionManualFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubicacion_manual, container, false);
        ButterKnife.bind(this, view);

        editTextHora.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(getActivity(), (timePicker, hourOfDay, minutes) ->{
                String amPm;
                int hour;
                if (hourOfDay >= 12){
                    amPm = "PM";
                    if (hourOfDay != 12) {
                        hour = hourOfDay - 12;
                    } else {
                        hour = 12;
                    }

                } else {
                    amPm = "AM";
                    hour = hourOfDay;

                }
                minutosIngresado = minutes;
                horaIngresada = hourOfDay;


                String hora = String.format(Locale.getDefault(), "%02d:%02d", hour, minutes) + " " + amPm;

                editTextHora.setText(hora);
            }, 0, 0, false);

            timePickerDialog.show();
        });
        editTextFecha.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month+1;
                    String diaFormateado = (dayOfMonth < 10)? CERO + dayOfMonth : String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + mesActual : String.valueOf(mesActual);
                    String fecha = diaFormateado + BARRA + mesFormateado + BARRA + year;
                    editTextFecha.setText(fecha);

                    diaIngresado = dayOfMonth;
                    mesIngresado = month;
                    anioIngresado = year;

                }
            }, anio, mes, dia);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        botonSiguiente.setOnClickListener(v -> {
            direccion = editTextDireccion.getText().toString();
            hora = editTextHora.getText().toString();
            fecha = editTextFecha.getText().toString();

            if (!direccion.isEmpty()){
                if (!hora.isEmpty()){
                    if (!fecha.isEmpty()){
                        if (diaIngresado == dia && mesIngresado == mes && anioIngresado == anio){
                            if (horaIngresada < horaActual){
                                mListener.enUbicacionManualConfirmada(fecha, hora, direccion);
                            } else {
                                if (horaIngresada > horaActual){
                                    Toast.makeText(getActivity(), "Error: No se puede ingresar una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (minutosIngresado < minutosActual){
                                        mListener.enUbicacionManualConfirmada(fecha, hora, direccion);
                                    } else {
                                        Toast.makeText(getActivity(), "Error: No se puede ingresar una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            mListener.enUbicacionManualConfirmada(fecha, hora, direccion);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Falta ingresar la fecha", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Falta ingresar la hora", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Falta ingresar la dirección", Toast.LENGTH_SHORT).show();
            }
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
        void enUbicacionManualConfirmada(String fecha, String hora, String direccion);
    }
}
