package com.nanodegree.udacity.popularmoviesapp.rest.model.videos;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("results")
    private List<Video> videoList;

    @Nullable
    public List<Video> getVideoList() {
        return videoList;
    }
}
