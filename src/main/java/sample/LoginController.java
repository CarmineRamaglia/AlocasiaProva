package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.sql.SQLException;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends Controller implements Initializable{


    //DICHIARAZIONE DEI DATI

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btClose, btLogin;
    @FXML
    private Button btRegister;
    @FXML
    private Button btMinus;
    @FXML
    private ImageView lockImageView;
    @FXML
    private Label lbMessage;
    @FXML
    private ImageView logoSfondoImageView;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfUsername;

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
        //stage.setFullScreenExitHint("  ");
        //stage.setFullScreen(true);
    }

    //INIZIALIZZARE IMMAGINI
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoSfondoFile=new File("src/main/java/Aloimg/logoSfondo.jpg");
        Image logoSfondoImage=new Image(logoSfondoFile.toURI().toString());
        logoSfondoImageView.setImage(logoSfondoImage);

        File lockFile=new File("src/main/java/Aloimg/lock.png");
        Image lockImage=new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    //CLICK SU LOGIN
    public void btLoginOnAction(ActionEvent event) throws SQLException{
        System.out.println("click su login");
        loginMethod();
    }

    //METODO DEL LOGIN
    public void loginMethod() throws SQLException {
        textControll(tfUsername);
        textControll(pfPassword);

        if(tfUsername.getText().length()==0 || pfPassword.getText().length()==0){
            lbMessage.setText("Inserire Username e password!");

        }else {
            User user = new User(tfUsername.getText(), pfPassword.getText());
            System.out.println(user.toString());

            System.out.println(user.exist());
            if (user.exist()) {
                setNormalStyle(tfUsername);
                setNormalStyle(pfPassword);

                try {
                    Stage stage = (Stage) btLogin.getScene().getWindow();
                    stage.close();
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("alocasia.fxml"));
                    Scene scene = new Scene(root);

                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);
                    primaryStage.setTitle("Alocasia");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }





            } else {
                setErrorStyle(tfUsername);
                setErrorStyle(pfPassword);
                lbMessage.setText("Username o Password errata!");
            }
        }
    }

    //ENTER CLICK SU tfUsername E pfPassword
    public void keyPressedEnter() {
        tfUsername.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println("click su username");
                try {
                    loginMethod();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        pfPassword.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println("click su password");
                try {
                    loginMethod();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    //BOTTONE CANCELLA
    public void btCancellaOnAction(ActionEvent event){
        setNormalStyle(tfUsername);
        setNormalStyle(pfPassword);
        tfUsername.clear();
        pfPassword.clear();
    }

    //PASSARE ALLA SCHERMATA DI REGISTRAZIONE
    public void switchToRegistration(ActionEvent event) throws IOException {
        Stage stage=(Stage) btRegister.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("registration.fxml"));
        Scene scene =new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}