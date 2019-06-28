package com.jaykapadia.nytimes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaykapadia.nytimes.Model.Article;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class article_adapter extends RecyclerView.Adapter<article_adapter.holder> {
    private Context context;
    private ArrayList<Article> articles;
    private String data;

    article_adapter(Context ctx, ArrayList<Article> articles, String date) {
        this.context = ctx;
        this.articles = articles;
        this.data = date;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new holder(inflater.inflate(R.layout.article_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.t1.setText(articles.get(position).getTitle());
        holder.t2.setText(articles.get(position).getAbstrac());
        holder.t3.setText(getTime(articles.get(position).getUpdated_date()));
        if (articles.get(position).getMultimedia().size() == 0) {
            Picasso.get().load(R.drawable.error).into(holder.i1);
        } else {
            Picasso.get().load(articles.get(position).getMultimedia().get(1).getUrl()).into(holder.i1);
        }

    }

    private String getTime(String s) {
        String year = s.substring(0, 4);
        String month = s.substring(5, 7);
        String date = s.substring(8, 10);
        String hour = s.substring(11, 13);
        String minute = s.substring(14, 16);
        String second = s.substring(17, 19);

        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String fi = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;

        int hours = 0;
        try {
            Date date1 = format.parse(data);
            Date date2 = format.parse(fi);
            long mills = date1.getTime() - date2.getTime();
            hours = (int) (mills / (1000 * 60 * 60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hours < 24) {
            return hours + "h ago";
        } else {
            return hours / 24 + "d ago";
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class holder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3;
        ImageView i1, i2;

        holder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.title);
            t2 = itemView.findViewById(R.id.abstrac);
            t3 = itemView.findViewById(R.id.time);
            i1 = itemView.findViewById(R.id.icon);
            i2 = itemView.findViewById(R.id.share);

            i2.setOnClickListener(v -> {
                String share = articles.get(getAdapterPosition()).getTitle() + "\n" + articles.get(getAdapterPosition()).getShort_url();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            });
        }
    }
}
