package com.rick.android.halalfood.module.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rick.android.halalfood.R;

public class TuDingLayout extends LinearLayout {

    private TextView tv_tjtitle;
    private ImageView iv_tuangou, iv_yuding, iv_youhui;

    public TuDingLayout(Context context) {
        this(context, null);
    }

    public TuDingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("InflateParams")
    public TuDingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_custom_tuding, null);
        tv_tjtitle = (TextView) inflate.findViewById(R.id.tv_tjtitle);

        // 团购icon
        iv_tuangou = (ImageView) inflate.findViewById(R.id.iv_tuangou);

        // 预定icon
        iv_yuding = (ImageView) inflate.findViewById(R.id.iv_yuding);

        // 优惠icon
        iv_youhui = (ImageView) inflate.findViewById(R.id.iv_youhui);

        addView(inflate, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置带文本的图钉Label
     * 
     * @param s
     * @param isTuan 是否团购
     * @param isYu 是否预约
     * @param isHui 是否优惠
     */
    public void setContent(String s, boolean isTuan, boolean isYu, boolean isHui) {
        tv_tjtitle.setText(s);
        iv_tuangou.setVisibility(isTuan ? View.VISIBLE : View.GONE);
        iv_yuding.setVisibility(isYu ? View.VISIBLE : View.GONE);
        iv_youhui.setVisibility(isHui ? View.VISIBLE : View.GONE);
    }

}
