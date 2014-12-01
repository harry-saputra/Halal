
package com.android.nation.halalfood.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 手机信息
 * <br>
 * AppName,AppPlatform,VersionCode,VersionName,UmengChannel,UmengKey
 * Model,OSVer,OSDesc,DeviceID,AndroidID,MacId,UniqueDeviceID,uuid,phoneNum
 * @author hua
 *
 */
public class PhoneInfo {

    public static Context mOutContext;

    /** app名字 */
    public static String AppName;
    /** app平台 */
    public static String AppPlatform;
    /** app内部版本 */
    public static int VersionCode;
    /** app版本 */
    public static String VersionName;
    /** Umeng channel */
    public static String UmengChannel;
    /** Umeng key */
    public static String UmengKey;

    /** 设备机型 */
    public static String Model;
    /** OS版本 */
    public static String OSVer;
    /** 系统描述ro.build.description */
    public static String OSDesc;

    /**
     * 设备的DeviceID<br>
     * 需要增加权限uses-permission android:name="android.permission.READ_PHONE_STATE" <br>
     * 根据不同的手机设备返回IMEI，MEID或者ESN码，非手机设备获取不到 IMEI:国际移动电话设备识别码（International
     * Mobile Equipment Identity），即手机串号 MEID:移动设备识别码 ESN嘛码:电子序列号(Electronic
     * Serial Number)
     */
    public static String DeviceID;

    /**
     * 设备的Android_ID，也有可能不唯一标识<br>
     * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，
     * 这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置(刷机，或某些烧写工具可以修改此值)<br>
     * 厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c<br>
     * 厂商定制系统的Bug：有些设备返回的值为null。<br>
     * 设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。<br>
     */
    public static String AndroidID;

    /**
     * 设备的MacId（Mac Address）<br>
     * 不一定获取到，若不含有wifi、蓝牙模块或含有但没有使用过，则获取不到macid
     */
    public static String MacId;

    /**
     * MD5之后的设备MacId<br>
     */
    public static String MacIdMD5;

    /**
     * 用于唯一标识设备的编号，介于deviceId、mac地址、androidid均有可能获取不到，这里统一逻辑最大程度上获取唯一标示手机的编码。 <br>
     * <br>
     * 算法依次取:deviceId、androidid、md5后的mac地址 <br>
     * <br>
     * 取imei时需要增加权限<br>
     * uses-permission android:name="android.permission.READ_PHONE_STATE"
     */
    public static String UniqueDeviceID;

    /**
     * uuid app安装一次重新生成个id <br>
     * 已经安装的使用线程从SharedPreferences中读取，uuid默认为null。
     */
    public static String uuid;

    /**
     * 本机的手机号 不一定获取得到 <br>
     * 需要增加权限:uses-permission android:name="android.permission.READ_PHONE_STATE"
     */
    public static String PhoneNum;

    public static final String PREFS_FILE_DEVICE = "device_id.xml";
    public static final String PREFS_DEVICE_ID = "device_id";
    public static final String KEY_MD5 = "halalfood_md5"; // 自定义生成 MD5 加密字符传前的混合
                                                          // KEY

    public static void initialize(Context outContext) {
        initialize(outContext, "");
    }

    /**
     * 初始化PhoneInfo
     * @param outContext
     * @param appName
     */
    public static void initialize(Context outContext, String appName) {
        mOutContext = outContext;
        PackageManager manager = outContext.getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(outContext.getPackageName(), 0);

            //  app名字
            try {
                if (appName == null || appName.trim().length() == 0) {
                    ApplicationInfo ai = manager.getApplicationInfo(outContext.getPackageName(), 0);
                    AppName = (String) manager.getApplicationLabel(ai);
                } else {
                    AppName = appName;
                }
            } catch (Exception e) {
                AppName = appName;
            }

            //  app版本： (VersionName)
            if (info != null) {
                VersionName = info.versionName;
            }

            // app内部版本： (VersionCode)
            if (info != null) {
                VersionCode = info.versionCode;
            }

            //  app平台： (AppPlatform)
            AppPlatform = "android";

            //  设备机型：如，iphone4，i9000 (Model)
            Model = "Android-" + android.os.Build.MODEL;

            //  OS版本：如，iOS5.0，android2.3.7 (OSVer)
            OSVer = android.os.Build.VERSION.RELEASE;

            //  OS描述
            OSDesc = getBuildDescription();

            //  Umeng渠道号：(UmengChannel)，UmengKey：(UmengKey)
            try {
                PackageManager localPackageManager = outContext.getPackageManager();
                ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(
                        outContext.getPackageName(), 128);
                UmengChannel = (String) localApplicationInfo.metaData.get("UMENG_CHANNEL");
                UmengKey = (String) localApplicationInfo.metaData.get("UMENG_APPKEY");
            } catch (Exception e) {
                // do nothing. not use the data
            }

            //  设备的DeviceID，平板可能会没有，若有，则作为唯一标示设备ID——UniqueDeviceID
            TelephonyManager mTelephonyMgr = (TelephonyManager) outContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyMgr != null) {
                DeviceID = mTelephonyMgr.getDeviceId();
                UniqueDeviceID = DeviceID;
            }

            //  设备AndroidID
            try {
                String androidId = Secure.getString(outContext.getContentResolver(), Secure.ANDROID_ID);
                AndroidID = androidId;
                if (TextUtils.isEmpty(UniqueDeviceID) || UniqueDeviceID.trim().length() == 0) {
                    UniqueDeviceID = androidId;
                }
            } catch (Exception e) {
                // do nothing. not use the data
            }

            //  设备MacID，其实质是手机Wifi或蓝牙的MAC地址，若没有硬件或wifi、蓝牙未打开，则不能获取到
            try {
                MacId = getLocalMacAddress(outContext);
                MacIdMD5 = MD5Tool.MD5(MacId);
                if (TextUtils.isEmpty(UniqueDeviceID) || UniqueDeviceID.trim().length() == 0) {
                    UniqueDeviceID = MacIdMD5;
                }
            } catch (Exception e) {
                // do nothing. not use the data
            }

            //  uuid app安装一次重新生成个id
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uuid = getUUID();
                    if (DeviceID == null || DeviceID.trim().length() < 8) {
                        // unknown
                        DeviceID = uuid;
                    }
                    if (TextUtils.isEmpty(UniqueDeviceID) || UniqueDeviceID.trim().length() == 0) {
                        UniqueDeviceID = uuid;
                    }
                }
            }).start();

            // 获取本机的手机号 有几率获取不到
            if (mTelephonyMgr != null) {
                PhoneNum = mTelephonyMgr.getLine1Number();
            }

        } catch (Exception e) {
            Log.e(PhoneInfo.class.getName(), String.valueOf(e));
        }
    }

    /**
     * 获得app的UUID，是一个随机数，每次均不一样（长度为32）<br>
     * UUID:全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的.在微软或其他平台上也称Guid
     * 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
     * 
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static String getUUID() {
        SharedPreferences prefs = mOutContext.getSharedPreferences(PREFS_FILE_DEVICE, 0);
        String id = prefs.getString(PREFS_DEVICE_ID, null);
        if (id != null && id.trim().length() > 0) {
            return id;
        } else {
            UUID cur_uuid;
            try {
                String androidId = Secure.getString(mOutContext.getContentResolver(), Secure.ANDROID_ID);
                if (!"9774d56d682e549c".equals(androidId)) {
                    cur_uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    String deviceId = ((TelephonyManager) mOutContext.getSystemService(Context.TELEPHONY_SERVICE))
                            .getDeviceId();
                    cur_uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            /**
             * 短链接算法<br>
             * 短链接算法是先将url进行MD5加密后生成的32位字符串，然后在对这32位处理，得出4个6位的字符串(这4个中每个都可最为短链接)
             * 。 程序中采用了uuid自动生成32位唯一字符串。然后对这32为进行处理，在得出的4个短链接字符串中，选取第一个，作为id
             */
            String ret = new String(cur_uuid.toString().getBytes());
            if (ret != null && ret.length() > 10) {
                id = ShortUrlGenerator.shortUrl(KEY_MD5, ret);
            }
            prefs.edit().putString(PREFS_DEVICE_ID, id).commit();
            return id;
        }
    }

    /**
     * 获得Mac地址
     * 
     * @param outContext
     * @return
     */
    public static String getLocalMacAddress(Context outContext) {
        WifiManager wifi = (WifiManager) outContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info != null ? info.getMacAddress() : "";
    }

    /**
     * 获得加密的Mac地址
     * 
     * @param outContext
     * @return
     */
    public static String getLocalMacAddressWithMD5(Context outContext) {
        WifiManager wifi = (WifiManager) outContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return "";
        }
        if (info.getMacAddress() == null) {
            return "";
        }
        if (info.getMacAddress().trim().length() == 0) {
            return "";
        }
        return MD5Tool.MD5(info.getMacAddress());
    }

    /**
     * 系统描述
     * 
     * @return
     */
    public static String getBuildDescription() {
        String desc = "unknown";
        try {
            Class<?> clazz = Class.forName("android.os.Build");
            Class<?> paraTypes = Class.forName("java.lang.String");
            Method method = clazz.getDeclaredMethod("getString", paraTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            Build b = new Build();
            desc = (String) method.invoke(b, "ro.build.description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desc;
    }

    /**
     * 是否是平板
     * 
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
