package org.example.ohtkoodia;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Haku {

    // Metodi hakee tietokannasta mökit annetun hakusanan perusteella
    public ObservableList<Mokki> haeMokitTietokannasta(String hakusana) {
        ObservableList<Mokki> mokkiTiedot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn"; // Tietokannan URL
        String username = "root"; // Tietokannan käyttäjänimi
        String password = ""; // Tietokannan salasana

        try (Connection connection = DriverManager.getConnection(url, username, password)) { // Yritetään luoda yhteys tietokantaan
            // SQL-kysely mökkien hakemiseksi annetun hakusanan perusteella
            String hakuQuery = "SELECT * FROM mokki WHERE mokkinimi LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(hakuQuery)) { // Valmistellaan SQL-lauseke
                preparedStatement.setString(1, "%" + hakusana + "%"); // Asetetaan hakusana parametrina ja % merkit osoittamaan, että sana voi esiintyä missä tahansa kohdassa mökin nimeä
                try (ResultSet resultSet = preparedStatement.executeQuery()) { // Suoritetaan kysely
                    // Käsitellään kyselyn tulos
                    while (resultSet.next()) {
                        // Luodaan uusi Mokki-olio tietokannasta saaduilla tiedoilla ja lisätään se ObservableListiin
                        Mokki mokki = new Mokki(resultSet.getInt("mokki_id"),
                                resultSet.getString("mokkinimi"),
                                resultSet.getString("katuosoite"),
                                resultSet.getString("postinro"),
                                resultSet.getDouble("hinta"),
                                resultSet.getString("kuvaus"),
                                resultSet.getInt("henkilomaara"),
                                resultSet.getString("varustelu")
                        );
                        mokkiTiedot.add(mokki); // Lisätään mökki ObservableListiin
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Jos tapahtuu virhe, tulostetaan virheilmoitus
        }
        return mokkiTiedot; // Palautetaan mökit ObservableListissä
    }
}
