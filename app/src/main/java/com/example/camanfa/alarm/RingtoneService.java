package com.example.camanfa.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Oyunlar.DragDrop;
import com.example.camanfa.alarm.Oyunlar.Math;
import com.example.camanfa.alarm.Oyunlar.Numbers;
import com.example.camanfa.alarm.Oyunlar.TiltSensor;

public class RingtoneService extends Service {
    MediaPlayer song;
    public RingtoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i;
        Log.e("In the service ", "start command");
        AlarmModel alarmModel = (AlarmModel)intent.getExtras().getSerializable("AlarmModel");
        if(alarmModel.getAlarmTur()==1) {
            i = new Intent(this, Math.class);
        }
        else if (alarmModel.getAlarmTur()==2) {
            i = new Intent(this, Numbers.class);
        }

        else if(alarmModel.getAlarmTur()==3)
            i = new Intent(this, DragDrop.class);
        else
            i = new Intent(this, TiltSensor.class);
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("AlarmModel",alarmModel);
        startActivity(i);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.e("On Destroy","On destroy called");
    }
}
