package com.melvin.seminario.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.melvin.seminario.R;
import com.melvin.seminario.controller.DenunciaController;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DenunciasActivity extends AppCompatActivity implements DenunciasAdapter.OnAdapterListener,
                                                                    ResumenFragment.OnFragmentInteractionListener{

    @BindView(R.id.recyclerDenuncias)
    RecyclerView recyclerView;
    @BindView(R.id.toolbarDenuncias)
    Toolbar toolbar;
    private DenunciasAdapter adapter;
    private List<Denuncia> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datos = new ArrayList<>();
        String username = getSharedPreferences(MainActivity.USER_PREFERENCES, MODE_PRIVATE).getString(MainActivity.KEY_USER, "");
        adapter = new DenunciasAdapter(datos, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        new DenunciaController().obtenerDenuncias(username, new ResultListener<List<Denuncia>>() {
            @Override
            public void finish(List<Denuncia> resultado) {
                datos = resultado;
                adapter.setDatos(datos);
            }
        });
    }

    @Override
    public void enDenunciaSeleccionada(String id) {
        ResumenFragment fragment = new ResumenFragment();
        Bundle datos = new Bundle();
        datos.putString(ResumenFragment.KEY_ID, id);
        fragment.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void enResumenConfirmado(Conductor asegurado, Conductor tercero) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
