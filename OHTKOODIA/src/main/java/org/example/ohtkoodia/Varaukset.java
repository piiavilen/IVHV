package org.example.ohtkoodia;

import java.io.Serializable;

//Tarvittava luokka, jotta taulukot toimivat
public class Varaukset implements Serializable {
    protected int varausID;
    protected String lahetysStat;
    protected String maksuStat;

    public Varaukset(int varausID, String lahetysStat, String maksuStat){
        this.setVarausID(varausID);
        this.setLahetysStat(lahetysStat);
        this.setMaksuStat(maksuStat);
    }
    //IntelliJ generoimat getterit ja setterit
    public int getVarausID() {
        return varausID;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public String getLahetysStat() {
        return lahetysStat;
    }

    public void setLahetysStat(String lahetysStat) {
        this.lahetysStat = lahetysStat;
    }

    public String getMaksuStat() {
        return maksuStat;
    }

    public void setMaksuStat(String maksuStat) {
        this.maksuStat = maksuStat;
    }

    public static void TallennaLasku (int varausID, String lahetysStat, String maksuStat){
        Varaukset uusiVaraus = new Varaukset(varausID,lahetysStat, maksuStat);
        VJ.laskudata.add(uusiVaraus);
        VJ.laskulistaObservable.add(uusiVaraus);
        LaskutListanHallinta.TallennaLaskuTiedostoon(VJ.laskudata);

    }

}
