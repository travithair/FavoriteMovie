package com.tugasmobile.searchmovieapplication.notification;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {

    @SerializedName("id")
    private Long id;
    @SerializedName("overview")
    private String deskripsi;
    @SerializedName("poster_path")
    private String imageMovie;
    @SerializedName("release_date")
    private String tanggalRilis;
    @SerializedName("title")
    private String judul;

    public MovieModel(Long id, String deskripsi, String imageMovie, String tanggalRilis, String judul) {
        this.id = id;
        this.deskripsi = deskripsi;
        this.imageMovie = imageMovie;
        this.tanggalRilis = tanggalRilis;
        this.judul = judul;
    }

    public MovieModel(String deskripsi, String imageMovie, String tanggalRilis, String judul) {
        this.deskripsi = deskripsi;
        this.imageMovie = imageMovie;
        this.tanggalRilis = tanggalRilis;
        this.judul = judul;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(String imageMovie) {
        this.imageMovie = imageMovie;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    protected MovieModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        deskripsi = in.readString();
        imageMovie = in.readString();
        tanggalRilis = in.readString();
        judul = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(deskripsi);
        dest.writeString(imageMovie);
        dest.writeString(tanggalRilis);
        dest.writeString(judul);
    }
}
