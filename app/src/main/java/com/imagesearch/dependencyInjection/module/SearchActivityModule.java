package com.imagesearch.dependencyInjection.module;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.imagesearch.ViewModelProviderFactory;
import com.imagesearch.data.DataManager;
import com.imagesearch.data.network.model.Hits;
import com.imagesearch.dependencyInjection.ActivityContext;
import com.imagesearch.ui.search.ImageListAdapter;
import com.imagesearch.ui.search.SearchActivity;
import com.imagesearch.ui.search.SearchViewModel;
import com.imagesearch.ui.search.fullimage.FullScreenImageDialogViewModel;
import com.imagesearch.utils.rx.AppSchedulerProvider;
import com.imagesearch.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Dagger dependency provider for {@link SearchActivity}
 */
@Module
public class SearchActivityModule {

    private AppCompatActivity mActivity;

    public SearchActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new SearchViewModel(dataManager, schedulerProvider);
    }

    @Provides
    FullScreenImageDialogViewModel provideFullScreenImageDialogViewModel(DataManager dataManager,
                                                                         SchedulerProvider schedulerProvider) {
        return new FullScreenImageDialogViewModel(dataManager, schedulerProvider);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    ViewModelProvider.Factory searchViewModelProvider(SearchViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }

    @Provides
    ImageListAdapter provideNewsListAdapter(AppCompatActivity activity) {
        return new ImageListAdapter(new ArrayList<Hits>());
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(AppCompatActivity activity) {
        return new GridLayoutManager(activity, 2);
    }
}