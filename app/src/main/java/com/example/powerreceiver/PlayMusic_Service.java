package com.example.powerreceiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PlayMusic_Service extends Service {
    private MediaPlayer mediaPlayer;
    static int length = 0;

    public static final String action_to_show_btn_stop = BuildConfig.APPLICATION_ID + "to show btn_stop";

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.industry_baby);
        //mediaPlayer.setLooping(true);

        /*if(mediaPlayer.isPlaying()){
            Intent i = new Intent(getSystemService(PlayMusic_Service.class), MainActivity.class);
            i.putExtra("show btn_stop", true);
        }*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.seekTo(length);
        Log.d("check", "start length "+length);
        mediaPlayer.start();

        Intent i = new Intent(action_to_show_btn_stop);
        i.putExtra("show btn_stop", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        return START_STICKY;
    }

    /*@Override
    public boolean stopService(Intent name) {

        return super.stopService(name);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
        Log.d("check", "stop length"+length);

        Intent i = new Intent(action_to_show_btn_stop);
        i.putExtra("show btn_stop", false);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    public PlayMusic_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}