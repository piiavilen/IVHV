package org.example.ohtkoodia;
import java.sql.*;
public class TietokannanYhdistaminen {
    public static void main(String[] args) {
        //Make: en ole varma onkohan localhost nyt 3306 vai 3307
        //Janne: 3306 antaa errorin java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: NO)
        //Janne: 3307 java.sql.SQLSyntaxErrorException: Unknown database 'vn'
        String url = "jdbc:mysql://localhost:3306/vn";

        String username = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from mokki");

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

