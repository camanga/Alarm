package com.example.camanfa.alarm.Oyunlar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;


import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CAMANFA on 5/30/2016.
 */
public class Numbers extends AppCompatActivity {

    List<Sayi> sayiList;
    public int sira = 1;
    public Button fbutton1;
    public Button fbutton2;
    public Button fbutton3;
    public Button fbutton4;
    public Button fbutton5;
    public Button fbutton6;
    public Button fbutton7;
    public Button fbutton8;
    public Button fbutton9;
    public int zorlukDerecesi;
    int tur;
    static MediaPlayer sound;
    protected void onCreate(Bundle savedInstanceState) {
        sayiList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.yerlestirme_layout);
        AlarmModel alarmModel=(AlarmModel) getIntent().getExtras().getSerializable("AlarmModel");
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
        List myList = new ArrayList();
        myList.add(0,0);
        GridLayout mGrid= (GridLayout)findViewById(R.id.grdly);
        final LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 1; i < 10; i++) {

            final View itemView = inflater.inflate(R.layout.siralama_layout, mGrid, false);
            final Button text = (Button) itemView.findViewById(R.id.buttonSiralama);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            text.setLayoutParams(new FrameLayout.LayoutParams(width/3-15,height/3-15));
            myList.add(i,text.getId());



            mGrid.addView(itemView);
        }




        final Button button1 = (Button)  mGrid.getChildAt(0).findViewById((int)myList.get(1));
        final Button button2 = (Button) mGrid.getChildAt(1).findViewById((int)myList.get(2));
        final Button button3 = (Button) mGrid.getChildAt(2).findViewById((int)myList.get(3));
        final Button button4 = (Button) mGrid.getChildAt(3).findViewById((int)myList.get(4));
        final Button button5 = (Button) mGrid.getChildAt(4).findViewById((int)myList.get(5));
        final Button button6 = (Button) mGrid.getChildAt(5).findViewById((int)myList.get(6));
        final Button button7 = (Button) mGrid.getChildAt(6).findViewById((int)myList.get(7));
        final Button button8 = (Button) mGrid.getChildAt(7).findViewById((int)myList.get(8));
        final Button button9 = (Button) mGrid.getChildAt(8).findViewById((int)myList.get(9));


        fbutton1=button1;
        fbutton2=button2;
        fbutton3=button3;
        fbutton4=button4;
        fbutton5=button5;
        fbutton6=button6;
        fbutton7=button7;
        fbutton8=button8;
        fbutton9=button9;


        zorlukDerecesi=alarmModel.getZorlukDerecesi();
        tur=1;


        for(int x =1 ;x<10;x++)
        {
         Sayi sayi = new Sayi(x,false);
            sayiList.add(sayi);
        }
        TekrarKaristir();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(0).getSira()) {
                    ((View) button1.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else {
                    TekrarKaristir();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(1).getSira()) {
                    ((View) button2.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(2).getSira()) {
                    ((View) button3.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(3).getSira()) {
                    ((View) button4.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(4).getSira()) {
                    ((View) button5.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(5).getSira()) {
                    ((View) button6.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(6).getSira()) {
                    ((View) button7.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(7).getSira()) {
                    ((View) button8.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sira == sayiList.get(8).getSira()) {
                    ((View) button9.getParent()).setVisibility(View.INVISIBLE);
                    sira++;
                    if(sira==10) {
                        if(zorlukDerecesi==tur)
                            Bitir();
                        else
                        {
                            tur++;
                            TekrarKaristir();
                        }
                    }
                }
                else
                {
                    TekrarKaristir();

                }
            }
        });





    }

    void TekrarKaristir()
    {
        sira=1;
        Collections.shuffle(sayiList);
        fbutton1.setText(String.valueOf(String.valueOf(sayiList.get(0).getSira())));
        fbutton2.setText(String.valueOf(String.valueOf(sayiList.get(1).getSira())));
        fbutton3.setText(String.valueOf(String.valueOf(sayiList.get(2).getSira())));
        fbutton4.setText(String.valueOf(String.valueOf(sayiList.get(3).getSira())));
        fbutton5.setText(String.valueOf(String.valueOf(sayiList.get(4).getSira())));
        fbutton6.setText(String.valueOf(String.valueOf(sayiList.get(5).getSira())));
        fbutton7.setText(String.valueOf(String.valueOf(sayiList.get(6).getSira())));
        fbutton8.setText(String.valueOf(String.valueOf(sayiList.get(7).getSira())));
        fbutton9.setText(String.valueOf(String.valueOf(sayiList.get(8).getSira())));

        ((View)fbutton1.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton2.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton3.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton4.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton5.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton6.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton7.getParent()).setVisibility(View.VISIBLE);

               ((View)fbutton8.getParent()).setVisibility(View.VISIBLE);
        ((View)fbutton9.getParent()).setVisibility(View.VISIBLE);


    }

    @Override
    public void onBackPressed() {

    }
    void Bitir()
    {
        sound.stop();
        System.exit(0);
    }

}
 class Sayi
{
    int sira;
    boolean dogru;

    public boolean isDogru() {
        return dogru;
    }

    public void setDogru(boolean dogru) {
        this.dogru = dogru;
    }

    public int getSira() {
        return sira;
    }

    public void setSira(int sira) {
        this.sira = sira;
    }

    public Sayi(int sira, boolean dogru) {
        this.sira = sira;
        this.dogru = dogru;
    }
}
