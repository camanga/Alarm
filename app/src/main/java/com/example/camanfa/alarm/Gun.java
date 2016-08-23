package com.example.camanfa.alarm;

/**
 * Created by CAMANFA on 5/25/2016.
 */
public class Gun {
    String gun;
    boolean secili;
    int deger;

    public int getDeger() {
        return deger;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public boolean isSecili() {
        return secili;
    }

    public void setSecili(boolean secili) {
        this.secili = secili;
    }

    public Gun(String gun, boolean secili,int deger) {
        this.gun = gun;
        this.secili = secili;
        this.deger=deger;
    }
}
