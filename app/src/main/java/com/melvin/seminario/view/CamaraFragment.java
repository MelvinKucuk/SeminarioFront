package com.melvin.seminario.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melvin.seminario.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CamaraFragment extends Fragment {

    public static final String KEY_CHOQUE = "choque";
    public static final String KEY_CEDULA = "cedula";
    public static final String KEY_POLIZA = "poliza";
    public static final String KEY_DANOS = "danos";
    public static final String KEY_LICENCIA = "licencia";


    public static final int CAMERA_ACTION = 200;
    private OnFragmentInteractionListener mListener;
    private ImageView image;
    private String currentPhotoPath;
    private TextView textView;
    private boolean esChoque;
    private boolean esCedula;
    private boolean esPoliza;
    private boolean esDanos;
    private boolean esLicenciaExtra;
    @BindView(R.id.cardViewOmitir)
    CardView botonOmitir;

    public CamaraFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camara, container, false);
        ButterKnife.bind(this, view);

        CardView botonAbrirCamara = view.findViewById(R.id.cardViewCamara);
        botonAbrirCamara.setOnClickListener(v -> dipatchTakePicture());

        image = view.findViewById(R.id.licenciaIcono);
        textView  = view.findViewById(R.id.textViewCamara);

        botonOmitir.setOnClickListener(v -> mListener.omitirFotoRegistro());


        if (getArguments() != null) {
            esChoque = getArguments().getBoolean(KEY_CHOQUE);
            esCedula = getArguments().getBoolean(KEY_CEDULA);
            esPoliza = getArguments().getBoolean(KEY_POLIZA);
            esDanos = getArguments().getBoolean(KEY_DANOS);
            esLicenciaExtra = getArguments().getBoolean(KEY_LICENCIA);
            if (esChoque){
                textView.setText(getString(R.string.text_choque));
                image.setImageResource(R.drawable.ic_auto);
                botonOmitir.setOnClickListener(v -> mListener.omitirFotoChoque());
            }
            if (esCedula){
                textView.setText(getString(R.string.text_cedula));
                image.setImageResource(R.drawable.ic_cedula);
                botonOmitir.setOnClickListener(v -> mListener.omitirFotoCedula());
            }
            if (esPoliza){
                textView.setText(getString(R.string.text_poliza));
                image.setImageResource(R.drawable.ic_poliza);
                botonOmitir.setOnClickListener(v -> mListener.omitirFotoPoliza());
            }
            if (esDanos){
                textView.setText(getString(R.string.text_danos));
                image.setImageResource(R.drawable.ic_fotos);
                botonOmitir.setOnClickListener(v -> mListener.omitirFotoDanos());
            }
            if (esLicenciaExtra){
                textView.setText(getString(R.string.text_licencia));
            }
        }



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
        void pasarDatosOtroConductor(String imagePath);
        void pasarDatosChoque(String imagePath);
        void pasarDatosCedula(String imagePath);
        void pasarDatosPoliza(String imagePath);
        void pasarDatosDanos(String imagePath);
        void pasarFotoExtraLicencia(String imagePath);
        void omitirFotoRegistro();
        void omitirFotoChoque();
        void omitirFotoCedula();
        void omitirFotoPoliza();
        void omitirFotoDanos();
    }

    private void dipatchTakePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;

        try{
            photoFile = createImageFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "com.melvin.seminario",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            CamaraFragment.this.startActivityForResult(intent, CAMERA_ACTION);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_ACTION) {

                if (esChoque){
                    mListener.pasarDatosChoque(currentPhotoPath);
                } else if (esCedula) {
                    mListener.pasarDatosCedula(currentPhotoPath);
                } else if (esPoliza){
                    mListener.pasarDatosPoliza(currentPhotoPath);
                } else if (esDanos) {
                    mListener.pasarDatosDanos(currentPhotoPath);
                } else if (esLicenciaExtra){
                    mListener.pasarFotoExtraLicencia(currentPhotoPath);
                } else {
                    mListener.pasarDatosOtroConductor(currentPhotoPath);
                }

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
