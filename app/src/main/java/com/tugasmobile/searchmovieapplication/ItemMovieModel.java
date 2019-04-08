package com.tugasmobile.searchmovieapplication;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.tugasmobile.searchmovieapplication.database.DatabaseContract;

import org.json.JSONObject;

public class ItemMovieModel implements Parcelable {

    int id;
    String judul;
    String deskripsi;
    long popularitas;
    double rating;
    String tanggalRilis;
    String imageMovie;
    String background;

    public ItemMovieModel() {

    }

    public ItemMovieModel(JSONObject object) {
        try {
            this.judul = object.getString("title");
            this.deskripsi = object.getString("overview");
            this.popularitas = object.getLong("popularity");
            this.rating = object.getDouble("vote_average");
            this.tanggalRilis = object.getString("release_date");
            this.imageMovie = object.getString("poster_path");
        } catch (Exception e) {
        }
    }

    protected ItemMovieModel(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        deskripsi = in.readString();
        popularitas = in.readLong();
        rating = in.readDouble();
        tanggalRilis = in.readString();
        imageMovie = in.readString();
        background = in.readString();
    }

    public static final Creator<ItemMovieModel> CREATOR = new Creator<ItemMovieModel>() {
        @Override
        public ItemMovieModel createFromParcel(Parcel in) {
            return new ItemMovieModel(in);
        }

        @Override
        public ItemMovieModel[] newArray(int size) {
            return new ItemMovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public long getPopularitas() {
        return popularitas;
    }

    public void setPopularitas(long popularitas) {
        this.popularitas = popularitas;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    public String getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(String imageMovie) {
        this.imageMovie = imageMovie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
        parcel.writeLong(popularitas);
        parcel.writeDouble(rating);
        parcel.writeString(tanggalRilis);
        parcel.writeString(imageMovie);
        parcel.writeString(background);
    }

    public ItemMovieModel(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(String.valueOf(id)));
        this.judul = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.judul));
        this.popularitas = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.popularitas));
        this.imageMovie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.image));
        this.background = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.background));
        this.deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.deskripsi));
        this.tanggalRilis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.tanggal_rilis));
        this.rating = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.rating));
    }

    public ItemMovieModel(int id, long popularitas, Double rating, String judul, String tanggalRilis, String imageMovie, String background, String deskripsi) {
        this.id = id;
        this.popularitas = popularitas;
        this.rating = rating;
        this.judul = judul;
        this.tanggalRilis = tanggalRilis;
        this.imageMovie = imageMovie;
        this.background = background;
        this.deskripsi = deskripsi;
    }
}
