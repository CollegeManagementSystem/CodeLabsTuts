package com.example.codelabstuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am =(AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        Button silentBut = findViewById(R.id.silent);
        Button ringerBut = findViewById(R.id.ringer);
        Button vibrateBut = findViewById(R.id.vibrate);
        createNotificationChannel();
        sampleNoti();

        vibrateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun(3);
            }
        });
        silentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun(1);
            }
        });
        ringerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun(2);
            }
        });
    }

    public void fun(int num) {
        switch (num) {
            case 1:
                NotificationManager notificationManager =
                        (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case 2:
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test")
                        .setSmallIcon(R.drawable.messageicon)
                        .setContentTitle("My notification")
                        .setContentText("Much longer text that cannot fit one line...")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line... yeh id hai majak thodi chal raha hai"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat.from(this).notify(1,builder.build());
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case 3:
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }

    public void sampleNoti() {

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




}
