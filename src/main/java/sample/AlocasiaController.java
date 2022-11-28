package sample;

import dbconnector.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;


public class AlocasiaController implements Initializable {
    @FXML
    private Button btDipendenti, btLezioni, btPacchetti, btStudenti, btVendite;
    @FXML
    private Button btClose, btMaxim, btMinus;
    @FXML
    private Button btAddPacchetto, btAddStudente, btAddLezione, btAddVendita, btAddPacchettoStudente, btAddDipendente;
    @FXML
    private Label lbStatus, lbStatusMin;
    @FXML
    private Pane pnlStatus, logoPane;
    @FXML
    private ImageView logoWhiteImageView;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private VBox vbox;
    @FXML
    private TextField filterFieldPac,filterFieldStu, filterFieldLez, filterFieldVen, filterFieldDip;

    @FXML
    private GridPane pnStudenti, pnLezioni, pnVendite, pnPacchetti, pnDipendenti;


    //TABELLA STUDENTI
    @FXML
    private TableView<TableStudenti> studenteTableView;
    @FXML
    private TableColumn<TableStudenti, String> cognomeStudente;
    @FXML
    private TableColumn<TableStudenti, String> nomeStudente;
    @FXML
    private TableColumn<TableStudenti, String> annoCorsoStudente;
    @FXML
    private TableColumn<TableStudenti, String> pacchettoStudente;
    @FXML
    private TableColumn<TableStudenti, Integer> oreAcquistateStudente;
    @FXML
    private TableColumn<TableStudenti, Integer> oreFatteStudente;
    @FXML
    private TableColumn<TableStudenti, Integer> oreDisponibiliStudente;


    //TABELLA LEZIONI
    @FXML
    private TableView<TableLezioni> lezioneTableView;
    @FXML
    private TableColumn<TableLezioni, Date> dataLezione;
    @FXML
    private TableColumn<TableLezioni, String> meseLezione;
    @FXML
    private TableColumn<TableLezioni, String> allievoLezione;
    @FXML
    private TableColumn<TableLezioni, Integer> oreFatteLezione;
    @FXML
    private TableColumn<TableLezioni, String> docenteLezione;
    @FXML
    private TableColumn<TableLezioni, String> pacchettoLezione;


    //TABELLA VENDITE
    @FXML
    private TableView<TableVendite> venditaTableView;
    @FXML
    private TableColumn<TableVendite, Integer> numVendita;
    @FXML
    private TableColumn<TableVendite, Date> dataVendita;
    @FXML
    private TableColumn<TableVendite, String> allievoVendita;
    @FXML
    private TableColumn<TableVendite, Integer> oreAcquistateVendita;
    @FXML
    private TableColumn<TableVendite, String> venditoreVendita;
    @FXML
    private TableColumn<TableVendite, String> pacchettoVendita;


    //TABELLA PACCHETTI
    @FXML
    private TableView<Pacchetto> pacchettoTableView;
    @FXML
    private TableColumn<Pacchetto, Double> prezzoPac;
    @FXML
    private TableColumn<Pacchetto, String> tipoPac;
    @FXML
    private TableColumn<Pacchetto, Double> percTutorPac;
    @FXML
    private TableColumn<Pacchetto, Double> percVenditorePac;
    @FXML
    private TableColumn<Pacchetto, Double> bonusPac;

    //TABELLA DIPENDENTE
    @FXML
    private TableView<TableDipendenti> dipendenteTableView;
    @FXML
    private TableColumn<TableDipendenti, String> cognomeDipendente;
    @FXML
    private TableColumn<TableDipendenti, String> tutorDipendente;
    @FXML
    private TableColumn<TableDipendenti, String> venditoreDipendente;
    @FXML
    private TableColumn<TableDipendenti, String> superUtenteDipendente;
    @FXML
    private TableColumn<TableDipendenti, Integer> oreFatteDipendente;
    @FXML
    private TableColumn<TableDipendenti, Integer> oreVenduteDipendente;
    @FXML
    private TableColumn<TableDipendenti, Double> importoOreFatteDipendente;
    @FXML
    private TableColumn<TableDipendenti, Double> importoOreVenduteDipendente;
    @FXML
    private TableColumn<TableDipendenti, Double> importoTotDipendente;

    private double x=0;
    private double y=0;

    Statement statement;

    //CREAZIONE DEI METODI

    //MUOVERE IL BORDERPANE
    @FXML
    private void borderPaneDragger(MouseEvent event){
        if((int)borderPane.getHeight()==borderPane.getPrefHeight()) {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }
    }
    @FXML
    private void borderPanePressed(MouseEvent event){
        if((int)borderPane.getHeight()==borderPane.getPrefHeight()) {
            x = event.getSceneX();
            y = event.getSceneY();
        }
    }

    //CHIUSURA CON IL BOTTONE ROSSO
    @FXML
    private void clickedClose(MouseEvent event){
        if(event.getSource()==btClose){
            System.exit(0);
        }
    }

    //MASSIMIZZARE FINESTRA CON BOTTONE VERDE
    @FXML
    private void clickedMaxim(MouseEvent event){
        Stage stage = (Stage) btMaxim.getScene().getWindow();
        if((int)borderPane.getHeight()==borderPane.getPrefHeight()) {
            stage.setFullScreen(true);
            borderPane.setStyle(" -fx-background-radius: 0;");
            logoPane.setStyle(" -fx-background-radius: 0; -fx-background-color: #719dcc");
            vbox.setStyle(" -fx-background-radius: 0; -fx-background-color: #a3cced");
        }else{
            stage.setFullScreen(false);
            borderPane.setStyle(" -fx-background-radius: 20;");
            logoPane.setStyle(" -fx-background-radius: 20 0 0 0; -fx-background-color: #719dcc");
            vbox.setStyle(" -fx-background-radius: 20 0 0 20; -fx-background-color: #a3cced");
        }
    }

    //MINIMIZZARE FINESTRA CON BOTTONE GIALLO
    @FXML
    private void clickedMinus(MouseEvent event){
        Stage stage= (Stage) btMinus.getScene().getWindow();
        stage.setIconified(true);
    }

//+---------------------------------------------------+
//|                    INIZIALIZZAZIONE               |
//+---------------------------------------------------+

    ObservableList<TableStudenti> tableStudentiList=FXCollections.observableArrayList();
    ObservableList<TableLezioni> tableLezioniList = FXCollections.observableArrayList();
    ObservableList<TableVendite> tableVenditeList = FXCollections.observableArrayList();
    ObservableList<Pacchetto> pacchettoList = FXCollections.observableArrayList();
    ObservableList<TableDipendenti> tableDipendentiList=FXCollections.observableArrayList();

    //INIZIALIZZARE IMMAGINI
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lbStatusMin.setText("/home");
        lbStatus.setText("Benvenuto in Alocasia");
        pnlStatus.setStyle("-fx-background-color: #c6c6c6");

        refreshAll();

        //FILTRARE PACCHETTI
        filterPac();
        filterStu();
        filterLez();
        filterVen();
        filterDip();

        //IMMAGINI
        File logoWhiteFile=new File("src/main/java/Aloimg/logoWhite.png");
        Image logoWhiteImage=new Image(logoWhiteFile.toURI().toString());
        logoWhiteImageView.setImage(logoWhiteImage);
    }

    //REFRESH DI TUTTE LE TABLE
    private void refreshAll(){
        refreshPacchettoTable();
        refreshTableStudentiTable();
        refreshTableLezioniTable();
        refreshTableVenditeTable();
        refreshTableDipendenti();
    }


    //CONNESSIONE AL DB
    public void MySQLConnection() throws SQLException {
        DBManager.setConnection(
                DBManager.JDBC_Driver_MySQL,
                DBManager.JDBC_URL_MySQL);
        statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }




    @FXML
    private void handleClick(ActionEvent event){

        if(event.getSource()==btStudenti) {
            lbStatusMin.setText("/home/studenti");
            lbStatus.setText("Studenti");
            pnlStatus.setStyle("-fx-background-color: #d6b3d3");
            pnStudenti.toFront();
        }
        else if(event.getSource()==btLezioni) {
            lbStatusMin.setText("/home/lezioni");
            lbStatus.setText("Lezioni");
            pnlStatus.setStyle("-fx-background-color: #97d6f5");
            pnLezioni.toFront();
        }
        else if(event.getSource()==btVendite) {
            lbStatusMin.setText("/home/vendite");
            lbStatus.setText("Vendite");
            pnlStatus.setStyle("-fx-background-color: #c0e4d6");
            pnVendite.toFront();
        }
        else if(event.getSource()==btPacchetti) {
            lbStatusMin.setText("/home/pacchetti");
            lbStatus.setText("Pacchetti");
            pnlStatus.setStyle("-fx-background-color: #D9EC89");
            pnPacchetti.toFront();
        }
        else if(event.getSource()==btDipendenti){
            lbStatusMin.setText("/home/dipendenti");
            lbStatus.setText("Dipendenti");
            pnlStatus.setStyle("-fx-background-color: #ffd3ae");
            pnDipendenti.toFront();
        }

    }

//+---------------------------------------------------------------------------------------------+
//|                                         STUDENTE                                            |
//+---------------------------------------------------------------------------------------------+

    //REFRESH STUDENTE TABLE
    public void refreshTableStudentiTable(){
        tableStudentiList.clear();

        try {
            MySQLConnection();

            //CARICO STUDENTE DAL DB
            ResultSet rs = statement.executeQuery("SELECT * FROM tableStudenti");
            while (rs.next()) {

                tableStudentiList.add(new TableStudenti(rs.getString("cognome"),rs.getString("nome"),
                        rs.getString("annoCorso"), rs.getString("pacchetto"), rs.getInt("oreAcquistate"),
                        rs.getInt("oreFatte"), rs.getInt("oreDisponibili")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //INSERIRE DATI NELLA TABELLA TABLESTUDENTI
        cognomeStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, String>("cognome"));
        nomeStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, String>("nome"));
        annoCorsoStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, String>("annoCorso"));
        pacchettoStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, String>("pacchetto"));
        oreAcquistateStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, Integer>("oreAcquistate"));
        oreFatteStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, Integer>("oreFatte"));
        oreDisponibiliStudente.setCellValueFactory(new PropertyValueFactory<TableStudenti, Integer>("oreDisponibili"));

        studenteTableView.setItems(tableStudentiList);

        editStudente();
    }

    //EDITARE STUDENTE
    private void editStudente(){

        //EDIT COGNOME
        cognomeStudente.setCellFactory(TextFieldTableCell.forTableColumn());

        cognomeStudente.setOnEditCommit(e -> {
            TableStudenti tableStudenti=studenteTableView.getSelectionModel().getSelectedItem();
            try {
                editStudente("cognome", e.getNewValue(), tableStudenti.getCognome(), tableStudenti.getNome());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCognome(e.getNewValue());
        });

        //EDIT NOME
        nomeStudente.setCellFactory(TextFieldTableCell.forTableColumn());

        nomeStudente.setOnEditCommit(e -> {
            TableStudenti tableStudenti=studenteTableView.getSelectionModel().getSelectedItem();

            try {
                editStudente("nome", e.getNewValue(), tableStudenti.getCognome(), tableStudenti.getNome());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNome(e.getNewValue());
        });

        //EDIT ANNO CORSO
        annoCorsoStudente.setCellFactory(TextFieldTableCell.forTableColumn());

        annoCorsoStudente.setOnEditCommit(e -> {
            TableStudenti tableStudenti=studenteTableView.getSelectionModel().getSelectedItem();

            System.out.println(tableStudenti.toString());

            try {
                editStudente("annoCorso", e.getNewValue(), tableStudenti.getCognome(), tableStudenti.getNome());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAnnoCorso(e.getNewValue());
        });

        studenteTableView.setEditable(true);
    }

    //METODO PER EDITARE
    public void editStudente(String value,String newValue,String cognome, String nome) throws SQLException {
        try {
            MySQLConnection();
            statement.executeUpdate("UPDATE studente SET "+value+"='"+newValue+"' WHERE nome='"+nome+"' AND cognome='"+cognome+"'");

            System.out.println("UPDATE studente SET "+value+"='"+newValue+"' WHERE nome='"+nome+"' AND cognome='"+cognome+"'");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //BOTTONE AGGIUNGI STUDENTE
    public void btAddStudenteOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btAddStudente.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addStudente.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Aggiungi Studente");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //BOTTONE REFRESH
    public void btRefreshStuOnAction(ActionEvent event){
        refreshAll();
        System.out.println("refresh degli studenti");
    }

    //BOTTONE DELETE
    public void btDeleteStuOnAction(ActionEvent event) throws SQLException{
        TableStudenti tableStudenti=studenteTableView.getSelectionModel().getSelectedItem();
        Studente studente=new Studente(tableStudenti.getCognome(), tableStudenti.getNome(), tableStudenti.getAnnoCorso());
        System.out.println(studente.toString());
        if(tableStudenti!=null) {
            studente.delete();
            System.out.println("pacchetto eliminato");
            refreshAll();
            System.out.println("refresh dei pacchetti");
        }else{
            System.out.println("studente non selezionato");
        }
    }

    //FILTRARE STUDENTI
    public void filterStu(){

        //1-inserire l'ObservableList in una FilteredList
        FilteredList<TableStudenti> filteredTableStudenti=new FilteredList<>(tableStudentiList, b->true);

        //2-impostare il predicato del filtro ogni volta che il filtro cambia
        filterFieldStu.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTableStudenti.setPredicate(tableStudenti -> {

                //se filter text è vuoto, fai vedere tutto
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }

                //filtrare cose utili
                String lowerCaseFilter=newValue.toLowerCase();

                if(String.valueOf(tableStudenti.getCognome()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match cognome
                }else if(tableStudenti.getNome().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match nome
                }else if(String.valueOf(tableStudenti.getAnnoCorso()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match anno corso
                }else if(String.valueOf(tableStudenti.getOreDisponibili()).toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match ore disponibli
                }else{
                    return false; //no match
                }
            });
        });

        //3-Inserire la FilteredList in una SorteredList
        SortedList<TableStudenti> sortedTableStudenti=new SortedList<>(filteredTableStudenti);

        //4-Associare la SortedList alla TableView
        sortedTableStudenti.comparatorProperty().bind(studenteTableView.comparatorProperty());

        //5-fine
        studenteTableView.setItems(sortedTableStudenti);
    }

    //BOTTONE SEARCH
    public void filterFieldTableStudentiOnAction(MouseEvent event){
        filterStu();
    }


//+---------------------------------------------------------------------------------------------+
//|                                            LEZIONE                                          |
//+---------------------------------------------------------------------------------------------+

    //REFRESH TABLELEZIONI TABLE
    public void refreshTableLezioniTable(){
        tableLezioniList.clear();

        try {
            MySQLConnection();

            //CARICO LEZIONI DAL DB
            ResultSet rs = statement.executeQuery("SELECT * FROM tableLezioni");
            while (rs.next()) {

                tableLezioniList.add(new TableLezioni(rs.getDate("data"), rs.getString("allievo"),
                        rs.getInt("oreFatte"), rs.getString("docente"), rs.getString("pacchetto")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //INSERIRE DATI NELLA TABELLA TABLELEZIONI
        dataLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, Date>("data"));
        meseLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, String>("mese"));
        allievoLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, String>("allievo"));
        oreFatteLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, Integer>("oreFatte"));
        docenteLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, String>("docente"));
        pacchettoLezione.setCellValueFactory(new PropertyValueFactory<TableLezioni, String>("pacchetto"));

        lezioneTableView.setItems(tableLezioniList);

        //editLezione();
    }

    //EDITARE LEZIONE
        /*forse in futuro*/

    //BOTTONE AGGIUNGI LEZIONE
    public void btAddLezioneOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btAddLezione.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addNewLezione.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Aggiungi Lezione");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //BOTTONE REFRESH
    public void btRefreshLezOnAction(ActionEvent event){
        refreshAll();
        System.out.println("refresh delle lezioni");
    }

    //BOTTONE DELETE
    public void btDeleteLezOnAction(ActionEvent event) throws SQLException{
        TableLezioni tableLezioni=lezioneTableView.getSelectionModel().getSelectedItem();
        System.out.println(tableLezioni.toString());
        if(tableLezioni!=null) {

            //TROVO MATR_UUID <-STUDENTE
            ResultSet rs= statement.executeQuery("SELECT matrUuid FROM studente WHERE allievo='"+tableLezioni.getAllievo()+"' ");
            String matrUuid=null;
            while (rs.next()){
                matrUuid=rs.getString("matrUuid");
            }

            //TROVO UUID  <-DOCENTE
            rs= statement.executeQuery("SELECT uuid FROM dipendente WHERE cognome='"+tableLezioni.getDocente()+"' ");
            String uuid=null;
            while (rs.next()){
                uuid=rs.getString("uuid");
            }

            //ELIMINO LA LEZIONE
            statement.executeUpdate("DELETE FROM lezione WHERE data='"+tableLezioni.data+"' AND matrUuid='"+matrUuid+"'" +
                    "AND uuid='"+uuid+"' AND id='"+tableLezioni.getPacchetto()+"'");

            System.out.println("DELETE FROM lezione WHERE data='"+tableLezioni.data+"' AND matrUuid='"+matrUuid+"'" +
                    " AND uuid='"+uuid+"' AND id='"+tableLezioni.getPacchetto()+"'");
            System.out.println("lezione eliminato");
            refreshAll();
            System.out.println("refresh delle lezioni");
        }else{
            System.out.println("lezione non selezionata");
        }
    }

    //FILTRARE LEZIONI
    public void filterLez(){

        //1-inserire l'ObservableList in una FilteredList
        FilteredList<TableLezioni> filteredTableLezioni=new FilteredList<>(tableLezioniList, b->true);

        //2-impostare il predicato del filtro ogni volta che il filtro cambia
        filterFieldLez.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTableLezioni.setPredicate(tableLezioni -> {

                //se filter text è vuoto, fai vedere tutto
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }

                //filtrare cose utili
                String lowerCaseFilter=newValue.toLowerCase();

                if(String.valueOf(tableLezioni.getData()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match data
                }else if(tableLezioni.getMese().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match mese
                }else if(tableLezioni.getAllievo().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match allievo
                }else if(tableLezioni.getDocente().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match doc
                }else{
                    return false; //no match
                }
            });
        });

        //3-Inserire la FilteredList in una SorteredList
        SortedList<TableLezioni> sortedTableLezioni=new SortedList<>(filteredTableLezioni);

        //4-Associare la SortedList alla TableView
        sortedTableLezioni.comparatorProperty().bind(lezioneTableView.comparatorProperty());

        //5-fine
        lezioneTableView.setItems(sortedTableLezioni);
    }

    //BOTTONE SEARCH
    public void filterFieldTableStudenteOnAction(MouseEvent event){
        filterLez();
    }


//+---------------------------------------------------------------------------------------------+------------------------------------------------------------------------------------------------
//|                                          VENDITA                                            |<<<<<<------------------------------------------------------------------------------------------
//+---------------------------------------------------------------------------------------------+------------------------------------------------------------------------------------------------

    //REFRESH TABLEVENDITE TABLE
    public void refreshTableVenditeTable(){
        tableVenditeList.clear();

        try {
            MySQLConnection();

            //CARICO VENDITE DAL DB
            ResultSet rs = statement.executeQuery("SELECT * FROM tableVendite");
            while (rs.next()) {

                tableVenditeList.add(new TableVendite(rs.getInt("num"), rs.getDate("data"),
                        rs.getString("allievo"), rs.getInt("oreAcquistate"),
                        rs.getString("venditore"), rs.getString("pacchetto")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //INSERIRE DATI NELLA TABELLA TABLEVENDITE
        numVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, Integer>("num"));
        dataVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, Date>("data"));
        allievoVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, String>("allievo"));
        oreAcquistateVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, Integer>("oreAcquistate"));
        venditoreVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, String>("venditore"));
        pacchettoVendita.setCellValueFactory(new PropertyValueFactory<TableVendite, String>("pacchetto"));

        venditaTableView.setItems(tableVenditeList);

        //editVendita();
    }


    //EDITARE VENDITA
        /*forse in futuro*/


    //BOTTONE AGGIUNGI VENDITA
    public void btAddVenditaOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btAddVendita.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addNewVendita.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Aggiungi Lezione");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //BOTTONE AGGIUNGI PACCHETTO STUDENTE
    public void btAddPacchettoStudenteOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btAddPacchettoStudente.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addPacchettoStudente.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Aggiungi Pacchetto Studente");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //BOTTONE REFRESH
    public void btRefreshVenOnAction(ActionEvent event){
        refreshAll();
        System.out.println("refresh delle vendite");
    }

    //BOTTONE DELETE
    public void btDeleteVenOnAction(ActionEvent event) throws SQLException{

        TableVendite tableVendite=venditaTableView.getSelectionModel().getSelectedItem();
        System.out.println(tableVendite.toString());

        if (tableVendite != null) {

            //ELIMINO LA LEZIONE
            statement.executeUpdate("DELETE FROM vendita WHERE num=" + tableVendite.getNum());

            System.out.println("DELETE FROM vendita WHERE num=" + tableVendite.getNum());
            System.out.println("vendita eliminato");
            refreshAll();
            System.out.println("refresh delle vendite");
        } else {
            System.out.println("vendita non selezionata");
        }

    }

    //FILTRARE VENDITE
    public void filterVen(){

        //1-inserire l'ObservableList in una FilteredList
        FilteredList<TableVendite> filteredTableVendite=new FilteredList<>(tableVenditeList, b->true);

        //2-impostare il predicato del filtro ogni volta che il filtro cambia
        filterFieldVen.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTableVendite.setPredicate(tableVendite -> {

                //se filter text è vuoto, fai vedere tutto
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }

                //filtrare cose utili
                String lowerCaseFilter=newValue.toLowerCase();

                if(String.valueOf(tableVendite.getData()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match data
                }else if(tableVendite.getAllievo().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match allievo
                }else if(tableVendite.getVenditore().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match venditore
                }else if(tableVendite.getPacchetto().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match pacchetto
                }else{
                    return false; //no match
                }
            });
        });

        //3-Inserire la FilteredList in una SorteredList
        SortedList<TableVendite> sortedTableVendite=new SortedList<>(filteredTableVendite);

        //4-Associare la SortedList alla TableView
        sortedTableVendite.comparatorProperty().bind(venditaTableView.comparatorProperty());

        //5-fine
        venditaTableView.setItems(sortedTableVendite);
    }

    //BOTTONE SEARCH
    public void filterFieldTableVenditeOnAction(MouseEvent event){
        filterVen();
    }


//+---------------------------------------------------------------------------------------------+
//|                                         PACCHETTO                                           |
//+---------------------------------------------------------------------------------------------+

    //REFRESH PACCHETTO TABLE
    public void refreshPacchettoTable(){
        pacchettoList.clear();

        try {
            MySQLConnection();

            //CARICO PACCHETTO DAL DB
            ResultSet rs = statement.executeQuery("SELECT * FROM pacchetto");
            while (rs.next()) {

                pacchettoList.add(new Pacchetto(rs.getDouble("prezzo"),rs.getString("tipo"),
                        rs.getDouble("percTutor"), rs.getDouble("percVenditore"), rs.getDouble("percAltro")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //INSERIRE DATI NELLA TABELLA PACCHETTO
        prezzoPac.setCellValueFactory(new PropertyValueFactory<Pacchetto, Double>("prezzo"));
        tipoPac.setCellValueFactory(new PropertyValueFactory<Pacchetto, String>("tipo"));
        percTutorPac.setCellValueFactory(new PropertyValueFactory<Pacchetto, Double>("percTutor"));
        percVenditorePac.setCellValueFactory(new PropertyValueFactory<Pacchetto, Double>("percVenditore"));
        bonusPac.setCellValueFactory(new PropertyValueFactory<Pacchetto, Double>("percAltro"));

        pacchettoTableView.setItems(pacchettoList);

        editPacchetto();
    }

    //EDITARE PACCHETTO
    private void editPacchetto(){

        //EDIT PERC TUTOR
        percTutorPac.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        percTutorPac.setOnEditCommit(e -> {
            Pacchetto pacchetto=pacchettoTableView.getSelectionModel().getSelectedItem();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPercTutor(e.getNewValue());
            try {
                pacchetto.edit("percTutor", e.getNewValue().toString());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        });

        //EDIT PERC VENDITORE
        percVenditorePac.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        percVenditorePac.setOnEditCommit(e -> {
            Pacchetto pacchetto=pacchettoTableView.getSelectionModel().getSelectedItem();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPercVenditore(e.getNewValue());
            try {
                pacchetto.edit("percVenditore", e.getNewValue().toString());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        });

        //EDIT PERC ALTRO
        bonusPac.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        bonusPac.setOnEditCommit(e -> {
            Pacchetto pacchetto=pacchettoTableView.getSelectionModel().getSelectedItem();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPercAltro(e.getNewValue());
            try {
                pacchetto.edit("percAltro", e.getNewValue().toString());
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        });

        pacchettoTableView.setEditable(true);

    }

    //BOTTONE AGGIUNGI PACCHETTO
    public void btAddPacchettoOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btAddPacchetto.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addPacchetto.fxml"));
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle("Aggiungi Pacchetto");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //BOTTONE REFRESH
    public void btRefreshPacOnAction(ActionEvent event){
        refreshAll();
        System.out.println("refresh dei pacchetti");
    }

    //BOTTONE DELETE
    public void btDeletePacOnAction(ActionEvent event) throws SQLException{
        Pacchetto pacchetto=pacchettoTableView.getSelectionModel().getSelectedItem();
        if(pacchetto!=null) {
            System.out.println(pacchetto.toString());
            pacchetto.delete();
            System.out.println("pacchetto eliminato");
            refreshAll();
            System.out.println("refresh dei pacchetti");
        }else{
            System.out.println("pacchetto non selezionato");
        }
    }

    //FILTRARE PACCHETTI
    public void filterPac(){

        //1-inserire l'ObservableList in una FilteredList
        FilteredList<Pacchetto> filteredPacchetti=new FilteredList<>(pacchettoList, b->true);

        //2-impostare il predicato del filtro ogni volta che il filtro cambia
        filterFieldPac.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPacchetti.setPredicate(pacchetto -> {

                //se filter text è vuoto, fai vedere tutto
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }

                //filtrare cose utili
                String lowerCaseFilter=newValue.toLowerCase();

                if(String.valueOf(pacchetto.getPrezzo()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match prezzo
                }else if(pacchetto.getTipo().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
                    return true; //match tipo
                }else if(String.valueOf(pacchetto.getPercTutor()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match percTutor
                }else if(String.valueOf(pacchetto.getPercVenditore()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match percVenditore
                }else if(String.valueOf(pacchetto.getPercAltro()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match bonus
                }else{
                    return false; //no match
                }
            });
        });

        //3-Inserire la FilteredList in una SorteredList
        SortedList<Pacchetto> sortedPacchetti=new SortedList<>(filteredPacchetti);

        //4-Associare la SortedList alla TableView
        sortedPacchetti.comparatorProperty().bind(pacchettoTableView.comparatorProperty());

        //5-fine
        pacchettoTableView.setItems(sortedPacchetti);
    }

    //BOTTONE SEARCH
    public void filterFieldPacchettoOnAction(MouseEvent event){
        filterPac();
    }



//+---------------------------------------------------------------------------------------------+
//|                                        DIPENDENTI                                           |
//+---------------------------------------------------------------------------------------------+

    //REFRESH DIPENDENTI TABLE
    public void refreshTableDipendenti(){
        tableDipendentiList.clear();

        try {
            MySQLConnection();

            //CARICO PACCHETTO DAL DB
            ResultSet rs = statement.executeQuery("SELECT * FROM tableDipendenti");
            while (rs.next()) {

                TableDipendenti tableDipendenti=new TableDipendenti(rs.getString("uuid"),rs.getString("cognome"),
                        rs.getInt("tutor"), rs.getInt("venditore"), rs.getInt("superUtente"),
                        rs.getInt("oreFatteTot"), rs.getInt("oreVenduteTot"),rs.getDouble("importoOreFatte"),
                        rs.getDouble("importoOreVendute"), rs.getDouble("importoTot"));


                tableDipendentiList.add(tableDipendenti);


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //INSERIRE DATI NELLA TABELLA DIPENDENTI
        cognomeDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, String>("cognome"));
        tutorDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, String>("tutor"));
        venditoreDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, String>("venditore"));
        superUtenteDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, String>("superUtente"));
        oreFatteDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, Integer>("oreFatteTot"));
        oreVenduteDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, Integer>("oreVenduteTot"));
        importoOreFatteDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, Double>("importoOreFatte"));
        importoOreVenduteDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, Double>("importoOreVendute"));
        importoTotDipendente.setCellValueFactory(new PropertyValueFactory<TableDipendenti, Double>("importoTot"));

        dipendenteTableView.setItems(tableDipendentiList);

        editDipendente();
    }

    //EDITARE DIPENDENTE
    private void editDipendente(){


        //EDIT TUTOR
        tutorDipendente.setCellFactory(TextFieldTableCell.forTableColumn());

        tutorDipendente.setOnEditCommit(e -> {

            System.out.println(e.getNewValue());
            if(e.getNewValue().equals("si") ||e.getNewValue().equals("no") ){

                TableDipendenti tableDipendenti=dipendenteTableView.getSelectionModel().getSelectedItem();
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setTutor(e.getNewValue());

                try {
                    MySQLConnection();
                    int num;
                    if(e.getNewValue().equals("si")){
                        num=1;
                    }else{
                        num=0;
                    }

                    statement.executeUpdate("UPDATE dipendente SET tutor="+num+" WHERE uuid='"+tableDipendenti.getUuid()+"'");

                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

        });

        //EDIT VENDITORE
        venditoreDipendente.setCellFactory(TextFieldTableCell.forTableColumn());

        venditoreDipendente.setOnEditCommit(e -> {

            System.out.println(e.getNewValue());
            if(e.getNewValue().equals("si") ||e.getNewValue().equals("no") ){

                TableDipendenti tableDipendenti=dipendenteTableView.getSelectionModel().getSelectedItem();
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setVenditore(e.getNewValue());

                try {
                    MySQLConnection();
                    int num;
                    if(e.getNewValue().equals("si")){
                        num=1;
                    }else{
                        num=0;
                    }

                    statement.executeUpdate("UPDATE dipendente SET venditore="+num+" WHERE uuid='"+tableDipendenti.getUuid()+"'");

                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

        });

        //EDIT SUPERUTENTE
        superUtenteDipendente.setCellFactory(TextFieldTableCell.forTableColumn());

        superUtenteDipendente.setOnEditCommit(e -> {

            System.out.println(e.getNewValue());
            if(e.getNewValue().equals("si") ||e.getNewValue().equals("no") ){

                TableDipendenti tableDipendenti=dipendenteTableView.getSelectionModel().getSelectedItem();
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setSuperUtente(e.getNewValue());

                try {
                    MySQLConnection();
                    int num;
                    if(e.getNewValue().equals("si")){
                        num=1;
                    }else{
                        num=0;
                    }

                    statement.executeUpdate("UPDATE dipendente SET superUtente="+num+" WHERE uuid='"+tableDipendenti.getUuid()+"'");

                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

        });

        dipendenteTableView.setEditable(true);

    }

    //BOTTONE AGGIUNGI DIPENDENTE
        //non utile


    //BOTTONE REFRESH
    public void btRefreshDipOnAction(ActionEvent event){
        refreshAll();
        System.out.println("refresh dei venditori");
    }


    //BOTTONE DELETE
        //per ora non serve

    //FILTRARE PACCHETTI
    public void filterDip(){

        //1-inserire l'ObservableList in una FilteredList
        FilteredList<TableDipendenti> filteredTableDipendenti=new FilteredList<>(tableDipendentiList, b->true);

        //2-impostare il predicato del filtro ogni volta che il filtro cambia
        filterFieldDip.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTableDipendenti.setPredicate(tableDipendenti -> {

                //se filter text è vuoto, fai vedere tutto
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }

                //filtrare cose utili
                String lowerCaseFilter=newValue.toLowerCase();

                if(String.valueOf(tableDipendenti.getCognome()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true; //match cognome
                }else{
                    return false; //no match
                }
            });
        });

        //3-Inserire la FilteredList in una SorteredList
        SortedList<TableDipendenti> sortedTableDipendenti=new SortedList<>(filteredTableDipendenti);

        //4-Associare la SortedList alla TableView
        sortedTableDipendenti.comparatorProperty().bind(dipendenteTableView.comparatorProperty());

        //5-fine
        dipendenteTableView.setItems(sortedTableDipendenti);
    }

    //BOTTONE SEARCH
    public void filterFieldTableDipendentiOnAction(MouseEvent event){
        filterDip();
    }

}