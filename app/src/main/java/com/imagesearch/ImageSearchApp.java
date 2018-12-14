package com.imagesearch;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.imagesearch.data.DataManager;
import com.imagesearch.dependencyInjection.component.ApplicationComponent;
import com.imagesearch.dependencyInjection.component.DaggerApplicationComponent;
import com.imagesearch.dependencyInjection.module.ApplicationModule;
import com.imagesearch.dependencyInjection.module.NetworkModule;

import javax.inject.Inject;

public class ImageSearchApp extends Application {

    @Inject
    DataManager mDataManager;

    // Global Instance, used by application
    private static ImageSearchApp sInstance;

    // GLOBAL SharedPrefs
    private SharedPreferences mSharedPrefs;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).
                        networkModule(new NetworkModule(BuildConfig.BASE_URL)).
                        build();

        mApplicationComponent.inject(this);
    }

    /**
     * STATIC METHODS
     * <p/>
     * exposing global information like :
     * 1. Context
     * 2. App Resources
     * 3. Shared Preferences
     * 4. Connected to Internet
     */
    public static synchronized ImageSearchApp getInstance() {
        return sInstance;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Required to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static synchronized SharedPreferences getSharedPrefs() {

        return getInstance().getSharedPreferences();
    }

    public SharedPreferences getSharedPreferences() {

        if (mSharedPrefs == null)
            mSharedPrefs = sInstance.getSharedPreferences(com.imagesearch.AppConstants.PREF_NAME, MODE_PRIVATE);

        return mSharedPrefs;
    }

    public static synchronized boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) sInstance.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}