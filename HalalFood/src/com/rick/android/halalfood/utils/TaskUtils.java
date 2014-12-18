package com.rick.android.halalfood.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Util：检测app是否在前台
 * 
 * @author hua
 */
public class TaskUtils {

    public static String getTopRunningTaskTopActivityName(Context context) {
        RunningTaskInfo info = getTopRunningTask(context);
        String className = info.topActivity.getClassName();
        return className;
    }

    public static String getTopRuningTaskPackageName(Context context) {
        RunningTaskInfo info = getTopRunningTask(context);
        return info.topActivity.getPackageName();
    }

    @SuppressWarnings("deprecation")
    public static RunningTaskInfo getTopRunningTask(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        return info;
    }

    public static boolean isAppCurentScreen(Context context) {
        return context.getPackageName().equals(getTopRuningTaskPackageName(context)) ? true : false;
    }

    /**
     * whether this process is named with processName
     * 
     * @param processName
     * @return
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (TextUtils.isEmpty(processName)) {
            return true;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processInfoList = manager
                .getRunningAppProcesses();
        if (processInfoList == null) {
            return true;
        }

        for (RunningAppProcessInfo processInfo : manager
                .getRunningAppProcesses()) {
            if (processInfo.pid == pid
                    && processName.equals(processInfo.processName)) {
                return true;
            }
        }
        return false;
    }
}
