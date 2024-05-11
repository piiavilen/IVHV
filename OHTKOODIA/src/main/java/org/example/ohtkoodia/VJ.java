package org.example.ohtkoodia;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class VJ extends Application{

    //----------------------------METODIT------------------------------------------------------------------------------

    //Source: https://stackoverflow.com/questions/59147960/how-to-insert-into-mysql-database-with-java
    //https://stackoverflow.com/questions/5005388/cannot-add-or-update-a-child-row-a-foreign-key-constraint-fails
    //https://stackoverflow.com/questions/59956372/sql-server-foreign-key-constraint-error-but-data-exists
    public void kirjoitaMokkiTietokantaan(int alue_id, String postiNro, String mokkinimi, String katuosoite, double hinta, String kuvaus,
                                          int henkilo, String varustelu) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, alue_id);
                preparedStatement.setString(2, postiNro);
                preparedStatement.setString(3, mokkinimi);
                preparedStatement.setString(4, katuosoite);
                preparedStatement.setDouble(5, hinta);
                preparedStatement.setString(6, kuvaus);
                preparedStatement.setInt(7, henkilo);
                preparedStatement.setString(8, varustelu);


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

    public void kirjoitaAlueTietokantaan(String nimi) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO alue (nimi) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nimi);

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

    public int haeAlueId(String alueNimi) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        int alueId = -1;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT alue_id FROM alue WHERE nimi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, alueNimi);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        alueId = resultSet.getInt("alue_id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return alueId;
    }

    public ObservableList<String> haeAlueetTietokannasta() {
        ObservableList<String> alueNimet = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT nimi FROM alue";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String alueNimi = resultSet.getString("nimi");
                    alueNimet.add(alueNimi);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alueNimet;
    }

    public void kirjoitaAsiakasTietokantaan(String postiNro, String etuNimi, String sukuNimi, String lahiosoite,
                                            String email, String puhelinNro) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO asiakas (postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, postiNro);
                preparedStatement.setString(2, etuNimi);
                preparedStatement.setString(3, sukuNimi);
                preparedStatement.setString(4, lahiosoite);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, puhelinNro);

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

    public ObservableList<Mokki> haeMokitTietokannasta() {
        ObservableList<Mokki> mokkiTiedot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM mokki";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Mokki mokki = new Mokki(resultSet.getString("mokkinimi"),
                            resultSet.getString("katuosoite"),
                            resultSet.getString("postinro"),
                            resultSet.getDouble("hinta"),
                            resultSet.getString("kuvaus"),
                            resultSet.getInt("henkilomaara"),
                            resultSet.getString("varustelu"));
                    mokkiTiedot.add(mokki);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mokkiTiedot;

    }



    //--------------------------------------UI-------------------------------------------------------------------------
    public void start(Stage primaryStage) throws Exception {

        //Make kysyy: Mitä paneeleja? mitä näihin lisätään? Luodaanko tähän paneeli myös taulukolle?
        //Aliisa vastaa: Nää on ne "uloimmat" paneelit missä on ne tabit, taulukot tulee varausTab borderpaneen
        BorderPane paneeli = new BorderPane();
        TabPane tabit = new TabPane();

        //-----------------------------VARAUSTAB-------------------------------------
        Tab varausTab = new Tab("Varaukset");
        BorderPane varausTabPaneeli = new BorderPane();
        varausTab.setContent(varausTabPaneeli);
        //Make kysyy: Mille tämä on paneeli? voisi nopean kommentin lisätä ken luonut
        //Aliisa vastaa: Tämä paneeli on sitä varten, että voi lisätä ne suodatinelementit kaikki kätevästi peräjälkeen tohon ylös
        HBox ylaPaneeli = new HBox();
        varausTabPaneeli.setTop(ylaPaneeli);
        TextField hakuKentta = new TextField();
        hakuKentta.setPromptText("Mökin nimi");
        Button hakuBt = new Button("Hae");
        // SLIDERIIN ASETETAAN MIN JA MAX PARAMETRIT JOTKA HAETAAN TIETOKANNASTA: MÖKKIEN PIENIN HINTA JA ISOIN HINTA
        Label hintaSliderLbl = new Label("Hinta: ");
        Slider hintaSlider = new Slider(0, 100, 50);
        Label hintaArvoLbl = new Label(hintaSlider.getValue() + "€");

        hintaSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            hintaArvoLbl.setText(String.format("%.2f €", newValue.doubleValue()));
        }));

        //-----------------------------MÖKKITAULUKKO-------------------------------------
        // Luo TableView
        TableView<Mokki> mokkiTableView = new TableView<>();
        // Luo ObservableList
        ObservableList<Mokki> mokki = FXCollections.observableArrayList();
        // Luo sarakkeet
        TableColumn<Mokki, String> nimiMOKKIColumn = new TableColumn<>("Nimi");
        nimiMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("nimi"));

        TableColumn<Mokki, String> osoiteMOKKIColumn = new TableColumn<>("osoite");
        osoiteMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("osoite"));

        TableColumn<Mokki, Integer> postinroMOKKIColumn = new TableColumn<>("Postinumero");
        postinroMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("postinumero"));

        TableColumn<Mokki, Double> hintaMOKKIColumn = new TableColumn<>("Hinta");
        hintaMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("hinta"));

        TableColumn<Mokki, Double> kuvausMOKKIColumn = new TableColumn<>("Kuvaus");
        kuvausMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));

        TableColumn<Mokki, Integer> hlomaaraMOKKIColumn = new TableColumn<>("Henkilömäärä");
        hlomaaraMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("henkilomaara"));

        TableColumn<Mokki, String> varusteluMOKKIColumn = new TableColumn<>("Varustelu");
        varusteluMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("varustelu"));

        mokki.addAll(haeMokitTietokannasta());

        mokkiTableView.setItems(mokki);

        //TÄHÄN TULISI VARAUS-PAINIKKEEN LUONTI JA TOIMINNALLISUUS, PALATAAN TÄHÄN MYÖHEMMIN!!!
        /*Marjutilla osa tätä koodia ja sen toiminnallisuutta, katsotaan backend-vaiheessa
        tarkemmin!!

        TableColumn<Mokki, Void> varausbtnColumn = new TableColumn<>("Tee varaus");
        varausbtnColumn.setCellValueFactory(column -> new TableCell<>()
            Button teeVarausbtn = new Button("Tee varaus");
        */

        // Lisää sarakkeet taulukkoon
        mokkiTableView.getColumns().addAll(nimiMOKKIColumn, osoiteMOKKIColumn, postinroMOKKIColumn, hintaMOKKIColumn,
                kuvausMOKKIColumn, hlomaaraMOKKIColumn, varusteluMOKKIColumn);//Muistetaan lisätä varausbtn

        // Lisää taulukko BorderPaneen
        varausTabPaneeli.setCenter(mokkiTableView);

        // TÄSSÄ HUOMIO, muissa kentissä on täällä textfield mutta kuvaukseen käytetään TextArea oliota
        // Alustavasti mahdollista syöttää pelkkiä mökkejä, koitetaan saada ensin tämä toimimaan ja sitten vasta palvelut
        Button mokkiTiedonSyottoBt = new Button("Lisää uusi mökki");
        mokkiTiedonSyottoBt.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Lisää uusi mökki");

            /* HUOMIO TÄSSÄ, postinro ja alue on viittauksia niihin toisiin tauluihin (refer to ER-kaavio) niiiin en tiedä oikein
            et mitä tähän. Pitäiskö se olla sit silleen, että alueen syöttö kohta johonkin ja sitä kautta tähän textfieldin
            tilalle dropdown menu josta valitaan se alue ja se asettaa postinumeron sit sen mukaan automaattisesti ???*/
            VBox mtsPaneeli = new VBox();
            mtsPaneeli.setSpacing(5);

            Label nimiLbl = new Label("Mökin nimi:");
            TextField nimiTf = new TextField();

            Label alueLbl = new Label("Alue:");
            ComboBox<String> alueCB = new ComboBox<>();
            alueCB.setItems(haeAlueetTietokannasta());

            Label osoiteLbl = new Label("Osoite:");
            TextField osoiteTf = new TextField();

            Label postiNroLbl = new Label("Postinumero");
            TextField postiNroTf = new TextField();

            Label hintaLbl = new Label("Hinta:");
            TextField hintaTf = new TextField();

            Label kuvausLbl = new Label("Mökin kuvaus:");
            TextArea kuvausTa = new TextArea();

            Label henkiloLbl = new Label("Henkilömäärä:");
            TextField henkiloTf = new TextField();

            Label varusteluLbl = new Label("Varustelu:");
            TextField varusteluTf = new TextField();

            Button tallennaMokkiBt = new Button("Tallenna tiedot");

            tallennaMokkiBt.setOnAction(p -> {
                String alueNimi = alueCB.getValue();
                int alueId = haeAlueId(alueNimi);
                String postiNro = postiNroTf.getText();
                String mokkinimi = nimiTf.getText();
                String katuosoite = osoiteTf.getText();
                double hinta = Double.parseDouble(hintaTf.getText());
                String kuvaus = kuvausTa.getText();
                int henkilo = Integer.parseInt(henkiloTf.getText());
                String varustelu = varusteluTf.getText();
                kirjoitaMokkiTietokantaan(alueId, postiNro, mokkinimi, katuosoite, hinta, kuvaus, henkilo, varustelu);
            });

            mtsPaneeli.getChildren().addAll(nimiLbl, nimiTf, alueLbl, alueCB, osoiteLbl, osoiteTf, postiNroLbl, postiNroTf,
                    hintaLbl, hintaTf, kuvausLbl, kuvausTa, henkiloLbl, henkiloTf, varusteluLbl, varusteluTf,
                    tallennaMokkiBt);

            Scene scene = new Scene(mtsPaneeli, 400, 500);
            stage.setScene(scene);
            stage.show();
        });

        // Nappi, joka avaa ikkunan, josta alueita voidaan syöttää tietokantaan
        Button alueTiedonSyottoBt = new Button("Lisää uusi alue");
        alueTiedonSyottoBt.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Lisää uusi alue");
            VBox atsPaneeli = new VBox();
            atsPaneeli.setSpacing(5);

            Label alueNimiLbl = new Label("Alueen nimi:");
            TextField alueNimiTf = new TextField();
            Button tallennaAlueBt = new Button("Tallenna tiedot");

            tallennaAlueBt.setOnAction(p -> {
                String alueNimi = alueNimiTf.getText();
                kirjoitaAlueTietokantaan(alueNimi);
            });

            atsPaneeli.getChildren().addAll(alueNimiLbl, alueNimiTf, tallennaAlueBt);

            Scene scene = new Scene(atsPaneeli, 400, 400);
            stage.setScene(scene);
            stage.show();
                });


        ylaPaneeli.getChildren().addAll(hakuKentta, hintaSliderLbl, hintaSlider, hintaArvoLbl, hakuBt, mokkiTiedonSyottoBt, alueTiedonSyottoBt);

        /*MAKEN NOTE! Koodi ei aja, jos alla oleva rivi käytössä? Mikä mahtaa olla syynä? Tämän takia laitoin sen kommentteihin.
        Pitää ehkä luoda oma paneeli taulukoille? Ja yhdistää kaksi paneelia root-paneen. Näin tein omassa javaharkkatyössä ja toimi.
         */
        //ylaPaneeli.getChildren().addAll(hakuKentta, mokkiTiedonSyottoBt, hintaSliderLbl, hintaSlider, hintaArvoLbl, hakuBt);

        //-----------------------------LASKUTAB-------------------------------------
        Tab laskuTab = new Tab("Laskut");
        BorderPane laskuTabPaneeli = new BorderPane();
        laskuTab.setContent(laskuTabPaneeli);

        Button asiakasTiedonSyottoBt = new Button("Syötä asiakastietoja");
        asiakasTiedonSyottoBt.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Syötä asiakastiedot");
            //postinumero nyt alustavasti vain noin, katsotaan yhdessä miten se sisällytetään tähän
            VBox atsPaneeli = new VBox();
            atsPaneeli.setSpacing(5);
            Label etuNimiLbl = new Label("Etunimi:");
            TextField etuNimiTf = new TextField();
            Label sukuNimiLbl = new Label("Sukunimi:");
            TextField sukuNimiTf = new TextField();
            Label asiaksOsoiteLbl = new Label("Osoite:");
            TextField asiakasOsoiteTf = new TextField();
            Label postiNroLbl = new Label("Postinumero:");
            TextField postiNroTF = new TextField();
            Label emailLbl = new Label("Sähköposti:");
            TextField emailTf = new TextField();
            Label puhelinnroLbl = new Label("Puhelinnumero:");
            TextField puhelinnroTf = new TextField();
            Button tallennaAsiakasBt = new Button("Tallenna tiedot");

            tallennaAsiakasBt.setOnAction(p -> {
                String postiNro = postiNroTF.getText();
                String etuNimi = etuNimiTf.getText();
                String sukuNimi = sukuNimiTf.getText();
                String lahiOsoite = asiakasOsoiteTf.getText();
                String email = emailTf.getText();
                String puhelinNro = puhelinnroTf.getText();

                kirjoitaAsiakasTietokantaan(postiNro, etuNimi, sukuNimi, lahiOsoite, email, puhelinNro);


            });

            atsPaneeli.getChildren().addAll(etuNimiLbl, etuNimiTf, sukuNimiLbl, sukuNimiTf, asiaksOsoiteLbl,
                    asiakasOsoiteTf, postiNroLbl, postiNroTF, emailLbl, emailTf, puhelinnroLbl, puhelinnroTf, tallennaAsiakasBt);

            Scene scene = new Scene(atsPaneeli, 400, 400);
            stage.setScene(scene);
            stage.show();
        });

        //----------------------------------------------------------TABLEVIEW-----------------------------------------
        // Luo TableView
        TableView<Varaukset> laskuTableView = new TableView<>();

        // Luo sarakkeet
        TableColumn<Varaukset, Integer> IdVARAUSColumn = new TableColumn<>("Varaus ID");
        IdVARAUSColumn.setCellValueFactory(new PropertyValueFactory<>("varausID"));

        TableColumn<Varaukset, String> lahetysVARAUSColumn = new TableColumn<>("Onko lasku lähetetty?");
        lahetysVARAUSColumn.setCellValueFactory(new PropertyValueFactory<>("lahetysStat"));

        TableColumn<Varaukset, String> maksuVARAUSColumn = new TableColumn<>("Onko maksettu?");
        maksuVARAUSColumn.setCellValueFactory(new PropertyValueFactory<>("maksuStat"));

        laskuTabPaneeli.setTop(asiakasTiedonSyottoBt);
        laskuTableView.getColumns().addAll(IdVARAUSColumn, lahetysVARAUSColumn, maksuVARAUSColumn);

        // Lisää taulukko BorderPaneen
        laskuTabPaneeli.setCenter(laskuTableView);

        //------------------------------------------------------------------------------------------------------------

        tabit.getTabs().addAll(varausTab, laskuTab);
        paneeli.setCenter(tabit);

        Scene scene = new Scene(paneeli, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökin varausjärjestelmä");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}