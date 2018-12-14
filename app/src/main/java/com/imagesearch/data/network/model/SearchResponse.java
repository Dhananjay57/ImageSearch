package com.imagesearch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Varun on 28,July,2018
 */

public class SearchResponse {

    @SerializedName("hits")
    @Expose
    private List<Hits> images;

    /**
     * @param images
     */
    public SearchResponse(List<Hits> images) {
        super();
        this.images = images;
    }

    public List<Hits> getHits() {
        return images;
    }

    public void setImages(List<Hits> images) {
        this.images = images;
    }
}
