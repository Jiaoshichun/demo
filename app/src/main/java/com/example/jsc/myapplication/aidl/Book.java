package com.example.jsc.myapplication.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jsc on 2018/1/17.
 */

public class Book implements Parcelable {
    public String name;
    public String tag;
    public double prices;

    public Book(String name, String tag, double prices) {
        this.name = name;
        this.tag = tag;
        this.prices = prices;
    }

    protected Book(Parcel in) {
        name = in.readString();
        prices = in.readDouble();
        tag = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(prices);
        dest.writeString(tag);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", prices=" + prices +
                '}';
    }
}
