package org.example.ohtkoodia;

import java.io.Serializable;

//Tarvittava luokka, jotta taulukot toimivat
public class Laskut implements Serializable {
    protected int laskuID;
    protected int varausID;

    protected double summa;
    protected double alv;
    protected int maksettu;

    public Laskut(int laskuID, int varausID, double summa, double alv, int maksettu){
        this.setLaskuID(laskuID);
        this.setVarausID(varausID);
        this.setSumma(summa);
        this.setAlv(alv);
        this.setMaksettu(maksettu);
    }


    public int getLaskuID() {
        return laskuID;
    }

    public void setLaskuID(int laskuID) {
        this.laskuID = laskuID;
    }

    public int getVarausID() {
        return varausID;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public double getAlv() {
        return alv;
    }

    public void setAlv(double alv) {
        this.alv = alv;
    }

    public int getMaksettu() {
        return maksettu;
    }

    public void setMaksettu(int maksettu) {
        this.maksettu = maksettu;
    }
}
