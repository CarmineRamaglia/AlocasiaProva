package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Studente {
    UUID matrUuid;
    String allievo;
    String cognome;
    String nome;
    String annoCorso;

    Statement statement;

    public Studente(String cognome, String nome, String annoCorso) {
        this.matrUuid = UUID.randomUUID();
        this.allievo=cognome+" "+nome;
        this.cognome = cognome;
        this.nome = nome;
        this.annoCorso = annoCorso;
    }

    public UUID getMatrUuid() {
        return matrUuid;
    }

    public void setMatrUuid(UUID matrUuid) {
        this.matrUuid = matrUuid;
    }

    public String getAllievo() {
        return allievo;
    }

    public void setAllievo(String allievo) {
        this.allievo = allievo;
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

    public String toCSV(){
        return matrUuid + ";" + allievo + ";" + cognome + ";" + nome + ";" + annoCorso;
    }

    @Override
    public String toString() {
        return "Studente{" +
                "matrUuid=" + matrUuid +
                ", allievo='" + allievo + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", annoCorso='" + annoCorso + '\'' +
                '}';
    }

    public void delete() throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("DELETE FROM studente WHERE nome='"+nome+"' AND cognome='"+cognome+"'");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public boolean exist() throws SQLException{
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM studente WHERE nome = '"+nome+"' AND cognome='"+cognome+"'");
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    res=true;
                } else {
                    res= false;
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return res;
    }

    public void insert() throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("INSERT INTO studente(matrUuid,allievo,cognome,nome,annoCorso) values('"
                    +matrUuid+ "','"+allievo+"','"+cognome+"','"+nome+"','"+annoCorso+"')");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void MySQLConnection() throws SQLException {
        DBManager.setConnection(
                DBManager.JDBC_Driver_MySQL,
                DBManager.JDBC_URL_MySQL);
        statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }
}
