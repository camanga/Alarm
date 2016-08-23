package com.example.camanfa.alarm.Ayarlar;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Baslangic;
import com.example.camanfa.alarm.MainActivity;
import com.example.camanfa.alarm.R;
import com.example.camanfa.alarm.RingtoneAdapter;

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZilSesiFragment extends Fragment {
    Context context ;
    LinkedHashMap<String,String> AlarmList;
    LinkedHashMap<String,Integer> newList;
    AlarmModel alarmModel;
    ListView myListView;
    static Object myObject;
    boolean update;

    public ZilSesiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=(View)inflater.inflate(R.layout.fragment_zil_sesi, container, false);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(null);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setTitle("Ses");
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#78909C")));


     //  if(getIntent().getExtras().getSerializable("UpdateModel")!=null) {
     //      alarmModel = (AlarmModel) getIntent().getExtras().getSerializable("UpdateModel");
     //      update=true;
     //  }
     //  else {
     //      alarmModel = (AlarmModel) getIntent().getExtras().getSerializable("YeniAlarm");
     //      update=false;
     //  }
       AlarmList=getNotifications(inflater.getContext());
     //  myListView=(ListView)findViewById(R.id.lstRingtone);
        int sira=0;

        sira=newList.get(MainActivity.newAlarm.getZilSesi());

        final ListAdapter myAdapter = new RingtoneAdapter(AlarmList,sira){
        };
        myListView=(ListView)view.findViewById(R.id.lstRingtone);
        myListView.setAdapter(myAdapter);
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        RingtoneAdapter.ringtoneStart(((AppCompatActivity)getActivity()).getApplicationContext(), Uri.parse(MainActivity.newAlarm.getZilSesiLocation()));


        return view;
    }
    public LinkedHashMap<String, String> getNotifications(Context context) {
        RingtoneManager manager = new RingtoneManager(context);
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
        cursor.close();

        return list;
    }
    public void SetZilSesi()
    {
        MainActivity.newAlarm.setZilSesi(myObject.getName());
        MainActivity.newAlarm.setZilSesiLocation(myObject.getLocation());
        RingtoneAdapter.ringtoneStop();
    }
  public  class Object
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
    public static void setMyObject(Object object)
    {
        myObject=object;
    }

}
