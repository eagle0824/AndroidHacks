
package com.eagle.hacks.activity;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeechService;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.ErrorListener;
import com.eagle.hacks.R;
import com.eagle.hacks.Utils;

import org.json.JSONObject;

public class VolleyActivity extends BaseActivity {

//    String format={fa:图片1,fb：图片2,fc:温度1,fd：温度2,fe:风向1,ff：风向2,fg:风力1,fh：风力2,fi:日出日落};

    
    private static final String TAG = VolleyActivity.class.getSimpleName();
    private Context mContext;
    private RequestQueue mRequestQueue;
    private static final String URI_STRING = "http://mobile.weather.com.cn/data/forecast/101190101.html";
    //private static final String URI_STRING = "http://m.weather.com.cn/data/101190101.html";
    private TextView mDisplay;
    int[] weatcherTypeIds;
    int[] windDirectionIds;
    int[] windPowerIds;
    String[] weatherType;
    String[] windDirection;
    String[] windPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley);
        mContext = this;
        mDisplay = (TextView) findViewById(R.id.content);
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestQueue.cancelAll(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onUpdateClick(View view) {
        Utils.logd(TAG, "onUpdateClick!");
        mRequestQueue.add(new JsonObjectRequest(Method.GET, URI_STRING, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utils.logd(TAG, "response : " + response.toString());
                        mHandler.obtainMessage(EVENT_UPDATE_CONTENT, response).sendToTarget();
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        Utils.logd(TAG, "onErrorResponse : " + arg0);

                    }

                }));
        mRequestQueue.start();
    }

    private static final int EVENT_UPDATE_CONTENT = 1;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_CONTENT:
                    mDisplay.setText(String.valueOf(msg.obj));
                    break;
                default:
                    break;
            }
        }

    };
}
