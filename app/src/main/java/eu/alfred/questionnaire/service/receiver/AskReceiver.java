package eu.alfred.questionnaire.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import eu.alfred.questionnaire.service.AskService;

public class AskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String text = "AskReceiver onReceive " + intent;
        Log.d(getClass().getSimpleName(), text);
        Intent serviceIntent = new Intent(context, AskService.class);
        context.startService(serviceIntent);
    }
}