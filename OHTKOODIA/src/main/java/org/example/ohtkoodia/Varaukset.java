package org.example.ohtkoodia;
//Tarvittava luokka, jotta taulukot toimivat
public class Varaukset {
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

}
