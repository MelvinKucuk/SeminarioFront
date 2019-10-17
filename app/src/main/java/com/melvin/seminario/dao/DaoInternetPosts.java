package com.melvin.seminario.dao;

import com.melvin.seminario.util.ResultListener;
import com.melvin.seminario.model.Conductor;
import com.melvin.seminario.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetPosts extends DaoHelper {

    public static final String BASE_URL = "https://stormy-wildwood-43671.herokuapp.com";
//    public static final String BASE_URL = "http://192.168.0.182:8080";
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

    public void mandarMail(Conductor conductor){
        postService.mandarEmail(conductor).enqueue(new Callback<String>() {
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
}
