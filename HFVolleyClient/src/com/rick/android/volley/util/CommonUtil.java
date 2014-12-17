package com.rick.android.volley.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;

import com.rick.android.volley.HFVolley;
import com.rick.android.volley.constant.CommonContants;

/**
 * Volley库中使用的工具方法，为避免过多的工具类引用，统一归置在CommonUtil中
 * 
 * @author hua
 */
public class CommonUtil {

    /****************************** Date 工具 Method ********************************/

    public static String getQtime() {
        return formatTime(System.currentTimeMillis(), "yyyyMMddHHmmss");
    }

    /**
     * 格式化时间
     * 
     * @param dateValue 单位毫秒 Long类型
     * @param format
     * @return
     */
    public static String formatTime(Long dateValue, String format) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTimeInMillis(dateValue);
        return formatTime(canlendar.getTime(), format);
    }

    /**
     * 格式化时间
     * 
     * @param dateValue Date类型
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(Date dateValue, String format) {
        DateFormat dateformat = new SimpleDateFormat(format);
        String date = dateformat.format(dateValue);
        return date;
    }

    /****************************** Log 工具 Method ********************************/
    public static void httpLog(String msg) {
        if (HFVolley.config.isShowLog()) {
            Log.d(CommonContants.Tag, msg);
        }
    }
}
