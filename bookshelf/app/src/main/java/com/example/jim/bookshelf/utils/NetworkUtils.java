package com.example.jim.bookshelf.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * Created by jim on 16-3-19.
 */
public class NetworkUtils
{
    public static final int MOBILE = 2;
    public static final int NONE = 0;
    public static final int WIFI = 1;

    public static int getNetworkState(Context paramContext)
    {
        if (paramContext != null)
        {
            ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
            if (localConnectivityManager != null)
            {
                NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
                if (localNetworkInfo1 != null)
                {
                    NetworkInfo.State localState2 = localNetworkInfo1.getState();
                    if ((localState2 == NetworkInfo.State.CONNECTED) || (localState2 == NetworkInfo.State.CONNECTING))
                        return 1;
                }
                NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
                if (localNetworkInfo2 != null)
                {
                    NetworkInfo.State localState1 = localNetworkInfo2.getState();
                    if ((localState1 == NetworkInfo.State.CONNECTED) || (localState1 == NetworkInfo.State.CONNECTING))
                        return 2;
                }
            }
        }
        return 0;
    }

    public static boolean isNetworkAvailable(Context paramContext)
    {
        boolean bool = false;
        if (paramContext != null)
        {
            ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            bool = false;
            if (localNetworkInfo != null)
                bool = localConnectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return bool;
    }
}
