package com.rick.android.halalfood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.constants.CommonConstants;
import com.rick.android.halalfood.module.guide.PagerAdapter;
import com.rick.android.halalfood.module.guide.VerticalViewPager;
import com.rick.android.halalfood.utils.PhoneInfo;

public class GuideActivity extends BaseActicity {

    private VerticalViewPager mViewPager;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();

        mViewPager = (VerticalViewPager) findViewById(R.id.pager);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PagerAdapter adapter = new MyPagerAdapter(this);
        mViewPager.setAdapter(adapter);
    }

    private class MyPagerAdapter extends PagerAdapter {

        private Activity activity;

        public MyPagerAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @SuppressLint("InflateParams")
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = null;
            switch (position) {
                case 0:
                    rootView = layoutInflater.inflate(R.layout.view_guide_one, null);
                    break;
                case 1:
                    rootView = layoutInflater.inflate(R.layout.view_guide_two, null);
                    break;
                case 2:
                    rootView = layoutInflater.inflate(R.layout.view_guide_three, null);
                    RelativeLayout rl_two = (RelativeLayout) rootView.findViewById(R.id.rl_3);
                    rl_two.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // setGuided();
                            goNext();
                        }
                    });
                    break;
                default:
                    break;
            }
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * @Title: goStart
         * @Description: 去下一页
         * @param 设定文件
         * @return void 返回类型
         * @throws
         */
        private void goNext() {
            Intent intent = new Intent();
            intent.setClass(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

        /**
         * 设置已经引导过了，下次启动不用再次引导
         */
        private void setGuided() {
            SharedPreferences preferences = activity.getSharedPreferences(
                    CommonConstants.SP_FIRST_START, Context.MODE_PRIVATE);
            Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.putString("version", PhoneInfo.VersionName);
            editor.commit();
        }

    }

}
