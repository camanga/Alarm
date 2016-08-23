package com.example.camanfa.alarm.Oyunlar;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.R;

import java.lang.*;
import java.lang.Math;

/**
 * Created by CAMANFA on 6/27/2016.
 */
public class TiltSensor extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    ProgressBar progressBar;
   private TextView myText;
    private float last_x, last_y, last_z = 0;
    int bas=0;
    int hedef=0;
    int artis=0;
    int pStatus = 0;
    boolean baslangic=false;
    static MediaPlayer sound;
    AlarmModel alarmModel;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.shake);


        myText=(TextView)findViewById(R.id.textKalan);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
         alarmModel = (AlarmModel) getIntent().getExtras().getSerializable("AlarmModel");
        Uri myUri = Uri.parse(alarmModel.getZilSesiLocation());
        sound=new MediaPlayer();
        try {
            sound.setDataSource(getApplicationContext(), myUri);
            sound.prepare();
        }
        catch (Exception ex)
        {
            String a=ex.getMessage();
        }
        sound.start();
        sound.setLooping(true);
         progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setProgress(pStatus);
        progressBar.setSecondaryProgress(100);


        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;



        switch (alarmModel.getZorlukDerecesi())
        {
            case 1:
                hedef=20;
                artis=100/hedef;
                myText.setText(String.valueOf(hedef));
                 mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
                break;
            case 2:
                hedef=25;
                artis=100/hedef;
                myText.setText(String.valueOf(hedef));
                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
                break;
            case 3:
                hedef=30;
                artis=100/hedef;
                myText.setText(String.valueOf(hedef));
                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

                break;
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {

            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
          //  float x = values[0];
          //  float y = values[1];
          //  float z = values[2];
            switch (alarmModel.getZorlukDerecesi())
            {
                case 1:
                    if (mAccel > 8) {
                        Log.e("a","a");
                        if(baslangic) {

                            hedef--;
                            myText.setText(String.valueOf(hedef));

                            pStatus +=  artis;
                            progressBar.setProgress(pStatus);

                        }

                        baslangic=true;
                        if(bas==hedef)
                        {
                            onDestroy();
                        }

                    }
                    break;
                case 2:
                    if (mAccel > 6 ) {
                        Log.e("a","a");
                        if(baslangic) {

                            hedef--;
                            myText.setText(String.valueOf(hedef));

                            pStatus +=  artis;
                            progressBar.setProgress(pStatus);

                        }

                        baslangic=true;
                        if(bas==hedef)
                        {
                            onDestroy();
                        }

                    }  break;
                case 3:
                    if (mAccel > 4 ) {
                        Log.e("a","a");
                        if(baslangic) {

                            hedef--;
                            myText.setText(String.valueOf(hedef));

                            pStatus +=  artis;
                            progressBar.setProgress(pStatus);

                        }

                        baslangic=true;
                        if(bas==hedef)
                        {
                            onDestroy();
                        }

                    }
                    break;
            }


            last_x = x;
            last_y = y;
            last_z = z;


        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        System.exit(0);

    }
}
