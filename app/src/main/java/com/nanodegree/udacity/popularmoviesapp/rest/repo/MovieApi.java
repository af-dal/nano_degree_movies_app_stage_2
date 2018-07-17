package com.nanodegree.udacity.popularmoviesapp.rest.repo;

import com.nanodegree.udacity.popularmoviesapp.BuildConfig;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.MoviesResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.ReviewResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {

    @GET("movie/popular?api_key=" + BuildConfig.API_KEY)
    Call<MoviesResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=" + BuildConfig.API_KEY)
    Call<MoviesResponse> getTopRatedMovies();

    @GET("movie/{movieId}/reviews?api_key=" + BuildConfig.API_KEY)
    Call<ReviewResponse> getReviews(@Path("movieId") final String movieId);

    @GET("movie/{movieId}/videos?api_key=" + BuildConfig.API_KEY)
    Call<VideoResponse> getVideos(@Path("movieId") final String movieId);

    @GET("movie/{movieId}?api_key=" + BuildConfig.API_KEY)
    Call<Movie> getMovie(@Path("movieId") final String movieId);
}
