package com.melvin.seminario.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.melvin.seminario.R;
import com.melvin.seminario.model.Conductor;

import java.io.File;


public class DatosOtroConductorFragment extends Fragment {


    public static final String KEY_IMAGE_PATH = "imagePath";

    private OnFragmentInteractionListener mListener;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextLicencia;
    private EditText editTextFechaNac;
    private EditText editTextPais;
    private ImageView imageView;
    private CardView botonSiguiente;

    public DatosOtroConductorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_otro_conductor, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido = view.findViewById(R.id.editTextApellido);
        editTextLicencia = view.findViewById(R.id.editTextLicencia);
        editTextFechaNac = view.findViewById(R.id.editTextFecha);
        editTextPais = view.findViewById(R.id.editTextPais);
        imageView = view.findViewById(R.id.imageViewLicencia);
        botonSiguiente = view.findViewById(R.id.cardViewSiguiente);

        botonSiguiente.setOnClickListener(v -> {
            Conductor conductor = new Conductor.Builder()
                                        .setNombre(editTextNombre.getText().toString())
                                        .setApellido(editTextApellido.getText().toString())
                                        .setLicencia(editTextLicencia.getText().toString())
                                        .setFechaNacimiento(editTextFechaNac.getText().toString())
                                        .setPais(editTextPais.getText().toString())
                                        .build();
            mListener.enDatosConfirmados(conductor);
        });

        String imagePath = getArguments().getString(KEY_IMAGE_PATH);
        Glide.with(view)
                .load(new File(imagePath))
                .into(imageView);

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
        void enDatosConfirmados(Conductor conductor);
    }
}
