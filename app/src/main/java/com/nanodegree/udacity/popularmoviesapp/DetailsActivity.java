package com.nanodegree.udacity.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nanodegree.udacity.popularmoviesapp.presenter.DetailPresenter;
import com.nanodegree.udacity.popularmoviesapp.presenter.IDetailListener;
import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.ReviewResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.Video;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.VideoResponse;
import com.nanodegree.udacity.popularmoviesapp.ui.adapter.MovieDetailAdapter;
import com.nanodegree.udacity.popularmoviesapp.ui.model.MovieDetailInfo;
import com.nanodegree.udacity.popularmoviesapp.ui.model.TrailerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements IDetailListener {
    private static final String TAG = "DetailsActivity";
    private static final String BUNDLE_MOVIE = "bundle.movie";
    private static final String BUNDLE_LAYOUT_MANAGER_STATE = "bundle.layout.manager.state";

    @BindView(R.id.movie_detail_recycler)
    RecyclerView movieDetailRecycler;

    private MovieDetailAdapter movieDetailAdapter;
    private List<Object> listData = new ArrayList<>();

    private DetailPresenter detailPresenter;
    private Parcelable linearLayoutState;

    public static Intent createIntent(final Context context, final Movie movie) {
        final Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(BUNDLE_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        detailPresenter = new DetailPresenter(this);
        movieDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        movieDetailAdapter = new MovieDetailAdapter();
        movieDetailRecycler.setAdapter(movieDetailAdapter);

        setTitle(R.string.movie_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        final Movie movie = getIntent().getParcelableExtra(BUNDLE_MOVIE);
        if (movie != null) {
            detailPresenter.loadDetail(movie, this, getSupportLoaderManager());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieDetailRecycler != null && movieDetailRecycler.getLayoutManager() != null) {
            outState.putParcelable(BUNDLE_LAYOUT_MANAGER_STATE, movieDetailRecycler.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        linearLayoutState = savedInstanceState.getParcelable(BUNDLE_LAYOUT_MANAGER_STATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetailLoaded(final Cursor cursor, final Movie movie, VideoResponse videoResponse, ReviewResponse reviewResponse) {
        listData.clear();
        if (cursor != null) {
            listData.add(new MovieDetailInfo(cursor, movie));

            if (videoResponse != null && videoResponse.getVideoList() != null) {
                for (Video video : videoResponse.getVideoList()) {
                    //we want only trailer and we also only support youtube linkout for now
                    if (video.isTrailerVideo() && video.isYouTube()) {
                        listData.add(new TrailerModel(listData.size(), video));
                    }
                }
            }

            if (reviewResponse != null && reviewResponse.getReviewList() != null) {
                listData.addAll(reviewResponse.getReviewList());
            }

            movieDetailAdapter.setItems(listData);
            movieDetailAdapter.notifyDataSetChanged();
            restoreLayoutManagerPosition();
        }
    }

    @Override
    public void onError(String error) {
        Log.d(TAG, "Ups something goes wrong loading movie detail:" + error);
        Toast.makeText(DetailsActivity.this, "Ups something goes wrong loading movie:" + error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.closeCursor();
    }

    private void restoreLayoutManagerPosition() {
        if (linearLayoutState != null && movieDetailRecycler != null && movieDetailRecycler.getLayoutManager() != null) {
            movieDetailRecycler.getLayoutManager().onRestoreInstanceState(linearLayoutState);
        }
    }
}
