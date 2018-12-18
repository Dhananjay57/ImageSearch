package com.imagesearch.ui.search;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.imagesearch.BR;
import com.imagesearch.ImageSearchApp;
import com.imagesearch.R;
import com.imagesearch.data.network.model.Hits;
import com.imagesearch.databinding.ActivitySearchBinding;
import com.imagesearch.dependencyInjection.component.DaggerSearchActivityComponent;
import com.imagesearch.dependencyInjection.component.SearchActivityComponent;
import com.imagesearch.dependencyInjection.module.SearchActivityModule;
import com.imagesearch.ui.EndlessRecyclerViewScrollListener;
import com.imagesearch.ui.base.BaseActivity;
import com.imagesearch.ui.search.fullimage.FullScreenImageDialog;
import com.imagesearch.ui.search.gridPopup.GridColumnsPopupWindow;
import com.imagesearch.ui.search.gridPopup.ImagesColumnCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel>
        implements ImagesColumnCallback, ImageListAdapter.Callback,
        SearchView.OnQueryTextListener, SearchNavigator {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Inject
    ViewModelProvider.Factory mSearchViewModelFactory;

    private SearchActivityComponent mSearchActivityComponent;

    ActivitySearchBinding mSearchActivityBinding;

    private SearchViewModel mSearchViewModel;

    @Inject
    public ImageListAdapter mImageAdapter;

    @Inject
    public GridLayoutManager mLayoutManager;

    private SearchView mSearchView;

    private Context mContext;

    private String mQuery = "Nature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ButterKnife.bind(this);
        mSearchActivityBinding = getViewDataBinding();
        mSearchViewModel.setNavigator(this);
        setUp();
    }

    @Override
    public void performDependencyInjection() {
        mSearchActivityComponent = DaggerSearchActivityComponent.builder()
                .searchActivityModule(new SearchActivityModule(this))
                .applicationComponent(((ImageSearchApp) getApplication()).getComponent())
                .build();

        mSearchActivityComponent.inject(this);

    }

    protected void setUp() {
        Intent intent = getIntent();
            if(intent.getStringExtra("Message_Body")!= null ||
                    intent.getStringExtra("search_term")!= null) {
                final String messageBody = intent.getStringExtra("Message_Body");
                mQuery = intent.getStringExtra("search_term");
        }
        setSupportActionBar(mToolbar);
        setTitle(mQuery +" Images");
        mSearchActivityBinding.recyclerView.setLayoutManager(mLayoutManager);
        mSearchActivityBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mImageAdapter.setCallback(this);
        showLoading();
        mSearchViewModel.fetchImages(mQuery, 1);

        mSearchActivityBinding.recyclerView.addOnScrollListener(
                /**
                 * Listener to implement pagination.
                 *
                 * See {@link EndlessRecyclerViewScrollListener}
                 */
                new EndlessRecyclerViewScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        // Triggered only when new data needs to be appended to the list
                        if (mSearchViewModel.getNextPageOffset() != -1) {
                            mSearchViewModel.fetchImages(mQuery, mSearchViewModel.getNextPageOffset());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.setQuery("", false);
            mSearchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLayoutManager.setSpanCount(mContext.getResources().getInteger(R.integer.default_column_cnt));
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(List<Hits> imageItems) {
        hideLoading();

        mImageAdapter.addItems(imageItems);
        if (mSearchActivityBinding.recyclerView.getAdapter() == null) {
            mSearchActivityBinding.recyclerView.setAdapter(mImageAdapter);
        }
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
        hideLoading();
    }

    @Override
    public void onNewsEmptyViewRetryClick() {
        showLoading();
        mSearchViewModel.fetchImages(mQuery, mSearchViewModel.getCurrentPageOffset());
    }

    @Override
    public void onNewsArticleClick(Hits image) {
        FullScreenImageDialog.newInstance(image).show(getSupportFragmentManager());
    }

    private void initSearchMenu(Menu menu) {
        SearchManager manager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (mSearchView == null)
            return;

        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mImageAdapter.clear();
        mQuery = query;
        setTitle(mQuery+" Images");
        mSearchViewModel.fetchImages(query, 1);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);

        initSearchMenu(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_grid_options:
                View v = findViewById(R.id.action_grid_options);
                new GridColumnsPopupWindow(this).showAsDropDown(v);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * get view model for this view (Activity) from {@link ViewModelProviders}. This view model
     * services when activity gets restarted on config change operations hence it is the best place
     * to hold/store view's data.
     *
     * @return {@link SearchViewModel}, a view model for this view
     */
    @Override
    public SearchViewModel getViewModel() {
        mSearchViewModel = ViewModelProviders.of(this, mSearchViewModelFactory).get(SearchViewModel.class);
        return mSearchViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onColumnNumberChange(int totalColumns) {
        mLayoutManager.setSpanCount(totalColumns);
    }
}