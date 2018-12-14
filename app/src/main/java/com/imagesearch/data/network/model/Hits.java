package com.imagesearch.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Comment;

/**
 * Created by Varun on 28,July,2018
 */

public class Hits implements Parcelable {

    @SerializedName("previewURL")
    @Expose
    private String previewURL;

    @SerializedName("largeImageURL")
    @Expose
    private String largeImageURL;

    @SerializedName("tags")
    @Expose
    private String tags;

    public Hits() {

    }

    public static final Creator<Hits> CREATOR = new Creator<Hits>() {

        @Override
        public Hits createFromParcel(Parcel parcel) {
            return new Hits(parcel);
        }

        @Override
        public Hits[] newArray(int size) {
            return new Hits[size];
        }
    };

    /**
     * No args constructor for use in serialization
     */
    public Hits(String previewURL, String largeImageURL, String tags) {
        super();
        this.previewURL = previewURL;
        this.largeImageURL = largeImageURL;
        this.tags = tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getTags() {
        return tags;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    private Hits(Parcel parcel) {
        this.previewURL = parcel.readString();
        this.largeImageURL = parcel.readString();
        this.tags = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(previewURL);
        parcel.writeString(largeImageURL);
        parcel.writeString(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
