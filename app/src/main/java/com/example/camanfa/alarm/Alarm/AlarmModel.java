package com.example.camanfa.alarm.Alarm;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by CAMANFA on 5/24/2016.
 */
public class AlarmModel implements
        Serializable,Comparator
{
    String name;
    String saat;
    String gunler;
    Boolean aktif;
    Boolean editable;
    int id;
    Boolean update;
    int hour;
    int minute;
    int alarmTur;
    int zorlukDerecesi;
    String zorlukText;
    String oyunText;
    String zilSesiLocation;

    public String getZilSesiLocation() {
        return zilSesiLocation;
    }

    public void setZilSesiLocation(String zilSesiLocation) {
        this.zilSesiLocation = zilSesiLocation;
    }

    public String getOyunText() {
        return oyunText;
    }

    public void setOyunText(String oyunText) {
        this.oyunText = oyunText;
    }

    public String getZorlukText() {
        return zorlukText;
    }

    public void setZorlukText(String zorlukText) {
        this.zorlukText = zorlukText;
    }

    public int getZorlukDerecesi() {
        return zorlukDerecesi;
    }

    public void setZorlukDerecesi(int zorlukDerecesi) {
        this.zorlukDerecesi = zorlukDerecesi;
        ZorlukDeresitoText(zorlukDerecesi);
    }
    void ZorlukDeresitoText(int zorlukDerecesi)
    {
        switch (zorlukDerecesi)
        {
            case 1:
                zorlukText="Kolay";
                break;
            case 2:
                zorlukText="Orta";
                break;
            case 3:
                zorlukText="Zor";
                break;

        }
    }

    public int getAlarmTur() {
        return alarmTur;
    }

    public void setAlarmTur(int alarmTur) {
        this.alarmTur = alarmTur;
        AlarmturtoOyunText(alarmTur);
    }

    void AlarmturtoOyunText(int alarmTur)
    {
        switch (alarmTur)
        {
            case 1:
                oyunText="Dört İşlem";
                break;
            case 2:
                oyunText="Sayı Sıralama";
                break;
            case 3:
                oyunText="Puzzle";
                break;
            case 4:
                oyunText="Telefon Sallama";
                break;

        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getHour() {
        String [] saat =getSaat().split(":");
        return Integer.valueOf(saat[0]);
    }

    public void setHour(int hour) {

        this.hour = hour;
    }

    public int getMinute() {
        String [] saat =getSaat().split(":");
        return Integer.valueOf(saat[1]);

    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlarmModel(String name,String saat, String gunler, Boolean aktif, String zilSesi,int id,int alarmTur,int zorlukDerecesi,String zilSesiLocation) {
        this.saat = saat;
        this.gunler = gunler;
        this.aktif = aktif;
        this.zilSesi = zilSesi;
        this.name=name;
        this.editable=false;
        this.id=id;
        update=false;
        this.alarmTur=alarmTur;
        AlarmturtoOyunText(alarmTur);
        this.zorlukDerecesi=zorlukDerecesi;
        ZorlukDeresitoText(zorlukDerecesi);
        this.zilSesiLocation=zilSesiLocation;

    }

    public AlarmModel() {
    }

    String zilSesi;

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getGunler() {
        return gunler;
    }

    public void setGunler(String gunler) {
        this.gunler = gunler;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }

    public String getZilSesi() {
        return zilSesi;
    }

    public void setZilSesi(String zilSesi) {
        this.zilSesi = zilSesi;
    }

    @Override
    public int compare(Object lhs, Object rhs) {
        AlarmModel alarmModel1= (AlarmModel)lhs;
        AlarmModel alarmModel2= (AlarmModel)rhs;
        return alarmModel1.saat.compareTo(alarmModel2.saat);
    }
    public static Comparator<AlarmModel> StuNameComparator = new Comparator<AlarmModel>() {
        @Override
        public int compare(AlarmModel lhs, AlarmModel rhs) {
            AlarmModel alarmModel1 = (AlarmModel) lhs;
            AlarmModel alarmModel2 = (AlarmModel) rhs;
            return alarmModel1.saat.compareTo(alarmModel2.saat);
        }

    };

}
