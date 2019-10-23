package com.melvin.seminario.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapsFragment extends Fragment implements LocationListener {

    @BindView(R.id.cardViewUbicacion)
    CardView botonUbicacion;

    private Location location;
    private double latitude;
    private double longitude;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private ProgressDialog progressDialog;
    private boolean gps_enabled;
    private boolean network_enabled;
    private SupportMapFragment mapFragment;
    private static final int SETTINGS_CODE = 3;


    public static final int REQUEST_LOCATION = 4;


    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        locationEnabled();

        ButterKnife.bind(this, rootView);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        if (gps_enabled && network_enabled) {
            progressDialog = new ProgressDialog(getActivity());
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap mMap) {
                    googleMap = mMap;
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    googleMap.clear();

                    locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, REQUEST_LOCATION);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                progressDialog.dismiss();
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                CameraPosition googlePlex = CameraPosition.builder()
                                        .target(new LatLng(latitude, longitude))
                                        .zoom(17)
                                        .bearing(0)
                                        .build();

                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 800, null);

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                );
                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }
                        });
                        progressDialog.setMessage("Obteniendo Ubicacion");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        if (location != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            });
        }

        botonUbicacion.setOnClickListener(v -> mListener.enUbicacionConfirmada(longitude, latitude));

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                progressDialog.dismiss();
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                CameraPosition googlePlex = CameraPosition.builder()
                                        .target(new LatLng(latitude, longitude))
                                        .zoom(17)
                                        .bearing(0)
                                        .build();

                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 800, null);

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                );

                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }
                        });
                        if (location != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                return;
            }
        }
    }


    private void locationEnabled() {
        LocationManager lm = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = false;
        network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("GPS Enable")
                    .setPositiveButton("Settings", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    MapsFragment.this.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_CODE);
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_CODE){
            if (gps_enabled && network_enabled){
                progressDialog = new ProgressDialog(getActivity());
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap mMap) {
                        googleMap = mMap;
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        googleMap.clear();

                        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            }, REQUEST_LOCATION);
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    progressDialog.dismiss();
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                    CameraPosition googlePlex = CameraPosition.builder()
                                            .target(new LatLng(latitude, longitude))
                                            .zoom(17)
                                            .bearing(0)
                                            .build();

                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 800, null);

                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latitude, longitude))
                                    );
                                }

                                @Override
                                public void onStatusChanged(String s, int i, Bundle bundle) {

                                }

                                @Override
                                public void onProviderEnabled(String s) {

                                }

                                @Override
                                public void onProviderDisabled(String s) {

                                }
                            });
                            progressDialog.setMessage("Obteniendo Ubicacion");
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            if (location != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                });
            }
        }
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
        void enUbicacionConfirmada(double longitude, double latitude);
    }
}
