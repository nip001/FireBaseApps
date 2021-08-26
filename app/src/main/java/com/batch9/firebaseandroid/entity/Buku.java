package com.batch9.firebaseandroid.entity;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buku implements Parcelable {
    private String id;
    private String namaBuku;
    private String author;
    private int jumlahHalaman;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.namaBuku);
        dest.writeString(this.author);
        dest.writeInt(this.jumlahHalaman);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.namaBuku = source.readString();
        this.author = source.readString();
        this.jumlahHalaman = source.readInt();
    }

    protected Buku(Parcel in) {
        this.id = in.readString();
        this.namaBuku = in.readString();
        this.author = in.readString();
        this.jumlahHalaman = in.readInt();
    }

    public static final Creator<Buku> CREATOR = new Creator<Buku>() {
        @Override
        public Buku createFromParcel(Parcel source) {
            return new Buku(source);
        }

        @Override
        public Buku[] newArray(int size) {
            return new Buku[size];
        }
    };
}
