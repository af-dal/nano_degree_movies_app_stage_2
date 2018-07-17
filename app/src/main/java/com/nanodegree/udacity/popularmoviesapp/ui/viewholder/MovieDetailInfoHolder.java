package com.nanodegree.udacity.popularmoviesapp.ui.viewholder;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.popularmoviesapp.R;
import com.nanodegree.udacity.popularmoviesapp.persistence.contracts.FavoriteMoviesContract;
import com.nanodegree.udacity.popularmoviesapp.ui.model.MovieDetailInfo;
import com.nanodegree.udacity.popularmoviesapp.util.ImageUrlHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailInfoHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.details_movie_poster_image)
    ImageView posterImage;
    @BindView(R.id.details_movie_description_text)
    TextView descriptionText;
    @BindView(R.id.details_movie_release_date_text)
    TextView releaseDateText;
    @BindView(R.id.details_movie_rating_text)
    TextView ratingText;
    @BindView(R.id.details_movie_title_text)
    TextView titleText;
    @BindView(R.id.details_movie_mark_favorite)
    Button markAsFavoriteButton;

    private boolean isMovieFavMarked = false;
    @Nullable private MovieDetailInfo movieDetailInfo;

    private MovieDetailInfoHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static MovieDetailInfoHolder create(final ViewGroup parent) {
        return new MovieDetailInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_detail_info, parent, false));
    }

    public void resetViews() {
        markAsFavoriteButton.setVisibility(View.GONE);
    }

    public void bind(@NonNull final MovieDetailInfo movieDetailInfo) {
        resetViews();

        this.movieDetailInfo = movieDetailInfo;
        Picasso.get().load(ImageUrlHelper.getPosterImageUrl(movieDetailInfo.getMovie().getPosterPath())).into(posterImage);
        titleText.setText(movieDetailInfo.getMovie().getTitle());
        descriptionText.setText(movieDetailInfo.getMovie().getOverview());
        ratingText.setText(itemView.getContext().getString(R.string.details_vote_average, String.valueOf(movieDetailInfo.getMovie().getVoteAverage())));
        releaseDateText.setText(movieDetailInfo.getMovie().getReleaseDate());
        updateFavoriteState(movieDetailInfo);
    }

    private void updateFavoriteState(@NonNull final MovieDetailInfo movieDetailInfo) {
        markAsFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMovieFavMarked) {
                    itemView.getContext().getContentResolver().delete(FavoriteMoviesContract.FavoriteMovieEntry.buildUriWithId(String.valueOf(movieDetailInfo.getMovie().getId())), null, null);
                } else {
                    final ContentValues contentValues = new ContentValues();
                    contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_ID, movieDetailInfo.getMovie().getId());
                    contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_NAME, movieDetailInfo.getMovie().getTitle());
                    itemView.getContext().getContentResolver().insert(FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI, contentValues);
                }
            }
        });

        if (movieDetailInfo.getCursor() != null) {
            if (movieDetailInfo.getCursor().getCount() > 0) {
                markAsFavoriteButton.setText(R.string.details_unmark_favorite);
                isMovieFavMarked = true;
            } else {
                markAsFavoriteButton.setText(R.string.details_mark_favorite);
                isMovieFavMarked = false;
            }
            markAsFavoriteButton.setVisibility(View.VISIBLE);
        }
    }
}