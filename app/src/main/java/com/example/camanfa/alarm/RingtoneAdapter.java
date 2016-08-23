package com.example.camanfa.alarm;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.*;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.example.camanfa.alarm.Ayarlar.ZilSesiFragment;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by CAMANFA on 7/27/2016.
 */
public class RingtoneAdapter extends BaseAdapter {
    private LinkedHashMap<String, String> mData = new LinkedHashMap<String, String>();
    private String[] mKeys;
    private int selectedItemIndex;
    static android.media.Ringtone ringtone;
    public ZilSesiFragment.Object myObject=new ZilSesiFragment().new Object("","");
    public RingtoneAdapter(LinkedHashMap<String, String> data,int position){

        mData  = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        myObject.setLocation(data.get(mKeys[0]));
        myObject.setName(mKeys[0]);
        selectedItemIndex=position;
        ZilSesiFragment.setMyObject(myObject);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        final String key = mKeys[pos];
       final String Value = getItem(pos).toString();
       final View result;
        result = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ringtone, parent, false);
        RadioButton radioButton= (RadioButton)result.findViewById(R.id.rbRingtone);
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
            radioButton.setButtonTintList(colorStateList);
        }
        if(selectedItemIndex==pos)
        {
            radioButton.setChecked(true);
        }
        else
        radioButton.setChecked(false);
        radioButton.setText(key);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone!=null)
                ringtone.stop();

                selectedItemIndex=pos;
                notifyDataSetChanged();


                Uri myUri1 = Uri.parse(Value);
                try {
                    ringtone=  RingtoneManager.getRingtone(result.getContext(), myUri1);
                    ringtone.play();

                    myObject.setName(key);
                    myObject.setLocation(Value);
                    ZilSesiFragment.setMyObject(myObject);
                }
                catch (Exception ex){

                }

            }
        });



        return result;
    }
    public static void ringtoneStop()
    {
        if(ringtone!=null)
        ringtone.stop();
    }
    public static void ringtoneStart(Context context,Uri uri)
    {
        ringtone=  RingtoneManager.getRingtone(context, uri);

        ringtone.play();
    }

}
