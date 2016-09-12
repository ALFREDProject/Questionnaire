package eu.alfred.questionnaire.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import eu.alfred.questionnaire.ui.activity.MainActivity;

public class AskService extends IntentService {

    public AskService() {
        super("AskService");
    }

    public AskService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String text = "AskService onHandleIntent " + intent;
        Log.d(getClass().getSimpleName(), text);

        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(activityIntent);
    }

    /*@Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, MyStartServiceReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        long currentTime = System.currentTimeMillis();
        long timeBetween = Prefs.getLong(Constants.KEY_TIME_BETWEEN_AWAKE, Constants.DEFAULT_INTERVAL_BETWEEN_AWAKE);
        service.setRepeating(AlarmManager.RTC_WAKEUP, currentTime + timeBetween, timeBetween, pending);
    }*/
}
