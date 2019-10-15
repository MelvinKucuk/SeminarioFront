package com.melvin.seminario.dao;

import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Email;
import com.melvin.seminario.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostService {

    @GET("posts")
    Call<List<Post>> obtenerposts();

    @POST("email")
    Call<String> mandarEmail(@Body Conductor email);

}
