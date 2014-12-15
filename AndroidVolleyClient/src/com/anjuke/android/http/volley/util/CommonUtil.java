package com.anjuke.android.http.volley.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.anjuke.android.http.volley.AndroidVolleyClient;
import com.anjuke.android.http.volley.constant.CommonContants;

import android.annotation.SuppressLint;
import android.util.Log;

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

    /****************************** MD5 工具 Method ********************************/

    /**
     * MD5变换<br>
     * <br>
     * Modify the method by Norika in 2012.6.21 11:11<br>
     * <br>
     * 1. Change StringBuffer to StringBuilder<br>
     * The method doesn't use no static filed, so it not exists thread-safe
     * problem;<br>
     * 2. Change compute ways<br>
     * Bit computing instead of Math.<br>
     * 3. Modify this, run with more speed.<br>
     * 4. Consider of the string which contains some blank. <br>
     * 
     * @param str
     * @return
     */
    public static String Md5(String str) {
        if (str != null && !str.trim().equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
                };
                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int) (md5Byte[i] & 0xf0) >>> 4]);
                    sb.append(HEX[(int) (md5Byte[i] & 0x0f)]);
                }
                str = sb.toString();
            } catch (NoSuchAlgorithmException e) {

            } catch (Exception e) {

            }
        }
        return str;
    }

    /****************************** Log 工具 Method ********************************/
    public static void httpLog(String msg) {
        if (AndroidVolleyClient.config.isShowLog()) {
            Log.d(CommonContants.Tag, msg);
        }
    }
}
