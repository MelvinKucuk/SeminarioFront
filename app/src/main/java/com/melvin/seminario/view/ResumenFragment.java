package com.melvin.seminario.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.melvin.seminario.R;
import com.melvin.seminario.model.Foto;
import com.melvin.seminario.util.TemplatePDF;
import com.melvin.seminario.model.Conductor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResumenFragment extends Fragment {

    public static final String KEY_LICENCIA = "licencia";
    public static final String KEY_CEDULA = "cedula";
    public static final String KEY_POLIZA = "poliza";
    public static final String KEY_CHOQUE = "choque";
    public static final String KEY_TERCERO = "tercero";

    private OnFragmentInteractionListener mListener;
    private TemplatePDF pdf;

    @BindView(R.id.recyclerFotos)
    RecyclerView recyclerView;
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
    @BindView (R.id.editTextNombreTercero)
    EditText editTextNombreTercero;
    @BindView (R.id.editTextApellidoTercero)
    EditText editTextApellidoTercero;
    @BindView (R.id.editTextPaisTercero)
    EditText editTextPaisTercero;
    @BindView (R.id.editTextLicencia)
    EditText editTextLicencia;
    @BindView (R.id.editTextFechaTercero)
    EditText editTextFechaTercero;


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

            Conductor tercero = datos.getParcelable(KEY_TERCERO);

            List<Foto> fotos = new ArrayList<>();
            fotos.add(new Foto(pathLicencia, "Licencia"));
            fotos.add(new Foto(pathCedula, "Cedula"));
            fotos.add(new Foto(pathPoliza, "Poliza"));
            fotos.add(new Foto(pathChoque, "Choque"));

            FotosAdapter adapter = new FotosAdapter(fotos);

            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);

            editTextNombreTercero.setText(tercero.getNombre());
            editTextApellidoTercero.setText(tercero.getApellido());
            editTextPaisTercero.setText(tercero.getPais());
            editTextLicencia.setText(tercero.getLicencia());
            editTextFechaTercero.setText(tercero.getFechaNacimiento());





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
                pdf.addCampo(getString(R.string.nombre), editTextNombre.getText().toString());
                pdf.addCampo(getString(R.string.apellido), editTextApellido.getText().toString());
                pdf.addCampo(getString(R.string.pais), editTextPais.getText().toString());
                pdf.addCampo(getString(R.string.dni), editTextDni.getText().toString());
                pdf.addCampo(getString(R.string.fecha_de_nacimiento), editTextFecha.getText().toString());
                pdf.addCampo(getString(R.string.domicilio), editTextDomicilio.getText().toString());
                pdf.addCampo(getString(R.string.hint_mail), editTextMail.getText().toString());
                pdf.addCampo(getString(R.string.detalle), editTextDetalle.getText().toString());
                pdf.addImage(pathCedula);
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
