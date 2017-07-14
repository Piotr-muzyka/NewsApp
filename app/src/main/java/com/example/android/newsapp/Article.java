package com.example.android.newsapp;

/**
 * Created by PiotrM on 03.07.2017.
 */


// Required fields include the title of the article and the sectionName of the section that it belongs to.
// Optional fields (if available) : author sectionName , publishDate published

import java.util.Date;

/**
 * webPublicationDate	"2017-04-28T18:29:31Z"
 * webTitle	            "Shameful shutdown of pro…ection debate | Letters"
 * sectionName	        "Politics"
 */

public class Article {

    private String title;
    private String sectionName;
    private String publishDate;
    private String url;

    public Article(String title, String sectionName, String publishDate, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.publishDate = publishDate;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getUrl() {
        return url;
    }
}

