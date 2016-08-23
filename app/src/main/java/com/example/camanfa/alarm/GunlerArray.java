package com.example.camanfa.alarm;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CAMANFA on 5/13/2016.
 */
public class GunlerArray extends ArrayAdapter<Gun> {
    public GunlerArray(Context context, List<Gun> array) {
        super(context,R.layout.custom_row, array);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator =LayoutInflater.from(getContext());
        View customView =myInflator.inflate(R.layout.custom_row, parent, false);
        Gun seciliGun= (Gun)getItem(position);


        String day =seciliGun.getGun();
        CheckBox checkBox= (CheckBox) customView.findViewById(R.id.cbDays);
        TextView textView =(TextView)customView.findViewById(R.id.txtDays);
        textView.setTextColor(Color.parseColor("#000000"));
        if(seciliGun.isSecili()) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
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
            checkBox.setLinkTextColor(colorStateList);
            checkBox.setHighlightColor(Color.parseColor("#ECEFF1"));

        }
        else {
            checkBox.setVisibility(View.INVISIBLE);

            checkBox.setChecked(false);
        }


        textView.setText(day);

        return customView;
    }
}
