package com.example.camanfa.alarm.Ayarlar;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.MainActivity;
import com.example.camanfa.alarm.R;


public class NameFragment extends Fragment {
    private    EditText nameText;
    InputMethodManager imm;

    public NameFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(null);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setTitle("Etiket");
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#78909C")));

        View view = inflater.inflate(R.layout.fragment_name,container,false);


        nameText = (EditText)view.findViewById(R.id.fragment_name_EditText);
        nameText.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nameText.setText(MainActivity.newAlarm.getName());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        nameText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        imm.showSoftInput(nameText, InputMethodManager.SHOW_FORCED);



//        (new Handler()).postDelayed(new Runnable() {
//
//            public void run() {
////              ((EditText) findViewById(R.id.et_find)).requestFocus();
////
////              InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////              imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);
//
//                nameText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
//                nameText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
//
//            }
//        }, 200);

        return view;
    }

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(nameText.getWindowToken(), 0);
        super.onPause();
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }

    public void setNameText()
    {
        MainActivity.newAlarm.setName(nameText.getText().toString());
    }

}
