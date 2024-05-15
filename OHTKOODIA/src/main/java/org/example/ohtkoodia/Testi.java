package org.example.ohtkoodia;

import javafx.collections.ObservableList;

public class Testi {

    public static void main(String[] args) {
        // Luo Palvelut-olio
        Palvelut palvelut = new Palvelut();

        // Tallenna uusi palvelu tietokantaan
        String uusiPalveluNimi = "Testipalvelu";
        String uusiPalveluKuvaus = "Testikuvaus";
        double uusiPalveluHinta = 50.0;
        double uusiPalveluAlv = 0.24; // Esimerkkialv
        int uusiPalveluAlueID = 1; // Tässä oletetaan, että alue_id on 1

        System.out.println("Lisätään uusi palvelu...");
        palvelut.lisaaPalveluTietokantaan(uusiPalveluNimi, uusiPalveluKuvaus, uusiPalveluHinta, uusiPalveluAlv, uusiPalveluAlueID);


        // Hae palvelut tietokannasta
        ObservableList<Palvelut.Palvelu> haetutPalvelut = palvelut.haePalvelutTietokannasta();

        // Tarkista, onko uusi palvelu lisätty
        boolean lisatty = false;
        for (Palvelut.Palvelu palvelu : haetutPalvelut) {
            if (palvelu.getNimi().equals(uusiPalveluNimi)) {
                lisatty = true;
                break;
            }
        }

        if (lisatty) {
            System.out.println("Uusi palvelu lisättiin onnistuneesti.");
        } else {
            System.out.println("Uuden palvelun lisääminen epäonnistui.");
        }
    }
}
