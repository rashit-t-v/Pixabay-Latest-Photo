package com.rashit.tiugaev.image.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.adapters.FavoriteRecAdapter;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.MyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavorite extends Fragment {

    private List<DataBase> data;
    private RecyclerView recyclerView;
    private FavoriteRecAdapter favoriteRecAdapter;
    private View view;
    private MyViewModel myViewModel;

    public MyFavorite() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_favorite, container, false);
        data = new ArrayList<>();
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerFavorite);
        favoriteRecAdapter = new FavoriteRecAdapter(getContext(), data);
        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(favoriteRecAdapter);
        favoriteRecAdapter.setItemCliick(new FavoriteRecAdapter.ItemCliick() {
            @Override
            public void onNoteClick(final int position) {
                removeItems(position);
            }
        });

        getData();

        favoriteRecAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    private void getData() {
        LiveData<List<DataBase>> itemsFromDb = myViewModel.getItems();
        itemsFromDb.observe(getActivity(), new Observer<List<DataBase>>() {
            @Override
            public void onChanged(List<DataBase> dataBases1) {
                favoriteRecAdapter.setItems(dataBases1);
                favoriteRecAdapter.notifyDataSetChanged();
            }
        });

    }

    private void removeItems(final int position) {
        DataBase dataBase = favoriteRecAdapter.getItems().get(position);
        myViewModel.deleteItem(dataBase);
        favoriteRecAdapter.notifyItemRemoved(position);

    }

}
