package com.eurojava.runtimeandroidapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class MainActivity extends Activity
{
    
    LinearLayout ll;  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
         ll = (LinearLayout) findViewById(R.id.layout);
         
         DatePicker dp = new DatePicker(this);
         dp.updateDate(2014, 1, 12);
         ll.addView(dp);
         
         
        
    }
}
