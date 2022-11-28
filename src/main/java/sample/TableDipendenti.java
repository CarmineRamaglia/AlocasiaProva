package sample;

import dbconnector.DBManager;
import javafx.scene.control.CheckBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDipendenti {
    String uuid;
    String cognome;
    String tutor;
    String venditore;
    String superUtente;
    Integer oreFatteTot;
    Integer oreVenduteTot;
    Double importoOreFatte;
    Double importoOreVendute;
    Double importoTot;


    public TableDipendenti(String uuid, String cognome, Integer intTutor, Integer intVenditore, Integer intSuperUtente, Integer oreFatteTot, Integer oreVenduteTot, Double importoOreFatte, Double importoOreVendute, Double importoTot) {
        this.uuid = uuid;
        this.cognome = cognome;
        if(intTutor==1) {
            this.tutor="si";
        }else{
            this.tutor="no";
        }

        if(intVenditore==1) {
            this.venditore="si";
        }else{
            this.venditore="no";
        }

        if(intSuperUtente==1) {
            this.superUtente="si";
        }else{
            this.superUtente="no";
        }
        this.oreFatteTot = oreFatteTot;
        this.oreVenduteTot = oreVenduteTot;
        this.importoOreFatte = importoOreFatte;
        this.importoOreVendute = importoOreVendute;
        this.importoTot = importoTot;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public String getSuperUtente() {
        return superUtente;
    }

    public void setSuperUtente(String superUtente) {
        this.superUtente = superUtente;
    }

    public Integer getOreFatteTot() {
        return oreFatteTot;
    }

    public void setOreFatteTot(Integer oreFatteTot) {
        this.oreFatteTot = oreFatteTot;
    }

    public Integer getOreVenduteTot() {
        return oreVenduteTot;
    }

    public void setOreVenduteTot(Integer oreVenduteTot) {
        this.oreVenduteTot = oreVenduteTot;
    }

    public Double getImportoOreFatte() {
        return importoOreFatte;
    }

    public void setImportoOreFatte(Double importoOreFatte) {
        this.importoOreFatte = importoOreFatte;
    }

    public Double getImportoOreVendute() {
        return importoOreVendute;
    }

    public void setImportoOreVendute(Double importoOreVendute) {
        this.importoOreVendute = importoOreVendute;
    }

    public Double getImportoTot() {
        return importoTot;
    }

    public void setImportoTot(Double importoTot) {
        this.importoTot = importoTot;
    }

    @Override
    public String toString() {
        return "TableDipendenti{" +
                "uuid='" + uuid + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tutor=" + tutor +
                ", venditore=" + venditore +
                ", superUtente=" + superUtente +
                ", oreFatteTot=" + oreFatteTot +
                ", oreVenduteTot=" + oreVenduteTot +
                ", importoOreFatte=" + importoOreFatte +
                ", importoOreVendute=" + importoOreVendute +
                ", importoTot=" + importoTot +
                '}';
    }


}
