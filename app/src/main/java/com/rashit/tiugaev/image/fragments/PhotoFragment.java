package com.rashit.tiugaev.image.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.rashit.tiugaev.image.pojo.Hit;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.activity.Detail;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.VersionDatabase;
import com.rashit.tiugaev.image.mvp.callback.PhotoCallBack;
import com.rashit.tiugaev.image.mvp.model.PhotoModel;
import com.rashit.tiugaev.image.mvp.presenter.PhotoPresenter;
import com.rashit.tiugaev.image.network.CheskInternet;
import com.rashit.tiugaev.image.network.RetrofitApi;
import com.rashit.tiugaev.image.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements PhotoCallBack.returnView {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Hit> dataHit;
    private Handler getPostHandler;
    private Snackbar snackbar;
    private View snakView;
    private VersionDatabase versionDatabase;
    private boolean state;
    private PhotoPresenter presenter;

    private int page = 1;
    private boolean state_page = true;

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_images, container, false);
        recyclerView = view.findViewById(R.id.RV_New_Images);
        versionDatabase = VersionDatabase.getInstance(getContext());

        dataHit = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), dataHit);
        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setItemCliick(new RecyclerViewAdapter.ItemCliick() {
            @Override
            public void onNoteClick(int position, View view) {
                if (view.getId() == R.id.imagePoster) {
                    Intent intent = new Intent(getContext(), Detail.class);
                    intent.putExtra("id", dataHit.get(position).getId());
                    intent.putExtra("url", dataHit.get(position).getWebformatURL());
                    intent.putExtra("userName", dataHit.get(position).getUser());
                    intent.putExtra("tags", dataHit.get(position).getTags());
                    startActivity(intent);
                }
            }
        });
        presenter = new PhotoPresenter(this, new PhotoModel(new RetrofitClient().getRetrofitInstance().create(RetrofitApi.class)));
        getPostHandler = new Handler();
        getPostHandler.postDelayed(runnable, 2000);
        if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
            presenter.getData(1, "latest", "all", 18);
            snackbar = Snackbar
                    .make(view, "Internet connected", Snackbar.LENGTH_LONG);
        } else {
            snackbar = Snackbar
                    .make(view, "Not internet connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            state = true;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (state_page &&((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == recyclerView.getLayoutManager().getItemCount() -1){
                    presenter.getData(page, "latest", "all", 18);
                    state_page = false;
                }

            }
        });
        return view;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
                if (state) {
                    presenter.getData(page, "latest", "all", 18);
                    state = false;
                }
                getPostHandler.postDelayed(this, 5000);
                if (snackbar != null && snackbar.isShown()) {
                    snackbar = Snackbar
                            .make(view, "Internet connected", Snackbar.LENGTH_LONG);
                    snakView = snackbar.getView();
                    snakView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.show();

                }
            } else {
                if (snackbar.isShown()) {
                } else {
                    snackbar = Snackbar
                            .make(view, "Not internet connection", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
                getPostHandler.postDelayed(this, 5000);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPostHandler.removeCallbacks(runnable);
        view = null;
    }

    @Override
    public void showData(List<Hit> posts) {
        dataHit.addAll(posts);
        recyclerViewAdapter.notifyItemInserted(dataHit.size());
        Log.i("TAG", "showData: " + page);
    }

    @Override
    public void countPage() {
        page = page + 1;
        state_page = true;
        Log.i("TAG", "countPage: " + state_page);
    }
}
