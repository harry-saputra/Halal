
package com.android.halalfood.module.crash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.android.halalfood.HalalFoodApp;
import com.android.halalfood.activity.GuideActivity;

/**
 * Crash后的未捕获异常处理,防止弹出“停止运行”对话框
 * 
 * @author hua
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private HalalFoodApp application;
    private static CrashHandler instance;
    public static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(HalalFoodApp application) {
        if (null != instance) {
            Thread.setDefaultUncaughtExceptionHandler(this);
            this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            this.application = application;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(application.getApplicationContext(), GuideActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(
                    application.getApplicationContext(), 0, intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
            // 1秒钟后重启应用
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
            // 退出程序
            application.finishActivityAndExit();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * 
     * @param ex
     * @return 处理该异常信息返回true;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(application.getApplicationContext(), "Sorry,App开小差了,稍后重启...",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

}
