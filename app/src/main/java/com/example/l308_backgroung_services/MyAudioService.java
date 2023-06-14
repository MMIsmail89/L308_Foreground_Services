package com.example.l308_backgroung_services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAudioService extends Service {
    //
    MediaPlayer md_mine;
    public static final String Channel_ID = "ID_Channel_1";
    public static final int ID_Foreground = 1;
    //
    public MyAudioService() {

    } // Constructor MyAudioService


    @Override
    public void onCreate() {
        super.onCreate();
        //
        Log.d("Magdi: ", "1-onCreate-My-Audio-Service is Created.");
        //

        md_mine = MediaPlayer.create(getBaseContext(), R.raw.cartoon_intro);
        Log.d("Magdi: ", "2-onCreate-Media Player is created.");
        md_mine.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
                Log.d("Magdi: ", "3-onCreate-Media Player is completed, then it is Stopped.");
            } // on Completion
        }); // md_mine set On Completion Listener

        //

    } // on Create Method

    @Override
    public void onDestroy() {
        super.onDestroy();
        //
        Log.d("Magdi: ", "5-onDestroy-My-Audio-Service is Destroyed.");
        //

        if(md_mine.isPlaying())
        {
            md_mine.stop();
            md_mine.release();
            Log.d("Magdi: ", "6-onDestroy-Media Player is Stopped.");
        } // if condition



    } // on Destroy Method


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //
        // Get the title from the intent extras


        String Noti_FiTitle = intent.getStringExtra("TITLE_EXTRA");

        int playButton = intent.getIntExtra("PLAY_BUTTON_EXTRA", 0);
        int size = intent.getIntExtra("SIZE_EXTRA", 0);
        int position = intent.getIntExtra("POSITION_EXTRA", 0);

        boolean isCancel = intent.getBooleanExtra("IS_CANCEL_EXTRA", false);

       String uri_song_String = intent.getStringExtra("Uri_EXTRA");
        Uri uri_song = Uri.parse(uri_song_String);

        String ArtworkUri_song_String = intent.getStringExtra("ArtworkUri_EXTRA");
        Uri ArtworkUri_song = Uri.parse(ArtworkUri_song_String);

        Context context = getApplicationContext();




        startForeground(ID_Foreground, getNotificationObject(Noti_FiTitle));
        //
        Log.d("Magdi: ", "4-onStartCommand-onStartCommand is called.");

        //

        if(!md_mine.isPlaying()) {
            md_mine.start();
        } // if condition


        //
        return START_STICKY;
        //
    } // onStartCommand Method

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented.");
    } // on Bind Method


    private Notification getNotificationObject(String settingTitle) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            NotificationChannel The_Channel_Done = new NotificationChannel
                    (Channel_ID, getString(R.string.ChannelName),  NotificationManager.IMPORTANCE_DEFAULT);

            The_Channel_Done.setDescription(getString(R.string.ChannelDescription));

            NotificationManager Notification_Manager = getSystemService(NotificationManager.class);

            Notification_Manager.createNotificationChannel(The_Channel_Done);

        } // if

        NotificationCompat.Builder TheBuilder = new NotificationCompat.Builder(getBaseContext(), Channel_ID);

        TheBuilder.setSmallIcon(R.drawable.notifications_active);

//        TheBuilder.setContentTitle(getString(R.string.title_notification));
        TheBuilder.setContentTitle(settingTitle);

        TheBuilder.setContentText(getString(R.string.title_message));

        TheBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        TheBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText
                ( getString(R.string.BigTextStyle)  ));

        Intent MyIntentThisActivity = new Intent(this, MainActivity.class);

        PendingIntent PI_Notification_Detail = PendingIntent.getActivity(this, 0, MyIntentThisActivity, PendingIntent.FLAG_IMMUTABLE);

        TheBuilder.addAction(R.drawable.details, getString(R.string.detail_action), PI_Notification_Detail);

        TheBuilder.setContentIntent(PI_Notification_Detail);

        NotificationManagerCompat NMCompact = NotificationManagerCompat.from(this);

//        NMCompact.notify(1, TheBuilder.build());
        return TheBuilder.build();
    } // the created method of the Activity Class to display the notification

} //  MyAudioService Class