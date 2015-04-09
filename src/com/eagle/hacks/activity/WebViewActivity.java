
package com.eagle.hacks.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eagle.hacks.R;
import com.eagle.hacks.Utils;

public class WebViewActivity extends BaseActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private Context mContext;
    private RequestQueue mRequestQueue;

    private WebView mWebContent;
    private EditText mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        mContext = this;
        mWebContent = (WebView) findViewById(R.id.web_content);
        mWeb = (EditText) findViewById(R.id.web);
        mRequestQueue = Volley.newRequestQueue(mContext);
        WebSettings settings = mWebContent.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true);
        mWebContent.setWebViewClient(new WebViewClient() {
            // 当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // 加载新的url
                return true; // 返回true,代表事件已处理,事件流到此终止
            }
        });
        mWebContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebContent.canGoBack()) {
                        mWebContent.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // mRequestQueue.cancelAll(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onGoClick(View view) {
        Utils.logd(TAG, "onGoClick!");
        String text = mWeb.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            mHandler.obtainMessage(EVENT_GO, text).sendToTarget();
        }
    }

    private static final int EVENT_GO = 1;
    private static final int EVENT_UPDATE_CONTENT = 2;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_GO:
                    String url = String.valueOf(msg.obj);
                    if (!url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    mWebContent.loadUrl(url);
                    mWebContent.requestFocus();
                    /*
                     * mRequestQueue.add(new
                     * StringRequest(String.valueOf(msg.obj), new
                     * Listener<String>() {
                     * @Override public void onResponse(String response) {
                     * Utils.logd(TAG, "response : " + response.toString());
                     * mHandler.obtainMessage(EVENT_UPDATE_CONTENT, response)
                     * .sendToTarget(); } }, new ErrorListener() {
                     * @Override public void onErrorResponse(VolleyError arg0) {
                     * Utils.logd(TAG, "onErrorResponse : " + arg0); } }));
                     */
                    break;
                case EVENT_UPDATE_CONTENT:

                    break;

                default:
                    break;
            }
        }
    };

}
