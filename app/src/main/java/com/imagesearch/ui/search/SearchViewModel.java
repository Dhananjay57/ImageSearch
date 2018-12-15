package com.imagesearch.ui.search;

import android.databinding.ObservableField;
import android.util.Log;

import com.imagesearch.BuildConfig;
import com.imagesearch.data.DataManager;
import com.imagesearch.data.network.QueryBuilder;
import com.imagesearch.data.network.model.Hits;
import com.imagesearch.ui.base.BaseViewModel;
import com.imagesearch.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;


public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    public final ObservableField<String> appVersion = new ObservableField<>();

    public Integer mPage;

    public SearchViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }

    /**
     * fetch news articles from {@link com.imagesearch.BuildConfig#BASE_URL}
     *
     * @param query search term
     *              //     * @param offset offset for pagination
     */
    public void fetchImages(String query, final int page) {
        getCompositeDisposable().add(getDataManager()
                .searchImages(BuildConfig.PIXABAY_API_KEY, query, QueryBuilder.PIXABAY_PARAM_SEARCH_TYPE, page, 12)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(searchResponse -> {

                    mPage = page;

                    List<Hits> images = new ArrayList<Hits>();

                    if (searchResponse == null) {
                        getNavigator().setAdapter(images);
                        return;
                    }

                    if (searchResponse.getHits() != null && searchResponse.getHits().size() > 0) {
                        images = searchResponse.getHits();
                    } else {
                        getNavigator().onError(null);
                    }
                    getNavigator().setAdapter(images);
                }, t -> {

                    Log.d("MYLOGS", "Throwable: " + t.toString());

                    getNavigator().setAdapter(new ArrayList<Hits>());
                    getNavigator().onError(t);
                }));
    }

    public int getNextPageOffset() {

        return mPage++;
    }

    public int getCurrentPageOffset() {

        return mPage;
    }
}