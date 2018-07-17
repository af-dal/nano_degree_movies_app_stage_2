package com.nanodegree.udacity.popularmoviesapp.rest.model.review;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("results")
    private List<Review> reviewList;

    @Nullable
    public List<Review> getReviewList() {
        return reviewList;
    }
}
