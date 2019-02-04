package com.rashit.tiugaev.image.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.R;

public class Detail extends AppCompatActivity {
    private  ImageView full;
    private String fullUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        full = findViewById(R.id.imageDetailFull);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            fullUrl = bundle.getString("url");
        }
        Glide.with(this).load(fullUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(full);
        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
