package com.rick.android.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.rick.android.volley.util.BitmapLruCache;

/**
 * AndroidVolleyClient 网络请求的封装类,在Application中调用init()初始化
 * 
 * @author hua
 */
public class HFVolley {

    private final static String TAG = "AndroidVolleyClient";
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    public static ApiConfig config;

    private HFVolley() {
    }

    /**
     * 初始化，默认不加载图片模块
     * 
     * @param context
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        // loadImageModule(context);
    }

    /**
     * 初始化，可选择是否加载图片模块
     * 
     * @param context
     * @param isLoadImageModule 是否初始话图片模块
     */
    public static void init(Context context, boolean isLoadImageModule) {
        mRequestQueue = Volley.newRequestQueue(context);
        if (isLoadImageModule) {
            loadImageModule(context);
        }
    }

    /**
     * 加载图片模块，图片加载使用总内存的1/8
     * 
     * @param context
     */
    private static void loadImageModule(Context context) {
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        int cacheSize = memClass * 1024 * 1024 / 8;
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
    }

    /**
     * 获得图片加载器
     * 
     * @return
     */
    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    /**
     * 获得Volley的请求队列
     * 
     * @return
     */
    private static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * 往请求队列里面添加请求，使用默认标签
     * 
     * @param req 要加进去的请求
     */
    public static <T> void addtoRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 往请求队列里面添加请求，使用参数tag为标签
     * 
     * @param req 要加进去的请求
     * @param tag 标签
     */
    public static <T> void addtoRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * 取消RequestQueue里面所有标签为tag的请求
     * 
     * @param tag 需要取消掉哪些请求的标签
     */
    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Http配置的相关设置
     * 
     * @param config
     */
    public static void setApiConfig(ApiConfig config) {
        HFVolley.config = config;
    }

}
