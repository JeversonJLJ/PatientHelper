package com.junkerapps.patienthelper.Class;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class OnScrollObserver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public abstract static class OnScrollObserverApi23 implements View.OnScrollChangeListener {
        RecyclerView observableView;
        int mLastFirstVisibleItem = 0;

        public OnScrollObserverApi23(RecyclerView view) {
            observableView = view;
        }

        public abstract void onScrollUp();

        public abstract void onScrollDown();

        int last = 0;
        boolean control = true;

        @Override
        public void onScrollChange(View view, int current, int i1, int i2, int i3) {
            if (view.getId() == observableView.getId()) {
                final int currentFirstVisibleItem = ((LinearLayoutManager) observableView.getLayoutManager()).findFirstVisibleItemPosition();
                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                    onScrollUp();
                } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    onScrollDown();
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;
            }
        }
    }

    public abstract static class OnScrollObserverRecyclerView extends RecyclerView.OnScrollListener {

        public abstract void onScrollUp();

        public abstract void onScrollDown();

        int last = 0;
        boolean control = true;

        @Override
        public void onScrolled(RecyclerView view, int dx, int current) {
            if (current < last && !control) {
                onScrollUp();
                control = true;
            } else if (current > last && control) {
                onScrollDown();
                control = false;
            }

            last = current;
        }
    }

}