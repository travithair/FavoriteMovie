package com.tugasmobile.searchmovieapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tugasmobile.searchmovieapplication.DetailMovieActivity;
import com.tugasmobile.searchmovieapplication.ItemMovieModel;
import com.tugasmobile.searchmovieapplication.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ItemMovieModel> movieList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(view);
    }

    public void setMovieModels(ArrayList<ItemMovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder myViewHolder, int i) {
        final ItemMovieModel movie = movieList.get(i);
        myViewHolder.judul.setText(movie.getJudul());
        myViewHolder.deskripsi.setText(movie.getDeskripsi());

        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movie.getImageMovie()).placeholder(context.getResources().
                getDrawable(R.drawable.ic_movie_black_24dp)).error(context.getResources().
                getDrawable(R.drawable.ic_movie_black_24dp)).into(myViewHolder.imageMovie);

        myViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);

                intent.putExtra(DetailMovieActivity.EXTRA_ID, movie.getId());
                intent.putExtra(DetailMovieActivity.EXTRA_JUDUL, movie.getJudul());
                intent.putExtra(DetailMovieActivity.EXTRA_RILIS, movie.getTanggalRilis());
                intent.putExtra(DetailMovieActivity.EXTRA_DESKRIPSI, movie.getDeskripsi());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER, movie.getImageMovie());
                intent.putExtra(DetailMovieActivity.EXTRA_POPULARITY, movie.getPopularitas());
                intent.putExtra(DetailMovieActivity.EXTRA_RATING, movie.getRating());
                intent.putExtra(DetailMovieActivity.EXTRA_BACKGROUND, movie.getBackground());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView judul, deskripsi;
        private ImageView imageMovie;
        private Button detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tvTitle);
            deskripsi = itemView.findViewById(R.id.tvDescription);
            detail = itemView.findViewById(R.id.btDetail);
            imageMovie = itemView.findViewById(R.id.ivImageMovie);
        }
    }

    public MovieAdapter(Context context, ArrayList<ItemMovieModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public MovieAdapter(ArrayList<ItemMovieModel> movieModels, Context context) {
        this.movieList = movieModels;
        this.context = context;
    }

    public ArrayList<ItemMovieModel> getMovieModels() {
        return movieList;
    }
}
