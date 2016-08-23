package com.example.camanfa.alarm.Ayarlar;

import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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


public class OyunSecimiFragment extends Fragment {
    RadioGroup radioGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    AlarmModel alarmModel;
    boolean update;

    public OyunSecimiFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=(View)inflater.inflate(R.layout.fragment_oyun_secimi,container,false);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(null);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();

        ((AppCompatActivity)getActivity()).  getSupportActionBar().setTitle("Oyun");
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#78909C")));

        radioGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        rb1= (RadioButton)view.findViewById(R.id.rbButton);
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

        rb2= (RadioButton)view.findViewById(R.id.rbSayi);
        rb3= (RadioButton)view.findViewById(R.id.rbPuzzle);
        rb4= (RadioButton)view.findViewById(R.id.rbSallama);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rb1.setButtonTintList(colorStateList);
            rb2.setButtonTintList(colorStateList);
            rb3.setButtonTintList(colorStateList);
            rb4.setButtonTintList(colorStateList);

        }



        switch (MainActivity.newAlarm.getAlarmTur())
        {
            case 1:
                rb1.setChecked(true);
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
                break;
        }
       return view;
        }

    public void SetOyun()
    {
        if(radioGroup.getCheckedRadioButtonId()==rb1.getId())
        {
            MainActivity.newAlarm.setAlarmTur(1);
        }
        else if (radioGroup.getCheckedRadioButtonId()==rb2.getId())
        {
            MainActivity.newAlarm.setAlarmTur(2);
        }
        else if (radioGroup.getCheckedRadioButtonId()==rb3.getId())
        {
            MainActivity.newAlarm.setAlarmTur(3);
        }
        else if (radioGroup.getCheckedRadioButtonId()==rb4.getId())
        {
            MainActivity.newAlarm.setAlarmTur(4);
        }
    }


}
