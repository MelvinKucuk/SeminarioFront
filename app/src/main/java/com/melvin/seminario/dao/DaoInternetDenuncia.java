package com.melvin.seminario.dao;

import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.User;
import com.melvin.seminario.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetDenuncia extends DaoHelper{

    private DenunciaService denunciaService;

    public DaoInternetDenuncia(){
        super();
        denunciaService = retrofit.create(DenunciaService.class);
    }

    public void obtenerDenuncias(String username, ResultListener<List<Denuncia>> controllerListener){
        denunciaService.getDenuncias(username).enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                controllerListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {

            }
        });
    }

    public void obtenerDenunciaPorId(String id, ResultListener<Denuncia> listenerController){
        denunciaService.getDenunciaById(id).enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                if (response.isSuccessful()){
                    listenerController.finish(response.body());
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                Throwable asd = t;
            }
        });
    }


}
