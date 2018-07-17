package com.nanodegree.udacity.popularmoviesapp.ui.model;

import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.Video;

public class TrailerModel {

    private final int number;

    private final Video video;

    public TrailerModel(int number, Video video) {
        this.number = number;
        this.video = video;
    }

    public int getNumber() {
        return number;
    }

    public Video getVideo() {
        return video;
    }
}
