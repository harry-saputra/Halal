package com.rick.android.halalfood.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.activity.base.BaseActicity;
import com.rick.android.halalfood.adapter.CustomDrawerAdapter;
import com.rick.android.halalfood.fragment.HomeFragment;
import com.rick.android.halalfood.fragment.TuangouFragment;
import com.rick.android.halalfood.fragment.YouhuiFragment;
import com.rick.android.halalfood.fragment.YudingFragment;
import com.rick.android.halalfood.module.drawer.DrawerItem;

public class MainActivity extends BaseActicity implements OnClickListener {

    private List<DrawerItem> mList;
    private CharSequence mTitle;// title 置 fragment Title
    private CharSequence mDrawerTitle; // drawTitle 置 appname

    private CustomDrawerAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private ListView mListView;
    private TextView tv_about, tv_settings;
    private ImageView iv_user_photo;
    private DrawerLayout mDrawerLayout;
    private LinearLayout ll_drawer_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        SelectItem(0);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_about:
                intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_settings:
                intent = new Intent(mContext, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_user_photo:
                intent = new Intent(mContext, UserInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.draw_listview);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ll_drawer_left = (LinearLayout) findViewById(R.id.ll_drawer_left);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_settings = (TextView) findViewById(R.id.tv_settings);
        iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        tv_about.setOnClickListener(this);
        tv_settings.setOnClickListener(this);
        iv_user_photo.setOnClickListener(this);

        initActionBar();
        initDrawerToggle();
        initDrawerLeftData();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mTitle = mDrawerTitle = getTitle();
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initDrawerLeftData() {
        mList = new ArrayList<DrawerItem>();
        mList.add(new DrawerItem("首页", R.drawable.ic_icon_grey));
        mList.add(new DrawerItem("优惠", R.drawable.ic_icon_grey, 9));
        mList.add(new DrawerItem("团购", R.drawable.ic_icon_grey, 39));
        mList.add(new DrawerItem("预约", R.drawable.ic_icon_grey, 69));
        mAdapter = new CustomDrawerAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectItem(position);
            }
        });
    }

    public void SelectItem(int position) {
        mListView.setItemChecked(position, true);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.getInstance();
                break;
            case 1:
                fragment = YouhuiFragment.getInstance();
                break;
            case 2:
                fragment = TuangouFragment.getInstance();
                break;
            case 3:
                fragment = YudingFragment.getInstance();
                break;
            default:
                break;
        }

        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.fl_content, fragment)
                .commit();

        setTitle(mList.get(position).getTitle());
        mDrawerLayout.closeDrawer(ll_drawer_left);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(ll_drawer_left);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(ll_drawer_left)) {
            mDrawerLayout.closeDrawer(ll_drawer_left);
        } else {
            /** 按Back键不退出应用，与Home键效果相同 */
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(mContext, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
