package com.example.lin.afinal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lin.afinal.R;
import com.example.lin.afinal.weatherJosn;

//import org.json.JSONObject;


public class HomeActivity extends AppCompatActivity {

    TextView wtextview,wintextview,temtextview;
    ImageView weatherimage;
    String icon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        wtextview = (TextView) findViewById(R.id.weather);
        wintextview = (TextView)findViewById(R.id.textView6);
        temtextview = (TextView)findViewById(R.id.wtemplow);
        weatherimage = (ImageView)findViewById(R.id.imageView);
        initdata();


    }
    public void setWeatherToView(weatherJosn weather) {
        wtextview.setText(weather.getWeathertext());
        wintextview.setText(weather.getWindspeed());
        temtextview.setText(weather.getTemper1());
    }

    public void iconimage(weatherJosn weather){
        String urlicon = weather.getWeathericon();
        ImageRequest imageRequest = new ImageRequest(urlicon,new Response.Listener<Bitmap>()(

                @Override
                        public void onResponse(Bitmap response){

        }
                )
        )
    }






    public void initdata() {
        String url = "http://192.168.43.251/weather/weather.php";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("weather", "response" + s);
                weatherJosn weather = JSONObject.parseObject(s, weatherJosn.class);
                setWeatherToView(weather);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("weather", "onErrorResponse: " + volleyError.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(request);
    }


    }

