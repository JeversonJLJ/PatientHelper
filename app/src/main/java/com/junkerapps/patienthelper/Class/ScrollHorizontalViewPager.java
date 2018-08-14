package com.junkerapps.patienthelper.Class;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ScrollHorizontalViewPager extends ViewPager{

    public ScrollHorizontalViewPager(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof NestedScrollView || v instanceof ScrollView || v instanceof HorizontalScrollView) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
