package com.example.aman.materialtest.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aman on 6/6/16.
 */
public class L {
    public static void m(String message){
        Log.d("aman",""+message );
    }

    public static void t(Context context,String message){
        Toast.makeText(context,message + "",Toast.LENGTH_LONG).show();
    }
}
