package com.imagesearch.dependencyInjection.component;

import com.imagesearch.ui.search.SearchActivity;
import com.imagesearch.dependencyInjection.PerActivity;
import com.imagesearch.dependencyInjection.module.SearchActivityModule;
import com.imagesearch.ui.search.fullimage.FullScreenImageDialog;

import dagger.Component;

/**
 * Created by Varun on 28,July,2018
 */

/**
 *  Dagger component for {@link SearchActivity}
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchActivityModule.class)
public interface SearchActivityComponent {

    void inject(SearchActivity activity);

    void inject(FullScreenImageDialog dialog);

}