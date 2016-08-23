package com.example.camanfa.alarm.Oyunlar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.LinearLayoutThatDetectsSoftKeyboard;
import com.example.camanfa.alarm.R;

import java.util.Random;

/**
 * Created by CAMANFA on 5/13/2016.
 */
public class Math extends Activity  {
    MediaPlayer sound;
    Button btnCheck;
    TextView txtQuestion;
    EditText txtAnswer;
    String question ;
    String answer;
    String userAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.math_layout);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnCheck=(Button)findViewById(R.id.btnKontrol);
        txtQuestion=(TextView)findViewById(R.id.textView);
        txtAnswer=(EditText)findViewById(R.id.editText);
        txtAnswer.requestFocus();
       final RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.rel);
       final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        final View activityRootView = findViewById(android.R.id.content);
     //  activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
     //     @Override
     //     public void onGlobalLayout() {
     //         Rect r = new Rect();
     //         //r will be populated with the coordinates of your view that area still visible.
     //         activityRootView.getWindowVisibleDisplayFrame(r);

     //         int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
     //         if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
     //             FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) myLayout.getLayoutParams();
     //             params.height = r.bottom - r.top;
     //             myLayout.setLayoutParams(params);

     //         }
     //     }
     // });

        //intent alinmasi

        AlarmModel alarmModel= (AlarmModel)getIntent().getExtras().getSerializable("AlarmModel");
        if(alarmModel!=null) {
            soruUret(alarmModel.getZorlukDerecesi());
            txtQuestion.setText(question);
        }
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



    }
    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
    @Override
    public void onBackPressed() {

    }

    public void soruUret(int zorluk)
    {
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        Random r = new Random();
        switch(zorluk)
        {
            case 1 :
                a = r.nextInt(10);
                while(a==0) {
                    a = r.nextInt(10);
                }
                b = r.nextInt(10);
                while(b==0) {
                    b = r.nextInt(10);
                }

                answer = String.valueOf(a*b);
                question=String.valueOf(a)+"*"+String.valueOf(b);
                break;
            case 2:
                a = r.nextInt(10);
                while(a==0) {
                    a = r.nextInt(10);
                }
                b = r.nextInt(10);
                while(b==0) {
                    b = r.nextInt(10);
                }
                 c= r.nextInt(15);
                answer = String.valueOf(a*b-c);
                question=String.valueOf(a)+"*"+String.valueOf(b)+"-"+String.valueOf(c);

                break;
            case 3:
                a = r.nextInt(10);
                while(a==0) {
                    a = r.nextInt(10);
                }
                b = r.nextInt(10);
                while(b==0) {
                    b = r.nextInt(10);
                }
                c= r.nextInt(15);
                d=r.nextInt(5);
                answer = String.valueOf(a*b-c+d);
                question=String.valueOf(a)+"*"+String.valueOf(b)+"-"+String.valueOf(c)+"+"+String .valueOf(d);
                break;
        }


    }
    public void   btnCheckOnClick(View view)
    {
        userAnswer=txtAnswer.getText().toString();
        if(userAnswer.equals(answer))
        {
            sound.stop();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            Toast.makeText(this,"Dogru",Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(this,"Yanlis",Toast.LENGTH_LONG).show();


    }

}
