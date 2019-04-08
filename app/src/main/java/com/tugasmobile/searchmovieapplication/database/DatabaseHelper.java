package com.tugasmobile.searchmovieapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.background;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.deskripsi;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.image;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.judul;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.popularitas;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.rating;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.FavoriteColumns.tanggal_rilis;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.NAMA_TABEL;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_TABLE = "movie";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL);
        onCreate(sqLiteDatabase);
    }

    private static final String TABLE = String.format("CREATE TABLE %s" +
                    "(%s INTEGER NOT NULL PRIMARY KEY, " +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s DOUBLE NOT NULL," +
                    "%s LONG NOT NULL)",
            NAMA_TABEL,
            _ID,
            judul,
            tanggal_rilis,
            deskripsi,
            image,
            background,
            rating,
            popularitas);
}
