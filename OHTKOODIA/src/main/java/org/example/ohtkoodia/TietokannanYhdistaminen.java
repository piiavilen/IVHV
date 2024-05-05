package org.example.ohtkoodia;
import java.sql.*;
public class TietokannanYhdistaminen {
    public static void main(String[] args) {
        //localhost:3307 on se, mihin porttiin teidän pitää liittää se XAMPP
        //käyttis ja salasana on ne, joista make laittoi kuvakaappauksen discoon.
        //tehkää sama käyttis niin tää toimii kaikilla
        String url = "jdbc:mysql://localhost:3307/vn";

        String username = "pmauser";
        String password = "password";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from alue");

            //datatyyppi + kolumni-indeksi haettavassa taulukossa
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1)+resultSet.getString(2));
            }

            connection.close();

        } catch (Exception e){
            System.out.println(e);
        }
    }

        /*Make: Jätin tähän vielä piian tekemän koodin varmuuden vuoksi!

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
    }*/
}

