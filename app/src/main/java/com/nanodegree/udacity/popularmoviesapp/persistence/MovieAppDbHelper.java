package com.nanodegree.udacity.popularmoviesapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nanodegree.udacity.popularmoviesapp.persistence.contracts.FavoriteMoviesContract;

public class MovieAppDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME + " (" +
            FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
            FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieDb.db";

    public MovieAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}