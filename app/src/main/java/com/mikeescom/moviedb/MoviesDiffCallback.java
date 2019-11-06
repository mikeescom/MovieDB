package com.mikeescom.moviedb;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.mikeescom.moviedb.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesDiffCallback extends DiffUtil.Callback {

    List<Movie> oldBooksList;
    List<Movie> newBooksList;

    public MoviesDiffCallback(List<Movie> oldBooksList, List<Movie> newBooksList) {
        this.oldBooksList = oldBooksList;
        this.newBooksList = newBooksList;
    }

    @Override
    public int getOldListSize() {
        return oldBooksList == null ? 0 : oldBooksList.size();
    }

    @Override
    public int getNewListSize() {
        return newBooksList == null ? 0 : newBooksList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBooksList.get(oldItemPosition).getId() == newBooksList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBooksList.get(oldItemPosition).equals(newBooksList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}