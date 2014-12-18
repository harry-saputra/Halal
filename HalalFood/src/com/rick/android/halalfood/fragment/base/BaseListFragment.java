package com.rick.android.halalfood.fragment.base;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.widget.Toast;

public class BaseListFragment extends ListFragment {
    
    protected String TAG;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = getClass().getSimpleName();
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
