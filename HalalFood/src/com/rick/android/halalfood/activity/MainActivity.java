package com.rick.android.halalfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.activity.base.BaseActicity;
import com.rick.android.halalfood.fragment.HomeFragment;
import com.rick.android.halalfood.fragment.TuangouFragment;
import com.rick.android.halalfood.fragment.YouhuiFragment;
import com.rick.android.halalfood.fragment.YudingFragment;

public class MainActivity extends BaseActicity implements OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ShareActionProvider mShareActionProvider;
    private Fragment homeFragment;
    private Fragment youhuiFragment;
    private Fragment tuangouFragment;
    private Fragment yudingFragment;
    private RelativeLayout rl_item1, rl_item2, rl_item3, rl_item4, rl_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_content, homeFragment)
                    .commit();
        }
        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        rl_item1 = (RelativeLayout) findViewById(R.id.rl_item1);
        rl_item2 = (RelativeLayout) findViewById(R.id.rl_item2);
        rl_item3 = (RelativeLayout) findViewById(R.id.rl_item3);
        rl_item4 = (RelativeLayout) findViewById(R.id.rl_item4);
        rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
        rl_item1.setOnClickListener(this);
        rl_item2.setOnClickListener(this);
        rl_item3.setOnClickListener(this);
        rl_item4.setOnClickListener(this);
        rl_settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item1:
                homeFragment = new HomeFragment();
                showFrontFragment(homeFragment);
                break;
            case R.id.rl_item2:
                youhuiFragment = new YouhuiFragment();
                showFrontFragment(youhuiFragment);
                break;
            case R.id.rl_item3:
                tuangouFragment = new TuangouFragment();
                showFrontFragment(tuangouFragment);
                break;
            case R.id.rl_item4:
                yudingFragment = new YudingFragment();
                showFrontFragment(yudingFragment);
                break;
            case R.id.rl_settings:
                Intent intent = new Intent(mContext, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 显示选中的Fragment
     * 
     * @param fragment
     */
    private void showFrontFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        /** 按Back键不退出应用，与Home键效果相同 */
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
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
                showToast("设置");
                break;
            case R.id.action_share:
                showToast("分享");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
