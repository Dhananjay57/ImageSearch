package com.imagesearch.dependencyInjection.component;

import android.app.Application;
import android.content.Context;

import com.imagesearch.ImageSearchApp;
import com.imagesearch.data.DataManager;
import com.imagesearch.dependencyInjection.ApplicationContext;
import com.imagesearch.dependencyInjection.module.ApplicationModule;
import com.imagesearch.dependencyInjection.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Varun on 28,July,2018
 */

/**
 *  Dagger component for {@link ImageSearchApp}
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(ImageSearchApp imageSearchApp);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}