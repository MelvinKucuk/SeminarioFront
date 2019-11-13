package com.melvin.seminario.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.melvin.seminario.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class MapsFragment extends Fragment implements LocationListener, OnMapReadyCallback {

    @BindView(R.id.buttonUbicacion)
    Button botonUbicacion;
    @BindView(R.id.botonActualizar)
    ImageView botonActualizar;

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private boolean isGpsEnabled;
    private boolean isPermissionEnabled;
    private boolean vieneDeResult;
    private boolean vieneDePermisoNegado;
    private Activity activity;
    private LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;
    private static LatLng gpsPosition = new LatLng(-35.5356, -64.6034);
    private double fusedLatitude = 0.0;
    private double fusedLongitude = 0.0;
    private boolean firstTime = true;
    private GoogleMap mGoogleMap;
    private ProgressDialog progressDialog;

    public static final int REQUEST_LOCATION = 4;


    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        ButterKnife.bind(this, rootView);

        SupportMapFragment mapFrag = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.frg));
        mapFrag.getMapAsync(this);
        progressDialog = new ProgressDialog(getActivity());

        isGpsEnabled = false;
        isPermissionEnabled = false;
        activity = getActivity();

        botonActualizar.setOnClickListener(v -> {
            if (isPermissionEnabled && isGpsEnabled) {
                actualizarPosicion();
            } else {
                checkPermissions();
            }
        });

        botonUbicacion.setOnClickListener(v -> mListener.enUbicacionConfirmada(fusedLongitude, fusedLatitude));

        return rootView;
    }

    private void actualizarPosicion() {
        if (isPermissionEnabled && isGpsEnabled) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gpsPosition, 17));
        } else {
            checkPermissions();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (mLocationRequest.getFastestInterval() == 0 || mLocationRequest.getInterval() == 0) {
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setInterval(5000);
        }
        setFusedLatitude(location.getLatitude());
        setFusedLongitude(location.getLongitude());
        gpsPosition = new LatLng(location.getLatitude(), location.getLongitude());
        // Toast.makeText(getApplicationContext(), "NEW LOCATION RECEIVED", Toast.LENGTH_LONG).show();
        if (firstTime) {
            actualizarPosicion();
            firstTime = false;
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

    private double getFusedLatitude() {
        return fusedLatitude;
    }

    private void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    private double getFusedLongitude() {
        return fusedLongitude;
    }

    private void setFusedLongitude(double lon) {
        fusedLongitude = lon;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        try {
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
            mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mGoogleMap.setOnMyLocationButtonClickListener(() -> false);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!vieneDeResult && !vieneDePermisoNegado) {
            checkPermissions();
        }
        vieneDePermisoNegado = false;
        vieneDeResult = false;
    }

    //Chequea si tenes los permisos habilitados
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            //mandamos a chequear si esta activado el gps ya que tenemos permiso
            checkLocationEnabled();
            isPermissionEnabled = true;
        }
    }

    //Al volver del popup de pedido de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //mandamos a chequear si esta activado el gps ya que tenemos permiso
                    checkLocationEnabled();
                } else {
                    //no se acepto el permiso de usar gps
                    vieneDePermisoNegado = true;
                    doWithoutLocation();
                }
            }
        }
    }

    private void checkLocationEnabled() {
        // Location Already on  ... start
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(activity)) {
            //  Toast.makeText(AtmFragment.this, "Gps already enabled", Toast.LENGTH_SHORT).show();
            enableLocation();
        }
        // Location Already on  ... end

        if (!hasGPSDevice(activity)) {
            //       Toast.makeText(AtmFragment.this, "Gps not Supported", Toast.LENGTH_SHORT).show();
            isGpsEnabled = false;
            doWithoutLocation();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(activity)) {
            //       Log.e("keshav", "Gps not enabled");
            enableLocation();
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers != null && providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLocation() {
        //  if (googleApiClient == null || !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        googleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(connectionResult -> Log.d("Location error", "Location error " + connectionResult.getErrorCode())).build();
        //      }
        googleApiClient.connect();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setSmallestDisplacement(50f);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in o nActivityResult().
                        status.startResolutionForResult(activity, REQUEST_LOCATION);

                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SUCCESS:
                    startFusedLocation();
                    isGpsEnabled = true;
                    doWithLocation();
            }

        });
        // }
    }

    private void startFusedLocation() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    //ACCIONES A TOMAR SIN GPS ACTIVADO
    private void doWithoutLocation() {
            //Muestra mapa en medio de Argentina
            gpsPosition = new LatLng(-35.5356, -64.6034);
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(gpsPosition, 3.822f, 0.0f, 0.0f)), 1500, null);
    }

    //ACCIONES A TOMAR CON EL GPS ACTIVADO
    private void doWithLocation() {
        //Desde aca se va a ejecutar onLocationChanged
        registerRequestUpdate(this);
        progressDialog.setMessage("Obteniendo Ubicación");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    private void registerRequestUpdate(final com.google.android.gms.location.LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setInterval(0); // every 10 second
        mLocationRequest.setSmallestDisplacement(50f);
        new Handler().postDelayed(() -> {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, listener);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                if (!isGoogleApiClientConnected()) {
                    googleApiClient.connect();
                }
                registerRequestUpdate(listener);
            }
        }, 2000);
    }

    private boolean isGoogleApiClientConnected() {
        return googleApiClient != null && googleApiClient.isConnected();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vieneDeResult = true;
        switch (requestCode) {
            case (REQUEST_LOCATION):
                if (resultCode == RESULT_OK) {
                    isGpsEnabled = true;
                    doWithLocation();
                } else {
                    isGpsEnabled = false;
                    doWithoutLocation();
                }
                break;
        }
    }

    private void stopFusedLocation() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopFusedLocation();
    }


    public interface OnFragmentInteractionListener {
        void enUbicacionConfirmada(double longitude, double latitude);
    }
}
