package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by PiotrM on 03.07.2017.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private static final String LOG_TAG = ArticleLoader.class.getName();
    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // network request, parsing, extraction of a list of articles.
        List<Article> articles = Utils.fetchArticleData(mUrl);
        return articles;
    }
}
