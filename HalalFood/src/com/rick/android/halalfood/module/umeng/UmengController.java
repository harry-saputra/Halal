package com.rick.android.halalfood.module.umeng;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.rick.android.halalfood.HalalFoodApp;
import com.umeng.analytics.MobclickAgent;

public class UmengController {

    /**
     * 初始化Umeng统计 (U盟的数据统计)
     */
    public static void initStatistics(Context ctx) {
        initUmeng(ctx);
        saveUMeng();
    }

    /**
     * 初始化Umeng统计(在程序入口Activity处添加)
     */
    public static void initUmeng(Context ctx) {
        // 发送策略定义了用户由统计分析SDK产生的数据发送回友盟服务器的频率(两种方式)
        // * 启动时发送
        // * 按间隔发送
        MobclickAgent.updateOnlineConfig(ctx);
        // 禁止默认的页面统计方式
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * Umeng 保存渠道编号和友盟Key
     */
    private static void saveUMeng() {
        try {
            ApplicationInfo ai = HalalFoodApp.getInstance().getPackageManager().getApplicationInfo(
                    HalalFoodApp.getInstance().getPackageName(),
                    PackageManager.GET_META_DATA);
            HalalFoodApp.getInstance().uMengChannel = (String) ai.metaData.get("UMENG_CHANNEL");

            HalalFoodApp.getInstance().uMengKey = (String) ai.metaData.get("UMENG_APPKEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
