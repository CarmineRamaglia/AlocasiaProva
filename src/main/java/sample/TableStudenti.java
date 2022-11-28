package sample;

import javafx.fxml.FXML;

public class TableStudenti {
    String cognome;
    String nome;
    String annoCorso;
    String pacchetto;
    Integer oreAcquistate;
    Integer oreFatte;
    Integer oreDisponibili;

    public TableStudenti(String cognome, String nome, String annoCorso, String pacchetto, Integer oreAcquistate, Integer oreFatte, Integer oreDisponibili) {
        this.cognome = cognome;
        this.nome = nome;
        this.annoCorso=annoCorso;
        this.pacchetto = pacchetto;
        this.oreAcquistate = oreAcquistate;
        this.oreFatte = oreFatte;
        this.oreDisponibili = oreDisponibili;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(String annoCorso) {
        this.annoCorso = annoCorso;
    }

    public String getPacchetto() {
        return pacchetto;
    }

    public void setPacchetto(String pacchetto) {
        this.pacchetto = pacchetto;
    }

    public Integer getOreAcquistate() {
        return oreAcquistate;
    }

    public void setOreAcquistate(Integer oreAcquistate) {
        this.oreAcquistate = oreAcquistate;
    }

    public Integer getOreFatte() {
        return oreFatte;
    }

    public void setOreFatte(Integer oreFatte) {
        this.oreFatte = oreFatte;
    }

    public Integer getOreDisponibili() {
        return oreDisponibili;
    }

    public void setOreDisponibili(Integer oreDisponibili) {
        this.oreDisponibili = oreDisponibili;
    }

    @Override
    public String toString() {
        return "TableStudenti{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", annoCorso='" + annoCorso + '\'' +
                ", pacchetto='" + pacchetto + '\'' +
                ", oreAcquistate=" + oreAcquistate +
                ", oreFatte=" + oreFatte +
                ", oreDisponibili=" + oreDisponibili +
                '}';
    }
}
