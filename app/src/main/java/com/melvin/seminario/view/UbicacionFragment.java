package com.melvin.seminario.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UbicacionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.cardViewSi)
    CardView botonSi;
    @BindView(R.id.cardViewNo)
    CardView botonNo;

    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubicacion, container, false);

        ButterKnife.bind(this, view);

        botonSi = view.findViewById(R.id.cardViewSi);
        botonSi.setOnClickListener(v -> mListener.enUbicacionSi());
        botonNo.setOnClickListener(v -> mListener.enUbicacionNo());

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
        void enUbicacionSi();
        void enUbicacionNo();
    }
}
