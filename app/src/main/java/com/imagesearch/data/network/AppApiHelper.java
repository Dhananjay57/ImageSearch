package com.imagesearch.data.network;

import com.imagesearch.data.network.model.SearchResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Query;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    public AppApiHelper() {
    }

    @Override
    public Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                                   @Query("q") String query,
                                                   @Query("image_type") String image_type,
                                                   @Query("page") Integer page,
                                                   @Query("per_page") Integer per_page) {
        return null;
    }
}