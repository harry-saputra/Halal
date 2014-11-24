
package com.android.halalfood.utils;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Build;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * HalalFood dev tools
 * 
 * @author hua
 */
public class DevUtil {

    private static Context mContext;

    private static boolean isDebug;
    private static boolean isUnit = false; // 是否单元测试
    private static boolean isAllowedKey = false; // 是否合法签名
    private static HashMap<String, Long> oldTimeMap = new HashMap<String, Long>();

    private static final String NOT_INITIALIZE_ERROR_STRING = DevUtil.class.getSimpleName()
            + " not initialize. Please run " + DevUtil.class.getSimpleName() + ".initialize() first !";

    /**
     * 初始化DevUtil开发工具
     * @param context
     */
    public static void initialize(Context context) {
        mContext = context;
        debugAccess(isUnit);
    }

    /**
     * 为了搭建测试环境必须重载此方法
     * 
     * @param context
     * @param isUnitTest
     */
    public static void initialize(Context context, boolean isUnitTest) {
        mContext = context;
        isUnit = true;
        debugAccess(isUnitTest);
    }

    /**
     * 设置debug 一般情况下不要调用 主要用于测试
     * 
     * @param isDebug
     */
    public static void setDebug(boolean isDebug) {
        DevUtil.isDebug = isDebug;
    }

    /**
     * 时间调试信息，以yourName为tag，日志输出<br>
     * 线下版本才会输入调试信息，线上版本会自动关闭
     * 
     * @param yourName 你的名字，避免与其他人统计区分
     * @param message
     * @throws Exception
     */
    public static void v(String yourName, String message) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }

        if (isDebug) {
            Log.v(yourName, message);
        }
    }

    public static void e(String yourName, String message) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        if (isDebug) {
            Log.e(yourName, message);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    public static void i(String yourName, String message) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        if (isDebug) {
            Log.i(yourName, message);
        }
    }

    public static void d(String yourName, String message) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        if (isDebug) {
            Log.v(yourName, message);
        }
    }

    /**
     * 时间调试信息，以yourName为tag，日志输出每步花费的时间。<br>
     * 线下版本才会输入调试信息，线上版本会自动关闭
     * 
     * @param yourName 你的名字，避免与其他人统计区分
     * @param message
     * @throws Exception
     */
    public static void timePoint(String yourName, String message) {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        if (isDebug) {
            Long currentTime = System.currentTimeMillis();
            Long oldTimeTemp = oldTimeMap.get(yourName);
            if (oldTimeTemp == null) {
                oldTimeTemp = System.currentTimeMillis();
            }
            Long durationTime = currentTime - oldTimeTemp;
            Log.v(yourName, message + " durationTime:" + durationTime);
            oldTimeTemp = currentTime;
            oldTimeMap.put(yourName, oldTimeTemp);
        }
    }

    /**
     * 通过签名判断是否为开发版，开发版keystore见项目根目录的debug.keystore
     * 
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isDebug() {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        return isDebug;
    }

    /**
     * 通过签名判断是否为合法的key签名: 线下debug keystore和线上 keystore
     * 
     * @return
     */
    public static boolean isAllowedKey() {
        if (mContext == null) {
            throw new RuntimeException(NOT_INITIALIZE_ERROR_STRING);
        }
        return isAllowedKey;
    }

    /**
     * 判断是否开发版本、是否合法的签名 新增:单元测试不支持签名，如果调用debugAccess()会报空指针异常，因此重写debugAccess方法
     * ，加入isUnitTest参数进行判断
     */
    private static void debugAccess(boolean isUnitTest) {
        final int DEBUG_SIGNATURE_HASH = -545290802;
        final int ONLINE_SIGNATURE_HASH = -1876577873; // HalalFood 的签名keystore
        isDebug = false;

        // 判断是否为调试状态
        // http://stackoverflow.com/questions/3029819/android-automatically-choose-debug-release-maps-api-key
        PackageManager manager = mContext.getPackageManager();
        if (isUnitTest) {
            isDebug = false;
            isAllowedKey = true;
        } else {
            try {
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);

                for (Signature sig : info.signatures) {
                    if (sig.hashCode() == DEBUG_SIGNATURE_HASH) {
                        isDebug = true;
                        isAllowedKey = true;
                    }
                    if (sig.hashCode() == ONLINE_SIGNATURE_HASH) {
                        isAllowedKey = true;
                    }
                }
            } catch (NameNotFoundException e) {
                isDebug = false;
            }
        }
    }

    /**
     * 判断是否模拟器。如果返回TRUE，则当前是模拟器
     * 
     * @param context
     * @return
     */
    public static boolean isEmulator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null || imei.equals("000000000000000")) {
            return true;
        }
        return false;
    }

    /**
     * 开启StrickMode
     */
    @TargetApi(9)
    public static void enableStrictMode() {
        if (hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    /**
     * 关闭StrickMode
     */
    @TargetApi(9)
    public static void disableStrictMode() {
        if (hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .permitAll()
                            .penaltyLog();
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
        }
    }

    /**
     * 手机操作系统是否>= level5 2.0
     * 
     * @return
     */
    public static boolean hasAndroid2_0() {
        return Build.VERSION.SDK_INT >= 5;// Build.VERSION_CODES.ECLAIR;
    }

    /**
     * 手机操作系统是否>= level7 2.1
     * 
     * @return
     */
    public static boolean hasAndroid2_1() {
        return Build.VERSION.SDK_INT >= 7;// Build.VERSION_CODES.ECLAIR_MR1
    }

    /**
     * 手机操作系统是否>=Froyo level8 2.2
     * 
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= 8;// Build.VERSION_CODES.FROYO;
    }

    /**
     * 手机操作系统是否>=Gingerbread level9 2.3.1
     * 
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;// 低版本Build.VERSION_CODES.GINGERBREAD未定义
    }

    /**
     * 手机操作系统是否>=Honeycomb level11 3.0
     * 
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;// Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 手机操作系统是否>=HoneycombMR1 level12 3.1
     * 
     * @return
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12;// Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 手机操作系统是否>=HoneycombMR2 level12 3.2
     * 
     * @return
     */
    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= 13;// Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * 手机操作系统是否>=ICE_CREAM_SANDWICH level14 4.0
     * 
     * @return
     */
    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= 14;// Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 手机操作系统是否>=JELLY_BEAN level16 4.1
     * 
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;// Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 手机操作系统是否>=JELLY_BEAN_MR1 level17 4.2
     * 
     * @return
     */
    public static boolean hasJellyBean4_2() {
        return Build.VERSION.SDK_INT >= 17;// Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 手机操作系统是否>=JELLY_BEAN_MR2 level18 4.3
     * 
     * @return
     */
    public static boolean hasJellyBean4_3() {
        return Build.VERSION.SDK_INT >= 18;// Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 手机操作系统是否>=KITKAT level19 4.4
     * 
     * @return
     */
    public static boolean hasKitKat4_4() {
        return Build.VERSION.SDK_INT >= 19;// Build.VERSION_CODES.KITKAT;
    }

    /**
     * 手机操作系统是否>=KITKAT_WATCH level20 4.4W
     * 
     * @return
     */
    public static boolean hasKitKat_Watch4_4W() {
        return Build.VERSION.SDK_INT >= 20;// Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * 手机操作系统是否>=LOLLIPOP level21 5.0
     * 
     * @return
     */
    public static boolean hasLollipop5() {
        return Build.VERSION.SDK_INT >= 21;// Build.VERSION_CODES.LOLLIPOP;
    }

}
