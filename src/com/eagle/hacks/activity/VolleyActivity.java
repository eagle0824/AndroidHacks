
package com.eagle.hacks.activity;

import android.R.integer;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class VolleyActivity extends BaseActivity {

    // String
    // format={fa:图片1,fb：图片2,fc:温度1,fd：温度2,fe:风向1,ff：风向2,fg:风力1,fh：风力2,fi:日出日落};

    private static final String TAG = VolleyActivity.class.getSimpleName();
    private Context mContext;
    private RequestQueue mRequestQueue;
    // private static final String URI_STRING =
    // "http://mobile.weather.com.cn/data/forecast/101190101.html";
    private static final String URI_STRING = "http://m.weather.com.cn/atad/101190101.html";

    private TextView mDisplay;

    private HashMap<String, String> mWeatherType;
    private HashMap<String, String> mWindDirection;
    private HashMap<String, String> mWindPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley);
        mContext = this;
        mDisplay = (TextView) findViewById(R.id.content);
        mRequestQueue = Volley.newRequestQueue(mContext);
        initDatas();
    }

    private void initDatas() {
        int[] weatcherTypeIds = getResources().getIntArray(R.array.wether_type_id);
        int[] windDirectionIds = getResources().getIntArray(R.array.wind_direction_id);
        int[] windPowerIds = getResources().getIntArray(R.array.wind_power_id);
        String[] weatherType = getResources().getStringArray(R.array.weather_type);
        String[] windDirection = getResources().getStringArray(R.array.wind_direction);
        String[] windPower = getResources().getStringArray(R.array.wind_power);
        int len = weatcherTypeIds.length;
        mWeatherType = new HashMap<String, String>(len);
        for (int i = 0; i < len; i++) {
            mWeatherType.put(String.valueOf(weatcherTypeIds[i]), weatherType[i]);
        }
        len = windDirectionIds.length;
        mWindDirection = new HashMap<String, String>(len);
        for (int i = 0; i < len; i++) {
            mWindDirection.put(String.valueOf(windDirectionIds[i]), windDirection[i]);
        }
        len = windPowerIds.length;
        mWindPower = new HashMap<String, String>(len);
        for (int i = 0; i < len; i++) {
            mWindPower.put(String.valueOf(windPowerIds[i]), windPower[i]);
        }
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
    }

    private static final int EVENT_UPDATE_CONTENT = 1;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_CONTENT:
                    City city = parserJSONObject((JSONObject) msg.obj);
                    mDisplay.setText(city != null ? city.toString() : " refresh failed!");
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
        Utils.logd(TAG, "city : " + (city == null ? null : city));
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
        data.setWeather(getKeyValue(object, City.Data.KEY_TEMP + dayStr));
        data.setWindDirection(getKeyValue(object, City.Data.KEY_WIND_DIRECTION + dayStr));
        data.setWindPower(getKeyValue(object, City.Data.KEY_WIND_POWER + dayStr));
        data.setWindLevel(getKeyValue(object, City.Data.KEY_WIND_LEVEL + dayStr));
        return data;
    }

    /*
     * private City parserCityObject(JSONObject object) { String cityId =
     * getKeyValue(object, City.ID_KEY); if (!TextUtils.isEmpty(cityId)) { City
     * city = new City(cityId); String cityName = getKeyValue(object,
     * City.NAME_KEY); String province = getKeyValue(object, City.PROVINCE_KEY);
     * String countryName = getKeyValue(object, City.COUNTRY_KEY); String
     * zipCode = getKeyValue(object, City.ZIP_CODE_KEY);
     * city.setCityName(cityName); city.setProvince(province);
     * city.setCountry(countryName); city.setZipCode(zipCode); return city; }
     * return null; } private ArrayList<Data> parserWeather(JSONObject object,
     * City city) { JSONArray dataArray = getJsonArray(object, City.DATA1_KEY);
     * if (dataArray != null) { ArrayList<Data> datas = new ArrayList<Data>();
     * int length = dataArray.length(); Data data = null; for (int i = 0; i <
     * length; i++) { data = parserWeatherData(getJsonObject(dataArray, i),
     * city); if (data != null) { datas.add(data); } } return datas.size() > 0 ?
     * datas : null; } return null; } private String getWindType(String typeId)
     * { if (mWeatherType.containsKey(typeId)) { return
     * mWeatherType.get(typeId); } return ""; } private String
     * getWindDirection(String typeId) { if (mWindDirection.containsKey(typeId))
     * { return mWindDirection.get(typeId); } return ""; } private String
     * getWindPower(String typeId) { if (mWindPower.containsKey(typeId)) {
     * return mWindPower.get(typeId); } return ""; } private Data
     * parserWeatherData(JSONObject object, City city) { Iterator<String> keys =
     * object.keys(); String key = ""; String value = ""; Data data = city.new
     * Data(); while (keys.hasNext()) { key = keys.next(); value =
     * getKeyValue(object, key); if (key.equals(City.Data.TYPE1_KEY)) {
     * data.setType1(getWindType(value)); } else if
     * (key.equals(City.Data.TYPE2_KEY)) { data.setType2(getWindType(value)); }
     * else if (key.equals(City.Data.TEMP1_KEY)) { data.setTemp1(value); } else
     * if (key.equals(City.Data.TEMP2_KEY)) { data.setTemp2(value); } else if
     * (key.equals(City.Data.WINDLEVEL1_KEY)) {
     * data.setWindLevel1(getWindDirection(value)); } else if
     * (key.equals(City.Data.WINDLEVEL2_KEY)) {
     * data.setWindLevel2(getWindDirection(value)); } else if
     * (key.equals(City.Data.WINDPOWER1_KEY)) {
     * data.setWindPower1(getWindPower(value)); } else if
     * (key.equals(City.Data.WINDPOWER2_KEY)) {
     * data.setWindPower2(getWindPower(value)); } else if
     * (key.equals(City.Data.SUNRISE_SUNSET_KEY)) { String[] values =
     * value.split("\\|"); if (values.length == 2) { data.setSunrise(values[0]);
     * data.setSunset(values[1]); } } } return data; }
     */
    private JSONArray getJsonArray(JSONObject object, String name) {
        try {
            return object.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getJsonObject(JSONObject object, String name) {
        try {
            return object.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getJsonObject(JSONArray array, int index) {
        try {
            return array.getJSONObject(index);
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

    private int getIntValue(JSONObject object, String name) {
        try {
            return object.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
