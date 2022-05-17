package com.example.akcsit_protikdatta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent in = new Intent(MainActivity.this, SignUpActivity.class); //message passing object
            startActivity(in);
            MainActivity.this.finish(); //destroy the lifecycle of the activity
        }, 2500);

    }
}