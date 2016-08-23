package com.example.camanfa.alarm;

import android.annotation.TargetApi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Ayarlar.GunlerFragment;
import com.example.camanfa.alarm.Ayarlar.NameFragment;
import com.example.camanfa.alarm.Ayarlar.OyunSecimiFragment;
import com.example.camanfa.alarm.Ayarlar.ZilSesiFragment;
import com.example.camanfa.alarm.Ayarlar.ZorlukDerecesiFragment;

import java.lang.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static String ALARM_MODEL="AlarmModel";

   static AlarmManager alarmManager ;
    TimePicker timePicker;
    Button startButton;
    Button stopButton;
    static Context context;
    Calendar calendar;
    PendingIntent pendingIntent;
    static Intent i;
    TextView alarmText;
    TextView tekrar;
    static DatabaseHandler maindbHandler;
   static Boolean update=false;
    AlarmModel updateAlarmModel;
    public static AlarmModel newAlarm ;


    long remaningHour;
    long remainingMinute;
    long remainingSeconds;
    long remainingMiliseconds;
    static Context baslangicContext;
    static Intent baslangicIntent;
    static AlarmManager baslangicAlarmManager;

    public static void setUpdate(Boolean update1) {
        update = update1;
    }

    public static Boolean getUpdate() {
        return update;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        LayoutInflater mInflater =LayoutInflater.from(this);

        View customView =mInflater.inflate(R.layout.custom_actionbar, null);
//
//        TextView bvazgec= (TextView)customView.findViewById(R.id.txtDuzenle);
//        bvazgec.setText("Vazgeç");
//        bvazgec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, Baslangic.class);
//                startActivity(i);
//            }
//        });
//       getSupportActionBar().setDisplayShowHomeEnabled(false);
//       getSupportActionBar().setDisplayShowCustomEnabled(true);
//       getSupportActionBar().setDisplayShowTitleEnabled(false);
//       getSupportActionBar().setHomeButtonEnabled(false); // disable the button
//       getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret

        setContentView(R.layout.activity_main);
        MainFragment.maindbHandler=maindbHandler;



        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = new MainFragment();
        ft.replace(R.id.main_frame,fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();




    }
    public List getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        List a = new ArrayList();


            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
        a.add(0,notificationTitle);
        a.add(1,notificationUri + "/" + id);


        return a;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void setAlarmOnClick(View view) {
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getHour());

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        alarmText.setText("Alarm set to : " + String.valueOf(hour) + ":" + String.valueOf(minute));
        Intent isa= new Intent(this.context,Gunler.class);
        startActivity(isa);



      /// pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
       // alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);





    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       Fragment activeFragment= getFragmentManager().findFragmentByTag("visible");
        Fragment newFragment= new MainFragment();
        if(activeFragment instanceof GunlerFragment)
        {
            ((GunlerFragment) activeFragment).SetGunler();

        }
        if(activeFragment instanceof NameFragment)
        {

         ((NameFragment) activeFragment).setNameText();

        }
        if(activeFragment instanceof OyunSecimiFragment)
        {
            ((OyunSecimiFragment)activeFragment).SetOyun();

        }
        if(activeFragment instanceof ZilSesiFragment)
        {
            ((ZilSesiFragment)activeFragment).SetZilSesi();

        }
        if(activeFragment instanceof ZorlukDerecesiFragment)
        {
            ((ZorlukDerecesiFragment)activeFragment).SetZorlukDerecesi();

        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,newFragment,"visible");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
     //   int id = item.getItemId();
//
     //   //noinspection SimplifiableIfStatement
     //   if (id == R.id.action_settings) {
     //       return true;
     //   }

        return super.onOptionsItemSelected(item);
    }

    public void unsetAlarmOnClick(View view)
    {
        alarmManager.cancel(pendingIntent);
        sendBroadcast(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    public void SetAlarmManager()
    {
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
    }

    public static void CancelPendingIntent(int intentId,AlarmModel model)
    {
        List<Integer> aad = new ArrayList<Integer>();

        if(model.getGunler().contains("Her gün"))
        {
            for (int x =1;x<8;x++)
                aad.add(x);



        }
        else if(model.getGunler().contains("Hafta sonu"))
        {
            aad.add(7);
            aad.add(1);

        }
        else if(model.getGunler().contains("Hafta içi"))
        {
            for (int x =2;x<7;x++)
                aad.add(x);

        }
        else {

            if (model.getGunler().contains("Pzt") || model.getGunler().contains("Pazartesi")) {
                aad.add(2);
            }
            if (model.getGunler().contains("Sal") || model.getGunler().contains("Salı")) {
                aad.add(3);

            }
            if (model.getGunler().contains("Car") || model.getGunler().contains("Çarşamba")) {
                aad.add(4);

            }
            if (model.getGunler().contains("Per") || model.getGunler().contains("Perşembe")) {
                aad.add(5);

            }
            if (model.getGunler().contains("Cum") || model.getGunler().contains("Cuma")) {
                aad.add(6);

            }
            if (model.getGunler().contains("Cmt") || model.getGunler().contains("Cumartesi")) {
                aad.add(7);

            }
            if(model.getGunler().contains("Pzr")||model.getGunler().contains("Pazar"))
            {
                aad.add(1);

            }
        }
        if(aad.size()==0)
        {
            int myid=Integer.valueOf(String.valueOf(model.getId())+String.valueOf(Calendar.DAY_OF_WEEK));
            PendingIntent  pendingIntent=PendingIntent.getBroadcast(baslangicContext,myid,baslangicIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            pendingIntent.cancel();
            baslangicAlarmManager.cancel(pendingIntent);
        }
        for(int number:aad)
        {
            int myid=Integer.valueOf(String.valueOf(intentId) + String.valueOf(number));
            try {
                PendingIntent  pendingIntent=PendingIntent.getBroadcast(baslangicContext,myid,baslangicIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                pendingIntent.cancel();
                baslangicAlarmManager.cancel(pendingIntent);
            }
            catch (Exception ex)
            {
                System.out.print(ex.getMessage());
            }

        }

    }

    public static void StartPendingIntent(AlarmModel model)
    {

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar=new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, model.getHour());
        calendar.set(Calendar.MINUTE, model.getMinute());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        calendar.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        //1 pazar, 2 pazartesi , 3 sali, 4 carsamba , 5 persembe , 6 cuma ,7 cumartesi

        List<Integer> aad = new ArrayList<Integer>();

        if(model.getGunler().contains("Her gün"))
        {
            for (int x =1;x<8;x++)
                aad.add(x);



        }
        else if(model.getGunler().contains("Hafta sonu"))
        {
            aad.add(7);
            aad.add(1);

        }
        else if(model.getGunler().contains("Hafta içi"))
        {
            for (int x =2;x<7;x++)
                aad.add(x);

        }
        else {

            if (model.getGunler().contains("Pzt") || model.getGunler().contains("Pazartesi")) {
                aad.add(2);
            }
            if (model.getGunler().contains("Sal") || model.getGunler().contains("Salı")) {
                aad.add(3);

            }
            if (model.getGunler().contains("Car") || model.getGunler().contains("Çarşamba")) {
                aad.add(4);

            }
            if (model.getGunler().contains("Per") || model.getGunler().contains("Perşembe")) {
                aad.add(5);

            }
            if (model.getGunler().contains("Cum") || model.getGunler().contains("Cuma")) {
                aad.add(6);

            }
            if (model.getGunler().contains("Cmt") || model.getGunler().contains("Cumartesi")) {
                aad.add(7);

            }
            if(model.getGunler().contains("Pzr")||model.getGunler().contains("Pazar"))
            {
                aad.add(1);

            }
        }



        if(aad.size()==0)
        {
            if(calendar.before(cur_cal)){
                calendar.add(Calendar.DATE,1);
            }
            int intentId=Integer.valueOf(String.valueOf(model.getId()) + String.valueOf(Calendar.DAY_OF_WEEK));
            baslangicIntent.putExtra("AlarmModel", model);
            PendingIntent  pendingIntent=PendingIntent.getBroadcast(baslangicContext, intentId, baslangicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            baslangicAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

        }
        for(int loop :aad)
        {
            calendar.set(Calendar.HOUR_OF_DAY, model.getHour());
            calendar.set(Calendar.MINUTE, model.getMinute());
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

            int intentId=Integer.valueOf(String.valueOf(model.getId())+String .valueOf(loop));
            long sonuc;
            if(day>loop)
            {
              calendar.add(Calendar.DATE,day-loop);
            }

            else if(loop>day)
            {
                calendar.add(Calendar.DATE,loop-day);
            }

            else
            {
                if(calendar.before(cur_cal)){
                    calendar.add(Calendar.DATE,1);
                }

            }
            baslangicIntent.putExtra("AlarmModel", model);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(baslangicContext,intentId,baslangicIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            baslangicAlarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        }


    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getFragmentManager().popBackStack();
        }

    }

    public static void setAlarmManager(AlarmManager alarmManager)
    {
     baslangicAlarmManager=alarmManager;

    }
    public static void setContext (Context context)
    {
        baslangicContext=context;
    }
    public static void setmyIntent(Intent intent)
    {
        baslangicIntent=intent;
    }
    public static void setDbHandler(DatabaseHandler dbHandler)
    {
        maindbHandler=dbHandler;
    }



}
