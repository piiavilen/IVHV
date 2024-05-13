package org.example.ohtkoodia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Palvelut extends VJ{
    private String url = "jdbc:mysql://localhost:3307/vn";
    private String username = "pmauser";
    private String password = "password";

    // Metodi, joka hakee palvelut tietokannasta
    public ObservableList<Palvelu> haePalvelutTietokannasta() {
        ObservableList<Palvelu> palvelut = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT p.*, a.nimi AS alue_nimi FROM palvelu p JOIN alue a ON p.alue_id = a.alue_id";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int palveluId = resultSet.getInt("palvelu_id");
                    String nimi = resultSet.getString("nimi");
                    String kuvaus = resultSet.getString("kuvaus");
                    double hinta = resultSet.getDouble("hinta");
                    int alueId = resultSet.getInt("alue_id");
                    String alueNimi = resultSet.getString("alue_nimi");
                    Palvelu palvelu = new Palvelu(palveluId, nimi, kuvaus, hinta, alueId);
                    palvelut.add(palvelu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return palvelut;
    }

    // Metodi, joka lisää uuden palvelun tietokantaan
    public void lisaaPalveluTietokantaan(String nimi, String kuvaus, double hinta, int alueId) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO palvelu (nimi, kuvaus, hinta, alue_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nimi);
                preparedStatement.setString(2, kuvaus);
                preparedStatement.setDouble(3, hinta);
                preparedStatement.setInt(4, alueId);

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

    public class Palvelu {
        private int palveluId;
        private String nimi;
        private String kuvaus;
        private double hinta;
        private int alueId; // Viittaus alue-taulun primary keyhin

        // Konstruktori
        public Palvelu(int palveluId, String nimi, String kuvaus, double hinta, int alueId) {
            this.palveluId = palveluId;
            this.nimi = nimi;
            this.kuvaus = kuvaus;
            this.hinta = hinta;
            this.alueId = alueId;
        }

        // Getterit ja setterit
        public int getPalveluId() {
            return palveluId;
        }

        public void setPalveluId(int palveluId) {
            this.palveluId = palveluId;
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

        public int getAlueId() {
            return alueId;
        }

        public void setAlueId(int alueId) {
            this.alueId = alueId;
        }

        @Override
        public String toString() {
            return "Palvelu{" +
                    "palveluId=" + palveluId +
                    ", nimi='" + nimi + '\'' +
                    ", kuvaus='" + kuvaus + '\'' +
                    ", hinta=" + hinta +
                    ", alueId=" + alueId +
                    '}';
        }
    }

}
