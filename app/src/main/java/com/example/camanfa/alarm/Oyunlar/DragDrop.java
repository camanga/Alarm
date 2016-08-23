package com.example.camanfa.alarm.Oyunlar;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.camanfa.alarm.Alarm.AlarmModel;
import com.example.camanfa.alarm.R;

import java.lang.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by CAMANFA on 5/30/2016.
 */
public class DragDrop extends Activity {

     static int NBR_ITEMS = 0;
    private static GridLayout mGrid;
    public static int old=-1;
    static Context context;
   static MediaPlayer sound;
    private ValueAnimator mAnimator;
    private AtomicBoolean mIsScrolling = new AtomicBoolean(false);
    public static Map<Integer,Bitmap> bitmapIntegerHashMap= new TreeMap<Integer,Bitmap>();
    int zorluk_derecesi=0;
    ImageView imageView;
    int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AlarmModel alarmModel=(AlarmModel) getIntent().getExtras().getSerializable("AlarmModel");
        Uri myUri = Uri.parse(alarmModel.getZilSesiLocation());


        setContentView(R.layout.deneme);
        sound=new MediaPlayer();
        context=getApplicationContext();
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
        //Intent alinmasi
        zorluk_derecesi=alarmModel.getZorlukDerecesi();
        switch (zorluk_derecesi)
        {
            case 1:
                NBR_ITEMS=4;
                break;
            case 2:
                NBR_ITEMS=9;
                break;
            case 3:
                NBR_ITEMS=16;
                break;
        }
        List a = new ArrayList();
        a.add(0,R.drawable.manzara1);
        a.add(1,R.drawable.manzara2);
        a.add(2,R.drawable.manzara3);
        a.add(3,R.drawable.manzara4);
        a.add(4,R.drawable.manzara5);
        a.add(5,R.drawable.manzara6);
        a.add(6,R.drawable.manzara7);
        a.add(7,R.drawable.manzara8);
        a.add(8,R.drawable.manzara9);

        imageView = (ImageView) findViewById(R.id.imageView2);
        Random random= new Random();
        int randomNumber=random.nextInt(8);

        imageView.setImageResource((int)a.get(randomNumber));
        imageView.setDrawingCacheEnabled(true);
        imageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


        imageView.buildDrawingCache(true);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(),
                (int)a.get(randomNumber));
        imageView.setDrawingCacheEnabled(false); // clear drawing cache

        Bitmap[] myBitmap = splitBitmap(b,zorluk_derecesi);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height-b.getHeight());

        imageView.setLayoutParams(layoutParams);

        mGrid = (GridLayout) findViewById(R.id.grid_layout);
        mGrid.setRowCount((int)Math.sqrt(NBR_ITEMS));
        mGrid.setColumnCount((int)Math.sqrt(NBR_ITEMS));
        mGrid.setOnDragListener(new DragListener());


        final LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < NBR_ITEMS; i++) {

            final View itemView = inflater.inflate(R.layout.drapdrop_layout, mGrid, false);
            final ImageView text = (ImageView) itemView.findViewById(R.id.text);
            text.setImageBitmap(myBitmap[i]);
            itemView.setOnTouchListener(new OnTouchListener());

            mGrid.addView(itemView);
        }


    }
        public static void Kontrol()
    {


            for (int x = 0; x <NBR_ITEMS ; x++) {

                View newView = mGrid.getChildAt(x);
                if (newView.getTag() != "Sabit") {
                    return;
                }

            }
        sound.stop();
        System.exit(0);


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    static class OnTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            old= calculateNewIndex(v.getX(),v.getY());

            return false;
        }
        public int calculateNewIndex(float x, float y) {
            // calculate which column to move to
            final float cellWidth = mGrid.getWidth() / mGrid.getColumnCount();
            final int column = (int) (x / cellWidth);

            // calculate which row to move to
            final float cellHeight = mGrid.getHeight() / mGrid.getRowCount();
            final int row = (int) java.lang.Math.floor(y / cellHeight);

            // the items in the GridLayout is organized as a wrapping list
            // and not as an actual grid, so this is how to get the new index
            int index = row * mGrid.getColumnCount() + column;
            if (index >= mGrid.getChildCount()) {
                index = mGrid.getChildCount() - 1;
            }

            return index;
        }
    }

    class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            final View view = (View) event.getLocalState();
            switch (event.getAction()) {



                case DragEvent.ACTION_DRAG_LOCATION:
                    // do nothing if hovering above own position

                    if (view == ((GridLayout) v).getChildAt(calculateNewIndex(event.getX(), event.getY()))) {
                      //  old=calculateNewIndex(event.getX(), event.getY());

                        return true;
                    }

                    break;
                case DragEvent.ACTION_DROP:
                    if (view == ((GridLayout) v).getChildAt(calculateNewIndex(event.getX(), event.getY()))) {

                        view.setVisibility(View.VISIBLE);
                        return true;
                    }


                    final int index = calculateNewIndex(event.getX(), event.getY());

                    // get the new list index
                    // remove the view from the old position
                    if(old!=-1) {
                        View newView = mGrid.getChildAt(index);
                        if(newView.getTag()=="Sabit")
                        {
                            view.setVisibility(View.VISIBLE);
                            return true;
                        }


                        BitmapDrawable a=(BitmapDrawable)((ImageView) newView.findViewById(R.id.text)).getDrawable();
                        BitmapDrawable b=(BitmapDrawable)((ImageView) view.findViewById(R.id.text)).getDrawable();
                        bitmapIntegerHashMap.get(index);
                        mGrid.removeView(view);
                        mGrid.addView(view, index);

                        mGrid.removeView(newView);
                        mGrid.addView(newView, old);
                        if(b.getBitmap()==bitmapIntegerHashMap.get(index))
                        {
                            view.setOnTouchListener(null);
                            view.setTag("Sabit");
                        }
                        if(a.getBitmap()==bitmapIntegerHashMap.get(old))
                        {
                            newView.setOnTouchListener(null);
                            newView.setTag("Sabit");
                        }



                    }



                    view.setVisibility(View.VISIBLE);
                    Kontrol();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!event.getResult()) {

                        view.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }
    }


    public int calculateNewIndex(float x, float y) {
        // calculate which column to move to
        final float cellWidth = mGrid.getWidth() / mGrid.getColumnCount();
        final int column = (int) (x / cellWidth);

        // calculate which row to move to
        final float cellHeight = mGrid.getHeight() / mGrid.getRowCount();
        final int row = (int) java.lang.Math.floor(y / cellHeight);

        // the items in the GridLayout is organized as a wrapping list
        // and not as an actual grid, so this is how to get the new index
        int index = row * mGrid.getColumnCount() + column;
        if (index >= mGrid.getChildCount()) {
            index = mGrid.getChildCount() - 1;
        }

        return index;
    }
    public Bitmap[] splitBitmap(Bitmap picture,int zorluk_derecesi)
    {
        int height;
        int width;
        Bitmap[] imgs;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture, picture.getWidth(), picture.getHeight(), true);
        if (zorluk_derecesi==1) {

            height = picture.getHeight() / 2;
            width = picture.getWidth() / 2;
            imgs = new Bitmap[4];
            imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, height, width);

            imgs[1] = Bitmap.createBitmap(scaledBitmap, height, 0, height, width);
            imgs[2] = Bitmap.createBitmap(scaledBitmap, 0, width, height, width);
            imgs[3] = Bitmap.createBitmap(scaledBitmap, height, width, height, width);


            for (int x = 0; x < 4; x++) {
                bitmapIntegerHashMap.put(x, imgs[x]);
            }
            imgs=   shuffleArray(imgs);
        }

else if (zorluk_derecesi==2) {
            height = picture.getHeight() / 3;
            width = picture.getWidth() / 3;
            imgs = new Bitmap[9];
            imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, height, width);

            imgs[1] = Bitmap.createBitmap(scaledBitmap, height, 0, height, width);
            imgs[2] = Bitmap.createBitmap(scaledBitmap, 2 * height, 0, height, width);
            imgs[3] = Bitmap.createBitmap(scaledBitmap, 0, width, height, width);
            imgs[4] = Bitmap.createBitmap(scaledBitmap, height, width, height, width);
            imgs[5] = Bitmap.createBitmap(scaledBitmap, 2 * height, width, height, width);
            imgs[6] = Bitmap.createBitmap(scaledBitmap, 0, 2 * width, height, width);
            imgs[7] = Bitmap.createBitmap(scaledBitmap, height, 2 * width, height, width);
            imgs[8] = Bitmap.createBitmap(scaledBitmap, 2 * height, 2 * width, height, width);
            for (int x = 0; x < 9; x++) {
                bitmapIntegerHashMap.put(x, imgs[x]);
            }
            imgs=  shuffleArray(imgs);
        }
           else
        {
                height = picture.getHeight() / 4;
                width = picture.getWidth() / 4;
                imgs = new Bitmap[16];
                imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, height, width);

                imgs[1] = Bitmap.createBitmap(scaledBitmap, height, 0, height, width);
                imgs[2] = Bitmap.createBitmap(scaledBitmap, 2 * height, 0, height, width);
                imgs[3] = Bitmap.createBitmap(scaledBitmap, 3 * height, 0, height, width);
                imgs[4] = Bitmap.createBitmap(scaledBitmap, 0, width, height, width);
                imgs[5] = Bitmap.createBitmap(scaledBitmap,  height, width, height, width);
                imgs[6] = Bitmap.createBitmap(scaledBitmap, 2 * height,  width, height, width);
                imgs[7] = Bitmap.createBitmap(scaledBitmap, 3*height,  width, height, width);
                imgs[8] = Bitmap.createBitmap(scaledBitmap, 0, 2 * width, height, width);
                imgs[9] = Bitmap.createBitmap(scaledBitmap,  height, 2 * width, height, width);
                imgs[10] = Bitmap.createBitmap(scaledBitmap, 2*height, 2 * width, height, width);
                imgs[11] = Bitmap.createBitmap(scaledBitmap,3* height, 2 * width, height, width);
                imgs[12] = Bitmap.createBitmap(scaledBitmap, 0, 3 * width, height, width);
                imgs[13] = Bitmap.createBitmap(scaledBitmap, height, 3 * width, height, width);
                imgs[14] = Bitmap.createBitmap(scaledBitmap, 2*height, 3 * width, height, width);
                imgs[15] = Bitmap.createBitmap(scaledBitmap, 3 * height, 3 * width, height, width);
                for (int x =0 ; x<16;x++)
                {
                    bitmapIntegerHashMap.put(x,imgs[x]);
                }
            imgs=  shuffleArray(imgs);



        }

        return imgs;




    }
    static Bitmap[] shuffleArray(Bitmap[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        List<Integer> mylist = new ArrayList<>();
        for(int counter = 0;counter<NBR_ITEMS;counter++)
        {
            mylist.add(counter);
        }
        Bitmap[] newar= new Bitmap[ar.length];
        Random rnd =new Random();

        for (int i = ar.length-1 ; i > -1; i--)
        {
            int dex=rnd.nextInt(mylist.size());

            int index = mylist.get(dex);
            // Simple swap
            if(i==1)
            {
                if(mylist.get(dex)==i)
                {
                    newar[0]=ar[mylist.get(1)];


                    newar[1]=ar[mylist.get(0)];
                }
                else
                {
                    newar[1]=ar[mylist.get(0)];

                    newar[0]=ar[mylist.get(1)];

                }

                break;
            }
         while(index==i) {

             dex=rnd.nextInt(mylist.size());
             index =  mylist.get(dex);


         }
            mylist.remove(dex);
            newar [i] = ar[index];


        }
        return newar;
    }
    @Override
    public void onBackPressed() {

    }
}
