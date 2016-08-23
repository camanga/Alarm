package com.example.camanfa.alarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.camanfa.alarm.Alarm.AlarmAdapter;
import com.example.camanfa.alarm.Alarm.AlarmModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by CAMANFA on 8/16/2016.
 */
public class DuzenleCardViewAdapter  extends RecyclerView.Adapter<DuzenleCardViewAdapter.DuzenleViewHolder> {

    private List<AlarmModel> alarmModels;
    private DatabaseHandler databaseHandler;
    private Context myContext;

    public DuzenleCardViewAdapter(List<AlarmModel> alarmModels, DatabaseHandler databaseHandler,Context context) {
        Collections.sort(alarmModels,AlarmModel.StuNameComparator);
        this.alarmModels = alarmModels;
        this.databaseHandler = databaseHandler;
        this.myContext=context;
    }

    @Override
    public void onBindViewHolder(DuzenleViewHolder holder,final int position) {
        final CardView cardView= holder.cardView;
        final LinearLayout mainLayout =(LinearLayout)cardView.findViewById(R.id.main_layout);
        final TextView textViewSaat=(TextView)cardView.findViewById(R.id.cwdtxtSaat);
        final TextView textViewName=(TextView)cardView.findViewById(R.id.cwdtxtAlarmName);
        final ImageButton imageButton=(ImageButton)cardView.findViewById(R.id.cwdimgButton);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.newAlarm=alarmModels.get(0);
                MainFragment.setUpdate(true);
                Intent myIntent= new Intent(myContext,MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                myContext.startActivity(myIntent);


            }
        });
        if(!alarmModels.get(position).getGunler().equals("Asla")) {
            textViewName.setText(alarmModels.get(position).getName() + "," + alarmModels.get(position).getGunler() + "," + alarmModels.get(position).getOyunText() + "," + alarmModels.get(position).getZorlukText());
        }
            else
        {
            textViewName.setText(alarmModels.get(position).getName()+","+alarmModels.get(position).getOyunText()+","+alarmModels.get(position).getZorlukText());
        }
        textViewSaat.setText(alarmModels.get(position).getSaat());


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                remove( alarmModels,position);
            }
        });


        if(!alarmModels.get(position).getAktif())
        {
            cardView.setBackgroundColor(Color.parseColor("#EFEEF4"));
            textViewName.setTextColor(Color.parseColor("#8E8D93"));
            textViewSaat.setTextColor(Color.parseColor("#8E8D93"));
            imageButton.setBackgroundColor(Color.parseColor("#EFEEF4"));
        }
        else
        {
            cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textViewName.setTextColor(Color.parseColor("#000000"));
            textViewSaat.setTextColor(Color.parseColor("#000000"));
            imageButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public DuzenleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.duzenle_cardview,parent,false);
        return new DuzenleViewHolder(cv);
    }

    @Override
    public int getItemCount() {
        return alarmModels.size();
    }
    public static class DuzenleViewHolder extends  RecyclerView.ViewHolder
    {
        private CardView cardView;
        public DuzenleViewHolder(CardView itemView)
        {
            super(itemView);
            cardView=itemView;
        }
    }
    public void remove(List<AlarmModel> alarmModel,int position) {
        MainActivity.CancelPendingIntent(alarmModel.get(position).getId(),alarmModel.get(position));
        databaseHandler.DeleteProduct(alarmModel.get(position).getId());
        alarmModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, alarmModel.size());
    }
}
