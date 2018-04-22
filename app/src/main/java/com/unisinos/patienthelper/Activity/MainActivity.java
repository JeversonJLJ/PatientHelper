package com.unisinos.patienthelper.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.Dialog.DialogSearch;
import com.unisinos.patienthelper.Fragment.DayOfTheWeekFragment;
import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Service.ServiceAlarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQ_LOAD_PATIENT = 0;
    public static final int REQ_CREATE_PATIENT = 1;
    public static final int REQ_LOAD_ALARMS = 2;
    private Date mDay;
    private TextView mTxtDate;
    private ImageButton mBtnNextDay;
    private ImageButton mBtnPreviousDay;
    private FloatingActionMenu mFabMenu;
    private FloatingActionButton mFabAddAlarm, mFabAddPatient;
    public int mPosition;
    private Fragment mFragmentDayOfTheWeek;
    private RelativeLayout mChangeDayBar;
    private Animation mAnimShow, mAnimHide;
    private boolean mLastShowHideState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AppCompatActivity activity = this;
        mTxtDate = (TextView) findViewById(R.id.date_text);
        mBtnNextDay = (ImageButton) findViewById(R.id.next_day);
        mBtnPreviousDay = (ImageButton) findViewById(R.id.previous_day);
        mDay = Calendar.getInstance().getTime();
        mChangeDayBar = findViewById(R.id.change_day_bar);

        mBtnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDay);
                calendar.add(Calendar.DATE, 1);
                mPosition++;
                changeDate(calendar.getTime());
            }
        });

        mBtnPreviousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDay);
                calendar.add(Calendar.DATE, -1);
                mPosition--;
                changeDate(calendar.getTime());

            }
        });

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        mFragmentDayOfTheWeek = Fragment.instantiate(MainActivity.this, "com.unisinos.patienthelper.Fragment.DayOfTheWeekFragment");
        tx.replace(R.id.fragment_day_of_the_week, mFragmentDayOfTheWeek);
        tx.commit();

        mFabMenu = (FloatingActionMenu) findViewById(R.id.fab_add_menu);
        mFabAddAlarm = (FloatingActionButton) findViewById(R.id.fab_add_alarm);
        mFabAddPatient = (FloatingActionButton) findViewById(R.id.fab_add_patient);


        mFabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
                DialogSearch.ShowPatients(activity, new DialogSearch.OnSelectedItemListener() {
                    @Override
                    public void onSelectedItem(Object item, int index) {
                        final Paciente patient = (Paciente) item;

                        Intent intent = new Intent(activity, PatientActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putLong(PatientActivity.COD_PATIENT, patient.getCodigo());
                        bundle.putInt(PatientActivity.TAB_LOAD, PatientActivity.TAB_LOAD_1);
                        intent.putExtras(bundle);

                        activity.startActivityForResult(intent, MainActivity.REQ_LOAD_PATIENT);


                    }
                });
            }
        });

        mFabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
                Intent intent = new Intent(activity, PatientActivity.class);
                activity.startActivityForResult(intent, REQ_CREATE_PATIENT);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent serviceIntent = new Intent(getBaseContext(), ServiceAlarm.class);
        startService(serviceIntent);
        initAnimation();

    }

    private void initAnimation() {
        mAnimShow = AnimationUtils.loadAnimation(this, R.anim.anim_view_show_bottom_to_top);
        mAnimHide = AnimationUtils.loadAnimation(this, R.anim.anim_view_hide_top_to_bottom);
    }


    private void changeDate(Date date) {
        setDate(date);
        DayOfTheWeekFragment dayOfTheWeekFragment = (DayOfTheWeekFragment) mFragmentDayOfTheWeek;
        dayOfTheWeekFragment.setPosition(mPosition);
    }

    public void showHideOnScroll(boolean show) {
        if (show != mLastShowHideState) {
            if (show) {

                mFabMenu.startAnimation(mAnimShow);

                mChangeDayBar.startAnimation(mAnimShow);
                mAnimShow.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mChangeDayBar.setVisibility(View.VISIBLE);
                        mFabMenu.showMenu(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                mFabMenu.startAnimation(mAnimHide);

                mChangeDayBar.startAnimation(mAnimHide);
                mAnimHide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mChangeDayBar.setVisibility(View.GONE);
                        mFabMenu.hideMenu(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
        mLastShowHideState = show;
    }

    public void setDate(Date date) {
        showHideOnScroll(true);
        mDay = date;
        final SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        mTxtDate.animate().alpha(0.0f).setDuration(100).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTxtDate.setVisibility(View.INVISIBLE);
                mTxtDate.setText(df.format(mDay).toUpperCase());
                mTxtDate.animate().alpha(1.0f).setDuration(100).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mTxtDate.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        ((DayOfTheWeekFragment) mFragmentDayOfTheWeek).reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_patient) {
            DialogSearch.ShowPatients(this, new DialogSearch.OnSelectedItemListener() {
                @Override
                public void onSelectedItem(Object item, int index) {
                    final Paciente patient = (Paciente) item;

                    Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putLong(PatientActivity.COD_PATIENT, patient.getCodigo());
                    bundle.putInt(PatientActivity.TAB_LOAD, PatientActivity.TAB_LOAD_0);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, MainActivity.REQ_LOAD_PATIENT);


                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
