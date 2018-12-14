package com.imagesearch.data;

import android.content.Context;

import com.imagesearch.data.network.ApiHelper;
import com.imagesearch.data.network.model.SearchResponse;
import com.imagesearch.data.preferences.PreferencesHelper;
import com.imagesearch.dependencyInjection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by Varun on 28,July,2018
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = AppDataManager.class.getSimpleName();

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void saveState(String keyString, int val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, String val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, long val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, boolean val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public String loadStringKey(String keyString) {
        return mPreferencesHelper.loadStringKey(keyString);
    }

    @Override
    public int loadIntKey(String keyString, int returnValue) {
        return mPreferencesHelper.loadIntKey(keyString, returnValue);
    }

    @Override
    public long loadLongKey(String keyString, long returnValue) {
        return mPreferencesHelper.loadLongKey(keyString, returnValue);
    }

    @Override
    public boolean loadBooleanKey(String keyString, boolean returnValue) {
        return mPreferencesHelper.loadBooleanKey(keyString, returnValue);
    }

    @Override
    public String loadSettingsStringKey(String keyString, String returnValue) {
        return mPreferencesHelper.loadSettingsStringKey(keyString, returnValue);
    }

    @Override
    public Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                                   @Query("q") String query,
                                                   @Query("image_type") String image_type,
                                                   @Query("page") Integer page,
                                                   @Query("per_page") Integer per_page) {
        return mApiHelper.searchImages(apiKey, query, image_type, page, per_page);
    }

}