package com.rashit.tiugaev.image.fragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.activity.Detail;
import com.rashit.tiugaev.image.adapters.RecyclerViewAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.VersionDatabase;
import com.rashit.tiugaev.image.network.CheskInternet;
import com.rashit.tiugaev.image.network.GetDataInternet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewImages extends Fragment{

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<DataBase> data;
    private Handler getPostHandler;
    private Snackbar snackbar;
    private View snakView;
    private VersionDatabase versionDatabase;
    private boolean state;

    public NewImages() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_images, container, false);
        recyclerView = view.findViewById(R.id.RV_New_Images);
        versionDatabase = VersionDatabase.getInstance(getContext());

        data = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), data);
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
                    intent.putExtra("id", data.get(position).getId());
                    intent.putExtra("url", data.get(position).getWeb());
                    intent.putExtra("userName", data.get(position).getUser());
                    intent.putExtra("tags", data.get(position).getTag());
                    startActivity(intent);
                }

            }

        });
        getPostHandler = new Handler();
        getPostHandler.postDelayed(runnable, 5000);
        if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
            GetDataInternet.getData(recyclerViewAdapter, data);
            snackbar = Snackbar
                    .make(view, "Internet connected", Snackbar.LENGTH_LONG);
        } else {
            snackbar = Snackbar
                    .make(view, "Not internet connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            getDataDB();
            state = true;
        }

        return view;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (CheskInternet.chekInternet(Objects.requireNonNull(getContext()))) {
                GetDataInternet.getTotalCount();
                if (state){
                    GetDataInternet.getData(recyclerViewAdapter, data);
                    state=false;
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
