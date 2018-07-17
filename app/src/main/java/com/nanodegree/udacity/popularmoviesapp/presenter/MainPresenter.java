package com.nanodegree.udacity.popularmoviesapp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import com.nanodegree.udacity.popularmoviesapp.persistence.FavoriteMovieCursorLoader;
import com.nanodegree.udacity.popularmoviesapp.persistence.IFavoriteMovieCursorCallback;
import com.nanodegree.udacity.popularmoviesapp.persistence.contracts.FavoriteMoviesContract;
import com.nanodegree.udacity.popularmoviesapp.rest.loaders.FavoriteMovieLoader;
import com.nanodegree.udacity.popularmoviesapp.rest.loaders.IFavoriteMovieCallback;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.MoviesResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.MovieRepository;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.RepositoryCallback;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    private static final int FAVORITE_MOVIE_LOADER_ID = 1;
    private static final int MOVIE_LOADER_ID = 2;

    @NonNull
    private final MovieRepository movieRepository;
    @NonNull
    private final IMainListener mainPresenterListener;

    public MainPresenter(@NonNull final IMainListener mainPresenterListener) {
        this.mainPresenterListener = mainPresenterListener;
        this.movieRepository = new MovieRepository();
    }

    public void loadMovies(@NonNull final Context context, @NonNull final LoaderManager loaderManager, @NonNull final MovieLoadType movieLoadType) {
        if (MovieLoadType.POPULAR == movieLoadType) {
            movieRepository.loadPopularMovies(new RepositoryCallback<MoviesResponse>() {
                @Override
                public void onSuccess(@Nullable final MoviesResponse data) {
                    if (data != null) {
                        mainPresenterListener.onMoviesLoaded(data.getMovieList());
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mainPresenterListener.onMoviesError(throwable);
                }
            });
        } else if (MovieLoadType.TOP_RATED == movieLoadType) {
            movieRepository.loadTopRatedMovies(new RepositoryCallback<MoviesResponse>() {
                @Override
                public void onSuccess(@Nullable final MoviesResponse data) {
                    if (data != null) {
                        mainPresenterListener.onMoviesLoaded(data.getMovieList());
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mainPresenterListener.onMoviesError(throwable);
                }
            });
        } else {
            loaderManager.initLoader(FAVORITE_MOVIE_LOADER_ID, null, new FavoriteMovieCursorLoader(context, new IFavoriteMovieCursorCallback() {
                @Override
                public void onCursorLoaded(final Cursor cursor) {
                    final int movieId = cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_ID);
                    final List<String> ids = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        ids.add(cursor.getString(movieId));
                    }
                    cursor.close();

                    loaderManager.destroyLoader(FAVORITE_MOVIE_LOADER_ID);

                    loaderManager.initLoader(MOVIE_LOADER_ID, null, new FavoriteMovieLoader(context, ids, new IFavoriteMovieCallback() {
                        @Override
                        public void onFavoriteMoviesLoaded(final List<Movie> movieList) {
                            mainPresenterListener.onMoviesLoaded(movieList);
                            loaderManager.destroyLoader(MOVIE_LOADER_ID);
                        }

                        @Override
                        public void onFavoriteMoviesError() {
                            mainPresenterListener.onMoviesError(new Throwable("Error loading favorite movies"));
                            loaderManager.destroyLoader(MOVIE_LOADER_ID);
                        }
                    }));
                }
            }, null));
        }
    }

    public enum MovieLoadType {
        TOP_RATED,
        POPULAR,
        FAVORITE
    }
}
