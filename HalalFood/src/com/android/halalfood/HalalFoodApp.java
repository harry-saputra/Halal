
package com.android.halalfood;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

import com.android.halalfood.module.crash.CrashHandler;
import com.android.halalfood.module.umeng.UmengController;
import com.android.halalfood.utils.DevUtil;
import com.android.halalfood.utils.PhoneInfo;
import com.umeng.analytics.MobclickAgent;

public class HalalFoodApp extends Application {

    private static HalalFoodApp _instance;
    private ArrayList<Activity> list = new ArrayList<Activity>();

    // U盟信息
    public String uMengChannel = "Umeng";
    public String uMengKey = "546b8b5bfd98c512fb002308";

    // 手机app名称
    public static final String PHONE_APPNAME = "a-halalfood";

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        PhoneInfo.initialize(this, PHONE_APPNAME);
        UmengController.initStatistics(this);
        DevUtil.initialize(this);
        if (!DevUtil.isDebug()) {
            // Crash后的未捕获异常处理,防止弹出“停止运行”对话框
            CrashHandler.getInstance().init(this);
        }
    }

    public static HalalFoodApp getInstance() {
        return _instance;
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity,并Kill该应用进程，退出App
     */
    public void finishActivityAndExit() {
        // 退出应用前，保存Umeng统计数据
        MobclickAgent.onKillProcess(this);
        // 退出activity，杀死应用进程，退出App
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
