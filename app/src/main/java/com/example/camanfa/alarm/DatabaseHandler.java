package com.example.camanfa.alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.camanfa.alarm.Alarm.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAMANFA on 5/24/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int database_version=1;
    private static final String database_name="Alarms.db";
    public static final String table_product="Alarms";
    public static final String column_id="_id";
    public static final String column_alarmname="alarmName";
    public static final String column_alarmsaat="alarmSaat";
    public static final String column_alarmAktif="alarmAktif";
    public static final String column_alarmSesi="alarmSesi";
    public static final String column_alarmGunler="alarmGunler";
    public static final String column_alarmTur="alarmTur";
    public static final String column_zorlukderecesi="alarmZorluk";
    public static final String column_zilSesiLocation="alarmzilSesiLocation";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, database_name, factory, database_version);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+table_product);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE "+table_product+" ( "+column_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +column_alarmname+" TEXT, "+column_alarmsaat+" TEXT, "+column_alarmAktif+" BOOLEAN, "+column_alarmGunler+" TEXT, "+column_alarmSesi + " TEXT, "+column_alarmTur + " INTEGER, "+column_zorlukderecesi + " INTEGER,"+column_zilSesiLocation + " TEXT"+")";
        db.execSQL(query);

    }
    public int AddProduct(AlarmModel alarmModel)
    {
        ContentValues contentValues= new ContentValues();
        contentValues.put(column_alarmname, alarmModel.getName());
        contentValues.put(column_alarmGunler,alarmModel.getGunler());
        contentValues.put(column_alarmsaat,alarmModel.getSaat());
        contentValues.put(column_alarmSesi,alarmModel.getZilSesi());
        contentValues.put(column_alarmAktif, alarmModel.getAktif());
        contentValues.put(column_alarmTur,alarmModel.getAlarmTur());
        contentValues.put(column_zorlukderecesi,alarmModel.getZorlukDerecesi());
        contentValues.put(column_zilSesiLocation,alarmModel.getZilSesiLocation());


        SQLiteDatabase db =getWritableDatabase();
        db.insert(table_product, null, contentValues);

        String query="SELECT * FROM "+table_product+ " ORDER BY "+column_id+" DESC";
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        int a = Integer.valueOf(c.getString(c.getColumnIndex(column_id)));
        return a;


    }

    public void DeleteProduct (int id)
    {
        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE FROM " + table_product + " WHERE " + column_id + " = " + id);

    }
    public List<AlarmModel> DatabasetoString()
    {
        List<AlarmModel> model =new ArrayList<AlarmModel>();

        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM "+table_product;

        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("alarmName"))!="")
            {
                try {

                    AlarmModel alarmModel = new AlarmModel(c.getString(c.getColumnIndex("alarmName")), c.getString(c.getColumnIndex("alarmSaat")), c.getString(c.getColumnIndex("alarmGunler")), c.getInt(c.getColumnIndex("alarmAktif")) > 0, c.getString(c.getColumnIndex("alarmSesi")), Integer.valueOf(c.getString(c.getColumnIndex(column_id))), Integer.valueOf(c.getString(c.getColumnIndex(column_alarmTur))), Integer.valueOf(c.getString(c.getColumnIndex(column_zorlukderecesi))), c.getString(c.getColumnIndex(column_zilSesiLocation)));
                    model.add(alarmModel);
                }
                catch (Exception e)
                {


                }
            }
            c.moveToNext();
        }
        db.close();
        return model;


    }

    public void DeleteAll()
    {
        SQLiteDatabase db =getWritableDatabase();
      onUpgrade(db,2,3);
       // db.execSQL("DELETE FROM " + table_product);

    }
    public void Update (AlarmModel alarmModel,int id)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_alarmname, alarmModel.getName());
        contentValues.put(column_alarmsaat,alarmModel.getSaat());
        contentValues.put(column_alarmSesi,alarmModel.getZilSesi());
        contentValues.put(column_alarmGunler,alarmModel.getGunler());
        contentValues.put(column_alarmAktif,alarmModel.getAktif());
        contentValues.put(column_zorlukderecesi,alarmModel.getZorlukDerecesi());
        contentValues.put(column_zilSesiLocation,alarmModel.getZilSesiLocation());

        SQLiteDatabase db =getWritableDatabase();
        try {
         int a=   db.update(table_product, contentValues, column_id + "=" + id, null);
            System.out.print(a);
        }
        catch (Exception ex)
        {
           System.out.print(ex.getMessage());
        }

    }
    public AlarmModel getModel(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM "+table_product + " WHERE "+column_id + " = " +id;
        AlarmModel model=new AlarmModel();

        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("alarmName"))!="")
            {
                try {

                    model = new AlarmModel(c.getString(c.getColumnIndex("alarmName")), c.getString(c.getColumnIndex("alarmSaat")), c.getString(c.getColumnIndex("alarmGunler")), true, c.getString(c.getColumnIndex("alarmSesi")), Integer.valueOf(c.getString(c.getColumnIndex(column_id))), Integer.valueOf(c.getString(c.getColumnIndex(column_alarmTur))), Integer.valueOf(c.getString(c.getColumnIndex(column_zorlukderecesi))),c.getString(c.getColumnIndex(column_zilSesiLocation)));
                    return model;
                }
                catch (Exception e)
                {
                    return new AlarmModel();


                }
            }
            c.moveToNext();
        }
        db.close();
        return model;

    }
}
