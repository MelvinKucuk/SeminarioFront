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


public class ExitoChoqueFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ExitoChoqueFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exito_choque, container, false);

        Button botonSiguiente = view.findViewById(R.id.buttonSiguiente);
        botonSiguiente.setOnClickListener(v -> mListener.enChoqueConfirmado());
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
        void enChoqueConfirmado();
    }
}
