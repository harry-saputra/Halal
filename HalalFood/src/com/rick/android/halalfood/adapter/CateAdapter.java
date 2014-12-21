package com.rick.android.halalfood.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rick.android.halalfood.R;
import com.rick.android.halalfood.model.Businesse;
import com.rick.android.halalfood.utils.DevUtil;

public class CateAdapter extends BaseListAdapter<Businesse> {

    public CateAdapter(Context context, List<Businesse> listItems) {
        super(context, listItems);
        DevUtil.d("hua", "List<Businesse>:" + listItems.toString());
    }

    public void refresh(List<Businesse> values) {
        resetListData(values);
        this.notifyDataSetChanged();
    }

    public void resetListData(List<Businesse> values) {
        if (null != values) {
            mValues.clear();
            mValues.addAll(values);
        }
    }

    @SuppressLint("InflateParams")
    protected View getItemView(View convertView, int position) {
        ViewHolder drawerHolder;
        if (convertView == null) {
            drawerHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_cate, null);
            drawerHolder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            drawerHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            drawerHolder.tv_label = (TextView) convertView.findViewById(R.id.tv_label);
            drawerHolder.tv_avg_rating = (TextView) convertView.findViewById(R.id.tv_avg_rating);
            drawerHolder.tv_avg_price = (TextView) convertView.findViewById(R.id.tv_avg_price);
            drawerHolder.tv_addres = (TextView) convertView.findViewById(R.id.tv_addres);
            drawerHolder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            convertView.setTag(drawerHolder);
        } else {
            drawerHolder = (ViewHolder) convertView.getTag();
        }

        Businesse item = (Businesse) mValues.get(position);
        drawerHolder.tv_name.setText(item.getName());

        String label = "";
        if ("1".equals(item.getHas_coupon())) {
            // 是否有优惠
            label = "优 ";
        }
        if ("1".equals(item.getHas_deal())) {
            // 是否有团购
            label = "团 ";
        }
        if ("1".equals(item.getHas_online_reservation())) {
            // 是否有预定
            label = "预";
        }
        if (!TextUtils.isEmpty(label)) {
            drawerHolder.tv_label.setText(label);
        }

        drawerHolder.tv_avg_rating.setText(item.getAvg_rating());
        drawerHolder.tv_avg_price.setText(item.getAvg_price());
        drawerHolder.tv_addres.setText(item.getAddress());
        drawerHolder.tv_distance.setText(item.getDistance());

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_name, tv_label, tv_avg_rating, tv_avg_price, tv_addres, tv_distance;
        ImageView iv_photo;
    }

}
