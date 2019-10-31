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

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetalleFragment extends Fragment {

    @BindView(R.id.cardViewSiguiente)
    CardView botonSiguiente;
    @BindView(R.id.cardViewOmitir)
    CardView botonOmitir;
    @BindView(R.id.editTextDetalle)
    EditText editTextDetalle;

    private OnFragmentInteractionListener mListener;

    public DetalleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        ButterKnife.bind(this, view);

        botonSiguiente.setOnClickListener(v -> {
            mListener.enDetalleCompletado(editTextDetalle.getText().toString());
        });
        botonOmitir.setOnClickListener(v -> mListener.enDetalleOmitir());

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

    interface OnFragmentInteractionListener {
        void enDetalleCompletado(String detalle);
        void enDetalleOmitir();
    }
}
