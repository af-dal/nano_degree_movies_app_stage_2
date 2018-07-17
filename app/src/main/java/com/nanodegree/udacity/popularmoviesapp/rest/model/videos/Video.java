package com.nanodegree.udacity.popularmoviesapp.rest.model.videos;

import com.google.gson.annotations.SerializedName;

public class Video {

    private String id;
    private String name;
    private String key;
    private Type type;
    private Site site;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public boolean isTrailerVideo() {
        return type != null && type == Type.TRAILER;
    }

    public boolean isYouTube() {
        return site != null && site == Site.YOUTUBE;
    }

    public enum Type {
        @SerializedName("Trailer")
        TRAILER
    }

    public enum Site {
        @SerializedName("YouTube")
        YOUTUBE
    }

}
