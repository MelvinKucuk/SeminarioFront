package com.melvin.seminario;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class FlujoAccidenteActivity extends AppCompatActivity
        implements UbicacionFragment.OnFragmentInteractionListener, CantidadVehiculosFragment.OnFragmentInteractionListener,
        OtroConductorFragment.OnFragmentInteractionListener{

    public final static int CAMERA_ACTION = 101;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujo_accidente);

        progressBar = findViewById(R.id.progressBarFlujo);
        animate(0, 250, 1000);
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
        animate(250, 500, 1000);
    }

    @Override
    public void enInteraccion() {
        OtroConductorFragment fragment = new OtroConductorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void abrirCamara() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, CAMERA_ACTION);
    }


}
