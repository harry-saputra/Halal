package com.android.halalfood.utils.common;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneInfo {

    /** app名字 */
    public static String AppName;
    /** app版本. */
    public static String AppVer;
    /** app内部版本 */
    public static int versionCode;
    /** app平台 */
    public static String AppPlatform;
    /** 安装包渠道号 */
    public static String AppPM;
    /** umeng key */
    public static String umengKey;
    /**
     * 设备ID <br>
     * 需要增加权限&#60;uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String DeviceID;
    /**
     * 新设备ID 因为app公共字段的macid字段的值是手机mac地址且做了MD5处理
     * 为了与之相对应，特修改NewID的值为手机mac地址且做MD5处理
     */
    public static String NewID;
    /** newId是否是mac的md5编码 **/
    public static boolean isNewIdMacMd5 = false;
    /** 设备机型 */
    public static String Model;
    /** OS版本 */
    public static String OSVer;
    /** 系统ro.build.description */
    public static String OSDesc;
    /** 鉴于2.5时<var>NewID</var>变更了自己原本获取ANDROIDID的意义，重新定义一个用于获取该值 */
    public static String AndroidID;

    /**
     * uuid app安装一次重新生成个id <br>
     * 已经安装的使用线程从SharedPreferences中读取，uuid默认为null。
     * PhoneInfo.initialize()后大概100ms不到后会设置该值
     */
    public static String uuid;

    /**
     * 用于唯一标识设备的编号，介于imei、mac地址、androidId均有可能获取不到，这里统一逻辑最大程度上获取唯一标示手机的编码。 <br>
     * <br>
     * 算法依次取:imei、androidid、md5后的mac地址 <br>
     * <br>
     * 取imei时需要增加权限&#60;uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String phoneId;

    /**
     * 本机的手机号 不一定获取得到 <br>
     * 需要增加权限&#60;uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String phoneNum;

    public static String lat;
    public static String lng;
    /** 当前定位到的城市id 若定位得到的城市在数据库中没有ID，cid=0 若是因为定位出错等问题，造成无法得知当前城市的，cid=-1 */
    public static String cid;
    /** 微聊id */
    public static String _chatId = "0";
    public static String _brokerId;
    public static Context mOutContext;
    private static String bakDevid;
    private static int mCityId = -1;

    public static int getmCityId() {
        return mCityId;
    }

    public static void setmCityId(int mCityId) {
        PhoneInfo.mCityId = mCityId;
    }

    public static void initialize(Context outContext) {
        initialize(outContext, "");
    }

    public static void initialize(Context outContext, String appName) {
        mOutContext = outContext;

        PackageManager manager = outContext.getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(outContext.getPackageName(), 0);

            // app名字
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

            //  app版本： (AppVer)
            if (info != null) {
                AppVer = info.versionName;
            }

            // app内部版本： (versionCode)
            if (info != null) {
                versionCode = info.versionCode;
            }

            //  app平台： (AppPlatform)
            AppPlatform = "android";

            //  安装包渠道号：(AppPM)
            try {
                PackageManager localPackageManager = outContext.getPackageManager();
                ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(
                        outContext.getPackageName(), 128);
                AppPM = (String) localApplicationInfo.metaData.get("UMENG_CHANNEL");
                umengKey = (String) localApplicationInfo.metaData.get("UMENG_APPKEY");
            } catch (Exception e) {
                // do nothing. not use the data
            }

            //  设备ID，每台设备唯一 (DeviceID)沿用anjuke以前的取数（设备的imei，平板可能会没有）
            TelephonyManager mTelephonyMgr = (TelephonyManager) outContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyMgr != null) {
                DeviceID = mTelephonyMgr.getDeviceId();
                phoneId = DeviceID;
            }

            //  新设备ID, 与设备号类似，也是一个唯一编号。(NewID)
            try {
                // String androidId =
                // Secure.getString(outContext.getContentResolver(),
                // Secure.ANDROID_ID);
                // NewID = androidId;
                NewID = MD5(getLocalMacAddress(outContext));// 因为app公共字段的macid字段的值是手机mac地址且做了MD5处理
                                                            // 为了与之相对应，特修改NewID的值为手机mac地址且做MD5处理

            } catch (Exception e) {
                // do nothing. not use the data

            }

            try {
                if (NewID == null || NewID.trim().length() == 0) {
                    String androidId = Secure.getString(outContext.getContentResolver(), Secure.ANDROID_ID);
                    NewID = androidId;
                } else {
                    isNewIdMacMd5 = true;
                }
            } catch (Exception e) {
                // do nothing. not use the data
            }

            try {
                String androidId = Secure.getString(outContext.getContentResolver(), Secure.ANDROID_ID);
                AndroidID = androidId;

                if (phoneId == null || phoneId.trim().length() == 0) {
                    phoneId = androidId;
                }
            } catch (Exception e) {

            }

            if (phoneId == null || phoneId.trim().length() == 0) {
                phoneId = MD5(getLocalMacAddress(outContext));
            }

            bakDevid = getBakDeviceID();
            if ((bakDevid == null || bakDevid.trim().length() == 0) && (NewID != null && NewID.trim().length() > 0)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setBakDeviceID(NewID);
                    }
                }).start();
            }
            if ((bakDevid == null || bakDevid.trim().length() == 0)
                    && (DeviceID != null && DeviceID.trim().length() > 7)) { // 过滤
                                                                             // 0，unknown,null
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setBakDeviceID(DeviceID);
                    }
                }).start();
            }

            //  设备机型：如，iphone4，i9000 (Model)
            Model = "Android-" + android.os.Build.MODEL;

            //  OS版本：如，iOS5.0，android2.3.7 (OSVer)
            OSVer = android.os.Build.VERSION.RELEASE;

            OSDesc = getBuildDescription();

            // uuid app安装一次重新生成个id
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uuid = getInstallID();
                    if (bakDevid == null || bakDevid.trim().length() == 0) {
                        setBakDeviceID(uuid);
                    }
                    if (NewID == null || NewID.trim().length() == 0) {
                        NewID = uuid;
                    }
                    if (DeviceID == null || DeviceID.trim().length() < 8) {// unknown
                        DeviceID = uuid;
                    }
                }
            }).start();

            // 获取本机的手机号 有几率获取不到
            if (mTelephonyMgr != null) {
                phoneNum = mTelephonyMgr.getLine1Number();
            }

            cid = "-1";

        } catch (Exception e) {
            Log.e(PhoneInfo.class.getName(), String.valueOf(e));
        }
    }

    public static String getLocalMacAddress(Context outContext) {
        WifiManager wifi = (WifiManager) outContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info != null ? info.getMacAddress() : "";
    }

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
        return MD5(info.getMacAddress());
    }

    // MD5加密，32位
    public static String MD5(String str) {
        if (str == null) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

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
     * 得到随机数，每次均不一样
     * 
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static String getUUId() {
        String ret;
        ret = new String(UUID.randomUUID().toString().getBytes());
        if (ret.length() > 10) {
            String udid = new SimpleDateFormat("yyyyMMddHH").format(new Date());
            ret = ret.substring(0, ret.length() - 10) + udid;
        }

        return ret;
    }

    /**
     * 一个app安装一次生成一个id
     * 
     * @return
     */
    private static String getInstallID() {
        String KEY = "uuid-aedcrasdfen";// key填充字符串 防止重复

        String uuid = SharedPreferencesHelper.getInstance(mOutContext).getString(KEY);
        if (uuid == null || uuid.trim().length() == 0) {
            uuid = getUUId();
            SharedPreferencesHelper.getInstance(mOutContext).putString(KEY, uuid);
        }

        return uuid;
    }

    /**
     * 保存唯一设备id
     * 
     * @return
     */
    private static String setBakDeviceID(String devid) {
        String KEY = "devid-anjuketag";
        String dvid = SharedPreferencesHelper.getInstance(mOutContext).getString(KEY);
        if (dvid == null || dvid.trim().length() == 0) {
            SharedPreferencesHelper.getInstance(mOutContext).putString(KEY, devid);
            dvid = devid;
        }
        return dvid;
    }

    /**
     * 获取唯一设备id
     * 
     * @return
     */
    private static String getBakDeviceID() {
        String KEY = "devid-anjuketag";
        String dvid = SharedPreferencesHelper.getInstance(mOutContext).getString(KEY);
        return dvid;
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
