package org.example.ohtkoodia;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Varaus {
    private int varausId;
    private int asiakasId;
    private int mokkiId;
    private Date varattuPvm;
    private Date vahvistusPvm;
    private Date varattuAlkupvm;
    private Date varattuLoppupvm;

    // Constructor
    public Varaus(int varausId, int asiakasId, int mokkiId, Date varattuPvm, Date vahvistusPvm, Date varattuAlkupvm, Date varattuLoppupvm) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.mokkiId = mokkiId;
        this.varattuPvm = varattuPvm;
        this.vahvistusPvm = vahvistusPvm;
        this.varattuAlkupvm = varattuAlkupvm;
        this.varattuLoppupvm = varattuLoppupvm;
    }

    // Getter methods
    public int getVarausId() {
        return varausId;
    }

    public int getAsiakasId() {
        return asiakasId;
    }

    public int getMokkiId() {
        return mokkiId;
    }

    public Date getVarattuPvm() {
        return varattuPvm;
    }

    public Date getVahvistusPvm() {
        return vahvistusPvm;
    }

    public Date getVarattuAlkupvm() {
        return varattuAlkupvm;
    }

    public Date getVarattuLoppupvm() {
        return varattuLoppupvm;
    }

    // toString-metodi, jotta ei näy hash-koodina
    @Override
    public String toString() {
        // Format the dates as strings using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String varattuPvmStr = (varattuPvm != null) ? dateFormat.format(varattuPvm) : "";
        String vahvistusPvmStr = (vahvistusPvm != null) ? dateFormat.format(vahvistusPvm) : "";
        String varattuAlkupvmStr = (varattuAlkupvm != null) ? dateFormat.format(varattuAlkupvm) : "";
        String varattuLoppupvmStr = (varattuLoppupvm != null) ? dateFormat.format(varattuLoppupvm) : "";

        return "Varaus ID: " + varausId +
                ", Asiakas ID: " + asiakasId +
                ", Mökki ID: " + mokkiId +
                ", Varattu Pvm: " + varattuPvmStr +
                ", Vahvistus Pvm: " + vahvistusPvmStr +
                ", Varattu Alkupvm: " + varattuAlkupvmStr +
                ", Varattu Loppupvm: " + varattuLoppupvmStr;
    }
}
