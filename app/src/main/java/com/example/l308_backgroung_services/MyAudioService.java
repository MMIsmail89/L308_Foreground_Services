package com.example.l308_backgroung_services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
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
        startForeground(ID_Foreground, getNotificationObject());
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


    private Notification getNotificationObject() {

//         L305: 2- inside the display Notification method,
//         check if the Android version is (equal or grater) than 26 or less that that, as
//         for calling the current android version (Build.VERSION.SDK_INT)
//         for calling  Android version 26 (Build.VERSION_CODES.O)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
//             L305: 3- inside the display Notification method,
//             Create a new class of the notification channel by (NotificationChannel), as
//             L305: note for 3-, the given data, 1-Channel Unique ID,
//             2-Channel Name to display in the setting of the Notification App,
//             3-Channel Importance, Choose one of the available, as (NotificationManager.IMPORTANCE_NONE (0 degree),
//             NotificationManager.IMPORTANCE_MIN (1 degree),
//             NotificationManager.IMPORTANCE_LOW (2 degree),
//             NotificationManager.IMPORTANCE_DEFAULT (3 degree),
//             NotificationManager.IMPORTANCE_HIGH (4 degree),
//             NotificationManager.IMPORTANCE_UNSPECIFIED (-1000 degree)).
//             this Importance of the Channel, for available to delete, and
//             for set the notification in the top list of the shown notifications.

            NotificationChannel The_Channel_Done = new NotificationChannel
                    (Channel_ID, getString(R.string.ChannelName),  NotificationManager.IMPORTANCE_DEFAULT);

//             L305: 4- inside the display Notification method,
//             set the description of the channel by (setDescription) method, as
            The_Channel_Done.setDescription(getString(R.string.ChannelDescription));

//             L305: 5- inside the display Notification method, create the channel by notification manager class,
//             L305: note for 5-, For setting the instance object of the (NotificationManager) class,
//             use the (getSystemService) Method of the (Service) class to return the class of the (NotificationManager),
//             note for L305: 5, Why?: because the Notification App is an Other Application in the Android OS,
//             that We need to use a service from him.
//             as
            NotificationManager Notification_Manager = getSystemService(NotificationManager.class);


//             L305: 6- inside the display Notification method,
//             Produce the notification channel based on the created instance object of the (NotificationManager) class,
            Notification_Manager.createNotificationChannel(The_Channel_Done);

        } // if
//             L306: 7- inside the display Notification method,
//             Create this Notification by create the instance object of the method of (Builder) for the class of (NotificationCompat),
//             The Given Parameters of This Method 1-Context = getBaseContext(), 2-Channel ID.
        NotificationCompat.Builder TheBuilder = new NotificationCompat.Builder(getBaseContext(), Channel_ID);

//             L306: 8- inside the display Notification method,
//             set the small icon of the Notification by (setSmallIcon)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as
        TheBuilder.setSmallIcon(R.drawable.notifications_active);

//             L306: 9- inside the display Notification method,
//             set the title of the Notification by (setContentTitle)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as

        TheBuilder.setContentTitle(getString(R.string.title_notification));

//             L306: 10- inside the display Notification method,
//             set the message of the Notification by (setContentText)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as

        TheBuilder.setContentText(getString(R.string.title_message));


//             L306: 11- inside the display Notification method,
//             set the priority of the Notification by (setPriority)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as
//             1- NotificationCompat.PRIORITY_MIN, 2-.PRIORITY_LOW,
//             3-.PRIORITY_DEFAULT, 4-.PRIORITY_HIGH, 5-.PRIORITY_MAX

        TheBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

//             L306: 12- inside the display Notification method,
//             set the style of the Notification by (setStyle)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as

        TheBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText
                ( getString(R.string.BigTextStyle)  ));

//             L306: 14- inside the display Notification method, Create implicit intent to connect from and to this Intent,

        Intent MyIntentThisActivity = new Intent(this, MainActivity.class);

//             L306: 15- inside the display Notification method,
//             Create Pending intent for this Action Notification for Step No. 14,
//             note for L306: 15, The Given Parameters of (Pending Intent), 1-context,
//             2-request code, 3- the original Intent, 4- flag.
        PendingIntent PI_Notification_Detail = PendingIntent.getActivity(this, 0, MyIntentThisActivity, 0);

//             L306: 13- inside the display Notification method,
//             set the Action of the Notification by (addAction)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as
//             note,1, for L306: 13, The Given Parameters of This Method 1-icon, 2-Title, 3- Pending Intent معلق.
//             note,2, for L306: 13, Why, Pending Intent معلق : Because the Notification App. is an external compared to this App,
//             and it is need to connect with my App, Thus, we add Pending Intent معلق, not normal Intent, Also for security.

        TheBuilder.addAction(R.drawable.details, getString(R.string.detail_action), PI_Notification_Detail);

//             L306: 16- inside the display Notification method,
//             for call the required (Pending Intent) by long hitting on
//             the Notification when it is already shown, use (setContentIntent)
//             of the instance object of the method of (Builder) for the class of (NotificationCompat), as
//             note for L306: 16, The Given Parameters of (the required Pending Intent).

        TheBuilder.setContentIntent(PI_Notification_Detail);

//             L306: 17- inside the display Notification method,
//             for show this Notification, Create the instance object (NotificationManagerCompat) class and its method of (from),
//             because you from first depend on the (NotificationCompat)
//             note for L306: 17, The Given Parameters of (NotificationManagerCompat.from),
//             1- the context that you are calling this Notification,

        NotificationManagerCompat NMCompact = NotificationManagerCompat.from(this);

//             L306: 18- inside the display Notification method,
//             for show push this Notification, use (notify) method of the instance object (NotificationManagerCompat) class, as
//             note,1, for L306: 18, The Given Parameters of This Method 1-ID for this pushing to call it if you need for deleting as example,
//             2-The (build) method for the created instance object of the Builder class.

//        NMCompact.notify(1, TheBuilder.build());
        return TheBuilder.build();
    } // the created method of the Activity Class to display the notification

} //  MyAudioService Class