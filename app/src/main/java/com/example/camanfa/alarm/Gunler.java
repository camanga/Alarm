package com.example.camanfa.alarm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.camanfa.alarm.Alarm.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAMANFA on 5/13/2016.
 */
public class Gunler extends AppCompatActivity {
    GunlerModel g;
    AlarmModel updateModel;
    String SeciliGunler;
    List<Gun> gelenGunler;
    boolean update;
    protected void onCreate(Bundle savedInstanceState) {
        gelenGunler=new ArrayList<>();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gunler_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));



        g=new GunlerModel();
        String[] days = {"Her Pazartesi","Her Salı","Her Çarşamba","Her Perşembe","Her Cuma","Her Cumartesi","Her Pazar"};
        GunleriHazirla();

        ListAdapter myAdapter = new GunlerArray(this,gelenGunler){
        };
        final ListView myListview=(ListView)findViewById(R.id.listView);
        myListview.setAdapter(myAdapter);

        if(getIntent().getSerializableExtra("UpdateModel")!=null) {
            updateModel = (AlarmModel) getIntent().getSerializableExtra("UpdateModel");
            update=true;
        }
        else
        {
            updateModel = (AlarmModel) getIntent().getSerializableExtra("YeniAlarm");
            update=false;
        }
        if(updateModel!=null)
        {
            SeciliGunler=updateModel.getGunler();
            if(SeciliGunler.contains("Her gün"))
            {
                for(Gun a:gelenGunler)
                {
                    a.setSecili(true);
                }



            }
            else if(SeciliGunler.contains("Hafta sonu"))
            {
                gelenGunler.get(5).setSecili(true);
                gelenGunler.get(6).setSecili(true);

            }
            else if(SeciliGunler.contains("Hafta içi"))
            {
                gelenGunler.get(0).setSecili(true);
                gelenGunler.get(1).setSecili(true);
                gelenGunler.get(2).setSecili(true);
                gelenGunler.get(3).setSecili(true);
                gelenGunler.get(4).setSecili(true);

            }
            else {

                if (SeciliGunler.contains("Pzt") || SeciliGunler.contains("Pazartesi")) {
                    gelenGunler.get(0).setSecili(true);

                }
                if (SeciliGunler.contains("Sal") || SeciliGunler.contains("Salı")) {
                    gelenGunler.get(1).setSecili(true);

                }
                if (SeciliGunler.contains("Car") || SeciliGunler.contains("Çarşamba")) {
                    gelenGunler.get(2).setSecili(true);

                }
                if (SeciliGunler.contains("Per") || SeciliGunler.contains("Perşembe")) {
                    gelenGunler.get(3).setSecili(true);

                }
                if (SeciliGunler.contains("Cum") || SeciliGunler.contains("Cuma")) {
                    gelenGunler.get(4).setSecili(true);

                }
                if (SeciliGunler.contains("Cmt") || SeciliGunler.contains("Cumartesi")) {
                    gelenGunler.get(5).setSecili(true);

                }
                if(SeciliGunler.contains("Pzr")||SeciliGunler.contains("Pazar"))
                {
                    gelenGunler.get(6).setSecili(true);

                }
            }

        }
        else
        {

        }




        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gun b = (Gun) myListview.getItemAtPosition(position);

                CheckBox a = (CheckBox) view.findViewById(R.id.cbDays);
                if (a.isChecked()) {
                    b.setSecili(false);
                    a.setChecked(false);
                    a.setVisibility(View.INVISIBLE);
                } else {
                    b.setSecili(true);
                    a.setVisibility(View.VISIBLE);
                    a.setChecked(true);

                }


            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        for(Gun gunler :gelenGunler)
        {
            if(gunler.isSecili())
            {
                g.gunEkle(gunler.getDeger());
            }

        }

        String asd =g.Duzenle();

        updateModel.setGunler(asd);
        if(update)
        myIntent.putExtra("UpdateModel",updateModel);
       else
            myIntent.putExtra("YeniAlarm",updateModel);

        startActivityForResult(myIntent, 0);
        return true;

    }
    public void GunEkle(int gun,boolean ekle)
    {

        if(ekle)
        {
            g.gunEkle(gun);



        }
        else
        {
           g.gunCikar(gun);
        }

    }
    public void GunleriHazirla()
    {

        Gun pazartesi = new Gun("Her Pazartesi",false,0);
        Gun sali = new Gun("Her Salı",false,1);
        Gun car = new Gun("Her Çarşamba",false,2);
        Gun per = new Gun("Her Perşembe",false,3);
        Gun cum = new Gun("Her Cuma",false,4);
        Gun cmt = new Gun("Her Cumartesi",false,5);
        Gun pzr = new Gun("Her Pazar",false,6);
        gelenGunler.add(pazartesi);
        gelenGunler.add(sali);
        gelenGunler.add(car);
        gelenGunler.add(per);
        gelenGunler.add(cum);
        gelenGunler.add(cmt);
        gelenGunler.add(pzr);

    }
}
