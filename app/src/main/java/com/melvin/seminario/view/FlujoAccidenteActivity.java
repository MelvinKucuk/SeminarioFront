package com.melvin.seminario.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.UsuarioController;
import com.melvin.seminario.dao.DaoInternetUsuarios;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.Foto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
                    UbicacionManualFragment.OnFragmentInteractionListener,
                    TieneDatosTerceroFragment.OnFragmentInteractionListener,
                    DetalleFragment.OnFragmentInteractionListener,
                    InformacionFragment.OnFragmentInteractionListener,
                    DatosFragment.OnFragmentInteractionListener{

    private String user;
    private ImageView imageView;
    private Foto fotoLicencia;
    private Foto fotoChoque;
    private Foto fotoCedula;
    private Foto fotoPoliza;
    private Foto fotoDanos;
    private ConstraintLayout rootLayout;
    private Conductor conductor;
    private String fecha;
    private String hora;
    private String direccion;
    private Denuncia denuncia;
    private String detalle;
    private ArrayList<String> filePathsLicencia = new ArrayList<>();
    private ArrayList<String> filePathsChoque = new ArrayList<>();
    private ArrayList<String> filePathsExtras = new ArrayList<>();
    private ArrayList<String> filePathsDanos = new ArrayList<>();

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
        cargarFragment(fragment);
    }

    @Override
    public void enUbicacionNo() {
        UbicacionManualFragment fragment = new UbicacionManualFragment();
        cargarFragment(fragment);
    }



    @Override
    public void enUbicacionManualConfirmada(String fecha, String hora, String direccion, boolean esEsquina, boolean esDobleMano) {
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        denuncia.setFecha(this.fecha);
        denuncia.setHora(this.hora);
        denuncia.setCalle(this.direccion);
        denuncia.setEsEsquina(esEsquina);
        denuncia.setEsDobleMano(esDobleMano);
        imageView.setImageResource(R.drawable.ic_progreso_2);
        TieneDatosTerceroFragment fragment = new TieneDatosTerceroFragment();
        cargarFragment(fragment);
    }

    @Override
    public void enUbicacionConfirmada(double longitude, double latitude) {
        TieneDatosTerceroFragment fragment = new TieneDatosTerceroFragment();
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
        denuncia.setFecha(this.fecha);
        denuncia.setHora(this.hora);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = "";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        } catch (Exception e){
            e.printStackTrace();
        }

        String calle = "";
        String altura = "";

        if (!address.isEmpty()) {

            String[] aux = address.split(",");
            String[] aux2 = aux[0].split(" ");

            if (aux2.length == 3) {
                calle = aux2[0] + " " + aux2[1];
                altura = aux2[2];
            } else {
                calle = aux2[0];
                altura = aux2[1];
            }

        }

        denuncia.setCalle(calle);
        denuncia.setAltura(altura);
        imageView.setImageResource(R.drawable.ic_progreso_2);
        cargarFragment(fragment);
    }

    @Override
    public void omitirFotoRegistro() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void omitirFotoChoque() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_DANOS, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void omitirFotoCedula() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_POLIZA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void omitirFotoPoliza() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CHOQUE, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void enOtroVehiculoSi() {
        TieneDatosTerceroFragment fragment = new TieneDatosTerceroFragment();
        cargarFragment(fragment);
    }

    @Override
    public void enTieneDatosTercero() {
        imageView.setImageResource(R.drawable.ic_progreso_3);
        CamaraFragment fragment = new CamaraFragment();
        cargarFragment(fragment);
    }

    @Override
    public void enNoTieneDatosTercero() {
        InformacionFragment fragment = new InformacionFragment();
        cargarFragment(fragment);
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
        filePathsLicencia.add(imagePath);
        denuncia.setImagePathsLicencia(filePathsLicencia);
        Bundle datos = new Bundle();
        datos.putString(DatosOtroConductorFragment.KEY_IMAGE_PATH, imagePath);
        fragment.setArguments(datos);
        cargarFragment(fragment);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void pasarFotoExtraLicencia(String imagePath) {
        filePathsLicencia.add(imagePath);
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
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
        cargarFragment(fragment);
    }

    @Override
    public void pasarDatosChoque(String imagePath) {
        fotoChoque = new Foto(imagePath, "Choque");
        filePathsChoque.add(imagePath);
        denuncia.setImagePathsChoque(filePathsChoque);
        ExitoChoqueFragment fragment = new ExitoChoqueFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void enExitoConductor() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void enAgregarMasFotosConductor() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_LICENCIA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void enChoqueConfirmado() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_DANOS, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void pasarDatosCedula(String imagePath) {
        fotoCedula = new Foto(imagePath, "Cedula");
        denuncia.setImagePathCedula(this.fotoCedula.getFilepath());
        ExitoCedulaFragment fragment = new ExitoCedulaFragment();
        cargarFragment(fragment);
    }

    @Override
    public void enCelulaConfirmada() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_POLIZA, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void pasarDatosPoliza(String imagePath) {
        fotoPoliza = new Foto(imagePath, "Poiliza");
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CHOQUE, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);

    }

    @Override
    public void pasarDatosDanos(String imagePath) {
        fotoDanos = new Foto(imagePath, "Daños");
        filePathsDanos.add(imagePath);
        denuncia.setImagePathsExtras(filePathsDanos);
        DetalleFragment fragment = new DetalleFragment();
        cargarFragment(fragment);
        imageView.setImageResource(R.drawable.ic_progreso_4);
    }

    @Override
    public void omitirFotoDanos() {
        DetalleFragment fragment = new DetalleFragment();
        cargarFragment(fragment);
        imageView.setImageResource(R.drawable.ic_progreso_4);
    }

    @Override
    public void enDetalleCompletado(String detalle) {
        this.detalle = detalle;
        terminarProceso();
    }

    @Override
    public void enDetalleOmitir() {
        terminarProceso();
    }

    @Override
    public void enOtroVehiculoNo() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_DANOS, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
        imageView.setImageResource(R.drawable.ic_progreso_3);
    }

    private void terminarProceso() {
        imageView.setImageResource(R.drawable.ic_progreso_5);
        if (fotoPoliza != null)
            denuncia.setImagePathPoliza(this.fotoPoliza.getFilepath());
        ResumenFragment fragment = new ResumenFragment();
        Bundle datos = new Bundle();
        if (!filePathsChoque.isEmpty())
            datos.putStringArrayList(ResumenFragment.KEY_CHOQUE, filePathsChoque);
        if (!filePathsLicencia.isEmpty())
            datos.putStringArrayList(ResumenFragment.KEY_LICENCIA, filePathsLicencia);
        if (fotoPoliza != null)
            datos.putString(ResumenFragment.KEY_POLIZA, fotoPoliza.getFilepath());
        if (fotoCedula != null)
            datos.putString(ResumenFragment.KEY_CEDULA, fotoCedula.getFilepath());
        if (!filePathsDanos.isEmpty())
            datos.putStringArrayList(ResumenFragment.KEY_DANOS, filePathsDanos);
        datos.putParcelable(ResumenFragment.KEY_TERCERO, denuncia.getTercero());
        if (detalle != null)
            datos.putString(ResumenFragment.KEY_DETALLE, detalle);
        if (!filePathsExtras.isEmpty())
            datos.putStringArrayList(ResumenFragment.KEY_EXTRAS, filePathsExtras);
        fragment.setArguments(datos);
        cargarFragment(fragment);

    }

    @Override
    public void enResumenConfirmado(Conductor conductor, Conductor tercero) {
        ExitoResumenFragment fragment = new ExitoResumenFragment();
        cargarFragment(fragment);
        denuncia.setAsegurado(conductor);
        denuncia.setTercero(tercero);
        new DaoInternetUsuarios().mandarMail(denuncia);
    }

    @Override
    public void enNotieneInformacion() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_DANOS, true);
        fragment.setArguments(datos);
        cargarFragment(fragment);
        imageView.setImageResource(R.drawable.ic_progreso_3);
    }

    @Override
    public void enSiTieneInformacion() {
        DatosFragment fragment = new DatosFragment();
        cargarFragment(fragment);
    }

    @Override
    public void enDatosIngresados(String datosTexto) {
        denuncia.setDatos(datosTexto);
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_DANOS, true);
        imageView.setImageResource(R.drawable.ic_progreso_3);
        fragment.setArguments(datos);
        cargarFragment(fragment);
    }

    @Override
    public void enReintentar() {
        onBackPressed();
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootLayout);
        constraintSet.clear(R.id.fragmentContainer, ConstraintSet.TOP);
        constraintSet.connect(R.id.fragmentContainer, ConstraintSet.TOP, R.id.imagen2, ConstraintSet.BOTTOM);
        constraintSet.applyTo(rootLayout);
        imageView.setVisibility(View.VISIBLE);
        filePathsLicencia = new ArrayList<>();
    }

    @Override
    public void enExitoResumen() {
        finish();
    }

    private void cargarFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}
