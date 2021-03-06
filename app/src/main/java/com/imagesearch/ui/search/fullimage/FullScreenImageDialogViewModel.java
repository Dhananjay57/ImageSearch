package com.imagesearch.ui.search.fullimage;

import com.imagesearch.data.DataManager;
import com.imagesearch.ui.base.BaseViewModel;
import com.imagesearch.utils.rx.SchedulerProvider;

public class FullScreenImageDialogViewModel extends BaseViewModel<FullScreenImageCallback> {

    public FullScreenImageDialogViewModel(DataManager dataManager,
                                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        getNavigator().dismissDialog();
    }

}