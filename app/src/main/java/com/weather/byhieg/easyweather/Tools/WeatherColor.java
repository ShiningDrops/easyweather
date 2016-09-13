package com.weather.byhieg.easyweather.Tools;

import com.weather.byhieg.easyweather.R;

/**
 * Created by shiqifeng on 2016/9/13.
 * Mail:byhieg@gmail.com
 */
public class WeatherColor {

    public static int getWeatherColor(String weatherCode){
        int code = Integer.parseInt(weatherCode);
        if (code == 100) {
            return R.color.bisque;
        } else if (code > 100 && code < 200) {
            return R.color.lightskyblue;
        } else if (code >= 200 && code < 300) {
            return R.color.maincolor;
        } else if (code >= 300 && code < 400) {
            return R.color.deepskyblue;
        } else if (code >= 500 && code < 600) {
            return R.color.peru;
        }else {
            return R.color.violet;
        }
    }
}
