package com.melvin.seminario.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.melvin.seminario.R;
import com.melvin.seminario.model.Conductor;

public class FlujoAccidenteActivity extends AppCompatActivity
        implements  UbicacionFragment.OnFragmentInteractionListener,
                    CantidadVehiculosFragment.OnFragmentInteractionListener,
                    CamaraFragment.OnFragmentInteractionListener,
                    DatosOtroConductorFragment.OnFragmentInteractionListener,
                    ExitoConductorFragment.OnFragmentInteractionListener,
                    ExitoChoqueFragment.OnFragmentInteractionListener,
                    ExitoCedulaFragment.OnFragmentInteractionListener,
                    ResumenFragment.OnFragmentInteractionListener{

    private ProgressBar progressBar;
    private ImageView imageView;
    private String imagePathLicencia;
    private String imagePathChoque;
    private String imagePathCedula;
    private String imagePathPoliza;
    private ConstraintLayout rootLayout;
    private Conductor conductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujo_accidente);

        imageView = findViewById(R.id.imagen2);
//        progressBar = findViewById(R.id.progressBarFlujo);
//        animate(0, 250, 1000);

        rootLayout = findViewById(R.id.rootLayout);

        UbicacionFragment ubicacionFragment = new UbicacionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ubicacionFragment).commit();
    }

    private void animate(int percentageStart, int percentageEnd, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", percentageStart, percentageEnd);
        animator.setDuration(duration);
        animator.start();
    }


    @Override
    public void enSi() {
        CantidadVehiculosFragment fragment = new CantidadVehiculosFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
//        animate(250, 500, 1000);
    }

    @Override
    public void enInteraccion() {
        CamaraFragment fragment = new CamaraFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarDatosOtroConductor(String imagePath) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootLayout);
        constraintSet.clear(R.id.fragmentContainer, ConstraintSet.TOP);
        constraintSet.connect(R.id.fragmentContainer, ConstraintSet.TOP, R.id.toolbarSiniestros, ConstraintSet.BOTTOM);
        constraintSet.applyTo(rootLayout);
        DatosOtroConductorFragment fragment = new DatosOtroConductorFragment();
        imagePathLicencia = imagePath;
        Bundle datos = new Bundle();
        datos.putString(DatosOtroConductorFragment.KEY_IMAGE_PATH, imagePath);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
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
        this.conductor = conductor;
        ExitoConductorFragment fragment = new ExitoConductorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarDatosChoque(String imagePath) {
        imagePathChoque = imagePath;
        ExitoChoqueFragment fragment = new ExitoChoqueFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enExitoCompleto() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CHOQUE, true);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enChoqueConfirmado() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_CEDULA, true);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarDatosCedula(String imagePath) {
        imagePathCedula = imagePath;
        ExitoCedulaFragment fragment = new ExitoCedulaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enCelulaConfirmada() {
        CamaraFragment fragment = new CamaraFragment();
        Bundle datos = new Bundle();
        datos.putBoolean(CamaraFragment.KEY_POLIZA, true);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarDatosPoliza(String imagePath) {
        imagePathPoliza = imagePath;
        ResumenFragment fragment = new ResumenFragment();
        Bundle datos = new Bundle();
        datos.putString(ResumenFragment.KEY_CHOQUE, imagePathChoque);
        datos.putString(ResumenFragment.KEY_POLIZA, imagePathPoliza);
        datos.putString(ResumenFragment.KEY_CEDULA, imagePathCedula);
        datos.putString(ResumenFragment.KEY_LICENCIA, imagePathLicencia);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}
