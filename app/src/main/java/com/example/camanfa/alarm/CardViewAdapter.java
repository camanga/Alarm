package com.example.camanfa.alarm;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.sip.SipAudioCall;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camanfa.alarm.Alarm.AlarmModel;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by CAMANFA on 8/14/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private List<AlarmModel> alarmModel;
    private Listener listener;
    private DatabaseHandler databaseHandler;


    public CardViewAdapter( List<AlarmModel> alarmModel,DatabaseHandler databaseHandler) {
        Collections.sort(alarmModel,AlarmModel.StuNameComparator);
        this.alarmModel =alarmModel;
        this.databaseHandler=databaseHandler;

    }

    public void setListener(Listener listener)
    {
        this.listener=listener;
    }

    public static interface Listener
    {
        public void onClick(int position);
        public void onRemove(AlarmModel alarmModel,int position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final CardView cardView= holder.cardView;
        final  TextView textSaat = (TextView)cardView.findViewById(R.id.cwtxtSaat);
        textSaat.setText(alarmModel.get(position).getSaat());
        final  TextView textIsim = (TextView)cardView.findViewById(R.id.cwtxtAlarmName);
        if(!alarmModel.get(position).getGunler().equals("Asla")) {
            textIsim.setText(alarmModel.get(position).getName()+","+alarmModel.get(position).getGunler()+","+alarmModel.get(position).getOyunText()+","+alarmModel.get(position).getZorlukText());
        }
        else
        {
            textIsim.setText(alarmModel.get(position).getName()+","+alarmModel.get(position).getOyunText()+","+alarmModel.get(position).getZorlukText());
        }
        final  Switch alarmSwitch= (Switch)cardView.findViewById(R.id.cwalarmswitch);
        alarmSwitch.setChecked(alarmModel.get(position).getAktif());
        alarmSwitch.getThumbDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        changeView(cardView,textSaat,textIsim,alarmSwitch,alarmModel.get(position).getAktif(),alarmModel.get(position).getEditable(),alarmModel.get(position),position);




        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                {
                    listener.onClick(position);
                }
            }

        });
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (buttonView.isPressed()) {
                    if (isChecked) {
                        cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        textIsim.setTextColor(Color.parseColor("#000000"));
                        textSaat.setTextColor(Color.parseColor("#000000"));
                        alarmSwitch.getTrackDrawable().setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.MULTIPLY);

                        alarmModel.get(position).setAktif(true);
                        databaseHandler.Update(alarmModel.get(position), alarmModel.get(position).getId());
                        alarmModel = databaseHandler.DatabasetoString();
                        MainActivity.StartPendingIntent( alarmModel.get(position));

                        Collections.sort(alarmModel, AlarmModel.StuNameComparator);
                    } else {
                        cardView.setBackgroundColor(Color.parseColor("#EFEEF4"));
                        textIsim.setTextColor(Color.parseColor("#8E8D93"));
                        textSaat.setTextColor(Color.parseColor("#8E8D93"));
                        alarmSwitch.getTrackDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
                        alarmModel.get(position).setAktif(false);
                        MainActivity.CancelPendingIntent(alarmModel.get(position).getId(), alarmModel.get(position));
                        databaseHandler.Update(alarmModel.get(position), alarmModel.get(position).getId());
                        alarmModel = databaseHandler.DatabasetoString();
                        Collections.sort(alarmModel, AlarmModel.StuNameComparator);
                    }
                }
            }
        });


      //  if(!alarmModel.get(position).getAktif())
      //  {
      //      cardView.setBackgroundColor(Color.parseColor("#EFEEF4"));
      //      alarmSwitch.getThumbDrawable().setColorFilter(Color.parseColor("#CFD8DC"), PorterDuff.Mode.MULTIPLY);
      //      textIsim.setTextColor(Color.parseColor("#8B8A90"));
      //      textSaat.setTextColor(Color.parseColor("#8B8A90"));
      //      alarmSwitch.getTrackDrawable().setColorFilter(Color.parseColor("#4ED966"), PorterDuff.Mode.MULTIPLY);
//
      //  }

    }

    @Override
    public int getItemCount() {
        return alarmModel.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final CardView cv= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.baslangic_cardview,parent,false);

        return new ViewHolder(cv,parent.getContext());
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener
    {
        private CardView cardView;
        public TextView textViewSaat;
        public TextView textViewName;
        public Switch alarmSwitch;
        private Context mContext;

        public ViewHolder(CardView itemView,Context mContext)
        {
            super(itemView);
            this.mContext=mContext;

            cardView=itemView;
            textViewName=(TextView)cardView.findViewById(R.id.cwtxtAlarmName);
            textViewSaat=(TextView)cardView.findViewById(R.id.cwtxtAlarmName);
            alarmSwitch=(Switch)cardView.findViewById(R.id.cwalarmswitch);
           // alarmSwitch.setOnCheckedChangeListener(this);

        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Toast.makeText(mContext,getAdapterPosition()+"",Toast.LENGTH_SHORT).show();

        }
    }

    private void changeView(CardView cardView,TextView textSaat,TextView textIsim,Switch alarmSwitch,boolean isSwitchChecked,boolean isEditable,AlarmModel alarmModel,int position)
    {


        if (isSwitchChecked) {
            cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textIsim.setTextColor(Color.parseColor("#000000"));
            textSaat.setTextColor(Color.parseColor("#000000"));

            alarmSwitch.getTrackDrawable().setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.MULTIPLY);
            alarmModel.setAktif(true);
           // dbHandler.Update(alarmModel, alarmModel.getId());
          //  this.alarmModel=dbHandler.DatabasetoString();
           // notifyItemChanged(position);
        } else {
            cardView.setBackgroundColor(Color.parseColor("#EFEEF4"));
            textIsim.setTextColor(Color.parseColor("#8E8D93"));
            textSaat.setTextColor(Color.parseColor("#8E8D93"));
            alarmSwitch.getTrackDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
            alarmModel.setAktif(false);
          //  dbHandler.Update(alarmModel, alarmModel.getId());
          //  this.alarmModel=dbHandler.DatabasetoString();
          //  notifyItemChanged(position);
        }

    }


    public void remove(List<AlarmModel> alarmModel,int position) {
      //  dbHandler.DeleteProduct(alarmModel.get(position).getId());
        alarmModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, alarmModel.size());
    }

}
