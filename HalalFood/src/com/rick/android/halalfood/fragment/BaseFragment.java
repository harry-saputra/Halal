package com.rick.android.halalfood.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); // 统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    protected void showAppToast(String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg)) {
            Toast.makeText(HalalFoodApp.getInstance(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(Context ctx, String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg) && null != ctx) {
            Toast.makeText(ctx, toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

}
