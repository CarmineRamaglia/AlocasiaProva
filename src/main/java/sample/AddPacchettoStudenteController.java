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

public class AddPacchettoStudenteController extends Controller implements Initializable {
    @FXML
    private Button btClose;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private ImageView addPacImageView;
    @FXML
    private ChoiceBox<String> cbAllievo, cbPacchetto;
    @FXML
    private Label lbMessage;

    Statement statement;

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

            //RIEMPIO BOX ALLIEVI
            cbAllievo.setItems(allievi);

            //RIEMPIO BOX PACCHETTI
            rs = statement.executeQuery("SELECT id FROM pacchetto");
            ObservableList pacchetti= FXCollections.observableArrayList();
            while(rs.next()){
                pacchetti.add(new String(rs.getString(1)));
            }

            //RIEMPIO BOX ALLIEVI
            cbAllievo.setItems(allievi);

            //RIEMPIO BOX PACCHETTI
            cbPacchetto.setItems(pacchetti);


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


        File addPacFile=new File("src/main/java/Aloimg/addPacchettoStudente.png");
        Image addPacImage=new Image(addPacFile.toURI().toString());
        addPacImageView.setImage(addPacImage);
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        cbAllievo.setValue(null);
        cbPacchetto.setValue(null);
    }

    //BOTTONE SALVA
    public void btSalvaOnAction() throws SQLException {
        textControll(cbAllievo);
        textControll(cbPacchetto);


        if(cbAllievo.getValue()==null || cbPacchetto.getValue()==null) {
            lbMessage.setText("Inserire tutti i campi!");
        }else {

            //TROVO MATR_UUID <-STUDENTE
            ResultSet rs= statement.executeQuery("SELECT matrUuid FROM studente WHERE allievo='"+cbAllievo.getValue()+"' ");
            String matrUuid=null;
            while (rs.next()){
                matrUuid=rs.getString("matrUuid");
            }

            //CREO NUOVO PACCHETTO STUDENTE
            Ha ha=new Ha(matrUuid, cbPacchetto.getValue());
            System.out.println(ha.toString());

            if(ha.exist()){
                setErrorStyle(cbAllievo);
                setErrorStyle(cbPacchetto);
                lbMessage.setText("A questo allievo è gia stato impostato questo pacchetto!");
            }else {
                setNormalStyle(cbAllievo);
                setNormalStyle(cbPacchetto);

                //inserisco nel db
                ha.insert();


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
