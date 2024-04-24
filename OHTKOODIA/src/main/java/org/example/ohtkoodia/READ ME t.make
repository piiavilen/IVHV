Oli puhetta ainakin miun, jannen ja aliisan kesken, että vain yks luokka mutta tämä ei ole mahdollista
Miun suunnitelma ois, että tehtäisiin SQL yhdistämiselle oma luokka myös, kun sen aika on.
Tekoäly sanoi, että siihen tarvis jotain rajapintaa käyttää

    1.Luo yhteys SQL-tietokantaan käyttäen JDBC (Java Database Connectivity) -rajapintaa.
    2. Suorita SQL-kysely tietokannasta ja lue tulokset.
    3.Muunna tulokset sopivaan muotoon (ObservableList tai vastaava).
    4. Aseta tulokset TableView-näkymän setItems()-metodilla.

SEURAAVA KOODI MYÖS PINNATTUNA DISCORDIN VKO3 KOHDALLA! AI:N ESIMERKKI
MITEN LUKEA JA NÄYTTÄÄ DATAA SQL-TIETOKANNASTA TABLEVIEW-KOMPONENTILLA
TÄMÄ MYÖS ILMEISESTI YHDISTÄÄ DATABASEN JAVAFX:ÄÄN:

----------------------------------------------------------------------------------------------
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.sql.*;

public class TietokantaSovellus extends Application {

    private static final String URL = "jdbc:mysql://localhost:3306/tietokannan_nimi";
    private static final String USER = "kayttajatunnus";
    private static final String PASSWORD = "salasana";

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        TableView<Object[]> tableView = new TableView<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM taulukon_nimi";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Luo sarakkeet
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                final int j = i - 1;
                TableColumn<Object[], Object> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData -> {
                    Object[] row = cellData.getValue();
                    return new SimpleObjectProperty<>(row[j]);
                });
                tableView.getColumns().add(column);
            }

            // Lisää rivit
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                tableView.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        pane.setCenter(tableView);

        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("TietokantaSovellus");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

----------------------------------------------------------------

TOINEN ESIMERKKI TIETOKANNAN JA TAULUKON YHDISTÄMISESTÄ. KOODI TARKOITETTU
KÄYTTÖLIITTYMÄÄN:

ObservableList<Mokki> mokit = FXCollections.observableArrayList();

// Hae mökkien tiedot tietokannasta ja lisää ne ObservableListiin
try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
    String query = "SELECT * FROM mokit";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
        String nimi = resultSet.getString("nimi");
        String sijainti = resultSet.getString("sijainti");
        double hinta = resultSet.getDouble("hinta");
        boolean vapaa = resultSet.getBoolean("vapaa");

        Mokki mokki = new Mokki(nimi, sijainti, hinta, vapaa);
        mokit.add(mokki);
    }
} catch (SQLException e) {
    e.printStackTrace();
}

// Aseta ObservableList TableView-näkymään
tableView.setItems(mokit);


