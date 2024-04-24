package org.example.ohtkoodia;

//Tarvittava luokka, jotta taulukot toimivat
//Make imported this. Tarvitaan varmaan myöhemmin ObservableListin kanssa. (SQL-tietokantaan yhdistäessä)
import java.io.Serializable;

public class Mokki implements Serializable {

    //Mökin taulukkoon tarvittavat kentät, muutettavissa tarpeen mukaan.

    protected String nimi;
    protected int postinro;
    protected double hinta;
    protected int hlomaara;
    protected String varustelu;

    //Mökin parametrillinen alustaja

    public Mokki(String nimi, int postinro, double hinta, int hlomaara, String varustelu){
        this.setNimi(nimi);
        this.setPostinro(postinro);
        this.setHinta(hinta);
        this.setHlomaara(hlomaara);
        this.setVarustelu(varustelu);
    }

    //Generoidaan IntelliJ työkalulla getterit/setterit
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getPostinro() {
        return postinro;
    }

    public void setPostinro(int postinro) {
        this.postinro = postinro;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
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



}
