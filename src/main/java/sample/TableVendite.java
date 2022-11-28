package sample;

import java.sql.Date;

public class TableVendite {
    Integer num;
    Date data;
    String allievo;
    Integer oreAcquistate;
    String venditore;
    String pacchetto;

    public TableVendite(Integer num, Date data, String allievo, Integer oreAcquistate, String venditore, String pacchetto) {
        this.num = num;
        this.data = data;
        this.allievo = allievo;
        this.oreAcquistate = oreAcquistate;
        this.venditore = venditore;
        this.pacchetto = pacchetto;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAllievo() {
        return allievo;
    }

    public void setAllievo(String allievo) {
        this.allievo = allievo;
    }

    public Integer getOreAcquistate() {
        return oreAcquistate;
    }

    public void setOreAcquistate(Integer oreAcquistate) {
        this.oreAcquistate = oreAcquistate;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public String getPacchetto() {
        return pacchetto;
    }

    public void setPacchetto(String pacchetto) {
        this.pacchetto = pacchetto;
    }

    @Override
    public String toString() {
        return "TableVendita{" +
                "num=" + num +
                ", data=" + data +
                ", allievo='" + allievo + '\'' +
                ", oreAcquistate=" + oreAcquistate +
                ", venditore='" + venditore + '\'' +
                ", pacchetto='" + pacchetto + '\'' +
                '}';
    }
}
