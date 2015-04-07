
package com.eagle.hacks.mode;

import java.util.ArrayList;

public class City {

    public static final String KEY_WEATHER_INFO = "weatherinfo";
    public static final String KEY_CITY = "city";
    public static final String KEY_DATA = "date_y";
    public static final String KEY_WEEK = "week";
    public static final String KEY_UPDATE_TIME = "fchh";
    public static final String KEY_CITY_ID = "cityid";
    public static final String KEY_INDEX = "index";
    public static final String KEY_INDEX_D = "index_d";

    /*
     * {"weatherinfo":{"city":"南京","city_en":"nanjing","date_y":"2015年4月7日","date"
     * :"",
     * "week":"星期二","fchh":"11","cityid":"101190101","temp1":"8℃~2℃","temp2"
     * :"13℃~4℃",
     * "temp3":"15℃~6℃","temp4":"18℃~8℃","temp5":"18℃~12℃","temp6":"20℃~13℃",
     * "tempF1"
     * :"46.4℉~35.6℉","tempF2":"55.4℉~39.2℉","tempF3":"59℉~42.8℉","tempF4"
     * :"64.4℉~46.4℉","tempF5":"64.4℉~53.6℉", "tempF6":"68℉~55.4℉",
     * "weather1":"阵雨转阴"
     * ,"weather2":"多云","weather3":"多云","weather4":"多云转阴","weather5"
     * :"阴","weather6":"阴转阵雨",
     * "img1":"3","img2":"2","img3":"1","img4":"99","img5"
     * :"1","img6":"99","img7":"1","img8":"2","img9":"2",
     * "img10":"99","img11":"2"
     * ,"img12":"3","img_single":"3","img_title1":"阵雨","img_title2"
     * :"阴","img_title3":"多云",
     * "img_title4":"多云","img_title5":"多云","img_title6":"多云"
     * ,"img_title7":"多云","img_title8":"阴","img_title9":"阴",
     * "img_title10":"阴","img_title11"
     * :"阴","img_title12":"阵雨","img_title_single":"阵雨",
     * "wind1":"东北风4-5级转3-4级","wind2"
     * :"东风3-4级转小于3级","wind3":"东风转东北风小于3级","wind4":
     * "东北风3-4级转东南风小于3级","wind5":"东南风转南风3-4级",
     * "wind6":"西南风转西北风4-5级","fx1":"东北风",
     * "fx2":"东北风","fl1":"4-5级转3-4级","fl2":"3-4级转小于3级","fl3":"小于3级",
     * "fl4":"3-4级转小于3级","fl5":"3-4级","fl6":"4-5级",
     * "index":"冷","index_d":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。",
     * "index48"
     * :"","index48_d":"","index_uv":"最弱","index48_uv":"","index_xc":"不宜"
     * ,"index_tr":"一般","index_co":"较舒适"
     * ,"st1":"4","st2":"-2","st3":"13","st4":"4"
     * ,"st5":"16","st6":"6","index_cl"
     * :"较不宜","index_ls":"不太适宜","index_ag":"较易发"}}
     */
    ArrayList<Data> mDatas;

    private String mCityId;
    private String mCityName;
    private String mDate;
    private String mWeek;
    private String mUpdateTime;
    private String mIndex;
    private String mIndexD;

    public City(String cityId) {
        mCityId = cityId;
    }

    public ArrayList<Data> getDatas() {
        return mDatas;
    }

    public void addData(Data data) {
        if (mDatas == null) {
            mDatas = new ArrayList<City.Data>();
        }
        mDatas.add(data);
    }

    public String getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getWeek() {
        return mWeek;
    }

    public void setWeek(String week) {
        mWeek = week;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String time) {
        mUpdateTime = time;
    }

    public void setIndex(String index) {
        mIndex = index;
    }

    public String getIndex() {
        return mIndex;
    }

    public void setIndexD(String indexD) {
        mIndexD = indexD;
    }

    public String getIndexD() {
        return mIndexD;
    }

    public class Data {
        public static final String KEY_TEMP = "temp";
        public static final String KEY_WEATHER_TYPE = "weather";
        public static final String KEY_WIND_LEVEL = "wind";
        public static final String KEY_WIND_DIRECTION = "fx";
        public static final String KEY_WIND_POWER = "fl";

        private int index;
        private String temp;
        private String weather;
        private String windLevel;
        private String windDirection;
        private String windPower;

        public Data(int index) {
            this.index = index;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWindLevel() {
            return windLevel;
        }

        public void setWindLevel(String windLevel) {
            this.windLevel = windLevel;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }

        public String getWindPower() {
            return windPower;
        }

        public void setWindPower(String windPower) {
            this.windPower = windPower;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(index).append(" ");
            sb.append(temp).append(" ");
            sb.append(weather).append(" ");
            sb.append(windLevel).append(" ");
            sb.append(windDirection).append(" ");
            sb.append(windPower).append(" ");
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mCityId).append(" ");
        sb.append(mCityName).append(" ");
        sb.append(mDate).append(" ");
        sb.append(mWeek).append(" ");
        sb.append(mUpdateTime).append(" ");
        sb.append(mIndex).append(" ");
        sb.append(mIndexD).append(" ");
        sb.append("\n");
        if (mDatas != null) {
            for (Data data : mDatas) {
                sb.append("    ").append(data).append("\n");
            }
        }
        return sb.toString();
    }

}
