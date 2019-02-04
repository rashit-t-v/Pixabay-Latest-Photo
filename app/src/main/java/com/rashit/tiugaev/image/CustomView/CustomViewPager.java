package com.rashit.tiugaev.image.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = true;
    private Boolean disable = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return disable ? false : super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return disable ? false : super.onTouchEvent(event);
        return false;
    }

    public void disableScroll(Boolean disable) {
        //When disable = true not work the scroll and when disble = false work the scroll
        this.disable = disable;
    }
}
