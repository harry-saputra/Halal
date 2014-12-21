package com.rick.android.halalfood.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {

    protected String TAG;
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.TAG = getClass().getSimpleName();
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
