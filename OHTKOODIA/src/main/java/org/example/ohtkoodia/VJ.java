package org.example.ohtkoodia;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;


public class VJ extends Application{

    // private Haku haku = new Haku(); // Luo uusi Haku-olio
    //testivittu
    // да да pjötr

    //----------------------------METODIT------------------------------------------------------------------------------

    //Source: https://stackoverflow.com/questions/59147960/how-to-insert-into-mysql-database-with-java
    //https://stackoverflow.com/questions/5005388/cannot-add-or-update-a-child-row-a-foreign-key-constraint-fails
    //https://stackoverflow.com/questions/59956372/sql-server-foreign-key-constraint-error-but-data-exists



    //----------------------ALUE-METODIT---------------------------



    // Metodi, joka kirjoittaa alueen tiedot tietokantaan
    public void kirjoitaAlueTietokantaan(String nimi) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "root";
        String password = "";

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
        String username = "root";
        String password = "";

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
        String username = "root";
        String password = "";

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
        String username = "root";
        String password = "";

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

    public int haeUusinAsiakasIdTietokannasta() {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "root";
        String password = "";

        int uusinAsiakasId = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT MAX(asiakas_id) AS max_asiakas_id FROM asiakas";
            try (Statement stmt = connection.createStatement();
                 ResultSet resultSet = stmt.executeQuery(sql)) {

                if (resultSet.next()) {
                    uusinAsiakasId = resultSet.getInt("max_asiakas_id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return uusinAsiakasId;
    }


    //--------------POSTINUMERO-METODIT---------------------



    // Metodi, joka kirjoittaa postinumeron ja toimipaikan tietokantaan
    public void kirjoitaPostiNroTietokantaan(String postiNro, String toimiPaikka) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "root";
        String password = "";

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
        String username = "root";
        String password = "";

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

        // Column jossa varausnappi, joka avaa uuden ikkunan varauksien tarkastelulle ja tekemiselle
        TableColumn<Mokki, String> varausMOKKIColumn = new TableColumn<>("Varaus");
        varausMOKKIColumn.setCellFactory(column -> {
            return new TableCell<Mokki, String>() {
                final Button teeVarausbtn = new Button("Tee varaus");

                {
                    teeVarausbtn.setOnAction(event -> {
                        Mokki selectedMokki = getTableView().getItems().get(getIndex());
                        ObservableList<ObservableList<String>> varaukset = Varaus.haeVarauksetTietokannasta(selectedMokki.getMokkiId());

                        TableView<ObservableList<String>> tableView = new TableView<>();
                        for (int i = 0; i < varaukset.get(0).size(); i++) {
                            TableColumn<ObservableList<String>, String> column = new TableColumn<>(varaukset.get(0).get(i));
                            final int columnIndex = i;
                            column.setCellValueFactory(cellData -> {
                                ObservableList<String> row = cellData.getValue();
                                return new SimpleStringProperty(row.get(columnIndex));
                            });
                            tableView.getColumns().add(column);
                        }
                        varaukset.remove(0); // Remove column names from data
                        tableView.setItems(varaukset);

                        // Varauksen tekoa varten informaatiokentät

                        Label asiakasPostiSyottoLbl = new Label("Lisää ensin asiakkaan postinumero ja -toimipaikka");

                        Label postinroLabel = new Label("Postinumero");
                        TextField postinroField = new TextField();

                        Label toimiPaikkaLabel = new Label("Postitoimipaikka");
                        TextField toimiPaikkaField = new TextField();

                        ComboBox<String> postiNroCB = new ComboBox<>();
                        postiNroCB.setItems(haePostiNroTietokannasta());

                        Button tallennaPostiButton = new Button("Tallenna posti");
                        tallennaPostiButton.setOnAction(e -> {
                            String postiNro = postinroField.getText();
                            String toimiPaikka = toimiPaikkaField.getText();
                            kirjoitaPostiNroTietokantaan(postiNro, toimiPaikka);
                            postiNroCB.setItems(haePostiNroTietokannasta());
                        });

                        Label etunimiLabel = new Label("Etunimi:");
                        TextField etunimiField = new TextField();

                        Label sukunimiLabel = new Label("Sukunimi:");
                        TextField sukunimiField = new TextField();

                        Label puhnroLabel = new Label("Puhelinnumero");
                        TextField puhnroField = new TextField();

                        Label emailLabel = new Label("E-mail:");
                        TextField emailField = new TextField();

                        Label lahiosoiteLabel = new Label("Lähiosoite");
                        TextField lahiosoiteField = new TextField();

                        Label valitsePostiNroLbl = new Label("Valitse postinumero:");
                        // Postinumero ComboBox luodaan ylempänä, jotta sen voi päivittää

                        CheckBox vahvistettuCheckbox = new CheckBox("Vahvistettu?");

                        Button tallennaAsiakasButton = new Button("Tallenna asiakastiedot");
                        tallennaAsiakasButton.setOnAction(e -> {
                            String postiNro = postiNroCB.getValue();
                            String etuNimi = etunimiField.getText();
                            String sukuNimi = sukunimiField.getText();
                            String lahiOsoite = lahiosoiteField.getText();
                            String email = emailField.getText();
                            String puhelinNro = puhnroField.getText();

                            kirjoitaAsiakasTietokantaan(postiNro, etuNimi, sukuNimi, lahiOsoite, email, puhelinNro);
                        });

                        Label varausAlkuPvmLbl = new Label("Milloin varaus alkaa?");
                        DatePicker varausAlkuPvmValitsin = new DatePicker();
                        Label varausLoppuPvmLbl = new Label("Milloin varaus loppuu?");
                        DatePicker varausLoppuPvmValitsin = new DatePicker();

                        Button tallennaVarausAikaButton = new Button("Tee varaus");
                        tallennaVarausAikaButton.setOnAction(e -> {
                            boolean onVahvistettu = vahvistettuCheckbox.isSelected();
                            LocalDate vahvistusPvm = null;
                            if (onVahvistettu) {
                                vahvistusPvm = LocalDate.now();
                            }

                            int asiakas_id = haeUusinAsiakasIdTietokannasta();
                            int mokki_id = selectedMokki.getMokkiId();
                            LocalDate varausPvm = LocalDate.now();
                            java.sql.Date varattu_pvm = java.sql.Date.valueOf(varausPvm);
                            java.sql.Date vahvistus_pvm = java.sql.Date.valueOf(vahvistusPvm);
                            LocalDate alkuPvm = varausAlkuPvmValitsin.getValue();
                            LocalDate loppuPvm = varausLoppuPvmValitsin.getValue();
                            java.sql.Date varattu_alkupvm = java.sql.Date.valueOf(alkuPvm);
                            java.sql.Date varattu_loppupvm = java.sql.Date.valueOf(loppuPvm);

                            Varaus mokkiVaraus = new Varaus(asiakas_id, mokki_id, varattu_pvm, vahvistus_pvm,
                                    varattu_alkupvm, varattu_loppupvm);
                            mokkiVaraus.kirjoitaVarausTietokantaan(mokkiVaraus);

                            int lasku_id = Lasku.haeSeuraavaLaskuId();
                            int varaus_id = Varaus.haeViimeisinVarausID();
                            Double summa = selectedMokki.getHinta();
                            Double alv = 10.0;
                            int maksettu = 0;

                            Lasku.kirjoitaLaskuTietokantaan(lasku_id, varaus_id, summa, alv, maksettu);
                        });


                        // GridPane asettelulle
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(10));


                        gridPane.addRow(1, asiakasPostiSyottoLbl);
                        gridPane.addRow(2, postinroLabel, postinroField, toimiPaikkaLabel, toimiPaikkaField);
                        gridPane.addRow(3, tallennaPostiButton);
                        gridPane.addRow(4, etunimiLabel, etunimiField, sukunimiLabel, sukunimiField);
                        gridPane.addRow(5, puhnroLabel, puhnroField, emailLabel, emailField);
                        gridPane.addRow(6, lahiosoiteLabel, lahiosoiteField, valitsePostiNroLbl, postiNroCB);
                        gridPane.addRow(7, tallennaAsiakasButton);
                        gridPane.addRow(8, varausAlkuPvmLbl, varausAlkuPvmValitsin);
                        gridPane.addRow(9, varausLoppuPvmLbl, varausLoppuPvmValitsin);
                        gridPane.addRow(10, vahvistettuCheckbox);
                        gridPane.addRow(11, tallennaVarausAikaButton);


                        // VBoxi jotta TableView ja GridPane saadaan aseteltua
                        VBox root = new VBox(tableView, gridPane);
                        Scene scene = new Scene(root, 865,750);
                        Stage varausStage = new Stage();
                        varausStage.setScene(scene);
                        varausStage.show();
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
        varausbtnColumn.setCellValueFactory(column -> new TableCell<>()x
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

            Label postiLisaysLbl = new Label("Lisää mökin postinumero ja -toimipaikka");
            Label postiNroLbl = new Label("Postinumero:");
            TextField postiNroTf = new TextField();
            Label toimiPaikkaLbl = new Label("Toimipaikka:");
            TextField toimiPaikkaTf = new TextField();
            ComboBox<String> postiNroCB = new ComboBox<>();

            // Button, joka ajaa metodin, joka tallentaa ylläolevien kenttien inputit posti-tauluun
            Button tallennaPostiBt = new Button("Tallenna tiedot");
            tallennaPostiBt.setOnAction(p -> {
                String postiNro = postiNroTf.getText();
                String toimiPaikka = toimiPaikkaTf.getText();
                kirjoitaPostiNroTietokantaan(postiNro, toimiPaikka);
                postiNroCB.setItems(haePostiNroTietokannasta());
            });

            Label nimiLbl = new Label("Mökin nimi:");
            TextField nimiTf = new TextField();
            Label alueLbl = new Label("Alue:");
            ComboBox<String> alueCB = new ComboBox<>();
            alueCB.setItems(haeAlueetTietokannasta());
            Label valitsePostiNroLbl = new Label("Valitse postinumero:");
            //itse postiNroCB ylempänä, jotta refresh toimii
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
                stage.close();
            });

            mtsPaneeli.getChildren().addAll(postiLisaysLbl, postiNroLbl, postiNroTf, toimiPaikkaLbl, toimiPaikkaTf, tallennaPostiBt,
                    nimiLbl, nimiTf, alueLbl, alueCB, valitsePostiNroLbl, postiNroCB, osoiteLbl, osoiteTf, hintaLbl,
                    hintaTf, kuvausLbl, kuvausTa, henkiloLbl, henkiloTf, varusteluLbl, varusteluTf, tallennaMokkiBt);

            Scene scene = new Scene(mtsPaneeli, 400, 770);
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
                stage.close();
            });

            atsPaneeli.getChildren().addAll(alueNimiLbl, alueNimiTf, tallennaAlueBt);

            Scene scene = new Scene(atsPaneeli, 400, 90);
            stage.setScene(scene);
            stage.show();
        });


        ylaPaneeli.getChildren().addAll(hakuKentta, hakuBt, alueTiedonSyottoBt, mokkiTiedonSyottoBt);


        //-----------------------------LASKUTAB-------------------------------------
        Tab laskuTab = new Tab("Laskut");
        BorderPane laskuTabPaneeli = new BorderPane();
        laskuTab.setContent(laskuTabPaneeli);

        //----------------------------------------------------------TABLEVIEW-----------------------------------------
        // Luo TableView
        TableView laskuTableView = new TableView<Lasku>();
        // Luo ObservableList
        ObservableList<Lasku> lasku = FXCollections.observableArrayList();

        // Luo sarakkeet
        TableColumn IdLASKUColumn = new TableColumn<Lasku, Integer>("Lasku ID");
        IdLASKUColumn.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("varausID"));

        TableColumn IdVARAUSColumn = new TableColumn<Lasku, Integer>("Varaus ID");
        IdVARAUSColumn.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("varausID"));

        TableColumn SummaColumn = new TableColumn<Lasku, Integer>("Summa");
        SummaColumn.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("summa"));

        TableColumn alvColumn = new TableColumn<Lasku, Integer>("ALV");
        alvColumn.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("alv"));

        TableColumn maksettuColumn = new TableColumn<Lasku, Integer>("Onko maksettu?");
        maksettuColumn.setCellValueFactory(new PropertyValueFactory<Lasku, Integer>("maksettu"));

        //------------------------------------------------------

        laskuTableView.getColumns().addAll(IdLASKUColumn, IdVARAUSColumn, SummaColumn, alvColumn, maksettuColumn);
        lasku.addAll(Lasku.haeLaskutTietokannasta());
        laskuTableView.setItems(lasku);

        // Lisää taulukko BorderPaneen
        laskuTabPaneeli.setCenter(laskuTableView);

        //----------------------Palvelut-tab--------------------------------------------------------------------------

        Tab palvelutTab = new Tab("Palvelut");
        BorderPane palvelutPaneeli = new BorderPane();
        palvelutTab.setContent(palvelutPaneeli);

        // Luo TableView palveluille
        TableView<Palvelut.Palvelu> palveluTableView = new TableView<>();
        // Luo sarakkeet taulukkoon
        TableColumn<Palvelut.Palvelu, String> nimiColumn = new TableColumn<>("Nimi");
        TableColumn<Palvelut.Palvelu, String> kuvausColumn = new TableColumn<>("Kuvaus");
        TableColumn<Palvelut.Palvelu, Double> hintaColumn = new TableColumn<>("Hinta");
        TableColumn<Palvelut.Palvelu, Integer> alueIDColumn = new TableColumn<>("Alue ID");

        // Määrittele miten Palvelu-olioiden kentät liittyvät TableView:n sarakkeisiin
        nimiColumn.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));
        hintaColumn.setCellValueFactory(new PropertyValueFactory<>("hinta"));
        alueIDColumn.setCellValueFactory(new PropertyValueFactory<>("alue_id"));

        // Lisää sarakkeet taulukkoon
        palveluTableView.getColumns().addAll(nimiColumn, kuvausColumn, hintaColumn, alueIDColumn);

        // Haetaan palvelut tietokannasta ja asetetaan ne TableView:hen
        Palvelut palvelut = new Palvelut();
        ObservableList<Palvelut.Palvelu> haetutPalvelut = palvelut.haePalvelutTietokannasta();
        palveluTableView.setItems(haetutPalvelut);

        // Lisää taulukko BorderPaneen
        palvelutPaneeli.setCenter(palveluTableView);

        // Luo nappi uuden palvelun lisäämiseksi
        Button lisaaPalveluButton = new Button("Lisää uusi palvelu");

        // Lisää nappi BorderPaneen
        palvelutPaneeli.setTop(lisaaPalveluButton);

        // Napin toiminnallisuus
        lisaaPalveluButton.setOnAction(event -> {
            // Luo uusi ikkuna palvelun lisäämistä varten
            Stage lisaaPalveluStage = new Stage();
            lisaaPalveluStage.setTitle("Lisää uusi palvelu");

            // Luo tarvittavat tekstikentät ja muut komponentit
            Label nimiLabel = new Label("Nimi:");
            TextField nimiTextField = new TextField();

            Label kuvausLabel = new Label("Kuvaus:");
            TextField kuvausTextField = new TextField();

            Label hintaLabel = new Label("Hinta:");
            TextField hintaTextField = new TextField();

            Label alueLbl = new Label("Alue:");
            ComboBox<String> alueCB = new ComboBox<>();
            alueCB.setItems(haeAlueetTietokannasta());

            Label palveluAlvLabel = new Label("ALV:");
            TextField palveluAlvTextField = new TextField();

            Button tallennaButton = new Button("Tallenna");

            // Aseta tapahtumankuuntelija tallennusnapille
            tallennaButton.setOnAction(e -> {
                String palveluNimi = nimiTextField.getText();
                String palveluKuvaus = kuvausTextField.getText();
                double palveluHinta = Double.parseDouble(hintaTextField.getText());
                int palveluAlv = Integer.parseInt(palveluAlvTextField.getText());
                String alueNimi = alueCB.getValue();
                int alueId = haeAlueId(alueNimi);


                // Lisätään palvelu tietokantaan
                Palvelut uusiPalvelu = new Palvelut();
                uusiPalvelu.lisaaPalveluTietokantaan(palveluNimi, palveluKuvaus, palveluHinta, palveluAlv, alueId);

                // Haetaan päivitetyt palvelut tietokannasta ja päivitetään TableView
                ObservableList<Palvelut.Palvelu> uudetPalvelut = palvelut.haePalvelutTietokannasta();
                palveluTableView.setItems(uudetPalvelut);

                // Sulje ikkuna palvelun lisäämisen jälkeen
                lisaaPalveluStage.close();
            });

            // Luo asettelupaneeli ja aseta komponentit siihen
            VBox layout = new VBox(10);
            layout.getChildren().addAll(
                    nimiLabel, nimiTextField,
                    kuvausLabel, kuvausTextField,
                    hintaLabel, hintaTextField,
                    alueLbl, alueCB,
                    palveluAlvLabel, palveluAlvTextField,
                    tallennaButton
            );
            layout.setPadding(new Insets(10));

            // Aseta asettelupaneeli näytettäväksi ikkunassa
            Scene scene = new Scene(layout, 500, 400);
            lisaaPalveluStage.setScene(scene);
            lisaaPalveluStage.show();
        });

        // Lisää nappi BorderPaneen
        palvelutPaneeli.setTop(lisaaPalveluButton);

        tabit.getTabs().addAll(mokkiTab, laskuTab, palvelutTab);
        paneeli.setCenter(tabit);

        Scene scene = new Scene(paneeli, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökin varausjärjestelmä");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}