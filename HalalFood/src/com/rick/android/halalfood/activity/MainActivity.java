package com.rick.android.halalfood.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.anjuke.android.http.volley.HFVolley;
import com.rick.android.halalfood.R;
import com.rick.android.halalfood.http.BusinessApi;
import com.rick.android.halalfood.http.response.FindBusinessResponse;
import com.rick.android.halalfood.utils.HFVolleyErrorListener;

public class MainActivity extends BaseActicity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ShareActionProvider mShareActionProvider;

    private TextView tv_test;
    Listener<FindBusinessResponse> sucessListener;
    ErrorListener errorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        tv_test = (TextView) findViewById(R.id.tv_test);

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
                    showToast(MainActivity.this, "出错了");
                } else if (response.isStatusOk()) {
                    tv_test.setText("total_count:" + response.getTotal_count() + "  count:" + response.getCount());
                } else {
                    showToast(MainActivity.this, "解析出错");
                }
            }
        };
        tv_test.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("city", "上海");
                paramMap.put("category", "美食");
                paramMap.put("region", "长宁区");
                paramMap.put("limit", "20");
                paramMap.put("radius", "5000");
                paramMap.put("has_coupon", "1");
                paramMap.put("has_deal", "1");
                paramMap.put("keyword", "̩泰国菜");
                paramMap.put("sort", "1");
                paramMap.put("format", "json");

                BusinessApi.findBussiness(paramMap, sucessListener, errorListener, TAG);
            }
        });
    }

    @Override
    protected void onStop() {
        HFVolley.cancelPendingRequests(TAG);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        /** ShareActionProvider配置 */
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu
                .findItem(R.id.action_share));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showToast(MainActivity.this, "action_settings");
                break;
            case R.id.action_share:
                showToast(MainActivity.this, "action_share");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
