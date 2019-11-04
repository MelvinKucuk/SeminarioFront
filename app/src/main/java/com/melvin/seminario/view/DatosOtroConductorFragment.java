package com.melvin.seminario.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.melvin.seminario.R;
import com.melvin.seminario.model.Conductor;

import java.io.File;
import java.util.List;


public class DatosOtroConductorFragment extends Fragment {


    public static final String KEY_IMAGE_PATH = "imagePath";

    private OnFragmentInteractionListener mListener;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextLicencia;
    private EditText editTextFechaNac;
    private EditText editTextPais;

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
        ImageView imageView = view.findViewById(R.id.imageViewLicencia);
        CardView botonSiguiente = view.findViewById(R.id.cardViewSiguiente);
        CardView botonReintentar = view.findViewById(R.id.cardViewReintentar);

        botonSiguiente.setOnClickListener(v -> {

            String nombre = editTextNombre.getText().toString();
            String apellido = editTextApellido.getText().toString();
            String licencia = editTextLicencia.getText().toString();
            String fechaNacimiento = editTextFechaNac.getText().toString();
            String pais = editTextPais.getText().toString();

            if (!nombre.isEmpty()){
                if (!apellido.isEmpty()){
                    if (!licencia.isEmpty()){
                        if (!fechaNacimiento.isEmpty()){
                            if (!pais.isEmpty()){
                                Conductor conductor = new Conductor.Builder()
                                        .setNombre(nombre)
                                        .setApellido(apellido)
                                        .setLicencia(licencia)
                                        .setFechaNacimiento(fechaNacimiento)
                                        .setPais(pais)
                                        .build();
                                mListener.enDatosConfirmados(conductor);
                            } else {
                                Toast.makeText(getActivity(), "Falta ingresar el Pais", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Falta ingresar la Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Falta ingresar la Licencia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Falta ingresar el Apellido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Falta ingresar el Nombre", Toast.LENGTH_SHORT).show();
            }
        });

        botonReintentar.setOnClickListener(v -> mListener.enReintentar());



        String imagePath = getArguments().getString(KEY_IMAGE_PATH);
        File fileImage = new File(imagePath);
        Glide.with(view)
                .load(fileImage)
                .into(imageView);

        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_PDF417)
                        .build();

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
        .getVisionBarcodeDetector(options);
        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        for (FirebaseVisionBarcode barcode: barcodes) {

                            String rawValue = barcode.getRawValue();
                            if (rawValue.contains("\r\n")) {

                                String[] parts = rawValue.split("\r\n");
                                String licencia = parts[1];
                                String nombre = parts[3];
                                String apellido = parts[4];
                                String fechaNacimiento = parts[5];
                                String pais = parts[6];

                                editTextNombre.setText(nombre);
                                editTextApellido.setText(apellido);
                                editTextLicencia.setText(licencia);
                                editTextFechaNac.setText(fechaNacimiento);
                                editTextPais.setText(pais);
                            } else {
                                Toast.makeText(getActivity(), "No se puede leer esta licencia", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (barcodes.isEmpty())
                            Toast.makeText(getActivity(), "No se leyo correctamente la foto", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "No se leyo correctamente la foto", Toast.LENGTH_SHORT).show();
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
        void enDatosConfirmados(Conductor conductor);
        void enReintentar();
    }
}
