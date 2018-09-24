package com.flickster.mplant.flickster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.flickster.mplant.flickster.models.MovieDetail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends AppCompatActivity {
    public TextView mTitleView;
    public ImageView mPosterImage;
    public TextView mOverview;
    public TextView mTagline;
    public RatingBar mRatingView;
    public TextView mRuntime;
    public TextView mReleaseDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", 0);


        AsyncHttpClient client = new AsyncHttpClient();
        setContentView(R.layout.movie_detail);
        client.get(this.getMovieUrl(movieId), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject movieJSON = null;
                try {
                    updateViews(new MovieDetail(response));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    public void updateViews(MovieDetail movieDetail){
        String title = movieDetail.getTitle();
        mTitleView = findViewById(R.id.title);
        mPosterImage = findViewById(R.id.poster_image);
        mOverview = findViewById(R.id.overview);
        mRatingView = findViewById(R.id.rating);
        mReleaseDate = findViewById(R.id.release_date);
        mRuntime = findViewById(R.id.runtime);
        mTagline = findViewById(R.id.tagline);

        findViewById(R.id.runtime);


        mTitleView.setText(title);
        RequestOptions options = new RequestOptions()
                .centerInside()
                .transform(new RoundedCorners(10))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error);
        Context ctx = mPosterImage.getContext();
        Glide.with(ctx)
                .load(movieDetail.getPosterImageUrl())
                .apply(options)
                .into(mPosterImage);
        mOverview.setText(movieDetail.getOverview());
        mRatingView.setRating(movieDetail.getRating());
        mTagline.setText(movieDetail.getTagLine());
        String rt = String.valueOf(movieDetail.getRuntime());
        String runtimeText = ctx.getResources().getString(R.string.runtime, rt);
        mRuntime.setText(runtimeText);
        String releaseDateText = ctx.getResources().getString(R.string.runtime, movieDetail.getReleaseDate());
        mReleaseDate.setText(releaseDateText);

    }

    public String getMovieUrl(int id){
        return String.format("https://api.themoviedb.org/3/movie/%s?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
    }

}
