package com.rick.android.halalfood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;

import com.rick.android.halalfood.HalalFoodApp;
import com.rick.android.halalfood.R;
import com.rick.android.halalfood.constants.CommonConstants;
import com.rick.android.halalfood.utils.NetworkUtil;
import com.rick.android.halalfood.utils.PhoneInfo;

public class WelcomeActivity extends BaseActicity {

    private static final int GO_GUIDE = 1000;
    private static final int GO_NEXT = 1001;

    boolean isBack = false;// FLAG
    boolean isFirstIn = false;// 是否是当前版本第一次启动app

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE:
                    goGuide();
                    break;
                case GO_NEXT:
                    if (!isBack) {
                        goNext();
                    }
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 程序crash后重启的友好提示 */
        showToast(this, getIntent().getStringExtra("toastMsg"));

        // 全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGo();
    }

    @Override
    protected void onStop() {
        isBack = true;
        super.onStop();
    }

    private void initGo() {
        if (!NetworkUtil.isNetworkAvailable(HalalFoodApp.getInstance())) {
            showAppToast("网络不畅，请检查网络设置...");
        }

        SharedPreferences preferences = getSharedPreferences(
                CommonConstants.SP_FIRST_START, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        String version = preferences.getString("version", "");

        /**
         * 判断当前版本app与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到登陆界面
         */
        if (!isFirstIn && TextUtils.equals(PhoneInfo.VersionName, version)) {
            mHandler.sendEmptyMessageDelayed(GO_NEXT, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 1000);
        }
    }

    /**
     * 跳转到下一流程
     */
    private void goNext() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        WelcomeActivity.this.startActivity(intent);
        WelcomeActivity.this.finish();
    }

    /**
     * 跳转到引导界面
     */
    private void goGuide() {
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        WelcomeActivity.this.startActivity(intent);
        WelcomeActivity.this.finish();
    }

}
