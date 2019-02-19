package com.rashit.tiugaev.image.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.VersionDatabase;
import com.rashit.tiugaev.image.mvp.callback.Photo;
import com.rashit.tiugaev.image.viewmodel.MyViewModel;

public class Detail extends AppCompatActivity implements Photo {
    private ImageView full, infoPhoto, dowloadPhoto;
    private int id;
    private String fullUrl;
    private String userName;
    private String tags;

    private Toolbar toolbar;
    private CheckBox checkBox;
    private MyViewModel viewModel;
    private boolean state;
    private boolean stateView = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        setContentView(R.layout.activity_detail);
        full = findViewById(R.id.imageDetailFull);
        toolbar = findViewById(R.id.toolbarDetail);
        checkBox = findViewById(R.id.favoriteIcon);
        infoPhoto = findViewById(R.id.imageViewInfo);
        dowloadPhoto = findViewById(R.id.imageViewDownload);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            id = bundle.getInt("id");
            fullUrl = bundle.getString("url");
            userName = bundle.getString("userName");
            tags = bundle.getString("tags");

        }
        final DataBase dataBase = new DataBase(id,fullUrl,userName,tags);
        viewModel.searchItemById(id, this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state){
                    viewModel.deleteItemById(id);
                    state = false;
                    Toast.makeText(Detail.this, "Deleted of favorites", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    viewModel.insetItem(dataBase);
                    state = true;
                    Toast.makeText(Detail.this, "Added in favorites", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        infoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail.this, "Soon...", Toast.LENGTH_SHORT).show();
            }
        });
        dowloadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail.this, "Soon...", Toast.LENGTH_SHORT).show();
            }
        });



        Glide.with(this).load(fullUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(full);
        full.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                boolean stateView = true;
                if (stateView){
                    getSupportActionBar().hide();
                    stateView = false;
                    return;
                }
                else {
                    getSupportActionBar().show();
                    stateView = true;
                    return;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSearchComliteByUserId(Boolean result) {
        if (result){
            checkBox.setChecked(true);
            state = true;
        }
        else {
            checkBox.setChecked(false);
            state = false;
        }
    }

}
