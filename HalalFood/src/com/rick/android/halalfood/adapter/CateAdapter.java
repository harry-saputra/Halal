package com.rick.android.halalfood.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rick.android.halalfood.R;
import com.rick.android.halalfood.model.Businesse;
import com.rick.android.halalfood.module.widget.TuDingLayout;
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
            drawerHolder.ll_tuding = (TuDingLayout) convertView.findViewById(R.id.ll_tuding);
            drawerHolder.rb_avg_rating = (RatingBar) convertView.findViewById(R.id.rb_avg_rating);
            drawerHolder.tv_avg_price = (TextView) convertView.findViewById(R.id.tv_avg_price);
            drawerHolder.tv_addres = (TextView) convertView.findViewById(R.id.tv_addres);
            drawerHolder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            convertView.setTag(drawerHolder);
        } else {
            drawerHolder = (ViewHolder) convertView.getTag();
        }

        Businesse item = (Businesse) mValues.get(position);

        ImageLoader.getInstance().displayImage(item.getS_photo_url(), drawerHolder.iv_photo);
        drawerHolder.rb_avg_rating.setRating(item.getAvg_rating());
        drawerHolder.ll_tuding.setContent(
                item.getName(), item.getHas_deal() == 1,
                item.getHas_online_reservation() == 1, item.getHas_coupon() == 1
                );
        drawerHolder.tv_avg_price.setText("￥" + item.getAvg_price() + "/人");
        drawerHolder.tv_addres.setText(item.getAddress());
        drawerHolder.tv_distance.setText(item.getDistance() + "m");

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_avg_price, tv_addres, tv_distance;
        ImageView iv_photo;
        TuDingLayout ll_tuding;
        RatingBar rb_avg_rating;
    }

}
