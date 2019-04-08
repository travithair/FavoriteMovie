package com.tugasmobile.searchmovieapplication;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tugasmobile.searchmovieapplication.database.DatabaseContract;
import com.tugasmobile.searchmovieapplication.database.MovieHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.tugasmobile.searchmovieapplication.database.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    TextView judul, deskripsi, popularitas, rating, tanggalRilis;
    ImageView imageMovie;
    ImageButton favorit;
    MovieHelper movieHelper;
    boolean isFavorite = false;

    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_JUDUL = "extra_judul";
    public static String EXTRA_RILIS = "extra_rilis";
    public static String EXTRA_DESKRIPSI = "extra_deskripsi";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_POPULARITY = "extra_popularity";
    public static String EXTRA_BACKGROUND = "extra_background";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent().getStringExtra(EXTRA_JUDUL));

        judul = findViewById(R.id.tvTitle);
        deskripsi = findViewById(R.id.tvDescription);
        popularitas = findViewById(R.id.tvPopularity);
        rating = findViewById(R.id.tvRating);
        tanggalRilis = findViewById(R.id.tvDateRelease);
        imageMovie = findViewById(R.id.ivImageMovie);
        favorit = findViewById(R.id.iv_favorite);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        judul.setText(getIntent().getStringExtra(EXTRA_JUDUL));
        deskripsi.setText(getIntent().getStringExtra(EXTRA_DESKRIPSI));
        tanggalRilis.setText(getIntent().getStringExtra(EXTRA_RILIS));
        rating.setText(String.valueOf(getIntent().getDoubleExtra(EXTRA_RATING, 0)));
        popularitas.setText(String.valueOf(getIntent().getLongExtra(EXTRA_POPULARITY, 0)));
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + getIntent().getStringExtra(EXTRA_POSTER)).placeholder(this.getResources().
                getDrawable(R.drawable.ic_movie_black_24dp)).error(this.getResources().
                getDrawable(R.drawable.ic_movie_black_24dp)).into(imageMovie);

        ArrayList<ItemMovieModel> list = movieHelper.query();

        for (int i = 0; i < list.size(); i++) {
            if (getIntent().getIntExtra(EXTRA_ID, 0) == list.get(i).getId()) {
                isFavorite = true;
            }
        }

        if (isFavorite) {
            favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        } else {
            favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }

        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues contentValues = new ContentValues();

                contentValues.put(_ID, getIntent().getIntExtra(EXTRA_ID, 0));
                contentValues.put(DatabaseContract.FavoriteColumns.judul, getIntent().getStringExtra(EXTRA_JUDUL));
                contentValues.put(DatabaseContract.FavoriteColumns.tanggal_rilis, getIntent().getStringExtra(EXTRA_RILIS));
                contentValues.put(DatabaseContract.FavoriteColumns.deskripsi, getIntent().getStringExtra(EXTRA_DESKRIPSI));
                contentValues.put(DatabaseContract.FavoriteColumns.image, getIntent().getStringExtra(EXTRA_POSTER));
                contentValues.put(DatabaseContract.FavoriteColumns.background, getIntent().getStringExtra(EXTRA_BACKGROUND));
                contentValues.put(DatabaseContract.FavoriteColumns.rating, getIntent().getDoubleExtra(EXTRA_RATING, 0));
                contentValues.put(DatabaseContract.FavoriteColumns.popularitas, getIntent().getLongExtra(EXTRA_POPULARITY, 0));
                if (isFavorite) {
                    favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    movieHelper.delete(String.valueOf(getIntent().getIntExtra(EXTRA_ID, 0)));
                } else {
                    favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
                    getContentResolver().insert(CONTENT_URI, contentValues);
                }
            }
        });
    }
}
