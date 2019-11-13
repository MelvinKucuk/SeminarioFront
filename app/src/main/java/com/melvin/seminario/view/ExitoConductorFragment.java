package com.melvin.seminario.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melvin.seminario.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExitoConductorFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    @BindView(R.id.buttonSiguiente)
    Button botonSiguiente;
    @BindView(R.id.buttonFoto)
    Button botonMasFotos;

    public ExitoConductorFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exito_conductor, container, false);
        ButterKnife.bind(this, view);

        botonSiguiente.setOnClickListener(v -> mListener.enExitoConductor());
        botonSiguiente.setOnTouchListener(
                (v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            v.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                            ((Button) v).setTextColor(getResources().getColor(R.color.white));
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            v.getBackground().clearColorFilter();
                            ((Button) v).setTextColor(getResources().getColor(R.color.primaryText));
                            v.invalidate();
                            break;
                        }
                    }
                    return false;
                });
        botonMasFotos.setOnClickListener(v -> mListener.enAgregarMasFotosConductor());

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
        void enExitoConductor();
        void enAgregarMasFotosConductor();
    }
}
