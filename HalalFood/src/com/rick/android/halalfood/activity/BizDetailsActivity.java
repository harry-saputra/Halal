package com.rick.android.halalfood.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.activity.base.BaseActicity;
import com.rick.android.halalfood.module.fadingactionbar.ScrollViewX;
import com.rick.android.halalfood.module.fadingactionbar.ScrollViewX.OnScrollViewListener;

public class BizDetailsActivity extends BaseActicity {

    private Toolbar mToolbar;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ColorDrawable cd = new ColorDrawable(Color.rgb(68, 74, 83));
        cd.setAlpha(0);
        getSupportActionBar().setBackgroundDrawable(cd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(Html.fromHtml("<b><font color='#ffffff'>店铺详情</font></b>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#ffffff'>耶里夏丽</font>"));

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        addTextViews(ll);

        ScrollViewX scrollView = (ScrollViewX) findViewById(R.id.scroll_view);
        scrollView.setOnScrollViewListener(new OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewX v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = 650;
                if (scrollY > maxDist) {
                    return 255;
                }
                else if (scrollY < minDist) {
                    return 0;
                }
                else {
                    int alpha = 0;
                    alpha = (int) ((255.0 / maxDist) * scrollY);
                    return alpha;
                }
            }
        });
    }

    private void addTextViews(LinearLayout ll) {
        for (int i = 0; i < 26; i++) {
            TextView tv1 = new TextView(this);
            tv1.setText(String.valueOf(i));
            tv1.setTextSize(10);
            tv1.setWidth(500);
            tv1.setHeight(500);
            tv1.setBackgroundColor(Color.rgb(255 - 10 * i, 255 - 10 * i, 255 - 10 * i));
            tv1.setGravity(Gravity.CENTER);
            ll.addView(tv1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.business_details, menu);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
