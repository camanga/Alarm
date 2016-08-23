package com.example.camanfa.alarm.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.DatabaseHandler;
import com.example.camanfa.alarm.RingtoneService;

/**
 * Created by CAMANFA on 5/13/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver","Yay!");
        AlarmModel alarmModel = (AlarmModel)intent.getExtras().getSerializable("AlarmModel");
        if(alarmModel!=null) {
            if (alarmModel.getGunler().contains("Asla")) {
                alarmModel.setAktif(false);
                DatabaseHandler databaseHandler = new DatabaseHandler(context, null, null, 1);
                databaseHandler.Update(alarmModel, alarmModel.getId());


            }
            Intent i = new Intent(context, RingtoneService.class);
            i.putExtra("AlarmModel", alarmModel);

            context.startService(i);
        }

    }
}
