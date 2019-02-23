package com.rashit.tiugaev.image.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import jp.wasabeef.glide.transformations.BlurTransformation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.rashit.tiugaev.image.Hit;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.activity.Detail;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.VersionDatabase;
import com.rashit.tiugaev.image.mvp.callback.PhotoCallBack;
import com.rashit.tiugaev.image.mvp.model.PhotoModel;
import com.rashit.tiugaev.image.mvp.presenter.PhotoPresenter;
import com.rashit.tiugaev.image.network.CheskInternet;
import com.rashit.tiugaev.image.network.GetDataInternet;
import com.rashit.tiugaev.image.network.RetrofitApi;
import com.rashit.tiugaev.image.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements PhotoCallBack.returnView {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<DataBase> data;
    private List<Hit> dataHit;
    private Handler getPostHandler;
    private Snackbar snackbar;
    private View snakView;
    private VersionDatabase versionDatabase;
    private boolean state;

    private PhotoPresenter presenter;
//    private RetrofitClient

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_images, container, false);
        recyclerView = view.findViewById(R.id.RV_New_Images);
        versionDatabase = VersionDatabase.getInstance(getContext());

        data = new ArrayList<>();
        dataHit = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), dataHit);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
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
//        getPostHandler = new Handler();
////        getPostHandler.postDelayed(runnable, 5000);
////        if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
////            GetDataInternet.getData(recyclerViewAdapter, data);
////            snackbar = Snackbar
////                    .make(view, "Internet connected", Snackbar.LENGTH_LONG);
////        } else {
////            snackbar = Snackbar
////                    .make(view, "Not internet connection", Snackbar.LENGTH_INDEFINITE);
////            snackbar.show();
////            getDataDB();
////            state = true;
////        }
        presenter = new PhotoPresenter (this, new PhotoModel(new RetrofitClient().getRetrofitInstance().create(RetrofitApi.class)));
        presenter.getData("latest", "vertical", 20);

        return view;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
                GetDataInternet.getTotalCount();
                if (state) {
                    GetDataInternet.getData(recyclerViewAdapter, data);
                    state = false;
                }
                if (GetDataInternet.getAfterTotal() > GetDataInternet.getBeforeTotal()) {
                    GetDataInternet.getData(recyclerViewAdapter, data);
                }
                getPostHandler.postDelayed(this, 20000);
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
                getPostHandler.postDelayed(this, 20000);
            }
        }
    };

    private void getDataDB() {
        LiveData<List<DataBase>> notsFromDb = versionDatabase.mDao().getAllNotes();
        notsFromDb.observe(Objects.requireNonNull(getActivity()), new Observer<List<DataBase>>() {
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

    @Override
    public void showData(List<Hit> posts) {
        dataHit.clear();
        dataHit.addAll(posts);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
