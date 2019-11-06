package com.melvin.seminario.view;

import android.content.Context;
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

public class CrearUsuarioMailFragment extends Fragment {

    @BindView(R.id.editTextMail)
    EditText editTextMail;
    @BindView(R.id.editTextContraseña)
    EditText editTextContrasena;
    @BindView(R.id.editTextRepetirContraseña)
    EditText editTextReptirContrasena;
    @BindView(R.id.cardViewSiguiente)
    CardView botonSiguiente;

    private OnFragmentInteractionListener mListener;

    public CrearUsuarioMailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_usuario_mail, container, false);
        ButterKnife.bind(this, view);
        botonSiguiente.setOnClickListener(v -> {
            String mail = editTextMail.getText().toString();
            String contrasena = editTextContrasena.getText().toString();
            String repetirContrasena = editTextReptirContrasena.getText().toString();

            if (!mail.isEmpty()){
                if (!contrasena.isEmpty()){
                    if (!repetirContrasena.isEmpty()){
                        if (contrasena.equals(repetirContrasena)){
                            mListener.enDatosCompletados(mail, contrasena);
                        } else {
                            Toast.makeText(getActivity(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "No puede estar la repeticion de la contraseña vacia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No puede estar la contraseña vacia", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "No puede estar el mail vacio", Toast.LENGTH_SHORT).show();
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
        void enDatosCompletados(String mail, String contrasena);
    }
}
