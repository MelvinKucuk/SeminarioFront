package com.melvin.seminario;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class FlujoAccidenteActivity extends AppCompatActivity
        implements UbicacionFragment.OnFragmentInteractionListener, CantidadVehiculosFragment.OnFragmentInteractionListener,
        OtroConductorFragment.OnFragmentInteractionListener, DatosOtroConductorFragment.OnFragmentInteractionListener{

    private ProgressBar progressBar;
    private ImageView imageView;
    private String imagePathLicencia;
    private ConstraintLayout rootLayout;

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
        OtroConductorFragment fragment = new OtroConductorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarDatosOtroConductor(String imagePath) {
        DatosOtroConductorFragment fragment = new DatosOtroConductorFragment();
        imagePathLicencia = imagePath;
        Bundle datos = new Bundle();
        datos.putString(DatosOtroConductorFragment.KEY_IMAGE_PATH, imagePath);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
        rootLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

}
