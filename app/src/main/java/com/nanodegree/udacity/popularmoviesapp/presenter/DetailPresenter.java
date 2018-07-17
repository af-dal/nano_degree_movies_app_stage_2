package com.nanodegree.udacity.popularmoviesapp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import com.nanodegree.udacity.popularmoviesapp.persistence.FavoriteMovieCursorLoader;
import com.nanodegree.udacity.popularmoviesapp.persistence.IFavoriteMovieCursorCallback;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.ReviewResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.VideoResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.MovieRepository;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.RepositoryCallback;

public class DetailPresenter {
    @NonNull
    private final MovieRepository movieRepository;
    @NonNull
    private final IDetailListener detailListener;
    @Nullable
    private VideoResponse videoResponse;
    @Nullable
    private ReviewResponse reviewResponse;
    @Nullable
    private Cursor cursor;

    public DetailPresenter(@NonNull final IDetailListener detailListener) {
        this.detailListener = detailListener;
        this.movieRepository = new MovieRepository();
    }

    public void loadDetail(@NonNull final Movie movie, final Context context, final LoaderManager loaderManager) {
        movieRepository.loadTrailerVideos(String.valueOf(movie.getId()), new RepositoryCallback<VideoResponse>() {
            @Override
            public void onSuccess(@Nullable final VideoResponse data) {
                videoResponse = data;
                detailListener.onDetailLoaded(cursor, movie, videoResponse, reviewResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                detailListener.onError("Ups something goes wrong loading videos : " + throwable.getMessage());
            }
        });

        loaderManager.restartLoader(1, null, new FavoriteMovieCursorLoader(context, new IFavoriteMovieCursorCallback() {
            @Override
            public void onCursorLoaded(final Cursor cursor) {
                DetailPresenter.this.cursor = cursor;
                detailListener.onDetailLoaded(cursor, movie, videoResponse, reviewResponse);
            }
        }, String.valueOf(movie.getId())));

        movieRepository.loadMovieReviews(String.valueOf(movie.getId()), new RepositoryCallback<ReviewResponse>() {
            @Override
            public void onSuccess(@Nullable ReviewResponse data) {
                reviewResponse = data;
                detailListener.onDetailLoaded(cursor, movie, videoResponse, reviewResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                detailListener.onError("Ups something goes wrong loading reviews:" + throwable.getMessage());
            }
        });
    }

    public void closeCursor() {
        if (cursor != null) {
            cursor.close();
        }
    }
}

