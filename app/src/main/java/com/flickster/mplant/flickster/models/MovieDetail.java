package com.flickster.mplant.flickster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetail extends Movie {
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getTagLine() {
        return tagLine;
    }
    public int getRuntime() {
        return runtime;
    }
    private String releaseDate;
    private String tagLine;
    private int runtime;
    public MovieDetail(JSONObject movieJSONData) throws JSONException {
        super(movieJSONData);
        this.releaseDate = movieJSONData.getString("release_date");
        this.tagLine = movieJSONData.getString("tagline");
        this.runtime = movieJSONData.getInt("runtime");
    }

}
