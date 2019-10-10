package com.melvin.seminario.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.seminario.R;


public class CantidadVehiculosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public CantidadVehiculosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cantidad_vehiculos, container, false);

        CardView botonSi = view.findViewById(R.id.cardViewSi);
        CardView botonNo = view.findViewById(R.id.cardViewNo);
        botonSi.setOnClickListener(v -> mListener.enInteraccion());
        botonNo.setOnClickListener(v -> mListener.enInteraccion());

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
        void enInteraccion();
    }
}
