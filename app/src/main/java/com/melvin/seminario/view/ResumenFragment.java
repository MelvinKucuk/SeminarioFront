package com.melvin.seminario.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.melvin.seminario.R;
import com.melvin.seminario.Util.TemplatePDF;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResumenFragment extends Fragment {

    public static final String KEY_LICENCIA = "licencia";
    public static final String KEY_CEDULA = "cedula";
    public static final String KEY_POLIZA = "poliza";
    public static final String KEY_CHOQUE = "choque";

    private OnFragmentInteractionListener mListener;
    private TemplatePDF pdf;

    @BindView(R.id.imageViewCedula)
    ImageView imagenCedula;
    @BindView(R.id.imageViewLicencia)
    ImageView imagenLicencia;
    @BindView(R.id.imageViewPoliza)
    ImageView imagenPoliza;
    @BindView(R.id.imageViewChoque)
    ImageView imagenChoque;
    @BindView (R.id.editTextNombre)
    EditText editTextNombre;
    @BindView (R.id.editTextApellido)
    EditText editTextApellido;
    @BindView (R.id.editTextDni)
    EditText editTextDni;
    @BindView (R.id.editTextFecha)
    EditText editTextFecha;
    @BindView (R.id.editTextPais)
    EditText editTextPais;
    @BindView (R.id.editTextDomicilio)
    EditText editTextDomicilio;
    @BindView (R.id.editTextMail)
    EditText editTextMail;
    @BindView (R.id.cardViewSiguiente)
    CardView botonSiguiente;
    @BindView (R.id.cardViewEditar)
    CardView botonEditar;
    @BindView (R.id.editTextDetalle)
    EditText editTextDetalle;


    private  String pathLicencia;


    public ResumenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        ButterKnife.bind(this, view);

        Bundle datos = getArguments();

        if (datos != null) {

            pathLicencia = datos.getString(KEY_LICENCIA);
            String pathCedula = datos.getString(KEY_CEDULA);
            String pathPoliza = datos.getString(KEY_POLIZA);
            String pathChoque = datos.getString(KEY_CHOQUE);

            File filePoliza = new File(pathPoliza);
            File fileChoque = new File(pathChoque);
            File fileCedula = new File(pathCedula);
            File fileLicencia = new File(pathLicencia);

            Glide.with(view)
                    .load(filePoliza)
                    .into(imagenPoliza);
            Glide.with(view)
                    .load(fileChoque)
                    .into(imagenChoque);
            Glide.with(view)
                    .load(fileCedula)
                    .into(imagenCedula);
            Glide.with(view)
                    .load(fileLicencia)
                    .into(imagenLicencia);


            botonSiguiente.setOnClickListener(v -> {
                Conductor conductor = new Conductor.Builder()
                        .setNombre(editTextNombre.getText().toString())
                        .setApellido(editTextApellido.getText().toString())
                        .setEmail(editTextMail.getText().toString())
                        .setDetalle(editTextDetalle.getText().toString())
                        .setDni(editTextDni.getText().toString())
                        .setPais(editTextPais.getText().toString())
                        .setFechaNacimiento(editTextFecha.getText().toString())
                        .setDomicilio(editTextDomicilio.getText().toString())
                        .build();
                mListener.enResumenConfirmado(conductor);
            });

            pdf = new TemplatePDF(getActivity());
            try{
                pdf.openDocument();
                pdf.addMetaData("Denuncia", "Denuncia", "Crash App");
                pdf.addCampo("Nombre", editTextNombre.getText().toString());
                pdf.addCampo("Apellido", editTextApellido.getText().toString());
                pdf.addCampo("Detalle", editTextDetalle.getText().toString());
                pdf.closeDocument();
            }catch (Exception e){
                e.printStackTrace();
            }



            botonEditar.setOnClickListener(v -> pdf.viewPdf(getActivity()));






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
        void enResumenConfirmado(Conductor mail);
    }
}
