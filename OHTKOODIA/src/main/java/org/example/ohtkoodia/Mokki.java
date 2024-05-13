package org.example.ohtkoodia;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Mokki extends VJ {

    // Metodi, joka syöttää mökin tiedot mokki-tauluun
    public static void kirjoitaMokkiTietokantaan(int alue_id, String postiNro, String mokkinimi, String katuosoite, double hinta, String kuvaus,
                                                 int henkilo, String varustelu) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, alue_id);
                preparedStatement.setString(2, postiNro);
                preparedStatement.setString(3, mokkinimi);
                preparedStatement.setString(4, katuosoite);
                preparedStatement.setDouble(5, hinta);
                preparedStatement.setString(6, kuvaus);
                preparedStatement.setInt(7, henkilo);
                preparedStatement.setString(8, varustelu);


                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Tiedot tallennetuivat");
                } else {
                    System.out.println("Jokin meni pieleen");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodi, joka palauttaa mökkien tiedot sisältävän listan tietokannasta
    public static ObservableList<Mokki> haeMokitTietokannasta() {
        ObservableList<Mokki> mokkiTiedot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM mokki";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Mokki mokki = new Mokki(resultSet.getInt("mokki_id"),
                            resultSet.getString("mokkinimi"),
                            resultSet.getString("katuosoite"),
                            resultSet.getString("postinro"),
                            resultSet.getDouble("hinta"),
                            resultSet.getString("kuvaus"),
                            resultSet.getInt("henkilomaara"),
                            resultSet.getString("varustelu"));
                    mokkiTiedot.add(mokki);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mokkiTiedot;
    }


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
