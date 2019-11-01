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

import butterknife.BindView;

public class InformacionFragment extends Fragment {

    @BindView(R.id.cardViewSi)
    CardView botonSi;
    @BindView(R.id.cardViewNo)
    CardView botonNo;

    private OnFragmentInteractionListener mListener;

    public InformacionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);

        botonNo.setOnClickListener(v -> mListener.enNotieneInformacion());
        botonSi.setOnClickListener(v -> mListener.enSiTieneInformacion());
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
        void enNotieneInformacion();
        void enSiTieneInformacion();
    }
}
