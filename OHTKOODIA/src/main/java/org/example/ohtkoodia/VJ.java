package org.example.ohtkoodia;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class VJ extends Application{

    // private Haku haku = new Haku(); // Luo uusi Haku-olio

    //----------------------------METODIT------------------------------------------------------------------------------

    //Source: https://stackoverflow.com/questions/59147960/how-to-insert-into-mysql-database-with-java
    //https://stackoverflow.com/questions/5005388/cannot-add-or-update-a-child-row-a-foreign-key-constraint-fails
    //https://stackoverflow.com/questions/59956372/sql-server-foreign-key-constraint-error-but-data-exists



    //----------------------ALUE-METODIT---------------------------



    // Metodi, joka kirjoittaa alueen tiedot tietokantaan
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

    // Metodi, joka hakee aluenimen perusteella oikean alue_id:n ja syöttää sen mokki-tauluun alue_id:seen
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

    // Metodi, joka hakee kaikki aluenimet tietokannasta
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


    //---------LUOKITTELEMATTOMAT METODIT-------------


    // Metodi, joka kirjoittaa asiakastiedot tietokantaan
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
                    System.out.println("Tiedot tallentuivat");
                } else {
                    System.out.println("Jokin meni pieleen");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    //--------------POSTINUMERO-METODIT---------------------



    // Metodi, joka kirjoittaa postinumeron ja toimipaikan tietokantaan
    public void kirjoitaPostiNroTietokantaan(String postiNro, String toimiPaikka) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO posti (postinro, toimipaikka) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, postiNro);
                preparedStatement.setString(2, toimiPaikka);
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

    // Metodi, joka palauttaa tietokannasta kaikki postinumerot sisältävän listan
    public ObservableList<String> haePostiNroTietokannasta() {
        ObservableList<String> postiNumerot = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT postinro FROM posti";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String postiNro = resultSet.getString("postinro");
                    postiNumerot.add(postiNro);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postiNumerot;
    }

    //------VARAUKSET

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
    public ObservableList<Varaus> haeVarauksetTietokannasta(int mokkiId) {
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



    //--------------------------------------UI-------------------------------------------------------------------------
    public void start(Stage primaryStage) throws Exception {

        // muuttuja mökkitietojen hakemiseen varaustabia varten
        ObservableList<Mokki> mokkiData = Mokki.haeMokitTietokannasta();


        BorderPane paneeli = new BorderPane();
        TabPane tabit = new TabPane();

        //-----------------------------MÖKKITAB-------------------------------------
        Tab mokkiTab = new Tab("Mökit");
        BorderPane mokkiTabPaneeli = new BorderPane();
        mokkiTab.setContent(mokkiTabPaneeli);
        HBox ylaPaneeli = new HBox();
        mokkiTabPaneeli.setTop(ylaPaneeli);
        TextField hakuKentta = new TextField();
        hakuKentta.setPromptText("Mökin nimi");


//HAKUPAINIKE SIIRRETTY TABLEVIEW OSIOON TOIMINNALLISUUDEN VUOKSI!!!


        //-- Slideri
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

        TableColumn<Mokki, String> postinroMOKKIColumn = new TableColumn<>("Postinumero");
        postinroMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("postinro"));

        TableColumn<Mokki, Double> hintaMOKKIColumn = new TableColumn<>("Hinta");
        hintaMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("hinta"));

        TableColumn<Mokki, String> kuvausMOKKIColumn = new TableColumn<>("Kuvaus");
        kuvausMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));

        TableColumn<Mokki, Integer> hlomaaraMOKKIColumn = new TableColumn<>("Henkilömäärä");
        hlomaaraMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("hlomaara"));

        TableColumn<Mokki, String> varusteluMOKKIColumn = new TableColumn<>("Varustelu");
        varusteluMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("varustelu"));



        TableColumn<Mokki, String> varausMOKKIColumn = new TableColumn<>("Varaus");
        varausMOKKIColumn.setCellFactory(column -> {
            return new TableCell<Mokki, String>() {
                final Button teeVarausbtn = new Button("Tee varaus");

                {
                    teeVarausbtn.setOnAction(event -> {
                        Mokki selectedMokki = getTableView().getItems().get(getIndex());

                        // Palauttaa valitun mökin varaukset
                        ObservableList<Varaus> varaukset = haeVarauksetTietokannasta(selectedMokki.getMokkiId());

                        // Näytä varauksista ListView
                        ListView<Varaus> varauksetListView = new ListView<>();
                        varauksetListView.setItems(varaukset);

                        VBox root = new VBox();
                        root.getChildren().addAll(varauksetListView);

                        Stage reservationStage = new Stage();
                        Scene scene = new Scene(root);
                        reservationStage.setScene(scene);
                        reservationStage.show();
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(teeVarausbtn);
                    }
                }
            };
        });






        // Lisää mökit ObservableListiin
        mokki.addAll(Mokki.haeMokitTietokannasta());
        // Aseta Observablelistin sisältö TableViewiin
        mokkiTableView.setItems(mokki);

        //Haku-painike ja toiminnallisuus
        Button hakuBt = new Button("Hae");
        hakuBt.setOnAction(e ->{
            String hakusana = hakuKentta.getText();
            mokkiTableView.setItems(Haku.haeMokitTietokannasta(hakusana));
        });
        //TÄHÄN TULISI VARAUS-PAINIKKEEN LUONTI JA TOIMINNALLISUUS, PALATAAN TÄHÄN MYÖHEMMIN!!!
        /*Marjutilla osa tätä koodia ja sen toiminnallisuutta, katsotaan backend-vaiheessa
        tarkemmin!!

        TableColumn<Mokki, Void> varausbtnColumn = new TableColumn<>("Tee varaus");
        varausbtnColumn.setCellValueFactory(column -> new TableCell<>()
            Button teeVarausbtn = new Button("Tee varaus");
        */

        // Lisää sarakkeet taulukkoon
        mokkiTableView.getColumns().addAll(nimiMOKKIColumn, osoiteMOKKIColumn, postinroMOKKIColumn, hintaMOKKIColumn,
                kuvausMOKKIColumn, hlomaaraMOKKIColumn, varusteluMOKKIColumn, varausMOKKIColumn);

        // Lisää taulukko BorderPaneen
        mokkiTabPaneeli.setCenter(mokkiTableView);

        /* Lisää hakutoiminnallisuus haku-painikkeelle
        hakuBt.setOnAction(e -> {
            String hakusana = hakuKentta.getText(); // Hae hakusana tekstikentästä
            ObservableList<Mokki> hakutulokset = haku.haeMokitTietokannasta(hakusana); // Hae mökit hakusanan perusteella
            mokkiTableView.setItems(hakutulokset); // Päivitä TableView hakutuloksilla
        });

        HBox.setHgrow(hakuKentta, Priority.ALWAYS);*/

        // Button, joka avaa ikkunan, josta syötetään mökkien tietoja tietokantaan
        Button mokkiTiedonSyottoBt = new Button("Lisää uusi mökki");
        mokkiTiedonSyottoBt.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Lisää uusi mökki");
            VBox mtsPaneeli = new VBox();
            mtsPaneeli.setSpacing(5);

            Label postiNroLbl = new Label("Postinumero");
            TextField postiNroTf = new TextField();
            Label toimiPaikkaLbl = new Label("Toimipaikka");
            TextField toimiPaikkaTf = new TextField();

            // Button, joka ajaa metodin, joka tallentaa ylläolevien kenttien inputit posti-tauluun
            Button tallennaPostiBt = new Button("Tallenna tiedot");
            tallennaPostiBt.setOnAction(p -> {
                String postiNro = postiNroTf.getText();
                String toimiPaikka = toimiPaikkaTf.getText();
                kirjoitaPostiNroTietokantaan(postiNro, toimiPaikka);
            });

            Label nimiLbl = new Label("Mökin nimi:");
            TextField nimiTf = new TextField();
            Label alueLbl = new Label("Alue:");
            ComboBox<String> alueCB = new ComboBox<>();
            alueCB.setItems(haeAlueetTietokannasta());
            Label valitsePostiNroLbl = new Label("Valitse postinumero:");
            ComboBox<String> postiNroCB = new ComboBox<>();
            postiNroCB.setItems(haePostiNroTietokannasta());
            Label osoiteLbl = new Label("Osoite:");
            TextField osoiteTf = new TextField();
            Label hintaLbl = new Label("Hinta:");
            TextField hintaTf = new TextField();
            Label kuvausLbl = new Label("Mökin kuvaus:");
            TextArea kuvausTa = new TextArea();
            Label henkiloLbl = new Label("Henkilömäärä:");
            TextField henkiloTf = new TextField();
            Label varusteluLbl = new Label("Varustelu:");
            TextField varusteluTf = new TextField();

            // Button, joka tallentaa ylläolevien kenttien inputit mokki-tauluun
            Button tallennaMokkiBt = new Button("Tallenna tiedot");
            tallennaMokkiBt.setOnAction(p -> {
                String alueNimi = alueCB.getValue();
                int alueId = haeAlueId(alueNimi);
                String postiNro = postiNroCB.getValue();
                String toimiPaikka = toimiPaikkaTf.getText();
                String mokkinimi = nimiTf.getText();
                String katuosoite = osoiteTf.getText();
                double hinta = Double.parseDouble(hintaTf.getText());
                String kuvaus = kuvausTa.getText();
                int henkilo = Integer.parseInt(henkiloTf.getText());
                String varustelu = varusteluTf.getText();
                Mokki.kirjoitaMokkiTietokantaan(alueId, postiNro, mokkinimi, katuosoite, hinta, kuvaus, henkilo, varustelu);
            });

            mtsPaneeli.getChildren().addAll(postiNroLbl, postiNroTf, toimiPaikkaLbl, toimiPaikkaTf, tallennaPostiBt,
                    nimiLbl, nimiTf, alueLbl, alueCB, valitsePostiNroLbl, postiNroCB, osoiteLbl, osoiteTf, hintaLbl,
                    hintaTf, kuvausLbl, kuvausTa, henkiloLbl, henkiloTf, varusteluLbl, varusteluTf, tallennaMokkiBt);

            Scene scene = new Scene(mtsPaneeli, 400, 600);
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

            // Nappi, joka ajaa metodin, jolla ylläolevien kenttien tiedot tallennetaan alue-tauluun
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


        //-----------------------------LASKUTAB-------------------------------------
        Tab laskuTab = new Tab("Laskut");
        BorderPane laskuTabPaneeli = new BorderPane();
        laskuTab.setContent(laskuTabPaneeli);

        // Nappi, joka avaa ikkunan, josta syötetään tietoja asiakas-tauluun
        Button asiakasTiedonSyottoBt = new Button("Syötä asiakastietoja");
        asiakasTiedonSyottoBt.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Syötä asiakastiedot");
            VBox atsPaneeli = new VBox();
            atsPaneeli.setSpacing(5);

            Label postiNroLbl = new Label("Postinumero");
            TextField postiNroTf = new TextField();
            Label toimiPaikkaLbl = new Label("Toimipaikka");
            TextField toimiPaikkaTf = new TextField();

            // Nappi, joka ajaa metodin, jolla ylläolevien kenttien inputit tallennetaan posti-tauluun
            Button tallennaPostiBt = new Button("Tallenna tiedot");
            tallennaPostiBt.setOnAction(p -> {
                String postiNro = postiNroTf.getText();
                String toimiPaikka = toimiPaikkaTf.getText();
                kirjoitaPostiNroTietokantaan(postiNro, toimiPaikka);
            });

            Label etuNimiLbl = new Label("Etunimi:");
            TextField etuNimiTf = new TextField();
            Label sukuNimiLbl = new Label("Sukunimi:");
            TextField sukuNimiTf = new TextField();
            Label asiaksOsoiteLbl = new Label("Osoite:");
            TextField asiakasOsoiteTf = new TextField();
            Label valitsePostiNroLbl = new Label("Valitse postinumero:");
            ComboBox<String> postiNroCB = new ComboBox<>();
            postiNroCB.setItems(haePostiNroTietokannasta());
            Label emailLbl = new Label("Sähköposti:");
            TextField emailTf = new TextField();
            Label puhelinnroLbl = new Label("Puhelinnumero:");
            TextField puhelinnroTf = new TextField();

            // Nappi, joka tallentaa ylläolevien kenttien inputit asiakas-tauluun
            Button tallennaAsiakasBt = new Button("Tallenna tiedot");
            tallennaAsiakasBt.setOnAction(p -> {
                String postiNro = postiNroCB.getValue();
                String etuNimi = etuNimiTf.getText();
                String sukuNimi = sukuNimiTf.getText();
                String lahiOsoite = asiakasOsoiteTf.getText();
                String email = emailTf.getText();
                String puhelinNro = puhelinnroTf.getText();

                kirjoitaAsiakasTietokantaan(postiNro, etuNimi, sukuNimi, lahiOsoite, email, puhelinNro);

            });

            atsPaneeli.getChildren().addAll(postiNroLbl, postiNroTf, toimiPaikkaLbl, toimiPaikkaTf, tallennaPostiBt,
                    etuNimiLbl, etuNimiTf, sukuNimiLbl, sukuNimiTf, asiaksOsoiteLbl,
                    asiakasOsoiteTf, valitsePostiNroLbl, postiNroCB, emailLbl, emailTf,
                    puhelinnroLbl, puhelinnroTf, tallennaAsiakasBt);

            Scene scene = new Scene(atsPaneeli, 400, 500);
            stage.setScene(scene);
            stage.show();
        });

        //----------------------------------------------------------TABLEVIEW-----------------------------------------
        // Luo TableView
        TableView laskuTableView = new TableView<Laskut>();
        // Luo ObservableList
        ObservableList<Laskut> lasku = FXCollections.observableArrayList();

        // Luo sarakkeet
        TableColumn IdLASKUColumn = new TableColumn<Laskut, Integer>("Lasku ID");
        IdLASKUColumn.setCellValueFactory(new PropertyValueFactory<Laskut, Integer>("varausID"));

        TableColumn IdVARAUSColumn = new TableColumn<Laskut, Integer>("Varaus ID");
        IdVARAUSColumn.setCellValueFactory(new PropertyValueFactory<Laskut, Integer>("varausID"));

        TableColumn SummaColumn = new TableColumn<Laskut, Integer>("Summa");
        SummaColumn.setCellValueFactory(new PropertyValueFactory<Laskut, Integer>("summa"));

        TableColumn alvColumn = new TableColumn<Laskut, Integer>("ALV");
        alvColumn.setCellValueFactory(new PropertyValueFactory<Laskut, Integer>("alv"));

        TableColumn maksettuColumn = new TableColumn<Laskut, Integer>("Onko maksettu?");
        maksettuColumn.setCellValueFactory(new PropertyValueFactory<Laskut, Integer>("maksettu"));

        //------------------------------------------------------
        laskuTabPaneeli.setTop(asiakasTiedonSyottoBt);

        laskuTableView.getColumns().addAll(IdLASKUColumn, IdVARAUSColumn, SummaColumn, alvColumn, maksettuColumn);
        lasku.addAll(Laskut.haeLaskutTietokannasta());
        laskuTableView.setItems(lasku);

        // Lisää taulukko BorderPaneen
        laskuTabPaneeli.setCenter(laskuTableView);

        //------------------------------------------------------------------------------------------------------------

        tabit.getTabs().addAll(mokkiTab, laskuTab);
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