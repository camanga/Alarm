package com.example.camanfa.alarm;


import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Ayarlar.GunlerFragment;
import com.example.camanfa.alarm.Ayarlar.NameFragment;
import com.example.camanfa.alarm.Ayarlar.OyunSecimiFragment;
import com.example.camanfa.alarm.Ayarlar.ZilSesiFragment;
import com.example.camanfa.alarm.Ayarlar.ZorlukDerecesiFragment;
import com.example.camanfa.alarm.Oyunlar.DenemeBaslandicActivity;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    static AlarmManager alarmManager ;
    TimePicker timePicker;
    Button startButton;
    Button stopButton;
    static Context context;
    Calendar calendar;
    PendingIntent pendingIntent;
    static Intent i;
    TextView alarmText;

    public static Boolean getUpdate() {
        return update;
    }

    public static void setUpdate(Boolean update) {
        MainFragment.update = update;
    }

    TextView tekrar;
    static DatabaseHandler maindbHandler;
    static Boolean update=false;
    AlarmModel updateAlarmModel;
    AlarmModel newAlarm;
    public static final int MAINFRAGMENT_ID=0;
    public static final int GUNLERFRAGMENT_ID=1;
    public static final int NAMEFRAGMENT_ID=2;
    public static final int ZILSESIFRAGMENT_ID=3;
    public static final int OYUNFRAGMENT_ID=4;
    public static final int ZORLUKFRAGMENT_ID=5;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar() .setHomeButtonEnabled(false); // disable the button
        ((AppCompatActivity)getActivity()).getSupportActionBar() .setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater =LayoutInflater.from((getActivity()).getApplicationContext());

        View customView =mInflater.inflate(R.layout.custom_actionbar, null);
        TextView txtName=(TextView)customView.findViewById(R.id.textNameMain);
        txtName.setText("Alarm Ekle");

        TextView bvazgec= (TextView)customView.findViewById(R.id.txtDuzenle);
        bvazgec.setText("Vazgeç");
        bvazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DenemeBaslandicActivity.class);
                startActivity(i);
            }
        });


        TextView bKaydet = (TextView)customView.findViewById(R.id.txtEkle);
        bKaydet.setText("Kaydet");


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(customView,layoutParams);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));

        final TextView gunler =(TextView)view. findViewById(R.id.txtGun);
        final TextView AlarmName = (TextView)view.findViewById(R.id.txtIsim);
        final TextView Oyun= (TextView)view.findViewById(R.id.txtOyun);
        final TextView zorluk=(TextView)view.findViewById(R.id.txtZorlukDerecesi);
        final TextView zilSesi=(TextView)view.findViewById(R.id.txtZil);
        timePicker =(TimePicker)view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        calendar= Calendar.getInstance();
        tekrar=(TextView)view.findViewById(R.id.txtGun);
        try {
            int saat=timePicker.getCurrentHour();
            int dakika = timePicker.getCurrentMinute();
            String saatstr="";
            String dakikastr="";
            if(dakika<10)
                dakikastr="0"+String.valueOf(dakika);
            else
                dakikastr=String.valueOf(dakika);
            if(saat<10)
                saatstr="0"+String.valueOf(saat);
            else
                saatstr=String.valueOf(saat);
            LinkedHashMap<String, String> stringStringLinkedHashMap= new ZilSesiFragment().getNotifications(((AppCompatActivity)getActivity()).getApplicationContext());

            Object key = stringStringLinkedHashMap.keySet().iterator().next();

            if(MainActivity.newAlarm==null) {
             MainActivity.newAlarm = new AlarmModel("Alarm", saatstr + ":" + dakikastr, "Asla", true, key.toString(), -1, 1, 1, stringStringLinkedHashMap.get(key).toString());
                gunler.setText(MainActivity.newAlarm.getGunler());
                Oyun.setText(MainActivity.newAlarm.getOyunText());
                AlarmName.setText(MainActivity.newAlarm.getName());
                zorluk.setText(MainActivity.newAlarm.getZorlukText());
                zilSesi.setText(MainActivity.newAlarm.getZilSesi());
            }
            else
         {
             gunler.setText(MainActivity.newAlarm.getGunler());
             Oyun.setText(MainActivity.newAlarm.getOyunText());
             AlarmName.setText(MainActivity.newAlarm.getName());
             zorluk.setText(MainActivity.newAlarm.getZorlukText());
             zilSesi.setText(MainActivity.newAlarm.getZilSesi());
             timePicker.setCurrentHour(MainActivity.newAlarm.getHour());
             timePicker.setCurrentMinute(MainActivity.newAlarm.getMinute());
         }
        }
        catch (Exception ex)
        {
            String a = ex.getMessage();
        }
        gunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changeframe(GUNLERFRAGMENT_ID);

              //  Intent isa = new Intent(context, Gunler.class);
              //  if (update) {
//
              //      isa.putExtra("UpdateModel", updateAlarmModel);
              //  }
              //  else {
              //      isa.putExtra("YeniAlarm", newAlarm);
              //  }
//
              //  startActivity(isa);
            }
        });

        Oyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changeframe(OYUNFRAGMENT_ID);

            }
        });
        AlarmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changeframe(NAMEFRAGMENT_ID);
//



            }
        });
       bKaydet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent myIntent = new Intent(getActivity().getApplicationContext(), DenemeBaslandicActivity.class);



               if(update)
               {
                   MainActivity.newAlarm.setAktif(true);
                   updateAlarmModel=MainActivity.newAlarm;

                       maindbHandler.Update(updateAlarmModel, updateAlarmModel.getId());

               }
               else {
                   int c=  maindbHandler.AddProduct(MainActivity.newAlarm);
                   updateAlarmModel=maindbHandler.getModel(c);
               }

               MainActivity.StartPendingIntent(updateAlarmModel);


               startActivity(myIntent);
           }


       });
        zorluk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             Changeframe(ZORLUKFRAGMENT_ID);






            }
        });
        zilSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changeframe(ZILSESIFRAGMENT_ID);


            }
        });


      // getSupportActionBar().setCustomView(customView,layoutParams);
      // getSupportActionBar().show();
      // Toolbar parent = (Toolbar) customView.getParent();
      // parent.setContentInsetsAbsolute(0, 0);
      // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));



      // this.context=this;



      // Bundle extras = getIntent().getExtras();


      // if (extras != null) {
      //     updateAlarmModel =(AlarmModel)getIntent().getSerializableExtra("UpdateModel");



      //     // and get whatever type user account id is
      //     if(updateAlarmModel!=null) {
      //         String[] sonsaat = updateAlarmModel.getSaat().split(":");
      //         int saat = Integer.valueOf(sonsaat[0].trim());
      //         int dakika = Integer.valueOf(sonsaat[1].trim());
      //         timePicker.setCurrentHour(saat);
      //         timePicker.setCurrentMinute(dakika);
      //         update = true;
      //         if(!updateAlarmModel.getGunler().isEmpty())
      //             tekrar.setText(updateAlarmModel.getGunler());
      //         else
      //             tekrar.setText("Asla");
      //         zilSesi.setText(updateAlarmModel.getZilSesi());








      //         AlarmName.setText(updateAlarmModel.getName());


      //         Oyun.setText(updateAlarmModel.getOyunText());

      //         zorluk.setText(updateAlarmModel.getZorlukText());


      //     }
      //     newAlarm=(AlarmModel)getIntent().getSerializableExtra("YeniAlarm");
      //     if(newAlarm!=null) {
      //         String[] sonsaat = newAlarm.getSaat().split(":");
      //         int saat = Integer.valueOf(sonsaat[0].trim());
      //         int dakika = Integer.valueOf(sonsaat[1].trim());
      //         timePicker.setCurrentHour(saat);
      //         timePicker.setCurrentMinute(dakika);
      //         update = false;



      //         if(!newAlarm.getGunler().isEmpty())
      //             tekrar.setText(newAlarm.getGunler());
      //         else
      //             tekrar.setText("Asla");

      //         zilSesi.setText(newAlarm.getZilSesi());


      //         AlarmName.setText(newAlarm.getName());

      //         Oyun.setText(newAlarm.getOyunText());
      //         zorluk.setText(newAlarm.getZorlukText());

      //     }


      // }
      // else
      // {
      //     zilSesi.setText(getNotifications().get(0).toString());
      // }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String saat=view.getCurrentHour()>9?String.valueOf(view.getCurrentHour()):"0"+String.valueOf(view.getCurrentHour());
                String dakika=view.getCurrentMinute()>9?String.valueOf(view.getCurrentMinute()):"0"+String.valueOf(view.getCurrentMinute());
//                if(update)
//                {
//                    updateAlarmModel.setSaat(saat + ":" + dakika);
//                }
//                else {
//                    newAlarm.setSaat(saat + ":" + dakika);
//                }
                MainActivity.newAlarm.setSaat(saat + ":" + dakika);
            }
        });









        return view;
    }

    private void Changeframe(int frameId)
    {
        Fragment newFragment;
        switch (frameId)
        {

            case GUNLERFRAGMENT_ID:
                newFragment= new GunlerFragment();
                break ;
            case NAMEFRAGMENT_ID:
                newFragment= new NameFragment();
                break  ;
            case ZILSESIFRAGMENT_ID:
                newFragment= new ZilSesiFragment();
                break  ;
            case OYUNFRAGMENT_ID:
                newFragment= new OyunSecimiFragment();
                break  ;
            case ZORLUKFRAGMENT_ID:
                newFragment= new ZorlukDerecesiFragment();
                break;

            default:
                    newFragment= new MainFragment();
                    break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,newFragment,"visible");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

}
