package com.melvin.seminario.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngresarMailFragment extends DialogFragment {

    @BindView(R.id.editTextMail)
    EditText editTextMail;
    @BindView(R.id.botonAceptarMail)
    Button botonMail;
    @BindView(R.id.botonCancelar)
    Button botonCancelar;

    private OnFragmentInteractionListener mListener;

    public IngresarMailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresar_mail, container, false);
        ButterKnife.bind(this, view);

        botonCancelar.setOnClickListener(v -> dismiss());
        botonMail.setOnClickListener(v -> {
            mListener.enMailIngresado(editTextMail.getText().toString());
            dismiss();
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
        void enMailIngresado(String mail);
    }
}
