package com.example.lin.afinal;

import android.graphics.Bitmap;

/**
 * Created by lin on 2016/12/12.
 */

public class weatherJosn {


    /**
     * temper1 : 23
     * realfeel : 24
     * weathertext : 多雲時晴
     * windspeed : 5
     * weathericon : http://api.accuweather.com/developers/Media/Default/WeatherIcons/03-s.png
     */

    private String temper1;
    private String realfeel;
    private String weathertext;
    private String windspeed;
    private String weathericon;

    public String getTemper1() {
        return temper1;
    }

    public void setTemper1(String temper1) {
        this.temper1 = temper1;
    }

    public String getRealfeel() {
        return realfeel;
    }

    public void setRealfeel(String realfeel) {
        this.realfeel = realfeel;
    }

    public String getWeathertext() {
        return weathertext;
    }

    public void setWeathertext(String weathertext) {
        this.weathertext = weathertext;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWeathericon() {
        return weathericon;
    }

    public void setWeathericon(String weathericon) {
        this.weathericon = weathericon;
    }

}
