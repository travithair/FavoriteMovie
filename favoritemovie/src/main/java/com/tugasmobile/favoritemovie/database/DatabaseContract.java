package com.tugasmobile.favoritemovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String NAMA_TABEL = "favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String judul = "judul";
        public static String deskripsi = "deskripsi";
        public static String popularitas = "popularitas";
        public static String rating = "rating";
        public static String tanggal_rilis = "tanggal_rilis";
        public static String image = "image";
        public static String background = "background";
    }

    public static String AUTHORITY = "com.tugasmobile.searchmovieapplication";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(NAMA_TABEL)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }

    public static Long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
