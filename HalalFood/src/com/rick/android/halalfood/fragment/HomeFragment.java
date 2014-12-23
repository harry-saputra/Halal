package com.rick.android.halalfood.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.rick.android.halalfood.R;
import com.rick.android.halalfood.activity.BizDetailsActivity;
import com.rick.android.halalfood.adapter.CateAdapter;
import com.rick.android.halalfood.fragment.base.BaseFragment;
import com.rick.android.halalfood.http.BusinessApi;
import com.rick.android.halalfood.http.response.FindBusinessResponse;
import com.rick.android.halalfood.model.Businesse;
import com.rick.android.halalfood.utils.HFVolleyErrorListener;
import com.rick.android.halalfood.utils.custom.ListViewUtil;
import com.rick.android.volley.HFVolley;

public class HomeFragment extends BaseFragment implements OnClickListener {

    private static HomeFragment instance;

    public static final HomeFragment getInstance() {
        if (null == instance) {
            instance = new HomeFragment();
        }
        return instance;
    }

    RelativeLayout rl_meishi, rl_tuangou, rl_youhui, rl_yuding;

    ListView mListView;
    CateAdapter mAdapter;

    ErrorListener errorListener;
    Listener<FindBusinessResponse> sucessListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview);
        rl_meishi = (RelativeLayout) rootView.findViewById(R.id.rl_meishi);
        rl_tuangou = (RelativeLayout) rootView.findViewById(R.id.rl_tuangou);
        rl_youhui = (RelativeLayout) rootView.findViewById(R.id.rl_youhui);
        rl_yuding = (RelativeLayout) rootView.findViewById(R.id.rl_yuding);
        rl_meishi.setOnClickListener(this);
        rl_tuangou.setOnClickListener(this);
        rl_youhui.setOnClickListener(this);
        rl_yuding.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        requestData();
    }

    private void requestData() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("city", "上海");
        paramMap.put("category", "美食");
        paramMap.put("region", "浦东新区");
        paramMap.put("limit", "20");
        paramMap.put("has_coupon", "0");
        paramMap.put("has_deal", "0");
        paramMap.put("keyword", "清真");
        paramMap.put("sort", "1");
        paramMap.put("format", "json");
        BusinessApi.findBussiness(paramMap, sucessListener, errorListener, TAG);
    }

    @Override
    public void onStop() {
        HFVolley.cancelPendingRequests(TAG);
        super.onStop();
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
                if (null != response && response.isStatusOk()) {
                    fillData(response.getBusinesses());
                } else {
                    showToast("数据异常");
                }
            }
        };

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, BizDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillData(List<Businesse> mList) {
        if (null == mAdapter) {
            mAdapter = new CateAdapter(mContext, mList);
            mListView.setAdapter(mAdapter);
            ListViewUtil.setListViewHeightBasedOnChildren(mListView);
        } else {
            mListView.setAdapter(mAdapter);
            ListViewUtil.setListViewHeightBasedOnChildren(mListView);
            mAdapter.refresh(mList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_meishi:
                break;
            default:
                break;
        }
    }

}
