package com.rashit.tiugaev.image.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rashit.tiugaev.image.pojo.Hit;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.activity.Detail;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
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
    private ImageButton btnSearchPhoto;
    private ImageButton btnSettingPhoto;
    private boolean state_page = true;

    private int page = 1;
    private String order = "popular";
    private String totalSearh = "";
    private String orientation = "all";


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
            public void onItemClick(int position, View view) {
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
            presenter.getData(page, order, totalSearh, orientation, 18);
            snackbar = Snackbar.make(view, "Internet connected", Snackbar.LENGTH_LONG);
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
                if (state_page && ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == recyclerView.getLayoutManager().getItemCount() - 1) {
                    presenter.getData(page, order, totalSearh, orientation, 18);
                    state_page = false;
                }

            }
        });
        btnSearchPhoto = view.findViewById(R.id.imBtnSearchPhoto);
        btnSearchPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
                View mView = getLayoutInflater().inflate(R.layout.search_photo, null);
                Button searchPhoto = mView.findViewById(R.id.searchPhoto);
                Button cancel = mView.findViewById(R.id.cancelPhoto);
                final EditText editText = mView.findViewById(R.id.editTextSearh);
                alertDialog.setView(mView);
                final AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                searchPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page = 1;
                        totalSearh = editText.getText().toString();
                        dataHit.clear();
                        presenter.getData(page, order, totalSearh, orientation, 18);
                        recyclerViewAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
            }
        });
        btnSettingPhoto = view.findViewById(R.id.imBtnSettingPhoto);
        btnSettingPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
                View mView = getLayoutInflater().inflate(R.layout.setting_photo, null);
                Button searchPhoto = mView.findViewById(R.id.updatePhoto);
                Button cancel = mView.findViewById(R.id.cancel);
                RadioGroup radioPopular = mView.findViewById(R.id.radioPopular);
                radioPopular.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.latest:
                                order = "latest";
                                break;
                            case R.id.popular:
                                order = "popular";
                                break;
                        }
                    }
                });
                RadioGroup radioOrientation = mView.findViewById(R.id.radioOrientation);
                radioOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.allphoto:
                                orientation = "all";
                                break;
                            case R.id.verticalPhoto:
                                orientation = "vertical";
                                break;
                            case R.id.horizontalPhoto:
                                orientation = "horizontal";
                                break;
                        }
                    }
                });
                alertDialog.setView(mView);
                final AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                searchPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page = 1;
                        dataHit.clear();
                        presenter.getData(page, order, totalSearh, orientation, 18);
                        recyclerViewAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
            }
        });

        return view;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
                if (state) {
                    presenter.getData(page, order, totalSearh, orientation, 18);
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
    }

    @Override
    public void countPage() {
        page = page + 1;
        state_page = true;
    }


}
