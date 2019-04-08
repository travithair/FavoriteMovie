package com.tugasmobile.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tugasmobile.favoritemovie.R;
import com.tugasmobile.favoritemovie.database.DatabaseContract;


import static com.tugasmobile.favoritemovie.database.DatabaseContract.FavoriteColumns.deskripsi;
import static com.tugasmobile.favoritemovie.database.DatabaseContract.FavoriteColumns.judul;

public class MovieAdapter extends CursorAdapter{

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvjudul = view.findViewById(R.id.tvTitle);
            TextView tvdeskripsi = view.findViewById(R.id.tvDescription);
            ImageView imageMovie = view.findViewById(R.id.ivImageMovie);

            tvjudul.setText(DatabaseContract.getColumnString(cursor, judul));
            tvdeskripsi.setText(DatabaseContract.getColumnString(cursor, deskripsi));

            String images = DatabaseContract.getColumnString(cursor, DatabaseContract.FavoriteColumns.image);

            Picasso.get().load("http://image.tmdb.org/t/p/w185/"+ images).into(imageMovie);
        }
    }
}