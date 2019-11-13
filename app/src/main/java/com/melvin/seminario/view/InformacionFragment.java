package com.melvin.seminario.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformacionFragment extends Fragment {

    @BindView(R.id.buttonSi)
    Button botonSi;
    @BindView(R.id.buttonNo)
    Button botonNo;

    private OnFragmentInteractionListener mListener;

    public InformacionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);
        ButterKnife.bind(this, view);

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
