package com.rick.android.halalfood.utils;

import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.anjuke.android.http.volley.util.VolleyErrorHelper;
import com.rick.android.halalfood.HalalFoodApp;
import com.rick.android.halalfood.R;

public class HFVolleyErrorListener implements ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        if (!NetworkUtil.isNetworkAvailable(HalalFoodApp.getInstance())) {
            Toast.makeText(HalalFoodApp.getInstance(), R.string.net_not_connected, Toast.LENGTH_SHORT).show();
        } else {
            String errorMessage = VolleyErrorHelper.getMessage(error, HalalFoodApp.getInstance());
            Toast.makeText(HalalFoodApp.getInstance(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

}
