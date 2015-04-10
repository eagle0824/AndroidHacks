
package com.eagle.hacks.mode;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("weatherinfo")
    private WeatherInfo mWeatherInfo;

    public Weather() {

    }

    public WeatherInfo getWeatherInfo() {
        return mWeatherInfo;
    }

    public class WeatherInfo {

        private String city;

        @SerializedName("date_y")
        private String date;
        private String week;
        private String cityid;
        private String fchh;
        private String index;
        private String index_d;

        private String temp1;
        private String temp2;
        private String temp3;
        private String temp4;
        private String temp5;
        private String temp6;

        private String tempF1;
        private String tempF2;
        private String tempF3;
        private String tempF4;
        private String tempF5;
        private String tempF6;

        private String wind1;
        private String wind2;
        private String wind3;
        private String wind4;
        private String wind5;
        private String wind6;

        private String fl1;
        private String fl2;
        private String fl3;
        private String fl4;
        private String fl5;
        private String fl6;

        private String fx1;
        private String fx2;
        private String fx3;
        private String fx4;
        private String fx5;
        private String fx6;

        private String weather1;
        private String weather2;
        private String weather3;
        private String weather4;
        private String weather5;
        private String weather6;

        public WeatherInfo() {

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(cityid).append(" ");
            sb.append(city).append(" ");
            sb.append(date).append(" ");
            sb.append(week).append(" ");
            sb.append(fchh).append(" ");
            sb.append(index).append(" ");
            sb.append(index_d).append(" ");
            sb.append("\n");
            sb.append("  1 ").append(weather1).append(" ").append(temp1).append(" ").append(wind1)
                    .append(" ").append(fx1).append(" ").append(fl1).append(" ").append(tempF1)
                    .append("\n");
            sb.append("  2 ").append(weather2).append(" ").append(temp2).append(" ").append(wind2)
                    .append(" ").append(fx2).append(" ").append(fl2).append(" ").append(tempF2)
                    .append("\n");
            sb.append("  3 ").append(weather3).append(" ").append(temp3).append(" ").append(wind3)
                    .append(" ").append(fx3).append(" ").append(fl3).append(" ").append(tempF3)
                    .append("\n");
            sb.append("  4 ").append(weather4).append(" ").append(temp4).append(" ").append(wind4)
                    .append(" ").append(fx4).append(" ").append(fl4).append(" ").append(tempF4)
                    .append("\n");
            sb.append("  5 ").append(weather5).append(" ").append(temp5).append(" ").append(wind5)
                    .append(" ").append(fx5).append(" ").append(fl5).append(" ").append(tempF5)
                    .append("\n");
            sb.append("  6 ").append(weather6).append(" ").append(temp6).append(" ").append(wind6)
                    .append(" ").append(fx6).append(" ").append(fl6).append(" ").append(tempF6);
            return sb.toString();
        }

    }

    @Override
    public String toString() {
        if (mWeatherInfo != null) {
            return mWeatherInfo.toString();
        }
        return "";
    }

}
