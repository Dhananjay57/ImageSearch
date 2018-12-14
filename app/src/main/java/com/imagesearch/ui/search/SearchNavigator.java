package com.imagesearch.ui.search;

import com.imagesearch.data.network.model.Hits;

import java.util.List;

/**
 * Created by Varun on 28,July,2018
 */

public interface SearchNavigator {

    void setAdapter(List<Hits> newsItems);

    void onError(Throwable error);
}