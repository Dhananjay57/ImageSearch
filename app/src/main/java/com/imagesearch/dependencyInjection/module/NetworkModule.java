package com.imagesearch.dependencyInjection.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imagesearch.BuildConfig;
import com.imagesearch.data.cache.CacheInterceptor;
import com.imagesearch.data.cache.CacheManager;
import com.imagesearch.data.network.ApiHelper;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Dagger dependency provider for Network APIs
 */
@Module
public class NetworkModule {

    private String mBaseUrl;

    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        try {
            CacheInterceptor cacheInterceptor = new CacheInterceptor(new CacheManager());
            return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(cacheInterceptor).build();
        } catch (Exception e) {
            return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        }
    }

    @Provides
    @Singleton
    GsonConverterFactory provideConverter(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory converter,
                             RxJava2CallAdapterFactory factory) {
        return new Retrofit.Builder()
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(Retrofit client) {
        return client.create(ApiHelper.class);
    }
}