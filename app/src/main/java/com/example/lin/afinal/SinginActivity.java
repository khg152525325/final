package com.example.lin.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class SinginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://192.168.43.251:8888/member/singin.php";

    public static final String Key_ph = "phone";
    public static final String Key_pass = "password";

    public static final String SHARE_PHONE = "phone";
    public static final String SHARE_PASS = "password";

    private EditText singinPhone;
    private EditText singinPassword;

    private TextInputLayout phoneLayout;
    private TextInputLayout passwordLayout;

    private Button singinButton;
    private Button loginbtn;

    private String phone;
    private String password;

    private boolean singgedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        singinPhone = (EditText) findViewById(R.id.singin_ph);
        singinPassword = (EditText) findViewById(R.id.singin_pa);

        phoneLayout = (TextInputLayout) findViewById(R.id.til_ph);
        passwordLayout = (TextInputLayout) findViewById(R.id.til_pa);

        singinButton = (Button) findViewById(R.id.singin_bt);
        loginbtn = (Button) findViewById(R.id.Login_btn);
        singinButton.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("dbuser", Context.MODE_PRIVATE);

        singgedIn = sharedPreferences.getBoolean("loggedin", false);

        if(singgedIn){
            Intent intent = new Intent(SinginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void singinUser() {
        phone = singinPhone.getText().toString().trim();
        password = singinPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = SinginActivity.this.getSharedPreferences("dbuser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putBoolean("loggedin", true);
                        editor.putString(SHARE_PHONE, phone);
                        editor.putString(SHARE_PASS,password);

                        editor.commit();
                        openProfile();
                        Toast.makeText(SinginActivity.this, response, Toast.LENGTH_LONG).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SinginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Key_ph, phone);
                params.put(Key_pass, password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Key_ph, phone);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        if(v == singinButton){
            if(singinPhone.length() == 0){
                phoneLayout.setError("請輸入電話");
            }else if(singinPhone.length() != 0){
                phoneLayout.setError(null);
            }
            if(singinPhone.length() != 10 && singinPhone.length() >= 1){
                phoneLayout.setError("您輸入電話要十位數字");
            }else if(singinPhone.length() == 10){
                phoneLayout.setError(null);
            }
            if(singinPassword.length() == 0){
                passwordLayout.setError("請輸入密碼");
            }else if(singinPassword.length() != 0){
                passwordLayout.setError(null);
            }
            if(singinPassword.length() != 6 && singinPassword.length() >= 1){
                passwordLayout.setError("您輸入密碼要六位字");
            }else if(singinPassword.length() == 6){
                phoneLayout.setError(null);
            }
            if(singinPhone.length() != 0 && singinPassword.length() != 0
                    && singinPhone.length() == 10 && singinPassword.length() == 6){

                phoneLayout.setError(null);
                passwordLayout.setError(null);
                singinUser();
            }

        }
        if (v == loginbtn) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}

