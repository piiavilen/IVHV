package org.example.ohtkoodia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class VJ extends Application{

    //Metodi, jolla syötetään mökin tiedot tietokantaan, EI TOIMI VIELÄ KOSKA ALUE_ID:LLÄ EI OLE ARVOA
    //Source: https://stackoverflow.com/questions/59147960/how-to-insert-into-mysql-database-with-java
    public void kirjoitaTietoKantaan(String mokkinimi, String katuosoite, double hinta, String kuvaus,
                                     int henkilo, String varustelu) {
        String url = "jdbc:mysql://localhost:3307/vn";
        String username = "pmauser";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO mokki (mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mokkinimi);
                preparedStatement.setString(2, katuosoite);
                preparedStatement.setDouble(3, hinta);
                preparedStatement.setString(4, kuvaus);
                preparedStatement.setInt(5, henkilo);
                preparedStatement.setString(6, varustelu);

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

        // Luo sarakkeet
        TableColumn<Mokki, String> nimiMOKKIColumn = new TableColumn<>("Nimi");
        nimiMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("nimi"));

        TableColumn<Mokki, Integer> postinroMOKKIColumn = new TableColumn<>("Postinumero");
        postinroMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("postinumero"));

        TableColumn<Mokki, Double> hintaMOKKIColumn = new TableColumn<>("Hinta");
        hintaMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("hinta"));

        TableColumn<Mokki, Integer> hlomaaraMOKKIColumn = new TableColumn<>("Henkilömäärä");
        hlomaaraMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("henkilomaara"));

        TableColumn<Mokki, String> varusteluMOKKIColumn = new TableColumn<>("Varustelu");
        varusteluMOKKIColumn.setCellValueFactory(new PropertyValueFactory<>("varustelu"));

        //TÄHÄN TULISI VARAUS-PAINIKKEEN LUONTI JA TOIMINNALLISUUS, PALATAAN TÄHÄN MYÖHEMMIN!!!
        /*Marjutilla osa tätä koodia ja sen toiminnallisuutta, katsotaan backend-vaiheessa
        tarkemmin!!

        TableColumn<Mokki, Void> varausbtnColumn = new TableColumn<>("Tee varaus");
        varausbtnColumn.setCellValueFactory(column -> new TableCell<>()
            Button teeVarausbtn = new Button("Tee varaus");
        */

        // Lisää sarakkeet taulukkoon
        mokkiTableView.getColumns().addAll(nimiMOKKIColumn, postinroMOKKIColumn, hintaMOKKIColumn, hlomaaraMOKKIColumn, varusteluMOKKIColumn);//Muistetaan lisätä varausbtn

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
            //Label alueLbl = new Label("Alue:");
            //TextField alueTf = new TextField();
            //Label postiLbl = new Label("Postinumero:");
            //TextField postiTf = new TextField();
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
            Button tallennaMokkiBt = new Button("Tallenna tiedot");

            tallennaMokkiBt.setOnAction(p -> {
                String mokkinimi = nimiTf.getText();
                //String alue = alueTf.getText();
                //int postiNro = Integer.parseInt(postiTf.getText());
                String katuosoite = osoiteTf.getText();
                double hinta = Double.parseDouble(hintaTf.getText());
                String kuvaus = kuvausTa.getText();
                int henkilo = Integer.parseInt(henkiloTf.getText());
                String varustelu = varusteluTf.getText();
                kirjoitaTietoKantaan(mokkinimi, katuosoite, hinta, kuvaus, henkilo, varustelu);

            });

            mtsPaneeli.getChildren().addAll(nimiLbl, nimiTf, /*alueLbl, alueTf, postiLbl, postiTf,*/ osoiteLbl, osoiteTf,
                    hintaLbl, hintaTf, kuvausLbl, kuvausTa, henkiloLbl, henkiloTf, varusteluLbl, varusteluTf,
                    tallennaMokkiBt);

            Scene scene = new Scene(mtsPaneeli, 400, 500);
            stage.setScene(scene);
            stage.show();
        });

        ylaPaneeli.getChildren().addAll(hakuKentta, hintaSliderLbl, hintaSlider, hintaArvoLbl, hakuBt, mokkiTiedonSyottoBt);

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

            VBox atsPaneeli = new VBox();
            atsPaneeli.setSpacing(5);
            Label etuNimiLbl = new Label("Etunimi:");
            TextField etuNimiTf = new TextField();
            Label sukuNimiLbl = new Label("Sukunimi:");
            TextField sukuNimiTf = new TextField();
            Label asiaksOsoiteLbl = new Label("Osoite:");
            TextField asiakasOsoiteTf = new TextField();
            Label emailLbl = new Label("Sähköposti:");
            TextField emailTf = new TextField();
            Label puhelinnroLbl = new Label("Puhelinnumero:");
            TextField puhelinnroTf = new TextField();
            Button tallennaAsiakasBt = new Button("Tallenna tiedot");

            atsPaneeli.getChildren().addAll(etuNimiLbl, etuNimiTf, sukuNimiLbl, sukuNimiTf, asiaksOsoiteLbl,
                    asiakasOsoiteTf, emailLbl, emailTf, puhelinnroLbl, puhelinnroTf, tallennaAsiakasBt);

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

        // Kokoa pitää ehkä adjustaa vielä kunhan saadaan mökkilista näkyviin
        Scene scene = new Scene(paneeli, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökin varausjärjestelmä");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}