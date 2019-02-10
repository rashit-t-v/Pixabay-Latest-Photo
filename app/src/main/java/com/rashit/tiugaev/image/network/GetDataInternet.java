package com.rashit.tiugaev.image.network;
import com.rashit.tiugaev.image.Hit;
import com.rashit.tiugaev.image.Post;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDataInternet {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static RetrofitApi retrofitApi;
    private static int beforeTotal;
    private static int afterTotal;

    public static void getData(final RecyclerViewAdapter adapter, final List<DataBase> data){
        retrofitApi = retrofit.create(RetrofitApi.class);

        Call<Post> call = retrofitApi.getPosts("latest", "vertical", 15);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                data.clear();
                Post posts = response.body();
                for (Hit hit : posts.getHits()) {
                    data.add(new DataBase(hit.getWebformatURL(), hit.getUser(), hit.getTags()));
                }
                adapter.notifyDataSetChanged();
                beforeTotal = posts.getTotal();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }
    public static void getTotalCount() {
        Call<Post> call = retrofitApi.getTotal("latest", "vertical", 3);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post posts = response.body();
                if (posts.getTotal() > beforeTotal) {
                    afterTotal = posts.getTotal();
                    beforeTotal = posts.getTotal();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });

    }

    public static int getBeforeTotal() {
        return beforeTotal;
    }

    public static int getAfterTotal() {
        return afterTotal;
    }
}
