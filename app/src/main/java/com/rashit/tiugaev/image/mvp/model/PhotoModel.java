package com.rashit.tiugaev.image.mvp.model;

import com.rashit.tiugaev.image.Post;
import com.rashit.tiugaev.image.mvp.callback.PhotoCallBack;
import com.rashit.tiugaev.image.network.RetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoModel {
    private RetrofitApi retrofit;
    private PhotoCallBack.returnPresenter returnPresenter;

    public  PhotoModel (RetrofitApi retrofitApi){
        retrofit = retrofitApi;
    }
    public void setCallBack (PhotoCallBack.returnPresenter returnPres){
        returnPresenter = returnPres;
    }
    public void getDataModel (String order, String orintation, int count_per_page){
        Call<Post> call = retrofit.getPosts(order, orintation, count_per_page);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post posts = response.body();
                returnPresenter.onSuccses(posts.getHits());
//                for (Hit hit : posts.getHits()) {
//                    data.add(new DataBase(hit.getId(),hit.getWebformatURL(), hit.getUser(), hit.getTags()));
//                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }
}
