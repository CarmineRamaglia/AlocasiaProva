package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class TestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Login");

        File fogliaFile=new File("src/main/java/Aloimg/foglia/white.png");
        Image fogliaImage=new Image(fogliaFile.toURI().toString());
        primaryStage.getIcons().add(fogliaImage);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){ launch(args);}
}
