package com.example.powerreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
        * The Power Receiver app responds to system broadcasts about the power
        * connected state as well as a custom broadcast that is sent when the user
        * taps the button.
        */

public class MainActivity extends AppCompatActivity {

    private CustomReceiver customReceiver = new CustomReceiver();

    Button sendBroadcast;
    Button btn_stop;
    public static final String action_to_show_btn_stop = BuildConfig.APPLICATION_ID + "to show btn_stop";

    // String constant that defines the custom broadcast Action.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    boolean toShow = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define the IntentFilter.
        IntentFilter intentFilter = new IntentFilter();

        // Adding system broadcast actions sent by the system when the power is connected and disconnected.
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);

        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        // Registering the receiver using the activity context, passing in the IntentFilter.
        this.registerReceiver(customReceiver, intentFilter);

        //-------------------------------------------------------------------------------------------------------

        // creating IntentFilter for custom Broadcast
        IntentFilter intentFilterForCustomBroadcast = new IntentFilter(ACTION_CUSTOM_BROADCAST);

        // Registering the BroadcastReceiver in LocalBroadcastManager to receive custom broadcast.
        LocalBroadcastManager.getInstance(this).registerReceiver(customReceiver, intentFilterForCustomBroadcast);

        sendBroadcast = findViewById(R.id.sendBroadcast);
        btn_stop = findViewById(R.id.btn_stop);
        btn_stop.setVisibility(View.INVISIBLE);
        btn_stop.setOnClickListener(view -> stopService(new Intent(this, PlayMusic_Service.class)));

        // creating specialized custom BroadcastReceiver to receive the data from PlayMusic_Service
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(action_to_show_btn_stop)){
                    toShow = intent.getBooleanExtra("show btn_stop", false);
                    Toast.makeText(context, "ToShow :" + toShow, Toast.LENGTH_SHORT).show();
                    if(toShow){
                        btn_stop.clearAnimation();
                        btn_stop.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_stop.clearAnimation();
                        btn_stop.setVisibility(View.INVISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Visibility: " + btn_stop.getVisibility(), Toast.LENGTH_LONG).show();
                }
            }
        };
        // registering the specialized custom BroadcastReceiver in LocalBroadcastManager to receive custom broadcast.
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(action_to_show_btn_stop));

    }

    public void sendCustomBroadcast(View view){
        // creating intent for custom action
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);

        // sending the custom Broadcast through LocalBroadCastManager
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }

    @Override
    protected void onDestroy() {
        // Unregistering the receiver
        this.unregisterReceiver(customReceiver);

        // Unregistering the custom Broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(customReceiver);

        super.onDestroy();
    }

}

