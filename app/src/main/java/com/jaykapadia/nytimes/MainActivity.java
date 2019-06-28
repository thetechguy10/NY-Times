package com.jaykapadia.nytimes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.jaykapadia.nytimes.Model.Article;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArticleViewModel model;
    article_adapter adapter;
    ArrayList<Article> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recyclerView = findViewById(R.id.recycler);

        adapter = new article_adapter(this, articles,getDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        load();




    }
    BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("app","Network connectivity change");
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(networkChangeReceiver);
    }

    private String getDate(){
        Calendar calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        String date = String.valueOf(calNewYork.get(Calendar.DAY_OF_MONTH));
        String month =  String.valueOf(calNewYork.get(Calendar.MONTH)+1);
        String year = String.valueOf(calNewYork.get(Calendar.YEAR));
        String hour = String.valueOf(calNewYork.get(Calendar.HOUR_OF_DAY));
        String minute= String.valueOf(calNewYork.get(Calendar.MINUTE));
        String second = String.valueOf(calNewYork.get(Calendar.SECOND));

        Log.d("xxxxxx",year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second);
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
    }




    private void load(){
        if (connectionAvailable()){
            model = ViewModelProviders.of(this).get(ArticleViewModel.class);
            model.getArticleRepository().observe(this, section1 -> {
                        if (section1 == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Error Fetching Data");
                            builder.setMessage("Unknown error occured");
                            builder.setPositiveButton("Retry", (dialog, which) -> load());
                            builder.setNegativeButton("Cancel",((dialog, which) -> dialog.dismiss()));
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            List<Article> articleList = section1.getResults();
                            articles.addAll(articleList);
                            adapter.notifyDataSetChanged();
                        }
                    }
            );
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Connection Problem");
            builder.setMessage("Please connect to the Internet");
            builder.setPositiveButton("Retry", (dialog, which) -> load());
            builder.setNegativeButton("Cancel",((dialog, which) -> {dialog.dismiss();
                finish();}));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean connectionAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Objects.requireNonNull(connectivityManager).getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        return connected;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
