package com.imagesearch.dependencyInjection.module;

import android.app.Application;
import android.content.Context;

import com.imagesearch.AppConstants;
import com.imagesearch.BuildConfig;
import com.imagesearch.data.AppDataManager;
import com.imagesearch.data.DataManager;
import com.imagesearch.data.preferences.AppPreferencesHelper;
import com.imagesearch.data.preferences.PreferencesHelper;
import com.imagesearch.dependencyInjection.ApiInfo;
import com.imagesearch.dependencyInjection.ApplicationContext;
import com.imagesearch.dependencyInjection.PreferenceInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *  Dagger dependency provider for {@link com.imagesearch.ImageSearchApp}
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.PIXABAY_API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}