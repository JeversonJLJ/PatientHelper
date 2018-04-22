package com.unisinos.patienthelper.Activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.unisinos.patienthelper.Adapters.ViewPagerAdapterPatient;
import com.unisinos.patienthelper.Class.ScrollHorizontalViewPager;
import com.unisinos.patienthelper.Fragment.AlarmsFragment;
import com.unisinos.patienthelper.Fragment.DataFragment;
import com.unisinos.patienthelper.R;

public class PatientActivity extends AppCompatActivity {

    public static final String COD_PATIENT = "codPatient";
    public static final String TAB_LOAD = "tabLoad";
    public static final int TAB_LOAD_0 = 0;
    public static final int TAB_LOAD_1 = 1;
    private long mCodPatient;
    private ScrollHorizontalViewPager mViewPager;
    private ViewPagerAdapterPatient mAdapter;
    private AlarmsFragment mAlarmsFragment;
    private DataFragment mDataFragment;
    private TabLayout mTabLayout;
    private FloatingActionButton mFabAddAlarm;
    private FloatingActionButton mFabSavePatient;
    private FloatingActionMenu mFabPatientEditMenu;
    private FloatingActionButton mFabMenuSavePatient;
    private FloatingActionButton mFabMenuDeletePatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        mCodPatient = -1;

        int tabLoad = 0;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            mCodPatient = bundle.getLong(COD_PATIENT);
            tabLoad = bundle.getInt(TAB_LOAD);
        }

        mFabAddAlarm = (FloatingActionButton) findViewById(R.id.fab_new_alarm);
        mFabSavePatient = (FloatingActionButton) findViewById(R.id.fab_save_patient);
        mFabPatientEditMenu = (FloatingActionMenu) findViewById(R.id.fab_patient_edit_menu);
        mFabPatientEditMenu.setIconAnimated(false);
        mFabMenuSavePatient = (FloatingActionButton) findViewById(R.id.fab_menu_save_patient);
        mFabMenuDeletePatient = (FloatingActionButton) findViewById(R.id.fab_menu_delete_patient);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ScrollHorizontalViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        allotEachTabWithEqualWidth();

        if (tabLoad == TAB_LOAD_0)
            setTab(0);
        else
            setTab(1);


        clickButtons();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void clickButtons() {
        mFabMenuSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataFragment.save(true);

            }
        });

        mFabMenuDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataFragment.delete();

            }
        });

        mFabSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataFragment.save(true);

            }
        });


        mFabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCodPatient >= 0) {
                    mAlarmsFragment.newAlarm();
                } else {
                    mDataFragment.save(false);
                    setCodPatient(mDataFragment.codPatient);
                    mAlarmsFragment.newAlarm();
                }
            }
        });

    }

    private void setCodPatient(long codPatient) {
        mCodPatient = codPatient;
        mDataFragment.codPatient = codPatient;
        mAlarmsFragment.codPatient = codPatient;
    }

    private void setTab(int position) {
        mTabLayout.getTabAt(position).select();
        mViewPager.setCurrentItem(position, false);
        if (position == 1) {
            mFabAddAlarm.show(true);
            mFabSavePatient.hide(true);
            mFabPatientEditMenu.hideMenu(true);
        } else {
            mFabAddAlarm.hide(true);
            if (mCodPatient >= 0) {
                mFabPatientEditMenu.showMenu(true);
                mFabSavePatient.hide(false);
            } else {
                mFabPatientEditMenu.hideMenu(false);
                mFabSavePatient.show(true);
            }

        }
    }

    private void allotEachTabWithEqualWidth() {

        ViewGroup slidingTabStrip = (ViewGroup) mTabLayout.getChildAt(0);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View tab = slidingTabStrip.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 1;
            tab.setLayoutParams(layoutParams);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new ViewPagerAdapterPatient(getSupportFragmentManager());
        mAlarmsFragment = new AlarmsFragment();
        mAlarmsFragment.codPatient = mCodPatient;
        mDataFragment = new DataFragment();
        mDataFragment.codPatient = mCodPatient;

        mAdapter.addFragment(mDataFragment, getString(R.string.data_text));
        mAdapter.addFragment(mAlarmsFragment, getString(R.string.alarm_text));

        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
