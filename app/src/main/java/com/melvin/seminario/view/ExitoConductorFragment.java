package com.melvin.seminario.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExitoConductorFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    @BindView(R.id.buttonSiguiente)
    Button botonSiguiente;
    @BindView(R.id.buttonFoto)
    Button botonMasFotos;

    public ExitoConductorFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exito_conductor, container, false);
        ButterKnife.bind(this, view);

        botonSiguiente.setOnClickListener(v -> mListener.enExitoConductor());
        botonMasFotos.setOnClickListener(v -> mListener.enAgregarMasFotosConductor());

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
        void enExitoConductor();
        void enAgregarMasFotosConductor();
    }
}
