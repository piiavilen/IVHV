package org.example.ohtkoodia;

//Tarvittava luokka, jotta taulukot toimivat
//Make imported this. Tarvitaan varmaan myöhemmin ObservableListin kanssa. (SQL-tietokantaan yhdistäessä)
import java.io.Serializable;

public class Mokki implements Serializable {

    //Mökin taulukkoon tarvittavat kentät, muutettavissa tarpeen mukaan.

    protected String nimi;
    protected String osoite;
    protected String postinro;
    protected double hinta;
    protected String kuvaus;
    protected int hlomaara;
    protected String varustelu;
    protected int mokkiId;

    //Mökin parametrillinen alustaja
    public Mokki(int mokkiId, String nimi, String osoite, String postinro, double hinta, String kuvaus, int hlomaara, String varustelu) {
        this.mokkiId = mokkiId;
        this.nimi = nimi;
        this.osoite = osoite;
        this.postinro = postinro;
        this.hinta = hinta;
        this.kuvaus = kuvaus;
        this.hlomaara = hlomaara;
        this.varustelu = varustelu;
    }

    //Generoidaan IntelliJ työkalulla getterit/setterit


    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getOsoite() {
        return osoite;
    }

    public void setOsoite(String osoite) {
        this.osoite = osoite;
    }

    public String getPostinro() {
        return postinro;
    }

    public void setPostinro(String postinro) {
        this.postinro = postinro;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public int getHlomaara() {
        return hlomaara;
    }

    public void setHlomaara(int hlomaara) {
        this.hlomaara = hlomaara;
    }

    public String getVarustelu() {
        return varustelu;
    }

    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    public int getMokkiId() {
        return this.mokkiId;
    }

    public void setMokkiId(int mokkiId) {
        this.mokkiId = mokkiId;
    }

    // Lisätään toString-metodi, jotta mökkien nimet näkyvät oikein Varaus-tabissa
    @Override
    public String toString() {
        return nimi;
    }

}
