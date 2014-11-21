
package com.android.halalfood.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.halalfood.R;
import com.android.halalfood.http.HalalApi;

public class MainActivity extends BaseActicity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    TextView tView = (TextView) findViewById(R.id.tv);
                    tView.setText(msg.getData().getString("value"));
                    break;
                default:
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("city", "上海");
            paramMap.put("latitude", "31.21524");
            paramMap.put("longitude", "121.420033");
            paramMap.put("category", "美食");
            paramMap.put("region", "长宁区");
            paramMap.put("limit", "20");
            paramMap.put("radius", "2000");
            paramMap.put("offset_type", "0");
            paramMap.put("has_coupon", "1");
            paramMap.put("has_deal", "1");
            paramMap.put("keyword", "̩泰国菜");
            paramMap.put("sort", "7");
            paramMap.put("format", "json");

            String requestResult = HalalApi.findBussiness(paramMap);

            Message msg = new Message();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putString("value", requestResult);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    public void makeCrash(View V) {
        new Thread(runnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
