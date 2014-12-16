package com.rick.android.halalfood;

import android.app.Application;

import com.anjuke.android.http.volley.HFVolley;
import com.rick.android.halalfood.http.ApiConfigUtil;
import com.rick.android.halalfood.module.crash.CrashHandler;
import com.rick.android.halalfood.module.umeng.UmengController;
import com.rick.android.halalfood.utils.DevUtil;
import com.rick.android.halalfood.utils.PhoneInfo;
import com.umeng.analytics.MobclickAgent;

public class HalalFoodApp extends Application {

    private static HalalFoodApp _instance;

    /** U盟信息 */
    public String uMengChannel = "Umeng";
    public String uMengKey = "546b8b5bfd98c512fb002308";

    /** 手机app名称 */
    public static final String PHONE_APPNAME = "a-halalfood";

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        PhoneInfo.initialize(this, PHONE_APPNAME);
        UmengController.initStatistics(this);
        DevUtil.initialize(this);
        if (!DevUtil.isDebug()) {
            CrashHandler.getInstance().init(this);
        }
        /** Volley 初始化 */
        HFVolley.setApiConfig(ApiConfigUtil.getApiConfig());
        HFVolley.init(this);
    }

    public HalalFoodApp() {
    }

    public static HalalFoodApp getInstance() {
        if (null == _instance) {
            _instance = new HalalFoodApp();
        }
        return _instance;
    }

    /**
     * Kill该应用进程，退出App
     */
    public void exitHalalFood() {
        /** 退出应用前，保存Umeng统计数据 */
        MobclickAgent.onKillProcess(this);
        /** 退出app */
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
