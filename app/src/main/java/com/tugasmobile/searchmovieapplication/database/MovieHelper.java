package com.tugasmobile.searchmovieapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.tugasmobile.searchmovieapplication.ItemMovieModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.background;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.deskripsi;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.image;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.judul;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.popularitas;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.rating;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.tanggal_rilis;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.NAMA_TABEL;

public class MovieHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<ItemMovieModel> query() {
        ArrayList<ItemMovieModel> arrayList = new ArrayList<>();
        Cursor cursor = database.query(NAMA_TABEL,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);

        cursor.moveToFirst();
        ItemMovieModel movieModel;

        if (cursor.getCount() > 0) {
            do {
                movieModel = new ItemMovieModel();

                movieModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(judul)));
                movieModel.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(tanggal_rilis)));
                movieModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(deskripsi)));
                movieModel.setImageMovie(cursor.getString(cursor.getColumnIndexOrThrow(image)));
                movieModel.setBackground(cursor.getString(cursor.getColumnIndexOrThrow(background)));
                movieModel.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(rating)));
                movieModel.setPopularitas(cursor.getLong(cursor.getColumnIndexOrThrow(popularitas)));

                arrayList.add(movieModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(NAMA_TABEL,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return database.query(NAMA_TABEL,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public long insert(ContentValues values) {
        return database.insert(NAMA_TABEL, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(NAMA_TABEL, values, _ID + " ?", new String[]{id});
    }

    public int delete(String id) {
        return database.delete(NAMA_TABEL, _ID + " = ?", new String[]{id});
    }

}
