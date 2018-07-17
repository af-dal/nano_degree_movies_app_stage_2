package com.nanodegree.udacity.popularmoviesapp.ui.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacity.popularmoviesapp.R;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewInfoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.details_movie_review_title)
    View reviewsTitle;
    @BindView(R.id.details_movie_review_content)
    TextView contentText;
    @BindView(R.id.details_movie_review_author)
    TextView authorText;

    private MovieReviewInfoHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static MovieReviewInfoHolder create(final ViewGroup parent) {
        return new MovieReviewInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_review, parent, false));
    }

    public void resetViews() {
        reviewsTitle.setVisibility(View.GONE);
        itemView.setOnClickListener(null);
    }

    public void bind(@NonNull final Review review, boolean isFirstReview) {
        resetViews();
        if (isFirstReview) {
            reviewsTitle.setVisibility(View.VISIBLE);
        }
        contentText.setText(review.getContent());
        authorText.setText(review.getAuthor());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())));
            }
        });
    }
}