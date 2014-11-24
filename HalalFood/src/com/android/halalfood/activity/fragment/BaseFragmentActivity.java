
package com.android.halalfood.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HalalFoodApp.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        HalalFoodApp.getInstance().removeActivity(this);
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