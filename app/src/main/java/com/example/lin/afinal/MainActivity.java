package com.example.lin.afinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_URL = "http://192.168.43.251/member/login.php";

    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWOED = "password";

    public static final String SHARE_PHONE = "phone";
    public static final String SHARE_PASS = "password";



    //private TextView content;
    private Button loginbtn;
    private Button singinbtn;
    private EditText loginphone;
    private EditText loginpassword;
    private TextInputLayout phoneLayout;
    private TextInputLayout passwordLayout;
    Dialog dialog;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //content = (TextView) findViewById(R.id.content);
        loginphone = (EditText) findViewById(R.id.login_ph);
        loginpassword = (EditText) findViewById(R.id.login_pa);
        phoneLayout = (TextInputLayout) findViewById(R.id.til_ph);
        passwordLayout = (TextInputLayout) findViewById(R.id.til_pa);


        loginbtn = (Button) findViewById(R.id.Login_btn);
        singinbtn = (Button) findViewById(R.id.singin_bt);
        loginbtn.setOnClickListener(this);
        singinbtn.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("dbuser",Context.MODE_PRIVATE);

        loggedIn = sharedPreferences.getBoolean("loggedin", false);

        if(loggedIn){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }


    private void userLogin() {
        dialog = ProgressDialog.show(MainActivity.this,
                "讀取中", "請等待...",true);
        final String phone = loginphone.getText().toString().trim();
        final String password = loginpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("dbuser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("loggedin", true);
                            editor.putString(SHARE_PHONE, phone);
                            editor.putString(SHARE_PASS,password);

                            editor.commit();

                            dialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "您的手機或密碼是無效的", Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_PHONE, phone);
                map.put(KEY_PASSWOED, password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {

        if (v == loginbtn) {

            if(loginphone.length() == 0){
                phoneLayout.setError("請輸入電話");
            }else if(loginphone.length() != 0){
                phoneLayout.setError(null);
            }
            if(loginphone.length() != 10 && loginphone.length() >= 1){
                phoneLayout.setError("您輸入電話要十位數字");
            }else if(loginphone.length() == 10){
                phoneLayout.setError(null);
            }
            if(loginpassword.length() == 0){
                passwordLayout.setError("請輸入密碼");
            }else if(loginpassword.length() != 0){
                passwordLayout.setError(null);
            }
            if(loginpassword.length() != 6 && loginpassword.length() >= 1){
                passwordLayout.setError("您輸入密碼要六位字");
            }else if(loginphone.length() == 6){
                phoneLayout.setError(null);
            }
            if(loginphone.length() != 0 && loginpassword.length() != 0
                    && loginphone.length() == 10 && loginpassword.length() == 6){

                phoneLayout.setError(null);
                passwordLayout.setError(null);
                userLogin();
            }

            }

            if (v == singinbtn) {
                startActivity(new Intent(this, SinginActivity.class));
            }
            //}


        }
    }

