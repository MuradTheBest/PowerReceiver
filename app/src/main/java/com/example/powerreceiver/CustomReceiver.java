package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

    /**
     * Broadcast Receiver implementation that delivers a custom Toast
     * message when it receives any of the registered broadcasts.
     */
public class CustomReceiver extends BroadcastReceiver {

    // String constant that defines the custom broadcast Action.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    /**
     * This callback method gets called when the Broadcast Receiver receives a
     * broadcast that it is registered for.
     *
     * @param context The context in which broadcast receiver is running.
     * @param intent The broadcast is delivered in the form of an intent which
     *               contains the broadcast action.
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i;

        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMessage = "unknown intent action";
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    i = new Intent(context, PlayMusic_Service.class);
                    context.stopService(i);
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    i = new Intent(context, PlayMusic_Service.class);
                    context.startService(i);
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast received";
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    toastMessage = "Headset plugged!";
                    break;
                case Intent.ACTION_SCREEN_ON:
                    toastMessage = "Time for music!";
                    i = new Intent(context, PlayMusic_Service.class);
                    context.startService(i);
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    toastMessage = "Not time for music!";
                    i = new Intent(context, PlayMusic_Service.class);
                    context.stopService(i);
                    break;

            }

            //Display the toast.
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

    }
}




