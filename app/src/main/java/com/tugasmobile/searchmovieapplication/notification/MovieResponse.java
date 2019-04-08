package com.tugasmobile.searchmovieapplication.notification;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {
    @SerializedName("results")
    private ArrayList<MovieModel> results;

    public ArrayList<MovieModel> getResults() {
        return results;
    }

}
