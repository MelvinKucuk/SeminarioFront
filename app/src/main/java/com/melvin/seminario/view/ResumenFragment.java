package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.DenunciaController;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.Foto;
import com.melvin.seminario.util.TemplatePDF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResumenFragment extends Fragment {

    public static final String KEY_LICENCIA = "licencia";
    public static final String KEY_CEDULA = "cedula";
    public static final String KEY_POLIZA = "poliza";
    public static final String KEY_CHOQUE = "choque";
    public static final String KEY_DANOS = "danos";
    public static final String KEY_TERCERO = "tercero";
    public static final String KEY_DETALLE = "detalle";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_ID = "id";

    private OnFragmentInteractionListener mListener;
    private TemplatePDF pdf;
    private String user;

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
    @BindView (R.id.buttonSiguiente)
    Button botonSiguiente;
    @BindView (R.id.buttonEditar)
    Button botonEditar;
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
    private String detalle;
    private Denuncia denuncia;

    public final Calendar c = Calendar.getInstance();
    private final int dia = c.get(Calendar.DAY_OF_MONTH);
    private final int mes = c.get(Calendar.MONTH);
    private final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";


    public ResumenFragment() {
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        ButterKnife.bind(this, view);
        String id;

        editTextFecha.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month+1;
                    String diaFormateado = (dayOfMonth < 10)? CERO + dayOfMonth : String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + mesActual : String.valueOf(mesActual);
                    String fecha = diaFormateado + BARRA + mesFormateado + BARRA + year;
                    editTextFecha.setText(fecha);

                }
            }, anio, mes, dia);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        editTextFechaTercero.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month+1;
                    String diaFormateado = (dayOfMonth < 10)? CERO + dayOfMonth : String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + mesActual : String.valueOf(mesActual);
                    String fecha = diaFormateado + BARRA + mesFormateado + BARRA + year;
                    editTextFechaTercero.setText(fecha);

                }
            }, anio, mes, dia);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        user = getActivity().getSharedPreferences(MainActivity.USER_PREFERENCES, MainActivity.MODE_PRIVATE).getString(MainActivity.KEY_USER, "");
        new UsuarioController().recuperarUsuario(user,
                usuario -> {
                    editTextNombre.setText(usuario.getNombre());
                    editTextApellido.setText(usuario.getApellido());
                    editTextDni.setText(usuario.getDni());
                    editTextFecha.setText(usuario.getFechaNacimeinto());
                    editTextPais.setText(usuario.getPais());
                    editTextMail.setText(usuario.getUsername());
                    editTextDomicilio.setText(usuario.getDomicilio());
                });

        Bundle datos = getArguments();

        if (datos != null) {
            id = datos.getString(KEY_ID);
            if (id != null) {

                botonSiguiente.setText("Guardar");

                new DenunciaController().obtenerDenuciaPorId(id,
                        denuncia -> {
                            this.denuncia = denuncia;
                            if (denuncia.getTercero() != null) {
                                if (denuncia.getTercero().getNombre() != null)
                                    editTextNombreTercero.setText(denuncia.getTercero().getNombre());
                                if (denuncia.getTercero().getApellido() != null)
                                    editTextApellidoTercero.setText(denuncia.getTercero().getApellido());
                                if (denuncia.getTercero().getFechaNacimiento() != null)
                                    editTextFechaTercero.setText(denuncia.getTercero().getFechaNacimiento());
                                if (denuncia.getTercero().getPais() != null)
                                    editTextPaisTercero.setText(denuncia.getTercero().getPais());
                                if (denuncia.getTercero().getLicencia() != null)
                                    editTextLicencia.setText(denuncia.getTercero().getLicencia());
                            }
                            if (denuncia.getAsegurado().getDetalle() != null)
                                editTextDetalle.setText(denuncia.getAsegurado().getDetalle());
                            List<Foto> fotos = new ArrayList<>();
                            if (denuncia.getImagePathCedula() != null)
                                fotos.add(new Foto(denuncia.getImagePathCedula(), "Cedula"));
                            if (denuncia.getImagePathPoliza() != null)
                                fotos.add(new Foto(denuncia.getImagePathPoliza(), "Poliza"));
                            if (!denuncia.getImagePathsChoque().isEmpty()) {
                                for (String filepath: denuncia.getImagePathsChoque()) {
                                    fotos.add(new Foto(filepath, "Choque"));
                                }
                            }
                            if (!denuncia.getImagePathsExtras().isEmpty()) {
                                for (String filepath : denuncia.getImagePathsExtras()) {
                                    fotos.add(new Foto(filepath, "Extras"));
                                }
                            }
                            if (!denuncia.getImagePathsLicencia().isEmpty()) {
                                for (String filepath : denuncia.getImagePathsLicencia()) {
                                    fotos.add(new Foto(filepath, "Licencia"));
                                }
                            }

                            FotosAdapter adapter = new FotosAdapter(fotos);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);

                        });

                botonSiguiente.setOnClickListener(v -> {
                    Conductor terceroConductor = new Conductor.Builder()
                            .setNombre(editTextNombreTercero.getText().toString())
                            .setApellido(editTextApellidoTercero.getText().toString())
                            .setPais(editTextPaisTercero.getText().toString())
                            .setLicencia(editTextLicencia.getText().toString())
                            .setFechaNacimiento(editTextFechaTercero.getText().toString())
                            .build();
                    denuncia.setTercero(terceroConductor);
                    denuncia.getAsegurado().setDetalle(editTextDetalle.getText().toString());
                    mListener.enDenunciaModificada(denuncia);
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




            } else {
                List<String> pathsLicencia = datos.getStringArrayList(KEY_LICENCIA);
                String pathCedula = datos.getString(KEY_CEDULA);
                String pathPoliza = datos.getString(KEY_POLIZA);
                List<String> pathsChoque = datos.getStringArrayList(KEY_CHOQUE);
                List<String> pathsDano = datos.getStringArrayList(KEY_DANOS);
                detalle = datos.getString(KEY_DETALLE);

                Conductor tercero = datos.getParcelable(KEY_TERCERO);

                List<Foto> fotos = new ArrayList<>();
                if (pathsLicencia != null) {
                    for (String filePath : pathsLicencia)
                        fotos.add(new Foto(filePath, "Licencia"));
                }
                if (pathCedula != null)
                    fotos.add(new Foto(pathCedula, "Cedula"));
                if (pathPoliza != null)
                    fotos.add(new Foto(pathPoliza, "Poliza"));
                if (pathsChoque != null) {
                    for (String filePath : pathsChoque)
                        fotos.add(new Foto(filePath, "Choque"));
                }
                if (pathsDano != null) {
                    for (String filePath : pathsDano)
                        fotos.add(new Foto(filePath, "Daños"));
                }

                FotosAdapter adapter = new FotosAdapter(fotos);

                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);

                if (tercero != null) {
                    editTextNombreTercero.setText(tercero.getNombre());
                    editTextApellidoTercero.setText(tercero.getApellido());
                    editTextPaisTercero.setText(tercero.getPais());
                    editTextLicencia.setText(tercero.getLicencia());
                    editTextFechaTercero.setText(tercero.getFechaNacimiento());
                }
                if (detalle != null)
                    editTextDetalle.setText(detalle);


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
                    Conductor terceroConductor = new Conductor.Builder()
                                .setNombre(editTextNombreTercero.getText().toString())
                                .setApellido(editTextApellidoTercero.getText().toString())
                                .setPais(editTextPaisTercero.getText().toString())
                                .setLicencia(editTextLicencia.getText().toString())
                                .setFechaNacimiento(editTextFechaTercero.getText().toString())
                            .build();
                    mListener.enResumenConfirmado(conductor, terceroConductor);
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


                botonEditar.setOnClickListener(v -> {
                    pdf = new TemplatePDF(getActivity());
                    try {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    pdf.viewPdf(getActivity());

                });
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
        void enResumenConfirmado(Conductor asegurado, Conductor tercero);
        void enDenunciaModificada(Denuncia denuncia);
    }
}
