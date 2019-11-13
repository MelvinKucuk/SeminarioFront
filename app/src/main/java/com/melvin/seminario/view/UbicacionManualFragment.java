package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private String calle;
    private String altura;
    private boolean esDobleMano;
    private boolean esEsquina;

    private int minutosIngresado;
    private int horaIngresada;
    private int diaIngresado;
    private int mesIngresado;
    private int anioIngresado;

    @BindView(R.id.editTextCalle)
    EditText editTextCalle;
    @BindView(R.id.editTextAltura)
    EditText editTextAltura;
    @BindView(R.id.editTextHora)
    EditText editTextHora;
    @BindView(R.id.editTextFecha)
    EditText editTextFecha;
    @BindView(R.id.buttonSiguiente)
    Button botonSiguiente;
    @BindView(R.id.checkboxSiDobleMano)
    CheckBox checkBoxSiDobleMano;
    @BindView(R.id.checkboxNoDobleMano)
    CheckBox checkBoxNoDobleMano;
    @BindView(R.id.checkboxSiEsquina)
    CheckBox checkBoxSiEsquina;
    @BindView(R.id.checkboxNoEsquina)
    CheckBox checkBoxNoEsquina;

    private TimePickerDialog timePickerDialog;


    private OnFragmentInteractionListener mListener;

    public UbicacionManualFragment() {
    }


    @SuppressLint("ClickableViewAccessibility")
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
        checkBoxNoDobleMano.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()){
                checkBoxSiDobleMano.setEnabled(false);
            } else {
                checkBoxSiDobleMano.setEnabled(true);
            }
        });
        checkBoxSiDobleMano.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked())
                checkBoxNoDobleMano.setEnabled(false);
            else
                checkBoxNoDobleMano.setEnabled(true);
            });
        checkBoxNoEsquina.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked())
                checkBoxSiEsquina.setEnabled(false);
            else
                checkBoxSiEsquina.setEnabled(true);
        });
        checkBoxSiEsquina.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked())
                checkBoxNoEsquina.setEnabled(false);
            else
                checkBoxNoEsquina.setEnabled(true);
        });
        botonSiguiente.setOnClickListener(v -> {
            calle = editTextCalle.getText().toString();
            altura = editTextAltura.getText().toString();
            hora = editTextHora.getText().toString();
            fecha = editTextFecha.getText().toString();
            esEsquina = checkBoxSiEsquina.isChecked();
            esDobleMano = checkBoxSiDobleMano.isChecked();


            if (!calle.isEmpty()){
                if (!hora.isEmpty()){
                    if (!fecha.isEmpty()){
                        if (diaIngresado == dia && mesIngresado == mes && anioIngresado == anio){
                            if (horaIngresada < horaActual){
                                if (!altura.isEmpty()) {
                                    if (checkBoxSiEsquina.isChecked() || checkBoxNoEsquina.isChecked()) {
                                        if (checkBoxNoDobleMano.isChecked() || checkBoxSiDobleMano.isChecked()){
                                            mListener.enUbicacionManualConfirmada(fecha, hora, calle, esEsquina, esDobleMano);
                                        } else {
                                            Toast.makeText(getActivity(), "Falta ingresar si es Doble Mano", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Falta ingresar si es Esquina", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getActivity(), "Falta ingresar la altura", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (horaIngresada > horaActual){
                                    Toast.makeText(getActivity(), "Error: No se puede ingresar una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (minutosIngresado < minutosActual){
                                        if (!altura.isEmpty()) {
                                            if (checkBoxSiEsquina.isChecked() || checkBoxNoEsquina.isChecked()) {
                                                if (checkBoxNoDobleMano.isChecked() || checkBoxSiDobleMano.isChecked()){
                                                    mListener.enUbicacionManualConfirmada(fecha, hora, calle, esEsquina, esDobleMano);
                                                } else {
                                                    Toast.makeText(getActivity(), "Falta ingresar si es Doble Mano", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "Falta ingresar si es Esquina", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Falta ingresar la altura", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Error: No se puede ingresar una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            if (!altura.isEmpty()) {
                                if (checkBoxSiEsquina.isChecked() || checkBoxNoEsquina.isChecked()) {
                                    if (checkBoxNoDobleMano.isChecked() || checkBoxSiDobleMano.isChecked()){
                                        mListener.enUbicacionManualConfirmada(fecha, hora, calle, esEsquina, esDobleMano);
                                    } else {
                                        Toast.makeText(getActivity(), "Falta ingresar si es Doble Mano", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Falta ingresar si es Esquina", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "Falta ingresar la altura", Toast.LENGTH_SHORT).show();
                            }
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
        botonSiguiente.setOnTouchListener(
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
        void enUbicacionManualConfirmada(String fecha, String hora, String direccion, boolean esEsquina, boolean esDobleMano);
    }
}
