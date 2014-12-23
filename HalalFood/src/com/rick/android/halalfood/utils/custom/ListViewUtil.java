package com.rick.android.halalfood.utils.custom;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * ScrollView(ListView) 嵌套 ListView 的解决方案
 * 
 * @author hua
 */
public class ListViewUtil {

    /**
     * 在设置ListView的Adapter后调用此静态方法，即可让ListView正确的显示在其父ScrollView(或父ListView)
     * 的ListItem中<br>
     * 注意：ListView的每个Item必须是LinearLayout，不能是其他的，因为其他的Layout(如RelativeLayout)
     * 没有重写onMeasure()，所以会在onMeasure()时抛出异常<br>
     * 
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
