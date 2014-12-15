package com.rick.android.halalfood.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public class BaseActicity extends ActionBarActivity {

    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
