package com.flickster.mplant.flickster.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    public String getTitle() {
        return title;
    }

    public String getPosterImageUrl() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterImageUrl);
    }

    public String getOverview() {
        return overview;
    }

    public float getRating() {
        return (float) (rating * .5);
    }

    public int getId() {
        return id;
    }

    public String getBackdropImageUrl() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropImageUrl);
    }

    private String title;
    private String posterImageUrl;
    private String overview;
    private int rating;
    private int id;
    private String backdropImageUrl;
    public Movie(JSONObject movieJSONData) throws JSONException {
        this.title = movieJSONData.getString("title");
        this.posterImageUrl = movieJSONData.getString("poster_path");
        this.backdropImageUrl = movieJSONData.getString("backdrop_path");
        this.overview = movieJSONData.getString("overview");
        this.rating = movieJSONData.getInt("vote_average");
        this.id = movieJSONData.getInt("id");
    }
    public static ArrayList<Movie> fromJSONArray (JSONArray moviesJSON) throws JSONException{
        ArrayList<Movie> results = new ArrayList<>();
        for(int i = 0; i < moviesJSON.length(); i++){
            results.add(new Movie(moviesJSON.getJSONObject(i)));
        }
        return results;
    }

}
