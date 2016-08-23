package com.example.camanfa.alarm;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.camanfa.alarm.Alarm.AlarmAdapter;
import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.Alarm.AlarmReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CAMANFA on 5/24/2016.
 */
public class Baslangic extends AppCompatActivity {


    static  Context context;
    Boolean clicked=true;
    static  DatabaseHandler dbHandler ;
    static ListView listView;
    static ListAdapter listAdapter;
    static TextView btnDuzenle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=this;
        context=this.context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbHandler= new DatabaseHandler(this.context,null,null,1);
        List<AlarmModel> alarmModel= new ArrayList<AlarmModel>();
        alarmModel=dbHandler.DatabasetoString();
        Collections.sort(alarmModel, AlarmModel.StuNameComparator);

        setContentView(R.layout.main_activity);




        final ListAdapter myAdapter = new AlarmAdapter(this.context,alarmModel){
        };

        final  ListView myListview=(ListView)findViewById(R.id.listView2);
        myListview.setAdapter(myAdapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(context, MainActivity.class);
                AlarmModel model = (AlarmModel) myListview.getItemAtPosition(position);

                a.putExtra("UpdateModel", model);
                startActivity(a);
            }
        });
        listView=myListview;



        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#607D8B")));
        LayoutInflater mInflater =LayoutInflater.from(this);
        View cView =mInflater.inflate(R.layout.custom_actionbar, null);
        getSupportActionBar().setCustomView(cView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        getSupportActionBar().setCustomView(cView, layoutParams);
        Toolbar parent = (Toolbar) cView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        btnDuzenle = (TextView) cView.findViewById(R.id.txtDuzenle);

        btnDuzenle.setText("Düzenle");
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (clicked) {
                        if(myAdapter.getCount()>0) {
                            for (int x = 0; x < myAdapter.getCount(); x++) {
                                AlarmModel a = (AlarmModel) myAdapter.getItem(x);
                                a.setEditable(true);
                            }
                           myListview.setAdapter(myAdapter);
                           ((ArrayAdapter<AlarmModel>) myAdapter).notifyDataSetChanged();


                            btnDuzenle.setText("Bitti");
                        }


                    } else {
                        for (int x = 0; x < myAdapter.getCount(); x++) {
                            AlarmModel a = (AlarmModel) myAdapter.getItem(x);
                            a.setEditable(false);
                        }
                       myListview.setAdapter(myAdapter);

                       ((ArrayAdapter<AlarmModel>) myAdapter).notifyDataSetChanged();

                        btnDuzenle.setText("Duzenle");


                    }

                if (clicked) clicked = false;
                else clicked = true;

            }
        });


        TextView title = (TextView)cView.findViewById(R.id.imageView1);
        title.setText("Alarm");
        TextView newAlarm =(TextView) cView.findViewById(R.id.txtEkle);
        newAlarm.setText("Yeni Alarm");

        newAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, MainActivity.class);
                startActivity(myIntent);


            }
        });
        AlarmManager alarmManager = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
        MainActivity.setAlarmManager(alarmManager);
        MainActivity.setContext(this.context);
        Intent myIntent = new Intent(this.context,AlarmReceiver.class);
        MainActivity.setmyIntent(myIntent);
        MainActivity.setDbHandler(dbHandler);
        AlarmAdapter.getIntent(myIntent);
        AlarmAdapter.getContent(this.context);
        AlarmAdapter.getMain(alarmManager);
        AlarmAdapter.setAlarmAdapter(dbHandler);





    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
  //  public static void Refresh()
   ///{
   ///    dbHandler= new DatabaseHandler(context,null,null,1);
   ///    List<AlarmModel> alarmModel= new ArrayList<AlarmModel>();
   ///    alarmModel=dbHandler.DatabasetoString();
   ///    Collections.sort(alarmModel, AlarmModel.StuNameComparator);
   ///    final ListAdapter myAdapter = new AlarmAdapter(context,alarmModel){
   ///    };
   ///    listView.setAdapter(myAdapter);
   ///    if(myAdapter.getCount()<1)
   ///    {
   ///        btnDuzenle.setText("Duzenle");
   ///    }



  //  }
    @Override
    public void onBackPressed() {

    }
}
