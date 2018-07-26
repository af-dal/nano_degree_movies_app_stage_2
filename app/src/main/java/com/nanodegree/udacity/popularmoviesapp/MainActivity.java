package com.nanodegree.udacity.popularmoviesapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nanodegree.udacity.popularmoviesapp.presenter.IMainListener;
import com.nanodegree.udacity.popularmoviesapp.presenter.MainPresenter;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.ui.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainListener {
    private static final String BUNDLE_MOVIE_LOAD_TYPE = "bundle.movie.load.type";
    private static final String BUNDLE_LAYOUT_MANAGER_STATE = "bundle.layout.manager.state";
    private static final String TAG = "MainActivity";

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    private MovieAdapter movieAdapter;
    private MainPresenter mainPresenter;
    private Parcelable linearLayoutState;
    private MainPresenter.MovieLoadType movieLoadType = MainPresenter.MovieLoadType.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadMovies(this, getSupportLoaderManager(), movieLoadType);
        updateCurrentTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getIntent().getExtras() == null) return;

        if (getIntent().getExtras().getSerializable(BUNDLE_MOVIE_LOAD_TYPE) != null) {
            movieLoadType = (MainPresenter.MovieLoadType) getIntent().getExtras().getSerializable(BUNDLE_MOVIE_LOAD_TYPE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_by_top_rated:
                this.movieLoadType = MainPresenter.MovieLoadType.TOP_RATED;
                break;
            case R.id.sort_by_popular:
                this.movieLoadType = MainPresenter.MovieLoadType.POPULAR;
                break;
            case R.id.sort_by_favorite:
                this.movieLoadType = MainPresenter.MovieLoadType.FAVORITE;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        mainPresenter.loadMovies(this, getSupportLoaderManager(), this.movieLoadType);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_MOVIE_LOAD_TYPE, movieLoadType);

        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            outState.putParcelable(BUNDLE_LAYOUT_MANAGER_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getSerializable(BUNDLE_MOVIE_LOAD_TYPE) != null) {
            movieLoadType = (MainPresenter.MovieLoadType) savedInstanceState.getSerializable(BUNDLE_MOVIE_LOAD_TYPE);
        }

        linearLayoutState = savedInstanceState.getParcelable(BUNDLE_LAYOUT_MANAGER_STATE);
    }

    private void updateCurrentTitle() {
        @StringRes final int stringRes;
        if (MainPresenter.MovieLoadType.POPULAR == movieLoadType) {
            stringRes = R.string.menu_sort_by_popular;
        } else if (MainPresenter.MovieLoadType.TOP_RATED == movieLoadType) {
            stringRes = R.string.menu_sort_by_top_rated;
        } else {
            stringRes = R.string.menu_sort_by_favorite;
        }
        setTitle(stringRes);
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onMoviesLoaded(@Nullable final List<Movie> movieList) {
        if (movieList != null) {
            movieAdapter.setMovieList(new ArrayList<>(movieList));
            updateCurrentTitle();
            restoreLayoutManagerPosition();
        }
    }

    @Override
    public void onMoviesError(@NonNull final Throwable throwable) {
        Log.d(TAG, "Ups something goes wrong loading movies:" + throwable.getMessage());
        Toast.makeText(this, "Ups something goes wrong loading movies:" + throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void restoreLayoutManagerPosition() {
        if (linearLayoutState != null && recyclerView != null && recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(linearLayoutState);
            linearLayoutState = null;
        }
    }
}
