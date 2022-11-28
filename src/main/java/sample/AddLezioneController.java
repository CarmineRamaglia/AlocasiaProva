package sample;

import dbconnector.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AddLezioneController extends Controller implements Initializable {

    @FXML
    private Button btClose;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private ImageView addPacImageView;
    @FXML
    private ChoiceBox<String> cbAllievo, cbDocente, cbPacchetto;
    @FXML
    private ChoiceBox<Integer> cbOreFatte;
    @FXML
    private Label lbMessage;
    @FXML
    private DatePicker datePicker;

    Statement statement;

    String date=null;
    public void getDate(ActionEvent event) throws ParseException {
        LocalDate myDate=datePicker.getValue();
        date=myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(date);
    }

    Integer[] ore= {1,2,3,4};

    private double x=0;
    private double y=0;

    //Parent root;

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
    private void clickedClose(MouseEvent event) throws IOException {
        if(event.getSource()==btClose){
            Stage stage= (Stage) borderPane.getScene().getWindow();
            stage.close();
        }
    }

    //INIZIALIZZARE IMMAGINI E CHOICE BOX
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbOreFatte.getItems().addAll(ore);

        try {
            MySQLConnection();

            //RIEMPIO BOX ALLIEVO
            ResultSet rs = statement.executeQuery("SELECT allievo FROM studente order by 1");
            ObservableList allievi= FXCollections.observableArrayList();
            while(rs.next()){
                allievi.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX DOCENTE
            rs = statement.executeQuery("SELECT cognome FROM dipendente where tutor=1 order by 1");
            ObservableList docenti= FXCollections.observableArrayList();
            while(rs.next()){
                docenti.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX ALLIEVI
            cbAllievo.setItems(allievi);

            //RIEMPIO BOX DOCENTI
            cbDocente.setItems(docenti);


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


        File addPacFile=new File("src/main/java/Aloimg/addLezione.png");
        Image addPacImage=new Image(addPacFile.toURI().toString());
        addPacImageView.setImage(addPacImage);
    }


    //CARICA PACCHETTO SOLO SE C'E' UNO STUDENTE
    public void loadPacchetti(MouseEvent event){
        if(cbAllievo.getValue()==null){
            setErrorStyle(cbAllievo);
            lbMessage.setText("Selezionare prima un allievo!");
        }else{
            setNormalStyle(cbAllievo);
            lbMessage.setText("");
            try {
                int control=0;
                MySQLConnection();

                //RIEMPIO BOX PACCHETTO
                ResultSet rs = statement.executeQuery("SELECT id FROM ha h JOIN studente s on s.matrUuid=h.matrUuid where s.allievo='"+
                        cbAllievo.getValue()+"'");
                ObservableList pacchetti= FXCollections.observableArrayList();
                while(rs.next()){
                    pacchetti.add(new String(rs.getString(1)));
                    control++;
                }

                //RIEMPIO BOX ALLIEVI
                cbPacchetto.setItems(pacchetti);

                if(control==0){
                    lbMessage.setText("Allo studente "+cbAllievo.getValue()+" non è stato asseganto ancora nessun pacchetto!");
                    setErrorStyle(cbAllievo);
                    setErrorStyle(cbPacchetto);
                }


            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }

    //SE CLICCHI SU ALLIEVO PACCHETTO SI TOGLIE
    public void removePacchetti(MouseEvent event) {
        setNormalStyle(cbAllievo);
        setNormalStyle(cbPacchetto);

        cbPacchetto.setValue(null);
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        //datePicker.setValue(null);
        cbAllievo.setValue(null);
        cbOreFatte.setValue(null);
        cbDocente.setValue(null);
        cbPacchetto.setValue(null);
    }

    //BOTTONE SALVA
    public void btSalvaOnAction() throws SQLException {
        textControll(datePicker);
        textControll(cbAllievo);
        textControll(cbOreFatte);
        textControll(cbDocente);
        textControll(cbPacchetto);


        if(datePicker.getValue()==null || cbAllievo.getValue()==null ||
                cbOreFatte.getValue()==null || cbDocente.getValue()==null || cbPacchetto.getValue()==null) {
            lbMessage.setText("Inserire tutti i campi!");
        }else {

            //TROVO MATR_UUID <-STUDENTE
            ResultSet rs= statement.executeQuery("SELECT matrUuid FROM studente WHERE allievo='"+cbAllievo.getValue()+"' ");
            String matrUuid=null;
            while (rs.next()){
                matrUuid=rs.getString("matrUuid");
            }

            //TROVO UUID  <-DOCENTE
            rs= statement.executeQuery("SELECT uuid FROM dipendente WHERE cognome='"+cbDocente.getValue()+"' ");
            String uuid=null;
            while (rs.next()){
                uuid=rs.getString("uuid");
            }

            //CREO NUOVA LEZIONE
            Lezione lezione=new Lezione(date, matrUuid, uuid, cbOreFatte.getValue(), cbPacchetto.getValue());
            System.out.println(lezione.toString());

            if(lezione.exist()){
                setErrorStyle(datePicker);
                setErrorStyle(cbAllievo);
                setErrorStyle(cbDocente);
                setErrorStyle(cbPacchetto);
                lbMessage.setText("Lezione già inserita!");
            }else {
                setNormalStyle(datePicker);
                setNormalStyle(cbAllievo);
                setNormalStyle(cbDocente);
                setNormalStyle(cbPacchetto);

                //inserisco nel db
                lezione.insert();


                //esco dal addlez
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();


                lbMessage.setStyle("-fx-text-fill: blue");
                lbMessage.setText("POSSO FARE ADD!");


            }

        }
    }

    public void MySQLConnection() throws SQLException {
        DBManager.setConnection(
                DBManager.JDBC_Driver_MySQL,
                DBManager.JDBC_URL_MySQL);
        statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }
}
