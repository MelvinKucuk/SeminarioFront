package com.melvin.seminario.dao;

import com.melvin.seminario.model.Denuncia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DenunciaService {

    @GET("/denuncia/{username}/")
    Call<List<Denuncia>> getDenuncias(@Path("username") String username);

    @GET("/denuncia/byId/{id}/")
    Call<Denuncia> getDenunciaById(@Path("id") String id);
}
