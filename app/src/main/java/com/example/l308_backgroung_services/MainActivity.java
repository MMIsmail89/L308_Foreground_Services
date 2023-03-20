package com.example.l308_backgroung_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.l308_backgroung_services.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    ActivityMainBinding mainBinding_activity;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mainBinding_activity = ActivityMainBinding.inflate(getLayoutInflater());
        mainBinding_activity.mainInfo.setText("start hit = "+i);

        setContentView(mainBinding_activity.getRoot());
        //
        mainBinding_activity.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_intent = new Intent(getBaseContext(), MyAudioService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(start_intent);
                } // if
                else {
                    ContextCompat.startForegroundService(getBaseContext(), start_intent);
                } // else
                mainBinding_activity.mainInfo.setText("start hit = "+ ++i);
            } // on Click Listener
        }); // mainBinding_activity.startBtn.setOnClickListener
        //
        mainBinding_activity.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stop_intent = new Intent(getBaseContext(), MyAudioService.class);
                stopService(stop_intent);
            } // on Click Listener
        }); // mainBinding_activity.stopBtn.setOnClickListener
        //

    } // on create activity Method
} // Main Activity Class