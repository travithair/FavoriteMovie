package com.tugasmobile.favoritemovie;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.tugasmobile.favoritemovie.adapter.MovieAdapter;

import static com.tugasmobile.favoritemovie.database.DatabaseContract.CONTENT_URI;
import static com.tugasmobile.favoritemovie.database.DatabaseContract.FavoriteColumns.judul;
import static com.tugasmobile.favoritemovie.database.DatabaseContract.getColumnDouble;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    int id = 1;
    ListView listView;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        listView = findViewById(R.id.lv_movie);
        movieAdapter = new MovieAdapter(this, null, true);
        listView.setAdapter(movieAdapter);

        getSupportLoaderManager().initLoader(id, null, this);

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        Stetho.initialize(initializerBuilder.build());

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, judul);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        movieAdapter.swapCursor(cursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getSupportLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }
}
