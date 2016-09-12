package eu.alfred.questionnaire;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import eu.alfred.questionnaire.service.receiver.AskReceiver;
import eu.alfred.questionnaire.util.Constants;
import eu.alfred.questionnaire.util.Prefs;

public class QuestionnaireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
        scheduleAskService(this, Prefs.getLong(Constants.KEY_TIME_BETWEEN_AWAKE, Constants.DEFAULT_INTERVAL_BETWEEN_AWAKE));
    }

    public static void scheduleAskService(Context context, long interval) {
        if(interval <= 0){
            interval = Constants.DEFAULT_INTERVAL_BETWEEN_AWAKE;
        }
        Intent intent = new Intent(context, AskReceiver.class);
        PendingIntent pendingToRun = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingToRun);
        pendingToRun = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pendingToRun);
    }
}
