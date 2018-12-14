package com.imagesearch.dependencyInjection.component;

import com.imagesearch.dependencyInjection.PerActivity;
import com.imagesearch.dependencyInjection.module.SearchActivityModule;
import com.imagesearch.ui.search.fullimage.FullScreenImageDialog;

import dagger.Component;

/**
 *  Dagger component for {@link FullScreenImageDialog}
 */


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchActivityModule.class)
public interface FullScreenImageDialogComponent {

    void inject(FullScreenImageDialog dialog);

}