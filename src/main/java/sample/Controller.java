package sample;

import dbconnector.DBManager;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller  {

    //SET DEL COLORE BORDO ROSSO CIOE' DEL COLORE "ERRATO"
    public void setErrorStyle(TextField text){
        text.setStyle(" -fx-background-radius: 20; -fx-border-color: red; -fx-border-radius: 20; -fx-prompt-text-fill: #e3857f;");
    }
    public void setErrorStyle(ChoiceBox box){
        box.setStyle(" -fx-background-radius: 20; -fx-border-color: red; -fx-border-radius: 20;");
    }
    public void setErrorStyle(DatePicker date){
        date.setStyle(" -fx-border-color: red");
    }

    //SET DEL COLORE BORDO NORMALE
    public void setNormalStyle(TextField text){
        text.setStyle(null);
        text.setStyle("-fx-background-radius: 20");
    }
    public void setNormalStyle(ChoiceBox box){
        box.setStyle(null);
        box.setStyle("-fx-background-radius: 20");
    }
    public void setNormalStyle(DatePicker date){
        date.setStyle(null);
    }


    //CONTROLLO DI UN CAMPO TEXT_FIELD E PASSWORD_FIELD
    public void textControll(TextField text){
        if(text.getText().length()==0){
            setErrorStyle(text);
        }   else{
            setNormalStyle(text);
        }
    }//CONTROLLO CHOICHEBOX
    public void textControll(ChoiceBox box){
        if(box.getValue()==null){
            setErrorStyle(box);
        }   else{
            setNormalStyle(box);
        }
    }
    public void textControll(DatePicker date){
        if(date.getValue()==null){
            setErrorStyle(date);
        }   else{
            setNormalStyle(date);
        }
    }

}

