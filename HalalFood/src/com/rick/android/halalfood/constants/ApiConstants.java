package com.rick.android.halalfood.constants;

public class ApiConstants {

    /** AppKey */
    public static final String appKey = "09936603";
    /** AppSecret */
    public static final String appSecret = "865aed6bfa484976846847d00271a044";

    /** Api Host */
    public static final String apiHost = "http://api.dianping.com";
    /** Api版本 (v1) */
    public static final String apiVersion = "/v1/";
    
    /** 默认请求超时时间 */
    public static final int overTime = 15 * 1000;

    public class Method {
        public static final int get = 0;
        public static final int post = 1;
    }

    /** 城市 */
    public static final String City = "city";
    /** 纬度 */
    public static final String Latitude = "latitude";
    /** 经度 */
    public static final String Longitude = "longitude";
    /** 分类 */
    public static final String Category = "category";
    /** 区域 */
    public static final String Region = "region";
    /** 数据条数 */
    public static final String Limit = "limit";
    /** 半径 */
    public static final String Radius = "radius";
    /** 优惠类型 */
    public static final String Offset_type = "offset_type";
    /** 是否有优惠券 */
    public static final String Has_coupon = "has_coupon";
    /** 是否交易 */
    public static final String Has_deal = "has_deal";
    /** 关键词 */
    public static final String keyword = "keyword";
    /** 种类 */
    public static final String Sort = "sort";

}
