package com.melvin.seminario.dao;

import com.melvin.seminario.Util.ResultListener;
import com.melvin.seminario.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetPosts extends DaoHelper {

    public static final String BASE_URL = "https://stormy-wildwood-43671.herokuapp.com";
    private PostService postService;

    public DaoInternetPosts(){
        super(BASE_URL);
        postService = retrofit.create(PostService.class);
    }

    public void obtenerPosts (final ResultListener<List<Post>> resultListener){
        Call<List<Post>> call = postService.obtenerposts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                resultListener.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}
