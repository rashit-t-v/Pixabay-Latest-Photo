package com.rashit.tiugaev.image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;
import jp.wasabeef.glide.transformations.BlurTransformation;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.rashit.tiugaev.image.adapters.VievPageAdapter;
import com.rashit.tiugaev.image.fragments.MyFavorite;
import com.rashit.tiugaev.image.fragments.NewImages;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ConstraintSet.Constraint constraint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        VievPageAdapter vievPageAdapter = new VievPageAdapter(getSupportFragmentManager());
        vievPageAdapter.AddFragment(new NewImages(),"Новые");
        vievPageAdapter.AddFragment(new MyFavorite(),"Избранные");
        viewPager.setAdapter(vievPageAdapter);
        tableLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finishAffinity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
