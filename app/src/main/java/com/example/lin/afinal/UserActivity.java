package com.example.lin.afinal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    public static final String KEY_PHONE = "phone";
    public static final String Key_pass = "password";

    private TextView textView;
    private Button logoutbtn,gamebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textView = (TextView) findViewById(R.id.textViewUsername);


        SharedPreferences sharedPreferences = getSharedPreferences("dbuser", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString(KEY_PHONE, "Not Available");


        textView.setText("使用者：" + phone);

        getButttonView();
        setButtonEvent();


        //Intent intent = getIntent();
        //Bundle bundle = getIntent().getExtras();


        //textView.setText("Welcome User " + intent.getStringExtra(MainActivity.key_ph));
    }

    public void getButttonView(){
        logoutbtn = (Button) findViewById(R.id.logoutbtn);
        gamebtn = (Button) findViewById (R.id.gamebtn);

    }

    public void setButtonEvent(){
        logoutbtn.setOnClickListener(buttonListener);
        gamebtn.setOnClickListener(buttonListener);
    }



    private void logout() {
        new AlertDialog.Builder(UserActivity.this)
                .setTitle("登出")
                .setMessage("你是否要登出？")
                .setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                SharedPreferences preferences = getSharedPreferences("dbuser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("loggedin", false);
                                editor.putString(KEY_PHONE, "");
                                editor.putString(Key_pass, "");
                                editor.commit();
                                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        })
                .setNegativeButton("否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        })
                .show();


    }
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(v == logoutbtn) {
                logout();
            }
            if(v == gamebtn){
                Intent intent = new Intent(UserActivity.this, GameActivity.class);
                startActivity(intent);
            }
        }
    };

}
