package org.example.ohtkoodia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

//varaus

public class Varaus {

    // Metodi, joka kirjoittaa varauksen tietokantaan
    public void kirjoitaVarausTietokantaan(Varaus varaus) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO varaus (asiakas_id, mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, varaus.getAsiakasId());
                preparedStatement.setInt(2, varaus.getMokkiId());
                preparedStatement.setDate(3, varaus.getVarattuPvm());
                preparedStatement.setDate(4, varaus.getVahvistusPvm());
                preparedStatement.setDate(5, varaus.getVarattuAlkupvm());
                preparedStatement.setDate(6, varaus.getVarattuLoppupvm());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Varaus tallennettu");
                } else {
                    System.out.println("Jokin meni pieleen");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodi, joka palauttaa tietokannasta kaikki varaukset sisältävän listan
    public static ObservableList<Varaus> haeVarauksetTietokannasta(int mokkiId) {
        ObservableList<Varaus> varaukset = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM varaus WHERE mokki_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, mokkiId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int varausId = resultSet.getInt("varaus_id");
                    int asiakasId = resultSet.getInt("asiakas_id");
                    Date varattuPvm = resultSet.getDate("varattu_pvm");
                    Date vahvistusPvm = resultSet.getDate("vahvistus_pvm");
                    Date varattuAlkupvm = resultSet.getDate("varattu_alkupvm");
                    Date varattuLoppupvm = resultSet.getDate("varattu_loppupvm");

                    Varaus varaus = new Varaus(varausId, asiakasId, mokkiId, varattuPvm, vahvistusPvm, varattuAlkupvm, varattuLoppupvm);
                    varaukset.add(varaus);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return varaukset;
    }



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
