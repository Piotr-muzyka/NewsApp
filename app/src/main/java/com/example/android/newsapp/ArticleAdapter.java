package com.example.android.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PiotrM on 03.07.2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = NewsActivity.class.getName();

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }

        // Find the article at the given position in the list of articles
        Article currentArticle = getItem(position);

        String title = currentArticle.getTitle();
        String section = currentArticle.getSectionName();
        String url = currentArticle.getUrl();
        String dateObject = currentArticle.getPublishDate();


        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(title);

        // Find the TextView with view ID date
        TextView sectionView = (TextView) listItemView.findViewById(R.id.sectionName);
        sectionView.setText(section);

        TextView dateView = (TextView) listItemView.findViewById(R.id.publishDate);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(formatTime(dateObject));
        return listItemView;
    }

    // Format the date string
    private String formatDate(String dateObject) {
        dateObject = dateObject.substring(0, dateObject.length() - 1);
        String formattedDate = "";

        try {
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateObject);
            formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(oldDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Parse exception" + e);
        }
        return formattedDate;
    }

    // Format the time string
    private String formatTime(String dateObject) {
        String formattedTime = "";

        try {
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateObject);
            formattedTime = new SimpleDateFormat("h:mm:ss a").format(oldDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Parse exception" + e);
        }
        return formattedTime;
    }
}
