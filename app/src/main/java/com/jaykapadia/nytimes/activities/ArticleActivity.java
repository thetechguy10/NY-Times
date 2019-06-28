package com.jaykapadia.nytimes.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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

import com.google.android.material.snackbar.Snackbar;
import com.jaykapadia.nytimes.viewModel.ArticleViewModel;
import com.jaykapadia.nytimes.model.Article;
import com.jaykapadia.nytimes.R;
import com.jaykapadia.nytimes.adapter.article_adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class ArticleActivity extends AppCompatActivity {
    private article_adapter adapter;
    private final ArrayList<Article> articles = new ArrayList<>();
    private ProgressBar p1;
    private String section;
    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        adapter = new article_adapter(this, articles, getDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        View v = findViewById(R.id.constraint);
        snackbar = Snackbar.make(v, "NO Internet Connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", v1 -> load());
        snackbar.setActionTextColor(Color.WHITE);
    }

    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            load();
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

    private String getDate() {
        Calendar calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String date = String.valueOf(calNewYork.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calNewYork.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calNewYork.get(Calendar.YEAR));
        String hour = String.valueOf(calNewYork.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calNewYork.get(Calendar.MINUTE));
        String second = String.valueOf(calNewYork.get(Calendar.SECOND));
        return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    }

    private void load() {
        if (connectionAvailable()) {
            snackbar.dismiss();
            if (adapter.getItemCount() == 0) {
                p1.setVisibility(View.VISIBLE);
                ArticleViewModel model = ViewModelProviders.of(this).get(ArticleViewModel.class);
                model.getArticleRepository(section).observe(this, section1 -> {
                            if (section1 == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ArticleActivity.this);
                                builder.setTitle("Error Fetching Data");
                                builder.setMessage("Unknown error occured");
                                builder.setPositiveButton("Ok", (dialog, which) -> {
                                    dialog.dismiss();
                                    finish();
                                });
                                builder.setCancelable(false);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                p1.setVisibility(View.GONE);
                            } else {
                                List<Article> articleList = section1.getResults();
                                articles.addAll(articleList);
                                adapter.notifyDataSetChanged();
                                p1.setVisibility(View.GONE);
                            }
                        }
                );
            }
        } else {
            p1.setVisibility(View.INVISIBLE);
            if (adapter.getItemCount() == 0) {
                View v = findViewById(R.id.constraint);
                snackbar = Snackbar.make(v, "NO Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v1 -> load());
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
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
