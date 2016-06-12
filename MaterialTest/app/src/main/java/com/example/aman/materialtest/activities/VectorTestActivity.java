package com.example.aman.materialtest.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.aman.materialtest.R;
import com.example.aman.materialtest.extras.Util;
import com.telly.mrvector.MrVector;

public class VectorTestActivity extends AppCompatActivity {
     Toolbar toolbar;
     ImageView mImageView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_test);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImageView =(ImageView) findViewById(R.id.vectorImage);
        Drawable drawable=null;
        if(Util.isLollipopOrGreater()){
            drawable = MrVector.inflate(getResources(), R.drawable.animator_vector_clock);
        }else{
            drawable = MrVector.inflate(getResources(),R.drawable.vector_clock);
        }if(Util.isJellyBeanOrGreater()){
            mImageView.setBackground(drawable);
        }else{
            mImageView.setBackgroundDrawable(drawable);
        }

        if (drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
