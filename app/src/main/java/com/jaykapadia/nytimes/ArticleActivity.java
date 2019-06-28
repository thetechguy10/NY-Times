package com.jaykapadia.nytimes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaykapadia.nytimes.Model.Article;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class ArticleActivity extends AppCompatActivity {
    ArticleViewModel model;
    article_adapter adapter;
    ArrayList<Article> articles = new ArrayList<>();
    ProgressBar p1;
    String section;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        }

        p1 = findViewById(R.id.pbar);
        section = getIntent().getStringExtra("section");
        RecyclerView recyclerView = findViewById(R.id.recycler);

        adapter = new article_adapter(this, articles,getDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        load();


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
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
    }

    private void load(){
        if (connectionAvailable()){
            model = ViewModelProviders.of(this).get(ArticleViewModel.class);
            model.getArticleRepository(section).observe(this, section1 -> {
                        if (section1 == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleActivity.this);
                            builder.setTitle("Error Fetching Data");
                            builder.setMessage("Unknown error occured");
                            builder.setPositiveButton("Retry", (dialog, which) -> load());
                            builder.setNegativeButton("Cancel",((dialog, which) -> dialog.dismiss()));
                            builder.setCancelable(false);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            List<Article> articleList = section1.getResults();
                            articles.addAll(articleList);
                            adapter.notifyDataSetChanged();
                            p1.setVisibility(View.GONE);
                        }
                    }
            );
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleActivity.this);
            builder.setTitle("Connection Problem");
            builder.setMessage("Please connect to the Internet");
            builder.setPositiveButton("Retry", (dialog, which) -> load());
            builder.setNegativeButton("Cancel",((dialog, which) -> {dialog.dismiss();
                finish();}));
            builder.setCancelable(false);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
