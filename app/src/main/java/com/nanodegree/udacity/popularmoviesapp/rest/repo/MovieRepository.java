package com.nanodegree.udacity.popularmoviesapp.rest.repo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nanodegree.udacity.popularmoviesapp.rest.MovieClient;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.MoviesResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.ReviewResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.VideoResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = "MovieRepository";
    private final MovieApi movieApi;

    public MovieRepository() {
        movieApi = MovieClient.getClient();
    }

    public void loadPopularMovies(@NonNull final RepositoryCallback<MoviesResponse> responseCallback) {
        movieApi.getPopularMovies().enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) responseCallback.onSuccess(response.body());
                else responseCallback.onFailure(new Exception("Error loading popular movies"));
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    public void loadTopRatedMovies(@NonNull final RepositoryCallback<MoviesResponse> responseCallback) {
        movieApi.getTopRatedMovies().enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) responseCallback.onSuccess(response.body());
                else responseCallback.onFailure(new Exception("Error loading popular movies"));
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    public void loadTrailerVideos(@NonNull final String movieId, @NonNull final RepositoryCallback<VideoResponse> responseCallback) {
        movieApi.getVideos(movieId).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) responseCallback.onSuccess(response.body());
                else responseCallback.onFailure(new Exception("Error loading trailer"));
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    public void loadMovieReviews(@NonNull final String movieId, @NonNull final RepositoryCallback<ReviewResponse> responseCallback) {
        movieApi.getReviews(movieId).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) responseCallback.onSuccess(response.body());
                else responseCallback.onFailure(new Exception("Error loading Reviews"));
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    @Nullable
    public Movie loadMovieBlocking(@NonNull final String id) {
        Call<Movie> call = movieApi.getMovie(id);
        Movie movie = null;

        try {
            movie = call.execute().body();
        } catch (RuntimeException | IOException e) {
            Log.d(TAG, "Error fetching movie");
        }
        return movie;
    }
}
