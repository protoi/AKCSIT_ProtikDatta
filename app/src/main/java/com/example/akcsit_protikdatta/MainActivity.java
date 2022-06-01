package com.example.akcsit_protikdatta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    LocalSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new LocalSession(MainActivity.this);

        new Handler().postDelayed(() -> {
            if(session.checkLogin())
            {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }
            else {
                Intent in = new Intent(MainActivity.this, SignUpActivity.class); //message passing object
                startActivity(in);
            }
            MainActivity.this.finish(); //destroy the lifecycle of the activity
        }, 2500);

    }
}