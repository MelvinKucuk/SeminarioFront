package com.melvin.seminario;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UbicacionManualFragment extends Fragment {

    @BindView(R.id.editTextDireccion)
    EditText editTextDireccion;
    @BindView(R.id.editTextHora)
    EditText editTextHora;
    @BindView(R.id.editTextFecha)
    EditText editTextFecha;

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
                    hour = hourOfDay-12;
                } else {
                    amPm = "AM";
                    hour = hourOfDay;

                }

                String hora = String.format(Locale.getDefault(), "%02d:%02d", hour, minutes) + " " + amPm;

                editTextHora.setText(hora);
            }, 0, 0, false);

            timePickerDialog.show();
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
    }
}
