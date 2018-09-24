package com.flickster.mplant.flickster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.flickster.mplant.flickster.MovieDetailActivity;
import com.flickster.mplant.flickster.R;
import com.flickster.mplant.flickster.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> mDataset;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public View mMovieView;
        public TextView mTitleView;
        public ImageView mPosterImage;
        public TextView mOverview;
        public ImageView mBackgroundImage;
        public RatingBar mRatingView;
        public int mMovieId;
        public MovieViewHolder(View v) {
            super(v);
            mMovieView = v;
            mTitleView = v.findViewById(R.id.title);
            mPosterImage = v.findViewById(R.id.poster_image);
            mBackgroundImage = v.findViewById(R.id.background_image);
            mOverview = v.findViewById(R.id.overview);
            mRatingView = v.findViewById(R.id.rating);
        }
    }

    public MovieAdapter(ArrayList<Movie> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_layout, parent, false);

        MovieViewHolder vh = new MovieViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Context ctx = holder.mMovieView.getContext();
        Movie movie = mDataset.get(position);
        holder.mMovieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Movie clickedMovie = mDataset.get(holder.getAdapterPosition());
                Intent i = new Intent(ctx, MovieDetailActivity.class);
                i.putExtra("movie_id", clickedMovie.getId());
                ((Activity) ctx).startActivity(i);
            }
        });
        holder.mTitleView.setText(movie.getTitle());
        holder.mOverview.setText(movie.getOverview());
        Drawable fallback = ctx.getResources().getDrawable(R.drawable.image_placeholder);
        int orientation = ctx.getResources().getConfiguration().orientation;
        RequestOptions options = new RequestOptions()
                .centerInside()
                .transform(new RoundedCorners(10))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error);

        holder.mRatingView.setRating(movie.getRating());

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(holder.mMovieView.getContext())
                    .load(movie.getPosterImageUrl())
                    .apply(options)
                    .into(holder.mPosterImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Glide.with(holder.mBackgroundImage.getContext())
                    .load(movie.getBackdropImageUrl())
                    .apply(options)
                    .into(holder.mBackgroundImage);
        }

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
