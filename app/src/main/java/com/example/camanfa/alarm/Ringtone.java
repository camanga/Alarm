package com.example.camanfa.alarm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.camanfa.alarm.Alarm.AlarmModel;

import java.util.LinkedHashMap;


/**
 * Created by CAMANFA on 5/27/2016.
 */
public class Ringtone extends AppCompatActivity {
    Context context ;
    LinkedHashMap<String,String> AlarmList;
    LinkedHashMap<String,Integer> newList;
    AlarmModel alarmModel;
    ListView myListView;
    static Object myObject;
    boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));

        setContentView(R.layout.ringtone);

        if(getIntent().getExtras().getSerializable("UpdateModel")!=null) {
            alarmModel = (AlarmModel) getIntent().getExtras().getSerializable("UpdateModel");
            update=true;
        }
        else {
            alarmModel = (AlarmModel) getIntent().getExtras().getSerializable("YeniAlarm");
            update=false;
        }
        AlarmList=getNotifications();
         myListView=(ListView)findViewById(R.id.lstRingtone);
        int sira=0;
        if (!alarmModel.getZilSesi().isEmpty())
        {
            sira=newList.get(alarmModel.getZilSesi());
        }

        final ListAdapter myAdapter = new RingtoneAdapter(AlarmList,sira){
        };
        myListView.setAdapter(myAdapter);
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);





    }
    public LinkedHashMap<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();
        newList=new LinkedHashMap<>();

        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        int counter=0;
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri + "/" + id);
            newList.put(notificationTitle, counter );
            counter++;
        }

        return list;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i= new Intent(this,MainActivity.class);
       alarmModel.setZilSesi(myObject.getName());
        alarmModel.setZilSesiLocation(myObject.getLocation());


        if(update)
            i.putExtra("UpdateModel", alarmModel);
        else
            i.putExtra("YeniAlarm", alarmModel);

        RingtoneAdapter.ringtoneStop();
        startActivity(i);
        return true;

    }
    public static void setMyObject(Object object)
    {
        myObject=object;
    }
    class Object
    {
        String name;
        String location;

        public Object(String location, String name) {
            this.location = location;
            this.name = name;
        }


        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
