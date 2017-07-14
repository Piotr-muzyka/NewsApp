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

    public ArticleAdapter(Context context, List<Article> earthquakes) {
        super(context, 0, earthquakes);
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

        // Find the earthquake at the given position in the list of earthquakes
        Article currentArticle = getItem(position);

        String title = currentArticle.getTitle();
        String section = currentArticle.getSectionName();
        String url = currentArticle.getUrl();
        String dateObject = currentArticle.getPublishDate();


        // Find the TextView with view ID date
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the date of the current earthquake in that TextView
        titleView.setText(title);

        // Find the TextView with view ID date
        TextView sectionView = (TextView) listItemView.findViewById(R.id.sectionName);
        // Display the date of the current earthquake in that TextView
        sectionView.setText(section);

//        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.publishDate);
//        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
//        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formatTime(dateObject));

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(String dateObject) {
        dateObject = dateObject.substring(0, dateObject.length() - 1);
        String formattedDate="";

        try {
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateObject);
            formattedDate= new SimpleDateFormat("dd/MM/yyyy").format(oldDate);
        }
        catch (ParseException e){
            Log.e(LOG_TAG,"Parse exception" +e);
        }
        return formattedDate;
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(String dateObject) {
        //Date timeFormat = new SimpleDateFormat("h:mm a").parse(dateObject);
        //String formattedTime = timeFormat.format(timeFormat);
        String formattedTime=dateObject;
        return formattedTime;
    }
}
