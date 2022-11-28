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

public class AddPacchettoController extends Controller implements Initializable {

    @FXML
    private Button btClose;
    @FXML
    private TextField tfPrezzo, tfTutor, tfVenditore;
    @FXML
    private AnchorPane borderPane;
    @FXML
    private ImageView addPacImageView;
    @FXML
    private ChoiceBox<String> cbTipo;
    @FXML
    private ChoiceBox<Double> cbBonus;
    @FXML
    private Label lbMessage;

    private String[] tipi={"SUPERIORI","UNIVERSITA","MEDIE & ELEMENTARI","METODO DI STUDIO / COACHING"};
    private Double[] bonus={0.00, 1.00};

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

        cbTipo.getItems().addAll(tipi);
        cbBonus.getItems().addAll(bonus);

        File addPacFile=new File("src/main/java/Aloimg/addPacchetto.png");
        Image addPacImage=new Image(addPacFile.toURI().toString());
        addPacImageView.setImage(addPacImage);
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        tfPrezzo.clear();
        tfTutor.clear();
        tfVenditore.clear();
    }

    //BOTTONE SALVA
    public void btSalvaOnAction() throws SQLException {
        textControll(tfPrezzo);
        textControll(tfTutor);
        textControll(tfVenditore);
        textControll(cbTipo);
        textControll(cbBonus);


        if(tfPrezzo.getText().length()==0 || tfTutor.getText().length()==0 ||
            tfVenditore.getText().length()==0 || cbTipo.getValue()==null || cbBonus.getValue()==null) {
            lbMessage.setText("Inserire tutti i campi!");
        }else {

            //CONTROLLO PREZZO IS NUM
            try {
                double prezzo=Double.parseDouble(tfPrezzo.getText());
                if(prezzo>0 && prezzo<=100){
                    setNormalStyle(tfPrezzo);
                }else{
                    setErrorStyle(tfPrezzo);
                    lbMessage.setText("Numero non valido");
                    return;
                }
            } catch (NumberFormatException e) {
                setErrorStyle(tfPrezzo);
                lbMessage.setText("campo non valido!");
                return;
            } catch (Exception e) {
                setErrorStyle(tfPrezzo);
                lbMessage.setText("Error!");
                return;
            }

            //CONTROLLO PERC TUTOR IS NUM
            try {
                double tutor=Double.parseDouble(tfTutor.getText());
                if(tutor>0 && tutor<=100){
                    setNormalStyle(tfTutor);
                }else{
                    setErrorStyle(tfTutor);
                    lbMessage.setText("Numero non valido");
                    return;
                }
            } catch (NumberFormatException e) {
                setErrorStyle(tfTutor);
                lbMessage.setText("campo non valido!");
                return;
            } catch (Exception e) {
                setErrorStyle(tfTutor);
                lbMessage.setText("Error!");
                return;
            }

            //CONTROLLO PERC VENDITORE IS NUM
            try {
                double venditore=Double.parseDouble(tfVenditore.getText());
                if(venditore>0 && venditore<=100){
                    setNormalStyle(tfVenditore);
                }else{
                    setErrorStyle(tfVenditore);
                    lbMessage.setText("Numero non valido!");
                    return;
                }
            } catch (NumberFormatException e) {
                setErrorStyle(tfVenditore);
                lbMessage.setText("campo non valido!");
                return;
            } catch (Exception e) {
                setErrorStyle(tfVenditore);
                lbMessage.setText("Error!");
                return;
            }

            //creo pacc
            Pacchetto pacchetto=new Pacchetto(Double.parseDouble(tfPrezzo.getText()),cbTipo.getValue(),
                    Double.parseDouble(tfTutor.getText()),Double.parseDouble(tfVenditore.getText()),cbBonus.getValue());

            System.out.println(pacchetto.toString());

            if(pacchetto.exist()){
                System.out.println(pacchetto.exist());
                setErrorStyle(tfPrezzo);
                setErrorStyle(cbTipo);
                lbMessage.setText("Pacchetto già esistente!");
            }else{
                setNormalStyle(tfPrezzo);
                setNormalStyle(cbTipo);
                System.out.println(pacchetto.exist());

                //inserisco nel db
                pacchetto.insert();

                //esco dal addpac
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();


                lbMessage.setStyle("-fx-text-fill: blue");
                lbMessage.setText("POSSO FARE ADD!");
            }
        }
    }
}
