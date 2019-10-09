package com.melvin.seminario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


public class DatosOtroConductorFragment extends Fragment {


    public static final String KEY_IMAGE_PATH = "imagePath";

    private OnFragmentInteractionListener mListener;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextLicencia;
    private EditText editTextFechaNac;
    private EditText editTextPais;
    private ImageView imageView;

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

        String imagePath = getArguments().getString(KEY_IMAGE_PATH);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

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
