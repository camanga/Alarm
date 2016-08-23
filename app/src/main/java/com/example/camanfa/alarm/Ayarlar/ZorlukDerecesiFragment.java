package com.example.camanfa.alarm.Ayarlar;


import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.MainActivity;
import com.example.camanfa.alarm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZorlukDerecesiFragment extends Fragment {

    boolean update;
    AlarmModel alarmModel;
    RadioGroup rgZorluk;
    RadioButton basit;
    RadioButton orta;
    RadioButton zor;
    public ZorlukDerecesiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=(View)inflater.inflate(R.layout.fragment_zorluk_derecesi,container,false);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(null);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setTitle("Zorluk");
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#78909C")));

        rgZorluk=(RadioGroup)view.findViewById(R.id.rgZorluk);
        basit=(RadioButton)view.findViewById(R.id.rbKolay);
        orta=(RadioButton)view.findViewById(R.id.rbOrta);
        zor=(RadioButton)view.findViewById(R.id.rbZor);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {

                        Color.BLACK //disabled
                        ,Color.parseColor("#37474F") //enabled

                }
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            basit.setButtonTintList(colorStateList);
            orta.setButtonTintList(colorStateList);
            zor.setButtonTintList(colorStateList);
        }


      switch (MainActivity.newAlarm.getZorlukDerecesi())
      {
          case 1:
              basit.setChecked(true);
              break;
          case 2:
              orta.setChecked(true);
              break;
          case 3:
              zor.setChecked(true);
              break;
      }
     // // Inflate the layout for this fragment
        return view;
    }

    public void SetZorlukDerecesi()
    {
        if(rgZorluk.getCheckedRadioButtonId()==basit.getId())
        {
            MainActivity.newAlarm.setZorlukDerecesi(1);
        }
        else if (rgZorluk.getCheckedRadioButtonId()==orta.getId())
        {
            MainActivity.newAlarm.setZorlukDerecesi(2);
        }
        else if (rgZorluk.getCheckedRadioButtonId()==zor.getId())
        {
            MainActivity.newAlarm.setZorlukDerecesi(3);
        }

    }

}
