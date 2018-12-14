package com.imagesearch.ui.search.gridPopup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.imagesearch.R;
import com.imagesearch.ui.search.SearchActivity;
import com.imagesearch.utils.SharedPrefUtils;

import java.lang.ref.WeakReference;

public class GridColumnsPopupWindow extends PopupWindow {

    public enum GridMode {
        COLUMN_TWO(2),
        COLUMN_THREE(3),
        COLUMN_FOUR(4);

        private int val;

        GridMode(int value) {
            val = value;
        }

        public int getValue() {
            return val;
        }
    }

    private WeakReference<Activity> activityWeakReference;
    private RadioGroup rgLanguageOptions;
    private ImagesColumnCallback mCallback;

    public GridColumnsPopupWindow(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
        mCallback = (SearchActivity) activity;
        init();
    }

    private void init() {
        LayoutInflater inflater = activityWeakReference.get().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_window_column_select, null);

        setContentView(view);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(10);
        }

        int selectedLanguage = SharedPrefUtils.getIntVal(SharedPrefUtils.COLUMNS_NO_SELECTED);

        rgLanguageOptions = view.findViewById(R.id.rgColumnOptions);
        rgLanguageOptions.check(getRadioButtonIdForColumnNumber(selectedLanguage));
        rgLanguageOptions.setOnCheckedChangeListener((radioGroup, i) -> {

            int columnNo = getSelectedColumnNumber();

            SharedPrefUtils.setIntVal(SharedPrefUtils.COLUMNS_NO_SELECTED, columnNo);

            if (mCallback != null)
                mCallback.onColumnNumberChange(columnNo);

            GridColumnsPopupWindow.this.dismiss();
        });
    }

    private int getSelectedColumnNumber() {
        int checkedRadioButtonId = rgLanguageOptions.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rbTwo:
            default:
                return GridMode.COLUMN_TWO.getValue();
            case R.id.rbThree:
                return GridMode.COLUMN_THREE.getValue();
            case R.id.rbFour:
                return GridMode.COLUMN_FOUR.getValue();
        }
    }

    private int getRadioButtonIdForColumnNumber(int gridMode) {

        if (gridMode == GridMode.COLUMN_THREE.getValue()) {
            return R.id.rbThree;
        } else if (gridMode == GridMode.COLUMN_FOUR.getValue()) {
            return R.id.rbFour;
        } else {
            return R.id.rbTwo;
        }
    }
}