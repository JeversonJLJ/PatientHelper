package com.unisinos.patienthelper.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unisinos.patienthelper.Activity.MainActivity;
import com.unisinos.patienthelper.Adapters.RecyclerAdapterAlarm;
import com.unisinos.patienthelper.Adapters.RecyclerAdapterSchedule;
import com.unisinos.patienthelper.Class.OnScrollObserver;
import com.unisinos.patienthelper.Class.Util;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Dialog.DialogDate;
import com.unisinos.patienthelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayOfTheWeekFragment extends Fragment {

    private View mRootView;
    AlarmCollectionPagerAdapter mAlarmCollectionPagerAdapter;
    ViewPager mViewPager;
    int mPosition = defaultPosition;
    public static final int defaultPosition =182;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.view_pager_main, container, false);



        mAlarmCollectionPagerAdapter = new AlarmCollectionPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) mRootView.findViewById(R.id.view_pager_alarm);
        mViewPager.setAdapter(mAlarmCollectionPagerAdapter);

        mViewPager.setCurrentItem(defaultPosition);
        changeDate(defaultPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                changeDate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PagerTitleStrip pagerTitleStrip = mRootView.findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setTextColor(Color.WHITE);



        return mRootView;
    }

    public void reload(){
        mViewPager.setAdapter(mAlarmCollectionPagerAdapter);
        mViewPager.setCurrentItem(mPosition);
    }
    private void changeDate(int position){
        Calendar tempDay = Calendar.getInstance();
        tempDay.add(Calendar.DATE, ((position + 1) - 182));
        MainActivity mainActivity =(MainActivity)this.getActivity();
        mainActivity.mPosition = position;
        mainActivity.setDate(tempDay.getTime());
    }

    public void setPosition(int position){
        Calendar tempDay = Calendar.getInstance();
        tempDay.add(Calendar.DATE, ((position + 1) - 182));
        mPosition = position;
        mViewPager.setCurrentItem(mPosition);
    }
    public class AlarmCollectionPagerAdapter extends FragmentStatePagerAdapter {
        private Calendar day;

        public AlarmCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            day = Calendar.getInstance();

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new AlarmObjectFragment();

            Bundle args = new Bundle();

            args.putInt(AlarmObjectFragment.ARG_KEY, position);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return 364;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar tempDay = Calendar.getInstance();
            tempDay.setTime(day.getTime());
            tempDay.add(Calendar.DATE, ((position + 1) - 182));
            SimpleDateFormat SDFdayOfTheWeek = new SimpleDateFormat("EEEE");
            String dayOfTheWeek = SDFdayOfTheWeek.format(tempDay.getTime());
            dayOfTheWeek = dayOfTheWeek.toUpperCase();
            return dayOfTheWeek;

        }

    }

    public static class AlarmObjectFragment extends Fragment {

        public static final String ARG_KEY = "position";
        private ProgressBar mProgressBar;
        //private TextView lblSemResultado;
        private View mRootView;
        private int mPosition;
        private List<Alarm> mAlarmList;
        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        private RecyclerView.Adapter mAdapter;


        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final Fragment fragmento = this;
            mRootView = inflater.inflate(R.layout.fragment_day_of_the_week, container, false);
            Bundle args = getArguments();
            mPosition = args.getInt(ARG_KEY);
            mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progressBar);
            loading(true);

            mRecyclerView = mRootView.findViewById(R.id.recycler_view_schedule);
            mLayoutManager = new LinearLayoutManager(mRootView.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAlarmList = new ArrayList<Alarm>();
            mAdapter = new RecyclerAdapterSchedule(getActivity(), mAlarmList);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mRecyclerView.setOnScrollChangeListener(new OnScrollObserver.OnScrollObserverApi23(mRecyclerView){
                    @Override
                    public void onScrollUp() {
                        ((MainActivity) getActivity()).showHideOnScroll(false);
                    }

                    @Override
                    public void onScrollDown() {
                        ((MainActivity) getActivity()).showHideOnScroll(true);
                    }
                });
            }
            else{
                mRecyclerView.setOnScrollListener(new OnScrollObserver.OnScrollObserverRecyclerView() {
                    @Override
                    public void onScrollUp() {

                    }

                    @Override
                    public void onScrollDown() {

                    }
                });
            }
            // lblSemResultado = (TextView) rootView.findViewById(R.id.lblSemResultados);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    loadData(mPosition);
                }
            }).start();

            return mRootView;
        }

        private void loading(boolean loading) {
            if (mProgressBar != null && mRecyclerView != null && this.getActivity() != null) {
                if (loading) {
                    this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);

                        }
                    });
                } else {
                    this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }


        private void loadData(int position) {
            try {
                loading(true);
                Calendar tempDay = Calendar.getInstance();
                tempDay.add(Calendar.DATE, ((position + 1) - 182));
                Database mDbHelper = new Database(this.getActivity());

                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                mAlarmList = Alarm.ConsultarSQL(db, tempDay.getTime());

                this.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter = new RecyclerAdapterSchedule(getActivity(), mAlarmList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
                // mProgressBar.setVisibility(View.GONE);
                mDbHelper.close();
                db.close();
                loading(false);
            } catch (Exception e) {
                Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
            } finally {
                loading(false);
            }

        }

    }
}
