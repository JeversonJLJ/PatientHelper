package com.unisinos.patienthelper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Date day;
    TextView txtDate;
    TextView txtDayOfWeek;
    ImageButton btnNextDay;
    ImageButton btnPreviousDay;
    ProgressBar progressBar;

    FloatingActionMenu fabMenu;
    FloatingActionButton fabAddAlarm, fabAddPatient;
    List<Alarm> alarmList;
    private ListView listView;
    private AdapterSchedule adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AppCompatActivity activity = this;
        txtDate = (TextView) findViewById(R.id.date_text);
        txtDayOfWeek = (TextView) findViewById(R.id.day_of_week_text);
        btnNextDay = (ImageButton) findViewById(R.id.next_day);
        btnPreviousDay= (ImageButton) findViewById(R.id.previous_day);
        progressBar = findViewById(R.id.progressBar);
        day = Calendar.getInstance().getTime();



        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(day);
                calendar.add(Calendar.DATE, 1);
                changeDate(calendar.getTime());
            }
        });

        btnPreviousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(day);
                calendar.add(Calendar.DATE, -1);
                changeDate(calendar.getTime());
            }
        });

        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_add_menu);
        fabAddAlarm = (FloatingActionButton) findViewById(R.id.fab_add_alarm);
        fabAddPatient = (FloatingActionButton) findViewById(R.id.fab_add_patient);


        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PatientActivity.class);
                //Bundle bundle = new Bundle();
               // bundle.putLong(FeedReaderContract.FeedPedidos.COLUMN_NAME_CODPEDIDO, itemPedido.getCodPedido());
                //intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        alarmList = new ArrayList<Alarm>();

        listView = findViewById(R.id.list_view_schedule);
        adapter = new AdapterSchedule(this,R.layout.layout_list_schedule, alarmList);
        listView.setAdapter(adapter);
        changeDate(day);


    }

    private void loadData(){
        Database mDbHelper = new Database(MainActivity.this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        alarmList = Alarm.ConsultarSQL(db,day);
        adapter = new AdapterSchedule(this,R.layout.layout_list_schedule, alarmList);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

    }
    private void changeDate(Date date){
        day = date;
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        txtDate.setText(df.format(day).toUpperCase());
        df = new SimpleDateFormat("EEEE");
        txtDayOfWeek.setText(df.format(day).toUpperCase());
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
