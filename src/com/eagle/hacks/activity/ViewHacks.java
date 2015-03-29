
package com.eagle.hacks.activity;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;

import com.eagle.hacks.R;
import com.eagle.hacks.view.LedTextView;

import java.util.Calendar;

public class ViewHacks extends BaseActivity {

    private static final int EVENT_UPDATE_TIME = 1;
    private LedTextView mLed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_hacks);
        mLed = (LedTextView) findViewById(R.id.led_time);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(EVENT_UPDATE_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(EVENT_UPDATE_TIME);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case EVENT_UPDATE_TIME:
                    Time time = new Time();
                    time.set(System.currentTimeMillis());
                    String display = String.format("%02d:%02d:%02d", time.hour,time.minute, time.second);
                    mLed.setText(display);
                    mHandler.sendEmptyMessageDelayed(EVENT_UPDATE_TIME, 1000);
                    break;
            }
        }

    };
}
