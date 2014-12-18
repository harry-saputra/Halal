package com.rick.android.halalfood.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.rick.android.halalfood.utils.TaskUtils;
import com.umeng.analytics.MobclickAgent;

public class BaseActicity extends ActionBarActivity {

    protected String TAG;
    private boolean isFromOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
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

    protected void showToast(Context ctx, String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg) && null != ctx) {
            Toast.makeText(ctx, toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

}
