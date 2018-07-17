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
import com.nanodegree.udacity.popularmoviesapp.ui.model.TrailerModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerInfoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.details_movie_trailer_title)
    View trailerTitle;
    @BindView(R.id.details_movie_trailer)
    TextView trailerNumber;

    private TrailerInfoHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static TrailerInfoHolder create(final ViewGroup parent) {
        return new TrailerInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_trailer, parent, false));
    }

    public void resetViews() {
        trailerTitle.setVisibility(View.GONE);
        itemView.setOnClickListener(null);
    }

    public void bind(@NonNull final TrailerModel trailerModel) {
        resetViews();

        if (trailerModel.getNumber() == 1) {
            trailerTitle.setVisibility(View.VISIBLE);
        }

        trailerNumber.setText(itemView.getContext().getString(R.string.details_trailer, String.valueOf(trailerModel.getNumber())));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + trailerModel.getVideo().getKey())));
            }
        });
    }
}