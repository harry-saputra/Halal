package com.rick.android.halalfood.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.rick.android.halalfood.utils.TaskUtils;
import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActicity extends FragmentActivity {

    protected String TAG;
    protected Context mContext;
    private boolean isFromOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = getClass().getSimpleName();
        this.mContext = this;
        isFromOnCreate = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromOnCreate) {
            onAppFront();
        }
        isFromOnCreate = false;

        MobclickAgent.onPageStart(getClass().getSimpleName());// 统计页面
        MobclickAgent.onResume(this);// 统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        HalalFoodApp.isActive = TaskUtils.isAppCurentScreen(this);
        if (!HalalFoodApp.isActive) {
            onAppBack();
        }
    }

    /**
     * app从后台切到前台
     */
    protected void onAppFront() {

    }

    /**
     * app从前台切到后台
     */
    protected void onAppBack() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
