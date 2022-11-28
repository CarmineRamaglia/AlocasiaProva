package sample;

import dbconnector.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddVenditaController extends Controller implements Initializable {
    @FXML
    private Button btClose;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private ImageView addPacImageView;
    @FXML
    private ChoiceBox<String> cbAllievo, cbVenditore, cbPacchetto;
    @FXML
    private Label lbMessage;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField tfOreAcquistate;

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

        try {
            MySQLConnection();

            //RIEMPIO BOX ALLIEVO
            ResultSet rs = statement.executeQuery("SELECT allievo FROM studente order by 1");
            ObservableList allievi= FXCollections.observableArrayList();
            while(rs.next()){
                allievi.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX VENDITORE
            rs = statement.executeQuery("SELECT cognome FROM dipendente where venditore=1 order by 1");
            ObservableList venditori= FXCollections.observableArrayList();
            while(rs.next()){
                venditori.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX PACCHETTI
            rs = statement.executeQuery("SELECT id FROM pacchetto");
            ObservableList pacchetti= FXCollections.observableArrayList();
            while(rs.next()){
                pacchetti.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX ALLIEVI
            cbAllievo.setItems(allievi);

            //RIEMPIO BOX DOCENTI
            cbVenditore.setItems(venditori);

            //RIEMPIO BOX DOCENTI
            cbPacchetto.setItems(pacchetti);


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


        File addPacFile=new File("src/main/java/Aloimg/addVendita.png");
        Image addPacImage=new Image(addPacFile.toURI().toString());
        addPacImageView.setImage(addPacImage);
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        //datePicker.setValue(null);
        cbAllievo.setValue(null);
        tfOreAcquistate.clear();
        cbVenditore.setValue(null);
        cbPacchetto.setValue(null);
    }

    //BOTTONE SALVA
    public void btSalvaOnAction() throws SQLException {
        textControll(datePicker);
        textControll(cbAllievo);
        textControll(tfOreAcquistate);
        textControll(cbVenditore);
        textControll(cbPacchetto);


        if(datePicker.getValue()==null || cbAllievo.getValue()==null ||
                tfOreAcquistate.getText().length()==0 || cbVenditore.getValue()==null || cbPacchetto.getValue()==null) {
            lbMessage.setText("Inserire tutti i campi!");
        }else {
            //CONTROLLO OREACQUISTATE IS NUM
            try {
                Integer ore=Integer.parseInt(tfOreAcquistate.getText());
                if(ore>0 && ore<=200){
                    setNormalStyle(tfOreAcquistate);
                }else{
                    setErrorStyle(tfOreAcquistate);
                    lbMessage.setText("Numero non valido");
                    return;
                }
            } catch (NumberFormatException e) {
                setErrorStyle(tfOreAcquistate);
                lbMessage.setText("campo non valido!");
                return;
            } catch (Exception e) {
                setErrorStyle(tfOreAcquistate);
                lbMessage.setText("Error!");
                return;
            }

            //TROVO MATR_UUID <-STUDENTE
            ResultSet rs= statement.executeQuery("SELECT matrUuid FROM studente WHERE allievo='"+cbAllievo.getValue()+"' ");
            String matrUuid=null;
            while (rs.next()){
                matrUuid=rs.getString("matrUuid");
            }

            //TROVO UUID  <-VENDITORE
            rs= statement.executeQuery("SELECT uuid FROM dipendente WHERE cognome='"+cbVenditore.getValue()+"' ");
            String uuid=null;
            while (rs.next()){
                uuid=rs.getString("uuid");
            }

            //CREO NUOVA VENDITA
            Vendita vendita=new Vendita(date, matrUuid, uuid, Integer.parseInt(tfOreAcquistate.getText()), cbPacchetto.getValue());
            System.out.println(vendita.toString());

            if(vendita.studenteHasPacchetto()) {
                System.out.println("nuova vendita soltanto");
                //NUOVA VENDITA
                vendita.insert();
            }else {
                System.out.println("nuova vendita e aggiungo nuovo pac");
                //INSERISCO NUOVO PACCHETTO ALLO STUDENTE
                statement.executeUpdate("INSERT INTO ha (matrUuid, id) values('"+vendita.getMatrUuid()+"' , '"+vendita.getId()+"') ");
                //NUOVA VENDITA
                vendita.insert();
            }

                //esco dal addVEN
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();


                lbMessage.setStyle("-fx-text-fill: blue");
                lbMessage.setText("POSSO FARE ADD!");

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
