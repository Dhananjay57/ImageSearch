package com.imagesearch.ui.search;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imagesearch.BR;
import com.imagesearch.R;
import com.imagesearch.data.network.model.Hits;
import com.imagesearch.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_ROW_MM = 1;

    private Callback mCallback;
    private ArrayList<Hits> mImageList;

    public ImageListAdapter(ArrayList<Hits> blogResponseList) {
        mImageList = blogResponseList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_ROW_MM:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.error_layout, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mImageList == null || mImageList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ROW_MM;
        }
    }

    @Override
    public int getItemCount() {
        if (mImageList != null && mImageList.size() > 0) {
            return mImageList.size();
        } else {
            return 1;
        }
    }

    public void clear() {
        mImageList.clear();
    }

    public void addItems(List<Hits> newsList) {
        mImageList.addAll(newsList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onNewsEmptyViewRetryClick();

        void onNewsArticleClick(Hits doc);

    }

    public class ViewHolder extends BaseViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        protected void clear() {
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void onBind(int position) {
            super.onBind(position);

            Hits image = mImageList.get(position);
            getBinding().setVariable(BR.image, image);
            getBinding().executePendingBindings();

            itemView.setOnClickListener(v -> {
                if (mCallback != null) {
                    mCallback.onNewsArticleClick(image);
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.error_layout)
        LinearLayout errorLayout;

        @BindView(R.id.error_btn_retry)
        Button retryButton;

        @BindView(R.id.error_txt_cause)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            errorLayout.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.error_btn_retry)
        void onRetryClick() {
            if (mCallback != null)
                mCallback.onNewsEmptyViewRetryClick();
        }
    }
}