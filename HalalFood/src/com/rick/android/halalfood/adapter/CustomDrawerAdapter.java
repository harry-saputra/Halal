package com.rick.android.halalfood.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.module.drawer.DrawerItem;

public class CustomDrawerAdapter extends BaseListAdapter<DrawerItem> {

    public CustomDrawerAdapter(Context context, List<DrawerItem> listItems) {
        super(context, listItems);
    }

    @SuppressLint("InflateParams")
    protected View getItemView(View convertView, int position) {
        ViewHolder drawerHolder;
        if (convertView == null) {
            drawerHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_drawer, null);
            drawerHolder.noticeNum = (TextView) convertView
                    .findViewById(R.id.tv_item_notice_num);
            drawerHolder.icon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
            drawerHolder.title = (TextView) convertView.findViewById(R.id.tv_item_title);
            convertView.setTag(drawerHolder);
        } else {
            drawerHolder = (ViewHolder) convertView.getTag();
        }

        DrawerItem dItem = (DrawerItem) mValues.get(position);
        drawerHolder.icon.setImageDrawable(convertView.getResources().getDrawable(
                dItem.getImgResID()));
        drawerHolder.title.setText(dItem.getTitle());
        if (dItem.getNoticeNum() > 0) {
            drawerHolder.noticeNum.setVisibility(View.VISIBLE);
            drawerHolder.noticeNum.setText(dItem.getNoticeNum() + "");
        } else {
            drawerHolder.noticeNum.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title, noticeNum;
        ImageView icon;
    }

}
