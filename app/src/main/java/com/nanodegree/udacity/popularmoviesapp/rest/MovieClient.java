package com.nanodegree.udacity.popularmoviesapp.rest;

import com.google.gson.Gson;
import com.nanodegree.udacity.popularmoviesapp.BuildConfig;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {

    private static MovieApi movieApi = null;

    public static MovieApi getClient() {
        if (movieApi == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

            movieApi = retrofit.create(MovieApi.class);
        }
        return movieApi;
    }
}
