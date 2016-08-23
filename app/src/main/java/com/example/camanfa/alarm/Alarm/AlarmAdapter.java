package com.example.camanfa.alarm.Alarm;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.camanfa.alarm.DatabaseHandler;
import com.example.camanfa.alarm.MainActivity;
import com.example.camanfa.alarm.R;

import java.util.List;

/**
 * Created by CAMANFA on 5/24/2016.
 */
public class AlarmAdapter extends ArrayAdapter<AlarmModel> {
    Context context;
    static DatabaseHandler maindbHandler;
   static Context mainActivityContext;
    static Intent myInteny;
    static AlarmManager myalarmManager;
    ViewHolder holder=null;
    LayoutInflater inf;
    View myView;
    public AlarmAdapter(Context context, List<AlarmModel> alarmModel) {
        super(context, R.layout.custom_switch, alarmModel);
    }

    ViewHolder v = new ViewHolder();
    public void setcheckbox() {

        Log.d("viewholser" + v, "checkbox" + v.nImageButton);

        v.nImageButton.setVisibility(View.VISIBLE);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        context=getContext();
        myView=convertView;

         inf=LayoutInflater.from(getContext());
        if (convertView == null){
            holder=new ViewHolder();
            convertView=inf.inflate(R.layout.custom_switch, null);
            holder.nClock=(TextView) convertView.findViewById(R.id.txtSaat);
            holder.nClock.setTextColor(Color.parseColor("#CFD8DC"));
            holder.nName=(TextView) convertView.findViewById(R.id.txtAlarmName);
            holder.nName.setTextColor(Color.parseColor("#CFD8DC"));
            holder.nSwitch=(Switch) convertView.findViewById(R.id.alarmswitch);

            holder.nImageButton=(ImageButton)convertView.findViewById(R.id.imgButton);

            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

      //  LayoutInflater myInflator =LayoutInflater.from(getContext());
       final AlarmModel saat =(AlarmModel)getItem(position);

        if(!saat.editable) {

            holder.nClock.setText(saat.getSaat());
            holder.nClock.setPadding(0, 0, 0, 0);
            holder.nName.setPadding(0, 0, 0, 0);
            holder.nName.setText(Html.fromHtml("<b>" +saat.getName() + "</b>" +" - "+
                            "<small>" + saat.getGunler() + "</small>"  ));
            holder.nSwitch.setVisibility(View.VISIBLE);

            holder.nImageButton.setImageResource(R.drawable.delete);
            if (saat.getAktif()) {
                holder.nSwitch.setChecked(true);
                holder.nSwitch.getThumbDrawable().setColorFilter(Color.parseColor("#37474F"), PorterDuff.Mode.MULTIPLY);
                convertView.setBackgroundColor(Color.parseColor("#EDE9CE"));
                holder.nName.setTextColor(Color.BLACK);
                holder.nClock.setTextColor(Color.BLACK);


            }
            else
            {
                holder.nSwitch.setChecked(false);
                holder.nSwitch.getThumbDrawable().setColorFilter(Color.parseColor("#CFD8DC"), PorterDuff.Mode.MULTIPLY);
                convertView.setBackgroundColor(Color.parseColor("#78909C"));


            }
            holder.nImageButton.setVisibility(View.INVISIBLE);
            holder.nSwitch.setClickable(true);
            holder.nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    View ourView =(View)(buttonView.getParent()).getParent();
                    TextView myText =(TextView)ourView.findViewById(R.id.txtSaat) ;
                    TextView myText2 =(TextView)ourView.findViewById(R.id.txtAlarmName) ;
                    if(isChecked)
                    {


                        ((Switch) buttonView).getThumbDrawable().setColorFilter(Color.parseColor("#37474F"), PorterDuff.Mode.MULTIPLY);
                        myText.setTextColor(Color.BLACK);
                        myText2.setTextColor(Color.BLACK);
                        ourView.setBackgroundColor(Color.parseColor("#EDE9CE"));

                       MainActivity.StartPendingIntent(saat);
                        saat.setAktif(true);
                        maindbHandler.Update(saat, saat.id);

                    }
                    else

                    {


                        try {
                            ((Switch) buttonView).getThumbDrawable().setColorFilter(Color.parseColor("#CFD8DC"), PorterDuff.Mode.MULTIPLY);
                            ((View)(buttonView.getParent()).getParent()).setBackgroundColor(Color.parseColor("#78909C"));
                            myText.setTextColor(Color.parseColor("#CFD8DC"));
                            myText2.setTextColor(Color.parseColor("#CFD8DC"));

                            MainActivity.CancelPendingIntent(saat.getId(), saat);
                            saat.setAktif(false);

                            maindbHandler.Update(saat, saat.id);
                        }
                        catch (Exception ex)
                        {
                            System.out.print(ex.getMessage());

                        }

                    }
                }
            });



        }
        else {
            holder.nClock.setText(saat.getSaat());

           // holder.nClock.setPadding(150, 0, 0, 0);
            holder.nName.setText(saat.getName() + " ," + saat.getGunler());
           // holder.nName.setPadding(150, 0, 0, 0);

            holder.nImageButton.setVisibility(View.VISIBLE);
            holder.nSwitch.setVisibility(View.INVISIBLE);
            holder.nSwitch.setClickable(false);
            holder.nSwitch.setFocusable(false);
            holder.nSwitch.setFocusableInTouchMode(false);
            holder.nImageButton.setFocusableInTouchMode(false);
            holder.nImageButton.setFocusable(false);
            holder.nImageButton.setBackgroundColor(Color.parseColor("#78909C"));
            android.view.ViewGroup.LayoutParams params =  holder.nImageButton.getLayoutParams();
            params.width = 150;
            holder.nImageButton.setLayoutParams(params);


        }
        final ViewHolder finalHolder = holder;
        holder.nImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              remove(saat);
              //  Intent a =new Intent(getContext(),Baslangic.class);
              //  context.startActivity(a);








            }
        });

        //  View customView =myInflator.inflate(R.layout.custom_switch, parent, false);
      //  TextView alarmSaat = (TextView)customView.findViewById(R.id.txtSaat);
      //  alarmSaat.setText(saat.getSaat());
      //  TextView alarmName = (TextView)customView.findViewById(R.id.txtAlarmName);
      //  alarmName.setText(saat.getName()+" ,"+saat.getGunler());
      //  if(saat.getAktif())
      //  {
      //      Switch sw =(Switch)customView.findViewById(R.id.alarmswitch);
      //      sw.setChecked(true);
      //  }
//
        return convertView;
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
    class ViewHolder{
        TextView nName;
        TextView nClock;
        Switch nSwitch;
        ImageButton nImageButton;
    }
    public void refreshEvents() {

        notifyDataSetChanged();
    }


    public void remove(AlarmModel object) {

        maindbHandler.DeleteProduct(object.getId());
        super.remove(object);
    }


    public static void getIntent(Intent mainIntent)
    {
        myInteny=mainIntent;

    }
    public static void getContent(Context context)
    {
        mainActivityContext=context;

    }
    public static  void getMain(AlarmManager alarmManager)
    {
     myalarmManager=alarmManager;
    }
    public static void setAlarmAdapter (DatabaseHandler dbHandler)
    {
        maindbHandler=dbHandler;

    }

}
