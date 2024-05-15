package org.example.ohtkoodia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Palvelut extends VJ{
    private String url = "jdbc:mysql://localhost:3307/vn";
    private String username = "root";
    private String password = "";

    // Metodi, joka hakee palvelut tietokannasta
    public ObservableList<Palvelu> haePalvelutTietokannasta() {
        ObservableList<Palvelu> palvelut = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT p.nimi, p.kuvaus, p.hinta, p.alue_id FROM palvelu p";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    // int palveluId = resultSet.getInt("palNvelu_id");
                    String nimi = resultSet.getString("nimi");
                    String kuvaus = resultSet.getString("kuvaus");
                    double hinta = resultSet.getDouble("hinta");
                    int alue_id = resultSet.getInt("alue_id");
                    // String alueNimi = resultSet.getString("alue_nimi");
                    Palvelu palvelu = new Palvelu(nimi, kuvaus, hinta, alue_id);
                    palvelut.add(palvelu);
                }
            }
        } catch (SQLException e) {
            // Tulosta virheilmoitus konsoliin
            System.err.println("Tietokantavirhe: " + e.getMessage());
            // Tulosta virheen jäljitys
            e.printStackTrace();
        }

        return palvelut;
    }


    // Metodi, joka lisää uuden palvelun tietokantaan
    public void lisaaPalveluTietokantaan(String nimi, String kuvaus, double hinta, double alv, int alue_id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO palvelu (nimi, kuvaus, hinta, alv, alue_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nimi);
                preparedStatement.setString(2, kuvaus);
                preparedStatement.setDouble(3, hinta);
                preparedStatement.setDouble(4, alv);
                preparedStatement.setInt(5, alue_id);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Palvelu lisättiin onnistuneesti.");
                } else {
                    System.out.println("Palvelun lisääminen epäonnistui.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Palvelu {
        private int palveluId;
        private String nimi;
        private String kuvaus;
        private double hinta;
        private int alue_id; // Viittaus alue-taulun primary keyhin

        // Konstruktori
        public Palvelu(String nimi, String kuvaus, double hinta, int alue_id) {
            // this.palveluId = palveluId;
            this.nimi = nimi;
            this.kuvaus = kuvaus;
            this.hinta = hinta;
            this.alue_id = alue_id;
        }

        // Getterit ja setterit
        public int getPalveluId() {
            return palveluId;
        }

        public String getNimi() {
            return nimi;
        }

        public void setNimi(String nimi) {
            this.nimi = nimi;
        }

        public String getKuvaus() {
            return kuvaus;
        }

        public void setKuvaus(String kuvaus) {
            this.kuvaus = kuvaus;
        }

        public double getHinta() {
            return hinta;
        }

        public void setHinta(double hinta) {
            this.hinta = hinta;
        }

        public int getAlue_id() {
            return alue_id;
        }

        public void setAlue_id(int alue_id) {
            this.alue_id = alue_id;
        }

    }

}
