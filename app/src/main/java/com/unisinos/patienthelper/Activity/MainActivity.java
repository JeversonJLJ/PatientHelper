package com.unisinos.patienthelper.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.unisinos.patienthelper.Adapters.AdapterSchedule;
import com.unisinos.patienthelper.Adapters.RecyclerAdapterSchedule;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.Dialog.DialogSearch;
import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Service.ServiceAlarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQ_LOAD_PATIENT = 0;
    public static final int REQ_CREATE_PATIENT = 1;
    Date mDay;
    TextView mTxtDate;
    TextView mTxtDayOfWeek;
    ImageButton mBtnNextDay;
    ImageButton mBtnPreviousDay;
    ProgressBar mProgressBar;

    FloatingActionMenu mFabMenu;
    FloatingActionButton mFabAddAlarm, mFabAddPatient;
    List<Alarm> mAlarmList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AppCompatActivity activity = this;
        mTxtDate = (TextView) findViewById(R.id.date_text);
        mTxtDayOfWeek = (TextView) findViewById(R.id.day_of_week_text);
        mBtnNextDay = (ImageButton) findViewById(R.id.next_day);
        mBtnPreviousDay = (ImageButton) findViewById(R.id.previous_day);
        mProgressBar = findViewById(R.id.progressBar);
        mDay = Calendar.getInstance().getTime();


        mBtnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDay);
                calendar.add(Calendar.DATE, 1);
                changeDate(calendar.getTime());
            }
        });

        mBtnPreviousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDay);
                calendar.add(Calendar.DATE, -1);
                changeDate(calendar.getTime());
            }
        });

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


        mAlarmList = new ArrayList<Alarm>();

        mRecyclerView = findViewById(R.id.recycler_view_schedule);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterSchedule(this, mAlarmList);
        mRecyclerView.setAdapter(mAdapter);
        changeDate(mDay);

        Intent serviceIntent = new Intent(getBaseContext(), ServiceAlarm.class);
        startService(serviceIntent);



    }

    private void loadData() {
        Database mDbHelper = new Database(MainActivity.this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mAlarmList = Alarm.ConsultarSQL(db, mDay);
        mAdapter = new RecyclerAdapterSchedule(this, mAlarmList);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);

    }

    private void changeDate(Date date) {
        mDay = date;
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        mTxtDate.setText(df.format(mDay).toUpperCase());
        df = new SimpleDateFormat("EEEE");
        mTxtDayOfWeek.setText(df.format(mDay).toUpperCase());
        loadData();
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
        loadData();
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
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
