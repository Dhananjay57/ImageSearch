package com.imagesearch.ui.search;

import com.imagesearch.data.network.model.Hits;

import java.util.List;


public interface SearchNavigator {

    void setAdapter(List<Hits> newsItems);

    void onError(Throwable error);
}