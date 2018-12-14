package com.imagesearch.data.network;

/**
 * Created by Varun on 28,July,2018
 */

import com.imagesearch.data.network.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface holding signatures for network APIs
 */

public interface ApiHelper {

    /**
     * rest api to search images from https://pixabay.com/
     *
     * @param apiKey     api key for accessing data from
     * @param query      search term
     * @param image_type type of context to search (photo/video)
     * @param page       page to return
     * @param per_page   total items in page
     * @return data in {@link SearchResponse} form
     */

    @GET("/api/")
    Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                            @Query("q") String query,
                                            @Query("image_type") String image_type,
                                            @Query("page") Integer page,
                                            @Query("per_page") Integer per_page);
}
