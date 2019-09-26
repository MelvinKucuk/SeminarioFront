package com.melvin.seminario.dao;

import com.melvin.seminario.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    @GET("posts")
    Call<List<Post>> obtenerposts();

}
