package org.example.ohtkoodia;
import java.sql.*;
public class TietokannanYhdistaminen {
    public static void main(String[] args) {
        try {
            // Yhdistetään tietokantaan
            String url = "jdbc:mysql://localhost:3306/vn";
            String username = "kayttaja";
            String password = "salasana";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Suoritetaan SQL-kysely
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM alue");

            // Käsitellään tulokset
            while (resultSet.next()) {
                String data = resultSet.getString("nimi");
                System.out.println(data);
            }

            // Suljetaan yhteys
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

