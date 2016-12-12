package com.example.lin.afinal;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class GameActivity extends Activity {

    public static final String URL = "http://192.168.43.251/member/login.php";

    public static final String KEY_PHONE = "phone";
    public static final String Key_pass = "password";

    public static final String SHARE_PHONE = "phone";
    public static final String SHARE_PASS = "password";




    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dialog = ProgressDialog.show(GameActivity.this,
                "讀取中", "請等待...",true);
        singinUser();
    }

    private void singinUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("dbuser", Context.MODE_PRIVATE);
        final String phone = sharedPreferences.getString(SHARE_PHONE, "Not Available");
        final String pass = sharedPreferences.getString(SHARE_PASS,"Not Available");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                            Toast.makeText(GameActivity.this, "成功登入", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Intent intent = new Intent(GameActivity.this, WebActivity.class);
                            startActivity(intent);
                            GameActivity.this.finish();
                        } else {
                            Toast.makeText(GameActivity.this, "登入失敗", Toast.LENGTH_LONG).show();
                            GameActivity.this.finish();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GameActivity.this, "連接失敗", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        GameActivity.this.finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_PHONE, phone);
                map.put(Key_pass, pass);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
