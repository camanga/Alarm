package com.example.camanfa.alarm;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by CAMANFA on 5/13/2016.
 */
public class GunlerModel implements Serializable {
    String gun;
    int deger;
    boolean secili=false;
    Map<String,Integer> seciligunler= new HashMap<String,Integer>();

    public GunlerModel(String gun, int deger) {
        this.gun = gun;
        this.deger = deger;
    }

    public GunlerModel() {
    }

    public String getGun() {

        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public int getDeger() {
        return deger;
    }

    public boolean isSecili() {
        return secili;
    }

    public void setSecili(boolean secili) {
        this.secili = secili;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }

    public void gunEkle(int deger)
    {
        switch (deger)
        {
            case 0:
               seciligunler.put("Pzt",0);
                break;
            case 1:
                seciligunler.put("Sal",1);
                break;
            case 2:
                seciligunler.put("Car",2);
                break;
            case 3:
                seciligunler.put("Per",3);
                break;
            case 4:
                seciligunler.put("Cum",4);
                break;
            case 5:
                seciligunler.put("Cmt",5);
                break;
            case 6:
                seciligunler.put("Pzr",6);
                break;
        }




    }
    public void gunCikar(int deger)
    {
        if(seciligunler.containsValue(deger))
        seciligunler.remove(deger);
    }

    public Map<String, Integer> getSeciligunler() {
        return seciligunler;
    }

    public void setSeciligunler(Map<String, Integer> seciligunler) {
        this.seciligunler = seciligunler;
    }
    public String Duzenle()
    {
        String sonuc="";
        if(seciligunler.size()==0)
        {
            return "Asla";
        }

        if(seciligunler.size()==7)
        {
           sonuc="Her gün";

        }
        else if(seciligunler.size()==2&&seciligunler.containsValue(5)&&seciligunler.containsValue(6))
        {
            sonuc="Hafta sonu";
        }
        else if(seciligunler.size()==5&&!seciligunler.containsValue(5)&&!seciligunler.containsValue(6))
        {
            sonuc="Hafta içi";
        }
        else if(seciligunler.size()==1&&seciligunler.containsValue(0))
        {
            sonuc="Sadece Pazartesi";
        }
        else if(seciligunler.size()==1&&seciligunler.containsValue(1))
        {
            sonuc="Sadece Salı";
        }

        else if(seciligunler.size()==1&&seciligunler.containsValue(2))
        {
            sonuc="Sadece Çarşamba";
        }

        else if(seciligunler.size()==1&&seciligunler.containsValue(3))
        {
            sonuc="Sadece Perşembe";
        }

        else if(seciligunler.size()==1&&seciligunler.containsValue(4))
        {
            sonuc="Sadece Cuma";
        }

        else if(seciligunler.size()==1&&seciligunler.containsValue(5))
        {
            sonuc="Sadece Cumartesi";
        }

        else if(seciligunler.size()==1&&seciligunler.containsValue(6))
        {
            sonuc="Sadece Pazar";
        }
        else
        {

            List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(seciligunler.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1,
                                   Map.Entry<String, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });

            // Maintaining insertion order with the help of LinkedList
            Map<String, Integer> sortedMap = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> entry : list)
            {

                sonuc  +=entry.getKey()+",";


            }
          sonuc=  sonuc.substring(0,sonuc.length()-1);
        }



        return sonuc;

    }

    String [] KesinGunler ={"Her Pazartesi","Her Salı","Her Çarşamba","Her Perşembe","Her Cuma","Her Cumartesi","Her Pazar"};

}
