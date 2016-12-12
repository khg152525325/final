package com.example.lin.afinal;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lin.afinal.R;
import com.example.lin.afinal.weatherJosn;

//import org.json.JSONObject;


public class TabFragment1 extends Fragment {

    TextView wtextview,wintextview,temtextview;
    RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wtextview = (TextView) getView().findViewById(R.id.weather);
        wintextview = (TextView) getView().findViewById(R.id.textView6);
        temtextview = (TextView) getView().findViewById(R.id.wtemplow);
        initdata();
        return inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

    }
    public void initdata() {
        String url = "http://192.168.43.251/weather/weather.php";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("weather", "response" + s);
                weatherJosn weather = com.alibaba.fastjson.JSONObject.parseObject(s, weatherJosn.class);
                setWeatherToView(weather);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("weather", "onErrorResponse: " + volleyError.getMessage());
            }
        });
        rq.add(request);
    }
    private void setWeatherToView(weatherJosn weather){
        wtextview.setText(weather.getWeathertext());
        wintextview.setText(weather.getWindspeed());
        temtextview.setText(weather.getTemper1());
    }

    }

