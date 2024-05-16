package org.example.ohtkoodia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

//Tarvittava luokka, jotta taulukot toimivat
public class Lasku extends VJ {

    protected int laskuID;
    protected int varausID;
    protected double summa;
    protected double alv;
    protected int maksettu;

    public Lasku(int laskuID, int varausID, double summa, double alv, int maksettu){
        this.setLaskuID(laskuID);
        this.setVarausID(varausID);
        this.setSumma(summa);
        this.setAlv(alv);
        this.setMaksettu(maksettu);
    }

    public static void kirjoitaLaskuTietokantaan(int lasku_id, int varaus_id, double summa, double alv, int maksettu) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO lasku (lasku_id, varaus_id, summa, alv, maksettu) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, lasku_id);
                preparedStatement.setInt(2, varaus_id);
                preparedStatement.setDouble(3, summa);
                preparedStatement.setDouble(4, alv);
                preparedStatement.setInt(5, maksettu);

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

    // Metodi, joka palauttaa tietokannasta listan kaikista laskuista
    public static ObservableList<Lasku> haeLaskutTietokannasta() {
        ObservableList<Lasku> laskuTiedot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM lasku";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet1 = statement.executeQuery(query);
                while (resultSet1.next()) {
                    Lasku lasku = new Lasku(
                            resultSet1.getInt("lasku_id"),
                            resultSet1.getInt("varaus_id"),
                            resultSet1.getDouble("summa"),
                            resultSet1.getDouble("alv"),
                            resultSet1.getInt("maksettu"));
                    laskuTiedot.add(lasku);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return laskuTiedot;
    }

    public static int haeSeuraavaLaskuId() {
        int seuraavaLaskuId = 0;
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "root";
        String password = "";


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT MAX(lasku_id) AS max_id FROM lasku";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int maxId = resultSet.getInt("max_id");
                seuraavaLaskuId = maxId + 1;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seuraavaLaskuId;
    }

    public int getLaskuID() {
        return laskuID;
    }

    public void setLaskuID(int laskuID) {
        this.laskuID = laskuID;
    }

    public int getVarausID() {
        return varausID;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public double getAlv() {
        return alv;
    }

    public void setAlv(double alv) {
        this.alv = alv;
    }

    public int getMaksettu() {
        return maksettu;
    }

    public void setMaksettu(int maksettu) {
        this.maksettu = maksettu;
    }
}



