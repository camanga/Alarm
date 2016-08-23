package com.example.camanfa.alarm.Ayarlar;


import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Gun;
import com.example.camanfa.alarm.GunlerArray;
import com.example.camanfa.alarm.GunlerModel;
import com.example.camanfa.alarm.MainActivity;
import com.example.camanfa.alarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GunlerFragment extends Fragment {

    GunlerModel g;
    AlarmModel updateModel;
    String SeciliGunler;
    List<Gun> gelenGunler;
    boolean update;
    public GunlerFragment() {
        // Required empty public constructor
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.fragment_gunler,container,false);



        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(null);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setTitle("Günler");

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#78909C")));


        gelenGunler=new ArrayList<>();
        g=new GunlerModel();
        String[] days = {"Her Pazartesi","Her Salı","Her Çarşamba","Her Perşembe","Her Cuma","Her Cumartesi","Her Pazar"};
        GunleriHazirla();
        SeciliGunler=MainActivity.newAlarm.getGunler();
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
            if (SeciliGunler.contains("Pzr") || SeciliGunler.contains("Pazar")) {
                gelenGunler.get(6).setSecili(true);

            }
        }

        ListAdapter myAdapter = new GunlerArray(inflater.getContext(),gelenGunler){
        };
        final ListView myListview=(ListView)view.findViewById(R.id.listView);
        myListview.setAdapter(myAdapter);
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


        return view;
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

    public void SetGunler()
    {
        for(Gun gunler :gelenGunler)
        {
            if(gunler.isSecili())
            {
                g.gunEkle(gunler.getDeger());
            }

        }
        String newGunler =g.Duzenle();
        MainActivity.newAlarm.setGunler(newGunler);
    }
}
