package sample;


import dbconnector.DBManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableLezioni {
    Date data;
    String mese;
    String allievo;
    Integer oreFatte;
    String docente;
    String pacchetto;


    public TableLezioni(Date data, String allievo, Integer oreFatte, String docente, String pacchetto) {
        this.data = data;
        this.mese = getNomeMese(data);
        this.allievo = allievo;
        this.oreFatte = oreFatte;
        this.docente = docente;
        this.pacchetto = pacchetto;
    }


    private String getNomeMese(Date date){
        int numMese=date.getMonth();
        String mese="";
        switch (numMese){
            case 0: mese="Gennaio";
                break;
            case 1: mese="Febbraio";
                break;
            case 2: mese="Marzo";
                break;
            case 3: mese="Aprile";
                break;
            case 4: mese="Maggio";
                break;
            case 5: mese="Giugno";
                break;
            case 6: mese="Luglio";
                break;
            case 7: mese="Agosto";
                break;
            case 8: mese="Settembre";
                break;
            case 9: mese="Ottobre";
                break;
            case 10: mese="Novembre";
                break;
            case 11: mese="Dicembre";
                break;
        }
        return mese;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getAllievo() {
        return allievo;
    }

    public void setAllievo(String allievo) {
        this.allievo = allievo;
    }

    public Integer getOreFatte() {
        return oreFatte;
    }

    public void setOreFatte(Integer oreFatte) {
        this.oreFatte = oreFatte;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getPacchetto() {
        return pacchetto;
    }

    public void setPacchetto(String pacchetto) {
        this.pacchetto = pacchetto;
    }

    @Override
    public String toString() {
        return "TableLezioni{" +
                "data=" + data +
                ", mese='" + mese + '\'' +
                ", allievo='" + allievo + '\'' +
                ", oreFatte=" + oreFatte +
                ", docente='" + docente + '\'' +
                ", pacchetto='" + pacchetto + '\'' +
                '}';
    }

}
