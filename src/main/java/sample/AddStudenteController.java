package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStudenteController extends Controller implements Initializable {

    @FXML
    private Button btClose;
    @FXML
    private TextField tfCognome, tfNome;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private ImageView addStuImageView;
    @FXML
    private ChoiceBox<String> cbAnnoCorso;
    @FXML
    private Label lbMessage;

    private String[] corsi={"1","2","3","4","5","altro"};

    private double x=0;
    private double y=0;

    Parent root;

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

        cbAnnoCorso.getItems().addAll(corsi);

        File addStuFile=new File("src/main/java/Aloimg/addStudente.png");
        Image addStuImage=new Image(addStuFile.toURI().toString());
        addStuImageView.setImage(addStuImage);
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        tfCognome.clear();
        tfNome.clear();
    }

    //BOTTONE SALVA
    public void btSalvaOnAction() throws SQLException {
        textControll(tfCognome);
        textControll(tfNome);
        textControll(cbAnnoCorso);


        if(tfCognome.getText().length()==0 || tfNome.getText().length()==0 ||
                cbAnnoCorso.getValue()==null) {
            lbMessage.setText("Inserire tutti i campi!");
        }else {

            //creo studente
            Studente studente=new Studente(tfCognome.getText(),tfNome.getText(),cbAnnoCorso.getValue());

            System.out.println(studente.toString());

            if(studente.exist()){
                setErrorStyle(tfCognome);
                setErrorStyle(tfNome);
                lbMessage.setText("Studente già esistente!");
            }else{
                setNormalStyle(tfCognome);
                setNormalStyle(tfNome);


                //inserisco nel db
                studente.insert();

                //esco dal addstud
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();


                lbMessage.setStyle("-fx-text-fill: blue");
                lbMessage.setText("POSSO FARE ADD!");
            }
        }
    }


}
