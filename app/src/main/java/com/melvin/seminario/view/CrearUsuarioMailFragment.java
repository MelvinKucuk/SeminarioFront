package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @BindView(R.id.buttonSiguiente)
    Button botonSiguiente;

    private OnFragmentInteractionListener mListener;

    public CrearUsuarioMailFragment() {
    }


    @SuppressLint("ClickableViewAccessibility")
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
        void enDatosCompletados(String mail, String contrasena);
    }
}
