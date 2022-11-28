package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Dipendente {
    UUID uuid;
    String nome;
    String cognome;
    String mail;
    String username;
    Integer tutor;
    Integer venditore;
    Integer superUtente;

    Statement statement;

    public Dipendente(UUID uuid, String nome,String cognome, String mail,String username){
        this.uuid=uuid;
        this.nome=nome;
        this.cognome=cognome;
        this.mail=mail;
        this.username=username;
        this.tutor=0;
        this.venditore=0;
        this.superUtente=0;
    }

    public Dipendente(UUID uuid,String nome,String cognome, String mail,String username, Integer tutor, Integer venditore, Integer superUtente){
        this.uuid=uuid;
        this.nome=nome;
        this.cognome=cognome;
        this.mail=mail;
        this.username=username;
        this.tutor=tutor;
        this.venditore=venditore;
        this.superUtente=superUtente;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTutor() {
        return tutor;
    }

    public void setTutor(Integer tutor) {
        this.tutor = tutor;
    }

    public Integer getVenditore() {
        return venditore;
    }

    public void setVenditore(Integer venditore) {
        this.venditore = venditore;
    }

    public Integer getSuperUtente() {
        return superUtente;
    }

    public void setSuperUtente(Integer superUtente) {
        this.superUtente = superUtente;
    }

    public String toCSV(){
        return uuid + ";" + nome + ";" + cognome + ";" + mail + ";" + username + ";" + tutor + ";" + venditore +
                ";" + superUtente;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "uuid=" + uuid +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", tutor=" + tutor +
                ", venditore=" + venditore +
                ", superUtente=" + superUtente +
                '}';
    }

    public boolean mailExist() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM dipendente WHERE BINARY mail = '"+getMail()+"'");
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

    public boolean usernameExist() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM dipendente WHERE BINARY mail = '"+getUsername()+"'");
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
            statement.executeUpdate("INSERT INTO dipendente(uuid,nome,cognome,mail,username,tutor,venditore,superUtente) values('"
                    +getUuid()+"','"+getNome()+"','"+getCognome()+"','"+getMail()+"','"+getUsername()+"','"+
                    getTutor()+"','"+getVenditore()+"','"+getSuperUtente()+"')");

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

