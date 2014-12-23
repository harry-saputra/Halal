package com.rick.android.halalfood;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.rick.android.halalfood.http.ApiConfigUtil;
import com.rick.android.halalfood.module.crash.CrashHandler;
import com.rick.android.halalfood.module.umeng.UmengController;
import com.rick.android.halalfood.utils.DevUtil;
import com.rick.android.halalfood.utils.PhoneInfo;
import com.rick.android.volley.HFVolley;
import com.umeng.analytics.MobclickAgent;

public class HalalFoodApp extends Application {

    private static HalalFoodApp _instance;
    public ImageLoader imageLoader;

    /** U盟信息 */
    public String uMengChannel = "Umeng";
    public String uMengKey = "546b8b5bfd98c512fb002308";

    /** 手机app名称 */
    public static final String PHONE_APPNAME = "a-halalfood";

    /** Flag：app是否在前台 */
    public static boolean isActive;

    public HalalFoodApp() {
    }

    public static HalalFoodApp getInstance() {
        if (null == _instance) {
            _instance = new HalalFoodApp();
        }
        return _instance;
    }

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
        /** Imageloader 初始化 */
        initImageLoader();
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_photo_loading)
                .showImageForEmptyUri(R.drawable.icon_photo_bg)
                .showImageOnFail(R.drawable.icon_photo_lose)
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
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
