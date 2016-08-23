package com.example.camanfa.alarm;


import android.app.AlarmManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Alarm.AlarmReceiver;

import java.util.List;

public class RecycleFragment extends Fragment {
    boolean clicked=true;
    List<AlarmModel> alarmModels;


    public RecycleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     final DatabaseHandler   dbHandler= new DatabaseHandler(getActivity().getBaseContext(),null,null,1);

       ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(false);
       ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayShowTitleEnabled(false);
       ((AppCompatActivity) getActivity()). getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));

        View cView =inflater.inflate(R.layout.custom_actionbar, null);
      ((AppCompatActivity) getActivity()).  getSupportActionBar().setCustomView(cView);
      ((AppCompatActivity) getActivity()).  getSupportActionBar().setDisplayShowCustomEnabled(true);

       alarmModels= dbHandler.DatabasetoString();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        ((AppCompatActivity) getActivity()).  getSupportActionBar().setCustomView(cView, layoutParams);
        Toolbar parent = (Toolbar) cView.getParent();
        parent.setContentInsetsAbsolute(0, 0);



        final RecyclerView alarmRecycle = (RecyclerView)inflater.inflate(R.layout.fragment_recycle,container,false);
        final CardViewAdapter cardViewAdapter= new CardViewAdapter(alarmModels,dbHandler);
        alarmRecycle.setAdapter(cardViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        alarmRecycle.setLayoutManager(linearLayoutManager);
        alarmRecycle.getRecycledViewPool().setMaxRecycledViews(50,50);


        cardViewAdapter.setListener(new CardViewAdapter.Listener(){
            @Override
            public void onClick(int position) {
                int a = 5;


            }

            @Override
            public void onRemove(AlarmModel alarmModel,int position) {
                dbHandler.DeleteProduct(alarmModel.getId());
                cardViewAdapter.notifyItemRemoved(position);
                cardViewAdapter.notifyItemRangeChanged(position,dbHandler.DatabasetoString().size());


            }
        });
        final TextView btnDuzenle = (TextView) cView.findViewById(R.id.txtDuzenle);

        btnDuzenle.setText("Düzenle");
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (clicked) {
                  /// if(alarmModels.size()>0) {
                  ///  for (int x = 0; x < alarmModels.size(); x++) {
                  ///      AlarmModel a = (AlarmModel) alarmModels.get(x);
                  ///      a.setEditable(true);
                  ///  }
                  ///  CardViewAdapter cardViewAdapter= new CardViewAdapter(alarmModels,dbHandler);
                    alarmModels=dbHandler.DatabasetoString();
                    DuzenleCardViewAdapter duzenleCardViewAdapter= new DuzenleCardViewAdapter(alarmModels,dbHandler,getActivity().getApplicationContext());



                     alarmRecycle.setAdapter(duzenleCardViewAdapter);
                       // cardViewAdapter.setEditable(true);


                        btnDuzenle.setText("Bitti");
                  //  }


                } else {
                    alarmModels=dbHandler.DatabasetoString();

                  CardViewAdapter cardViewAdapter= new CardViewAdapter(alarmModels,dbHandler);
                    alarmRecycle.setAdapter(cardViewAdapter);


                   // cardViewAdapter.setEditable(false);

                    btnDuzenle.setText("Düzenle");


                }

                if (clicked) clicked = false;
                else clicked = true;

            }
        });
        MainActivity.setDbHandler(dbHandler);
        Intent myIntent = new Intent(((AppCompatActivity) getActivity()).getApplicationContext(),AlarmReceiver.class);
        MainActivity.setmyIntent(myIntent);
        MainActivity.setAlarmManager((AlarmManager)((AppCompatActivity) getActivity()).getSystemService(Context.ALARM_SERVICE));
        MainActivity.setContext(((AppCompatActivity) getActivity()).getApplicationContext());
        TextView newAlarm =(TextView) cView.findViewById(R.id.txtEkle);
        newAlarm.setText("Yeni Alarm");

        newAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.newAlarm=null;
                MainFragment.setUpdate(false);
                Intent myIntent = new Intent( ((AppCompatActivity) getActivity()).getApplicationContext(), MainActivity.class);
                startActivity(myIntent);


            }
        });


        return alarmRecycle;
    }
    public  void startIntent()
    {
        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}
