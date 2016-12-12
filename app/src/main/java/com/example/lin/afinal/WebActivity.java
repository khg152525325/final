package com.example.lin.afinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String URL = "http://192.168.43.251/game/a2.php";

    public static final String KEY_PHONE = "phone";

    public static final String SHARE_PHONE = "phone";

    private static final String TAG = "log";

    Button backbtn;
    WebView webView;
    Context context;
    String sessionCookie;
    CookieManager cookieManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        backbtn = (Button) findViewById(R.id.backbtn);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://192.168.43.251/test1/success.php");
        //webView.loadUrl("http://qooah.com/2016/12/01/spark-for-mac/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //WebView.addJava(new JavaScriptinterface(this),"android");
        webSettings.setDomStorageEnabled(true);
        //CookieManager cm = CookieManager.getInstance();
        //cm.removeAllCookie();
        //cm.getCookie("http://192.168.43.251/test1/success.php");
        //cm.setCookie("http://192.168.43.251/test1/success.php", cookie);
        webView.setWebViewClient(new WebViewClient());
        backbtn.setOnClickListener(this);
    }



    public void phonelogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("dbuser", Context.MODE_PRIVATE);
        final String phone = sharedPreferences.getString(SHARE_PHONE, "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                            Log.d(TAG,"phone ->" + response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_PHONE, phone);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*public static boolean syncCookie(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie)?false:true;
    }*/


    @Override
    public void onClick(View v) {
        finish( );
        System.exit(0);

    }


        }

