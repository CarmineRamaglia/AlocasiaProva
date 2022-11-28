package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController extends Controller  implements Initializable {

    //DICHIARAZIONE DEI DATI



    @FXML
    private AnchorPane borderPane;
    @FXML
    private Button btClose, btMinus;
    @FXML
    private ImageView logoImageView, addUserRedImageView;
    @FXML
    private Button btSalva, btAnnulla;
    @FXML
    private TextField tfNome, tfCognome, tfMail, tfUsername;
    @FXML
    private PasswordField pfPassword, pfConfermaPwd;
    @FXML
    private Label lbMessage;

    private double x=0;
    private double y=0;


    //CREAZIONE DEI METODI

    //MUOVERE IL BORDERPANE
    @FXML
    private void borderPaneDragger(MouseEvent event){
        Stage stage=(Stage) borderPane.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    @FXML
    private void borderPanePressed(MouseEvent event){
        x=event.getSceneX();
        y=event.getSceneY();
    }

    //CHIUSURA CON IL BOTTONE ROSSO
    @FXML
    private void clickedClose(MouseEvent event){
        if(event.getSource()==btClose){
            System.exit(0);
        }
    }

    //MINIMIZZARE FINESTRA CON BOTTONE GIALLO
    @FXML
    private void clickedMinus(MouseEvent event){
        Stage stage= (Stage) btMinus.getScene().getWindow();
        stage.setIconified(true);
    }

    //INIZIALIZZARE IMMAGINI
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoFile=new File("src/main/java/Aloimg/logo.png");
        Image logoImage=new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File addUserRedFile=new File("src/main/java/Aloimg/addUserRed.png");
        Image addUserRedImage=new Image(addUserRedFile.toURI().toString());
        addUserRedImageView.setImage(addUserRedImage);
    }

    //BOTTONE ANNULLA
    public void btAnnullaOnAction(ActionEvent event) throws IOException{
        switchToLogin(btAnnulla);
    }


    //TORNARE ALLA SCHERMATA DI LOGIN
    public void switchToLogin(Button bt) throws IOException {
        Stage stage=(Stage) bt.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene =new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    //METODO DEL SALVA
    public void btSalvaOnAction(ActionEvent event) throws SQLException{
        //CONTROLLO SE TUTTI I CAMPI SONO PIENI
        textControll(tfNome);
        textControll(tfCognome);
        textControll(tfMail);
        textControll(tfUsername);
        textControll(pfPassword);
        textControll(pfConfermaPwd);

        if(tfNome.getText().length()==0 || tfCognome.getText().length()==0 || tfMail.getText().length()==0 ||
                tfUsername.getText().length()==0 || pfPassword.getText().length()==0 || pfConfermaPwd.getText().length()==0){
            lbMessage.setText("Inserire i campi mancanti!");
        }else{

            //CONVALIDARE LA REGSTRAZIONE
            User user=new User(tfUsername.getText(),pfPassword.getText());
            Dipendente dipendente=new Dipendente(user.getUuid(), tfNome.getText(), tfCognome.getText(), tfMail.getText(),tfUsername.getText());

            System.out.println(user.toString());
            System.out.println(dipendente.toString());

            //CONTROLLO 1: mail già esistente, mail non valida
            if(dipendente.mailExist()){
                setErrorStyle(tfMail);
                lbMessage.setText("Mail già esistente!");
                return;
            }else if(!dipendente.getMail().contains("@")){
                setErrorStyle(tfMail);
                lbMessage.setText("Mail non valida!");
                return;
            }else{
                setNormalStyle(tfMail);
            }

            //CONTROLLO 2: username già esistente
            if(dipendente.usernameExist()) {
                setErrorStyle(tfUsername);
                lbMessage.setText("Username già esistente!");
                return;
            }else{
                setNormalStyle(tfUsername);
            }

            //CONTROLLO 3:password sicura

            if(pfPassword.getLength()<8){
                setErrorStyle(pfPassword);
                setErrorStyle(pfConfermaPwd);
                lbMessage.setText("Per la tua password utilizza almeno 8 caratteri!");
                return;
            }else if(!user.isValidPassword()){
                setErrorStyle(pfPassword);
                setErrorStyle(pfConfermaPwd);
                lbMessage.setText("Scegli una password più sicura formata da caratteri e numeri!");
                return;
            }else{
                setNormalStyle(pfPassword);
                setNormalStyle(pfConfermaPwd);
            }

            //CONTROLLO 4: matching
            if(!pfPassword.getText().equals(pfConfermaPwd.getText())){
                setErrorStyle(pfConfermaPwd);
                pfConfermaPwd.clear();
                lbMessage.setText("Le password non corrispondono. Riprova!");
                return;
            }else{
                setNormalStyle(pfConfermaPwd);
                System.out.println("password match");
            }

            user.insert();
            dipendente.insert();

            /*
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alocasia Registration");
            alert.setHeaderText("Sei stato registrato con successo!");
            alert.setContentText("Clicca il tasto ok per passare al login");

            Optional<ButtonType> result=alert.showAndWait();
            if(result.isPresent() && (result.get()==ButtonType.OK || result.get()==ButtonType.CLOSE)){
                System.out.println("OK button clicked");

            }*/

            try{
                switchToLogin(btSalva);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }



        }
    }

}
