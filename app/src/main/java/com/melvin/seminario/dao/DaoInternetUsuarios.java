package com.melvin.seminario.dao;

import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.User;
import com.melvin.seminario.util.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetUsuarios extends DaoHelper {

    private UsuarioService usuarioService;

    public DaoInternetUsuarios(){
        super();
        usuarioService = retrofit.create(UsuarioService.class);
    }

    public void obtenerUsuario(User user, ResultListener<User> controllerListener){
        usuarioService.obtenerUsuario(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                    controllerListener.finish(response.body());
                else
                    controllerListener.finish(new User());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                controllerListener.finish(new User());
            }
        });
    }


    public void mandarMail(Denuncia denuncia){
        usuarioService.mandarEmail(denuncia).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String a = call.toString();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String a = call.toString();
            }
        });
    }

    public void crearUsuario(User usuario, ResultListener<Boolean> listenerController){
        usuarioService.crearUsuario(usuario).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    listenerController.finish(true);
                } else {
                    listenerController.finish(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listenerController.finish(false);
            }
        });
    }

    public void actualizarUsuario(User user, ResultListener<Boolean> listenerController){
        usuarioService.actualizarUsuario(user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                listenerController.finish(true);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                listenerController.finish(true);
            }
        });
    }
}
