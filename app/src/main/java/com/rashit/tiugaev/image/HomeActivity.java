package com.rashit.tiugaev.image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.material.tabs.TabLayout;
import com.rashit.tiugaev.image.adapters.VievPageAdapter;
import com.rashit.tiugaev.image.fragments.MyFavorite;
import com.rashit.tiugaev.image.fragments.PhotoFragment;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ConstraintSet.Constraint constraint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        VievPageAdapter vievPageAdapter = new VievPageAdapter(getSupportFragmentManager());
        vievPageAdapter.AddFragment(new PhotoFragment(),"PHOTO");
        vievPageAdapter.AddFragment(new MyFavorite(),"FAVORITE");
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
