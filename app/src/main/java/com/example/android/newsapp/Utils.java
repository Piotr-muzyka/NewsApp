package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PiotrM on 03.07.2017.
 */

public class Utils {

    // Tag setting.
    private static final String LOG_TAG = Utils.class.getSimpleName();

    private Utils() {
    }

    public static List<Article> fetchArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Article}
        return articles;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");


            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            Log.v(LOG_TAG, "before if statement");
            if (response.has("results")) {
                JSONArray articleArray = response.getJSONArray("results");

                // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
                for (int i = 0; i < articleArray.length(); i++) {

                    // Get a single earthquake at position i within the list of earthquakes
                    JSONObject currentArticle = articleArray.getJSONObject(i);

                    Log.v(LOG_TAG, "IN if statement");
                    // For a given earthquake, extract the JSONObject associated with the
                    // key called "properties", which represents a list of all properties
                    // for that earthquake.

                    // Extract the value for the key called "webTitle"
                    String title = currentArticle.getString("webTitle");
                    Log.v(LOG_TAG, "title: " + title);
                    // Extract the value for the key called "sectionName"
                    String sectionName = currentArticle.getString("sectionName");
                    Log.v(LOG_TAG, "section: " + sectionName);
                    // Extract the value for the key called "webPublicationDate"
                    String time = currentArticle.getString("webPublicationDate");
                    Log.v(LOG_TAG, "date: " + time);
                    // Extract the value for the key called "webUrl"
                    String url = currentArticle.getString("webUrl");
                    Log.v(LOG_TAG, "url: " + url);
                    // Create a new {@link Earthquake} object with the magnitude, location, time,
                    // and url from the JSON response.

                    Article article = new Article(title, sectionName, time, url);

                    // Add the new {@link Earthquake} to the list of earthquakes.
                    articles.add(article);
                }
            }
            Log.v(LOG_TAG, "after if statement");

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of earthquakes
        return articles;
    }

}

