package com.melvin.seminario.controller;

import com.melvin.seminario.dao.DaoInternetDenuncia;
import com.melvin.seminario.dao.DaoInternetUsuarios;
import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.util.ResultListener;

import java.util.List;

public class DenunciaController {

    public void obtenerDenuncias (String username, ResultListener<List<Denuncia>> listenerView){
        new DaoInternetDenuncia().obtenerDenuncias(username, new ResultListener<List<Denuncia>>() {
            @Override
            public void finish(List<Denuncia> resultado) {
                listenerView.finish(resultado);
            }
        });
    }

    public void obtenerDenuciaPorId (String id, ResultListener<Denuncia> listenerView){
        new DaoInternetDenuncia().obtenerDenunciaPorId(id, listenerView);
    }

    public void actualizarDenunciaPorId(String id, Denuncia denuncia, ResultListener<Boolean> listenerView){
        new DaoInternetDenuncia().actualizarDenunciaPorId(id, denuncia, listenerView);
    }
}
