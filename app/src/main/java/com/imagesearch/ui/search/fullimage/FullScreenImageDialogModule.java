package com.imagesearch.ui.search.fullimage;

import com.imagesearch.data.DataManager;
import com.imagesearch.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class FullScreenImageDialogModule {

    @Provides
    FullScreenImageDialogViewModel provideRateUsViewModel(DataManager dataManager,
                                                          SchedulerProvider schedulerProvider) {
        return new FullScreenImageDialogViewModel(dataManager, schedulerProvider);
    }

}