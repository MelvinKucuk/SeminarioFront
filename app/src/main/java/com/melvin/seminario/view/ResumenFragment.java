package com.melvin.seminario.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.melvin.seminario.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResumenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ResumenFragment extends Fragment {

    public static final String KEY_LICENCIA = "licencia";
    public static final String KEY_CEDULA = "cedula";
    public static final String KEY_POLIZA = "poliza";
    public static final String KEY_CHOQUE = "choque";

    private OnFragmentInteractionListener mListener;

    private ImageView imagenLicencia;
    private ImageView imagenCedula;
    private ImageView imagenPoliza;
    private ImageView imagenChoque;
    private  String pathLicencia;

    public ResumenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        Bundle datos = getArguments();

        if (datos != null) {

            pathLicencia = datos.getString(KEY_LICENCIA);
            String pathCedula = datos.getString(KEY_CEDULA);
            String pathPoliza = datos.getString(KEY_POLIZA);
            String pathChoque = datos.getString(KEY_CHOQUE);

            imagenCedula = view.findViewById(R.id.imageViewCedula);
            imagenLicencia = view.findViewById(R.id.imageViewLicencia);
            imagenChoque = view.findViewById(R.id.imageViewChoque);
            imagenPoliza = view.findViewById(R.id.imageViewPoliza);

//            setPic(imagenCedula, pathCedula);
//            setPic(imagenPoliza, pathPoliza);
//            setPic(imagenChoque, pathChoque);
//            setPic(imagenLicencia, pathLicencia);
            Glide.with(view)
                    .load(new File(pathPoliza))
                    .into(imagenPoliza);
            Glide.with(view)
                    .load(new File(pathChoque))
                    .into(imagenChoque);
            Glide.with(view)
                    .load(new File(pathCedula))
                    .into(imagenCedula);
            Glide.with(view)
                    .load(new File(pathLicencia))
                    .into(imagenLicencia);



        }

        return view;
    }


    private void setPic(ImageView imageView, String currentPhotoPath) {
        // Get the dimensions of the View
        int targetW = 300;
        int targetH = 200;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
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
