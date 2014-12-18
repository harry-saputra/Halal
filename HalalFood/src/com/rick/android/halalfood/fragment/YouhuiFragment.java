package com.rick.android.halalfood.fragment;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.rick.android.halalfood.R;
import com.rick.android.halalfood.activity.BizDetailsActivity;
import com.rick.android.halalfood.fragment.base.BaseFragment;
import com.rick.android.halalfood.http.BusinessApi;
import com.rick.android.halalfood.http.response.FindBusinessResponse;
import com.rick.android.halalfood.utils.HFVolleyErrorListener;

public class YouhuiFragment extends BaseFragment {

    Listener<FindBusinessResponse> sucessListener;
    ErrorListener errorListener;

    @InjectView(R.id.tv_ceshi)
    TextView tv_ceshi;
    @InjectView(R.id.btn_ceshi)
    Button btn_ceshi;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_youhui;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    @OnClick({
            R.id.tv_ceshi, R.id.btn_ceshi
    })
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.tv_ceshi:
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("city", "上海");
                paramMap.put("category", "美食");
                paramMap.put("region", "长宁区");
                paramMap.put("limit", "20");
                paramMap.put("has_coupon", "1");
                paramMap.put("has_deal", "1");
                paramMap.put("keyword", "肯德基̩");
                paramMap.put("sort", "1");
                paramMap.put("format", "json");
                BusinessApi.findBussiness(paramMap, sucessListener, errorListener, TAG);
                break;
            case R.id.btn_ceshi:
                Intent intent = new Intent(mContext, BizDetailsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initListener() {
        errorListener = new HFVolleyErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

        };

        sucessListener = new Listener<FindBusinessResponse>() {

            @Override
            public void onResponse(FindBusinessResponse response) {
                if (response == null) {
                    showToast("出错了");
                } else if (response.isStatusOk()) {
                    tv_ceshi.setText("total_count:" +
                            response.getTotal_count() + "  count:" +
                            response.getCount());
                } else {
                    showToast("解析出错");
                }
            }
        };
    }

}
