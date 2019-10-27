package com.melvin.seminario.dao;

import com.melvin.seminario.model.Denuncia;
import com.melvin.seminario.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("users")
    Call<User> obtenerUsuario(@Body User user);

    @POST("email")
    Call<String> mandarEmail(@Body Denuncia email);

}
