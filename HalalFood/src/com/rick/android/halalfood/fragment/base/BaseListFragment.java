package com.rick.android.halalfood.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseListFragment extends ListFragment {

    protected String TAG;
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.TAG = getClass().getSimpleName();
    }

    /**
     * @return 当前Fragment对应的Layout id
     */
    public abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); // 统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void showAppToast(String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg)) {
            Toast.makeText(HalalFoodApp.getInstance(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg) && null != mContext) {
            Toast.makeText(mContext, toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

}
