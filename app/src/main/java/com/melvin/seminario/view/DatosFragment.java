package com.melvin.seminario.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DatosFragment extends Fragment {

    @BindView(R.id.editTextDetalle)
    EditText editTextDatos;
    @BindView(R.id.cardViewSiguiente)
    CardView botonSiguiente;

    private OnFragmentInteractionListener mListener;

    public DatosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        ButterKnife.bind(this, view);

        botonSiguiente.setOnClickListener(v -> {
            if (!editTextDatos.getText().toString().isEmpty()){
                mListener.enDatosIngresados(editTextDatos.getText().toString());
            } else {
                Toast.makeText(getContext(), "No pueden estar los datos vacíos", Toast.LENGTH_LONG).show();
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
        void enDatosIngresados(String datos);
    }
}
