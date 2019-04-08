package com.tugasmobile.searchmovieapplication.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.AUTHORITY;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {

    static final int MOVIE = 1;
    static final int MOVIE_ID = 2;
    MovieHelper movieHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DatabaseContract.NAMA_TABEL, MOVIE);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.NAMA_TABEL + "/#", MOVIE_ID);
    }


    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int update;

        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                update = movieHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                update = 0;
                break;
        }

        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long add;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                add = movieHelper.insert(contentValues);
                break;
            default:
                add = 0;
                break;
        }

        if (add > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + add);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int delete;

        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                delete = movieHelper.delete(uri.getLastPathSegment());
                break;
            default:
                delete = 0;
                break;
        }

        if (delete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delete;
    }
}
