package com.example.aman.materialtest.extras;

import android.os.Build;

/**
 * Created by aman on 4/6/16.
 */
public class Util {
    public static boolean isLollipopOrGreater(){
        return Build.VERSION.SDK_INT>=21? true : false;
    }
    public static boolean isJellyBeanOrGreater(){
        return Build.VERSION.SDK_INT>=16? true : false;
    }
}
