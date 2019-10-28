package com.melvin.seminario.view;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.melvin.seminario.R;
import com.melvin.seminario.dao.DaoInternetUsuarios;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.Foto;

import java.util.Calendar;

public class FlujoAccidenteActivity extends AppCompatActivity
        implements  UbicacionFragment.OnFragmentInteractionListener,
                    CantidadVehiculosFragment.OnFragmentInteractionListener,
                    CamaraFragment.OnFragmentInteractionListener,
                    DatosOtroConductorFragment.OnFragmentInteractionListener,
                    ExitoConductorFragment.OnFragmentInteractionListener,
                    ExitoChoqueFragment.OnFragmentInteractionListener,
                    ExitoCedulaFragment.OnFragmentInteractionListener,
                    ResumenFragment.OnFragmentInteractionListener,
                    ExitoResumenFragment.OnFragmentInteractionListener,
                    MapsFragment.OnFragmentInteractionListener,
                    UbicacionManualFragment.OnFragmentInteractionListener{

    private String user;
    private ImageView imageView;
    private Foto fotoLicencia;
    private Foto fotoChoque;
    private Foto fotoCedula;
    private Foto fotoPoliza;
    private ConstraintLayout rootLayout;
    private Conductor conductor;
    private double latitude;
    private double longitude;
    private String fecha;
    private String hora;
    private String direccion;
    private Denuncia denuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujo_accidente);

        user = getSharedPreferences(MainActivity.USER_PREFERENCES, MODE_PRIVATE).getString(MainActivity.KEY_USER, "");


        imageView = findViewById(R.id.imagen2);

        denuncia = new Denuncia();

        rootLayout = findViewById(R.id.rootLayout);

        UbicacionFragment ubicacionFragment = new UbicacionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ubicacionFragment).commit();


    }



    @Override
    public void enUbicacionSi() {
        MapsFragment fragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enUbicacionNo() {
        UbicacionManualFragment fragment = new UbicacionManualFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enUbicacionManualConfirmada(String fecha, String hora, String direccion) {
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        denuncia.setFecha(this.fecha);
        denuncia.setHora(this.hora);
        denuncia.setDireccion(this.direccion);
        CantidadVehiculosFragment fragment = new CantidadVehiculosFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enUbicacionConfirmada(double longitude, double latitude) {
        CantidadVehiculosFragment fragment = new CantidadVehiculosFragment();
        Calendar ahora = Calendar.getInstance();
        String amPm;
        String hora;
        if (ahora.get(Calendar.HOUR_OF_DAY) > 12){
            amPm = "PM";
            hora = (ahora.get(Calendar.HOUR_OF_DAY)-12) + ":" + ahora.get(Calendar.MINUTE) + " " + amPm;
        } else {
            amPm = "AM";
            hora = ahora.get(Calendar.HOUR_OF_DAY) + ":" + ahora.get(Calendar.MINUTE) + " " + amPm;
        }
        this.hora = hora;
        this.fecha = ahora.get(Calendar.DAY_OF_MONTH) + "/" + (ahora.get(Calendar.MONTH)+1) + "/" + ahora.get(Calendar.YEAR);
        this.longitude = longitude;
        this.latitude = latitude;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void omitirFotoRegistro() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CHOQUE, true);
        imageView.setImageResource(R.drawable.ic_progreso_2);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void omitirFotoChoque() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        imageView.setImageResource(R.drawable.ic_progreso_3);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void omitirFotoCedula() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_POLIZA, true);
        imageView.setImageResource(R.drawable.ic_progreso_4);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void omitirFotoPoliza() {
        terminarProceso(null);
    }

    @Override
    public void enOtroVehiculoSi() {
        CamaraFragment fragment = new CamaraFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void pasarDatosOtroConductor(String imagePath) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootLayout);
        constraintSet.clear(R.id.fragmentContainer, ConstraintSet.TOP);
        constraintSet.connect(R.id.fragmentContainer, ConstraintSet.TOP, R.id.toolbarSiniestros, ConstraintSet.BOTTOM);
        constraintSet.applyTo(rootLayout);
        imageView.setVisibility(View.GONE);
        DatosOtroConductorFragment fragment = new DatosOtroConductorFragment();
        fotoLicencia = new Foto(imagePath, "Licencia");
        denuncia.setImagePathsLicencia(new String[]{imagePath});
        Bundle datos = new Bundle();
        datos.putString(DatosOtroConductorFragment.KEY_IMAGE_PATH, imagePath);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        rootLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void enDatosConfirmados(Conductor conductor) {
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootLayout);
        constraintSet.clear(R.id.fragmentContainer, ConstraintSet.TOP);
        constraintSet.connect(R.id.fragmentContainer, ConstraintSet.TOP, R.id.imagen2, ConstraintSet.BOTTOM);
        constraintSet.applyTo(rootLayout);
        imageView.setVisibility(View.VISIBLE);
        this.conductor = conductor;
        denuncia.setTercero(this.conductor);
        ExitoConductorFragment fragment = new ExitoConductorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void pasarDatosChoque(String imagePath) {
        fotoChoque = new Foto(imagePath, "Choque");
        denuncia.setImagePathsChoque(new String[]{imagePath});
        ExitoChoqueFragment fragment = new ExitoChoqueFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enExitoConductor() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CHOQUE, true);
        imageView.setImageResource(R.drawable.ic_progreso_2);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enChoqueConfirmado() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        imageView.setImageResource(R.drawable.ic_progreso_3);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void pasarDatosCedula(String imagePath) {
        fotoCedula = new Foto(imagePath, "Cedula");
        denuncia.setImagePathCedula(this.fotoCedula.getFilepath());
        ExitoCedulaFragment fragment = new ExitoCedulaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enCelulaConfirmada() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_POLIZA, true);
        imageView.setImageResource(R.drawable.ic_progreso_4);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void pasarDatosPoliza(String imagePath) {
        terminarProceso(imagePath);
    }

    private void terminarProceso(String  imagePath) {
        if (fotoChoque != null && fotoCedula != null && fotoLicencia != null && fotoLicencia != null) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootLayout);
            constraintSet.clear(R.id.fragmentContainer, ConstraintSet.TOP);
            constraintSet.connect(R.id.fragmentContainer, ConstraintSet.TOP, R.id.toolbarSiniestros, ConstraintSet.BOTTOM);
            constraintSet.applyTo(rootLayout);
            fotoPoliza = new Foto(imagePath, "Poliza");
            denuncia.setImagePathPoliza(this.fotoPoliza.getFilepath());
            imageView.setVisibility(View.GONE);
            ResumenFragment fragment = new ResumenFragment();
            Bundle datos = new Bundle();
            datos.putString(ResumenFragment.KEY_CHOQUE, fotoChoque.getFilepath());
            datos.putString(ResumenFragment.KEY_POLIZA, fotoPoliza.getFilepath());
            datos.putString(ResumenFragment.KEY_CEDULA, fotoCedula.getFilepath());
            datos.putString(ResumenFragment.KEY_LICENCIA, fotoLicencia.getFilepath());
            datos.putParcelable(ResumenFragment.KEY_TERCERO, denuncia.getTercero());
            fragment.setArguments(datos);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        } else {
            ExitoResumenFragment fragment = new ExitoResumenFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void enResumenConfirmado(Conductor conductor) {
        ExitoResumenFragment fragment = new ExitoResumenFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
        denuncia.setAsegurado(conductor);
        new DaoInternetUsuarios().mandarMail(denuncia);
    }

    @Override
    public void enReintentar() {
        onBackPressed();
    }

    @Override
    public void enExitoResumen() {
        finish();
    }
}
