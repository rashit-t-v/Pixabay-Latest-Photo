package com.rashit.tiugaev.image.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rashit.tiugaev.image.Hit;
import com.rashit.tiugaev.image.Post;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.activity.Detail;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.NotesDatabase;
import com.rashit.tiugaev.image.network.CheskInternet;
import com.rashit.tiugaev.image.network.GetDataInternet;
import com.rashit.tiugaev.image.network.RetrofitApi;
import com.rashit.tiugaev.image.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewImages extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<DataBase> data;
    private Handler getPostHandler;
    private int totalCount;
    private Retrofit retrofit;
    private RetrofitApi retrofitApi;
    private Snackbar snackbar;
    private View snakView;
    private NotesDatabase notesDatabase;
    private MyViewModel myViewModel;
    private Context context;

    public NewImages() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_images, container, false);
        recyclerView = view.findViewById(R.id.RV_New_Images);
        notesDatabase = NotesDatabase.getInstance(getContext());
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        data = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), data);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerViewAdapter.setNoteCliick(new RecyclerViewAdapter.NoteCliick() {
            @Override
            public void onNoteClick(int position, View view) {
                if (view.getId() == R.id.imagePoster) {
                    String url = data.get(position).getWeb();
                    Intent intent = new Intent(getContext(), Detail.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }

        });
        recyclerViewAdapter.setOnCheckedChangeListener(new RecyclerViewAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final int position, CompoundButton button, boolean ischeked) {
                if (ischeked) {
                    button.setChecked(true);
                    DataBase notsForDataBase = new DataBase(data.get(position).getWeb(), data.get(position).getUser(), data.get(position).getTag());
                    myViewModel.insetNote(notsForDataBase);
                    button.setBackground(getResources().getDrawable(R.drawable.ic_star_cheked));
                    Toast.makeText(getContext(), "Добавлено в избранное.", Toast.LENGTH_SHORT).show();
                } else {
                    button.setChecked(true);
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);

        totalCount = 0;
        getPostHandler = new Handler();
        getPostHandler.postDelayed(runnable, 5000);

        if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
            GetDataInternet.getData(recyclerViewAdapter,data);
            snackbar = Snackbar
                    .make(view, "Есть интернет соединение", Snackbar.LENGTH_LONG);
        } else {
            snackbar = Snackbar
                    .make(view, "Нет интернет соединения", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            getDataDB();
        }

        return view;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
                getTotalCount();
                getPostHandler.postDelayed(this, 5000);
                if (snackbar != null && snackbar.isShown()) {
                    snackbar = Snackbar
                            .make(view, "Есть интернет соединение", Snackbar.LENGTH_LONG);
                    snakView = snackbar.getView();
                    snakView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.show();
                }

            } else {
                if (snackbar.isShown()) {
                } else {
                    snackbar = Snackbar
                            .make(view, "Нет интернет соединения", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
                getPostHandler.postDelayed(this, 5000);
            }
        }
    };

//    private void getData() {
//        Call<Post> call = retrofitApi.getPosts("latest", "vertical", 15);
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                data.clear();
//                Post posts = response.body();
//                for (Hit hit : posts.getHits()) {
//                    data.add(new DataBase(hit.getWebformatURL(), hit.getUser(), hit.getTags()));
//                }
//                recyclerViewAdapter.notifyDataSetChanged();
//                totalCount = posts.getTotal();
//
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//
//            }
//
//        });
//    }

    private void getTotalCount() {
        Call<Post> call = retrofitApi.getTotal("latest", "vertical", 3);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post posts = response.body();
                if (posts.getTotal() > totalCount) {
                    GetDataInternet.getData(recyclerViewAdapter,data);
                    totalCount = posts.getTotal();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }
    private int getCurrentItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void getDataDB() {
        LiveData<List<DataBase>> notsFromDb = notesDatabase.notesDao().getAllNotes();
        notsFromDb.observe(getActivity(), new Observer<List<DataBase>>() {
            @Override
            public void onChanged(List<DataBase> dataBases1) {
                data.clear();
                data.addAll(dataBases1);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPostHandler.removeCallbacks(runnable);
        view = null;
    }



}
