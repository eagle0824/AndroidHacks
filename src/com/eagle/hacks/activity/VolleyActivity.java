
package com.eagle.hacks.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eagle.hacks.R;
import com.eagle.hacks.Utils;
import com.eagle.hacks.mode.City;
import com.eagle.hacks.mode.City.Data;
import com.eagle.hacks.mode.WeatherInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyActivity extends BaseActivity {

    private static final String TAG = VolleyActivity.class.getSimpleName();
    private Context mContext;
    private RequestQueue mRequestQueue;
    // private static final String URI_STRING =
    // "http://mobile.weather.com.cn/data/forecast/101190101.html";
    private static final String URI_STRING = "http://m.weather.com.cn/atad/101190101.html";

    private TextView mDisplay;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley);
        mContext = this;
        mDisplay = (TextView) findViewById(R.id.content);
        mRequestQueue = Volley.newRequestQueue(mContext);
        mGson = new Gson();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestQueue.cancelAll(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(EVENT_UPDATE_WEATHER_INFO);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onUpdateClick(View view) {
        Utils.logd(TAG, "onUpdateClick!");
        mHandler.sendEmptyMessage(EVENT_UPDATE_WEATHER_INFO);
    }

    private static final int EVENT_UPDATE_WEATHER_INFO = 1;
    private static final int EVENT_UPDATE_CONTENT = 2;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_WEATHER_INFO:
                    mRequestQueue.add(new JsonObjectRequest(Method.GET, URI_STRING, null,
                            new Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Utils.logd(TAG, "response : " + response.toString());
                                    mHandler.obtainMessage(EVENT_UPDATE_CONTENT, response)
                                            .sendToTarget();
                                }
                            }, new ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError arg0) {
                                    Utils.logd(TAG, "onErrorResponse : " + arg0);
                                }
                            }));
                    break;
                case EVENT_UPDATE_CONTENT:
                    StringBuilder sb = new StringBuilder();
                    City city = parserJSONObject((JSONObject) msg.obj);
                    sb.append("JSONOBJECT : ").append("\n");
                    sb.append(city != null ? city.toString() : " refresh failed!");
                    sb.append("\n");
                    String json = String.valueOf(msg.obj);
                    int start = json.indexOf("weatherinfo") + "weatherinfo".length() + 2;
                    String newJson = json.substring(start, json.lastIndexOf("}"));
                    WeatherInfo info = mGson.fromJson(newJson, WeatherInfo.class);
                    sb.append("GSON : ").append("\n");
                    sb.append(info.toString());
                    mDisplay.setText(sb.toString());
                    break;

                default:
                    break;
            }
        }
    };

    private City parserJSONObject(JSONObject object) {
        City city = null;
        JSONObject cityObject = getJsonObject(object, City.KEY_WEATHER_INFO);
        if (cityObject != null) {
            city = parserWeatherInfo(cityObject);
        }
        return city;
    }

    private City parserWeatherInfo(JSONObject object) {
        String cityId = getKeyValue(object, City.KEY_CITY_ID);
        if (!TextUtils.isEmpty(cityId)) {
            City city = new City(cityId);
            city.setCityName(getKeyValue(object, City.KEY_CITY));
            city.setWeek(getKeyValue(object, City.KEY_WEEK));
            city.setDate(getKeyValue(object, City.KEY_DATA));
            city.setUpdateTime(getKeyValue(object, City.KEY_UPDATE_TIME));
            city.setIndex(getKeyValue(object, City.KEY_INDEX));
            city.setIndexD(getKeyValue(object, City.KEY_INDEX_D));
            City.Data data = null;
            for (int i = 0; i < 6; i++) {
                data = parserData(object, i + 1, city);
                city.addData(data);
            }
            return city;
        }
        return null;
    }

    private City.Data parserData(JSONObject object, int day, City city) {
        Data data = city.new Data(day);
        String dayStr = String.valueOf(day);
        data.setTemp(getKeyValue(object, City.Data.KEY_TEMP + dayStr));
        data.setWeather(getKeyValue(object, City.Data.KEY_WEATHER_TYPE + dayStr));
        data.setWindDirection(getKeyValue(object, City.Data.KEY_WIND_DIRECTION + dayStr));
        data.setWindPower(getKeyValue(object, City.Data.KEY_WIND_POWER + dayStr));
        data.setWindLevel(getKeyValue(object, City.Data.KEY_WIND_LEVEL + dayStr));
        data.setFahrenheit(getKeyValue(object, City.Data.KEY_TEMPF + dayStr));
        return data;
    }

    private JSONObject getJsonObject(JSONObject object, String name) {
        try {
            return object.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getKeyValue(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
