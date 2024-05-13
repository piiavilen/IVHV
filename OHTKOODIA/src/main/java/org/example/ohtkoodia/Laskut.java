package org.example.ohtkoodia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.sql.*;

//Tarvittava luokka, jotta taulukot toimivat
public class Laskut implements Serializable {

    // Metodi, joka palauttaa tietokannasta listan kaikista laskuista
    public static ObservableList<Laskut> haeLaskutTietokannasta() {
        ObservableList<Laskut> laskuTiedot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM lasku";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet1 = statement.executeQuery(query);
                while (resultSet1.next()) {
                    Laskut lasku = new Laskut(resultSet1.getInt("lasku_id"),
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
    protected int laskuID;
    protected int varausID;

    protected double summa;
    protected double alv;
    protected int maksettu;

    public Laskut(int laskuID, int varausID, double summa, double alv, int maksettu){
        this.setLaskuID(laskuID);
        this.setVarausID(varausID);
        this.setSumma(summa);
        this.setAlv(alv);
        this.setMaksettu(maksettu);
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
