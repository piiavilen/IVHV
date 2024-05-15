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
    public static ObservableList<ObservableList<String>> haeVarauksetTietokannasta(int mokkiId) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, " +
                    "asiakas.email, varaus.varattu_pvm, varaus.vahvistus_pvm, varaus.varattu_alkupvm," +
                    "varaus.varattu_loppupvm " +
                    "FROM varaus " +
                    "INNER JOIN asiakas ON varaus.asiakas_id = asiakas.asiakas_id " +
                    "WHERE varaus.mokki_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, mokkiId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columns = metaData.getColumnCount();

                    ObservableList<String> columnNames = FXCollections.observableArrayList();

                    // Muokkaa columnit luettevampaan muotoon
                    for (int i = 1; i <= columns; i++) {
                        String columnName = metaData.getColumnName(i);

                        switch (columnName) {
                            case "etunimi":
                                columnName = "Etunimi";
                                break;
                            case "sukunimi":
                                columnName = "Sukunimi";
                                break;
                            case "puhelinnro":
                                columnName = "Puhelinnumero";
                                break;
                            case "email":
                                columnName = "E-mail";
                                break;
                            case "varattu_pvm":
                                columnName = "Varattu";
                                break;
                            case "vahvistus_pvm":
                                columnName = "Vahvistettu";
                                break;
                            case "varattu_alkupvm":
                                columnName = "Varattu alkaen";
                                break;
                            case "varattu_loppupvm":
                                columnName = "Varaus päättyy";
                                break;
                        }
                        columnNames.add(columnName);
                    }
                    data.add(columnNames);

                    // Lisää tiedot tietokannasta
                    while (resultSet.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        row.add(resultSet.getString("etunimi"));
                        row.add(resultSet.getString("sukunimi"));
                        row.add(resultSet.getString("puhelinnro"));
                        row.add(resultSet.getString("email"));
                        row.add(resultSet.getString("varattu_pvm"));
                        row.add(resultSet.getString("vahvistus_pvm"));
                        row.add(resultSet.getString("varattu_alkupvm"));
                        row.add(resultSet.getString("varattu_loppupvm"));
                        data.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private int varausId;
    private int asiakasId;
    private int mokkiId;
    private Date varattuPvm;
    private Date vahvistusPvm;
    private Date varattuAlkupvm;
    private Date varattuLoppupvm;

    // Constructor
    public Varaus(int asiakasId, int mokkiId, Date varattuPvm, Date vahvistusPvm, Date varattuAlkupvm, Date varattuLoppupvm) {
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
