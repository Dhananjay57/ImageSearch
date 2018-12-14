package com.imagesearch.ui.search.fullimage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.imagesearch.AppConstants;
import com.imagesearch.ImageSearchApp;
import com.imagesearch.R;
import com.imagesearch.data.network.model.Hits;
import com.imagesearch.databinding.FullScreenImageDialogBinding;
import com.imagesearch.dependencyInjection.component.DaggerFullScreenImageDialogComponent;
import com.imagesearch.dependencyInjection.component.FullScreenImageDialogComponent;
import com.imagesearch.dependencyInjection.module.SearchActivityModule;
import com.imagesearch.ui.base.BaseDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FullScreenImageDialog extends BaseDialog implements FullScreenImageCallback {

    private static final String TAG = FullScreenImageDialog.class.getSimpleName();

    @Inject
    FullScreenImageDialogViewModel mFullScreenImageDialogViewModel;

    private FullScreenImageDialogComponent mFullScreenImageDialogComponent;

    @BindView(R.id.photo)
    public ImageView mPhotoView;

    @BindView(R.id.title)
    public TextView mTitleTextView;

    public static FullScreenImageDialog newInstance(Hits image) {
        FullScreenImageDialog fragment = new FullScreenImageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.KEY_PHOTO_URL, image.getLargeImageURL());
        bundle.putString(AppConstants.KEY_TITLE, image.getTags());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FullScreenImageDialogBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.full_screen_image_dialog, container, false);
        View view = binding.getRoot();

        ButterKnife.bind(this, view);

        performDependencyInjection();

        binding.setViewModel(mFullScreenImageDialogViewModel);

        mFullScreenImageDialogViewModel.setNavigator(this);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
    }

    private void bindView() {
        Bundle b = getArguments();

        String photoUrl = b.getString(AppConstants.KEY_PHOTO_URL);

        String title = b.getString(AppConstants.KEY_TITLE);
        mFullScreenImageDialogViewModel.setIsLoading(true);
        Glide.with(mPhotoView.getContext())
                .load(photoUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        getBaseActivity().onError(new Throwable("Can not load image"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mFullScreenImageDialogViewModel.setIsLoading(false);
                        return false;
                    }
                })
                .into(mPhotoView);

        mTitleTextView.setText(title);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public void performDependencyInjection() {
        mFullScreenImageDialogComponent = DaggerFullScreenImageDialogComponent.builder()
                .searchActivityModule(new SearchActivityModule(getBaseActivity()))
                .applicationComponent(((ImageSearchApp) getBaseActivity().getApplication()).getComponent())
                .build();

        mFullScreenImageDialogComponent.inject(this);
    }
}