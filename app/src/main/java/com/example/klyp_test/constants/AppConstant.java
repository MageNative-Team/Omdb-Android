package com.example.klyp_test.constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AppConstant {

    public static final String BASE_URL = "http://www.omdbapi.com/";
    public static final String APIKEY = "1d094e25";
    public static final String RESULT_TYPE = "movie";
    public static final String FALSE = "False";
    public static final String INTERNETISSUE="internetIssue";
    public static final String ERROROCCURED="errorOccured";
    public static final String OOPS="oops";

    public static boolean ConnectivityCheck(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
