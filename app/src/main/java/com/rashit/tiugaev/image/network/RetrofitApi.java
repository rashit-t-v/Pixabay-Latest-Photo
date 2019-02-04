package com.rashit.tiugaev.image.network;

import com.rashit.tiugaev.image.Post;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    String KEY = "?key=10290517-dc79227a5978a569b0208a078";
    @GET (KEY)
    Call<Post> getPosts(
            @Query("order") String order,
            @Query("orientation") String orientation,
            @Query("per_page") int count_per_page
    );
    @GET (KEY)
    Call<Post> getTotal(
            @Query("order") String order,
            @Query("orientation") String orientation,
            @Query("per_page") int count_per_page
    );
}
