package com.example.l308_backgroung_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
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

        mainBinding_activity.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_intent = new Intent(getBaseContext(), MyAudioService.class);
                //

                String title = mainBinding_activity.mainTitle.getText().toString();

                Uri uri = Uri.parse("content://com.example.provider/songs/123");

                Song song = new Song("song",uri,uri,8,5); // The Song object
                int playButton = 5; // The playButton value
                int position = 55; // The position value
                int size = 555; // The size value
                boolean isCancel = false; // The isCancel value

                if(title.isEmpty())
                {
                    title = "Empty";
                }
                //
                start_intent.putExtra("TITLE_EXTRA", title);

                start_intent.putExtra("PLAY_BUTTON_EXTRA", playButton);
                start_intent.putExtra("POSITION_EXTRA", position);
                start_intent.putExtra("SIZE_EXTRA", size);

                start_intent.putExtra("IS_CANCEL_EXTRA", isCancel);

                start_intent.putExtra("Uri_EXTRA", song.getUri().toString());
                start_intent.putExtra("ArtworkUri_EXTRA", song.getArtworkUri().toString());
                //

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(start_intent);
                }
                else {
                    ContextCompat.startForegroundService(getBaseContext(), start_intent);
                }
                mainBinding_activity.mainInfo.setText("start hit = "+ ++i);
            }
        });


        mainBinding_activity.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stop_intent = new Intent(getBaseContext(), MyAudioService.class);
                stopService(stop_intent);
            }
        });


    }
}