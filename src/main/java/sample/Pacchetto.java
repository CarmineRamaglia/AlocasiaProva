package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pacchetto {
    String id;
    double prezzo;
    String tipo;
    double percTutor;
    double percVenditore;
    double percAltro;

    Statement statement;

    public Pacchetto(double prezzo, String tipo, double percTutor, double percVenditore, double percAltro) {
        this.id= String.valueOf(prezzo) +" "+tipo;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.percTutor = percTutor;
        this.percVenditore = percVenditore;
        this.percAltro = percAltro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPercTutor() {
        return percTutor;
    }

    public void setPercTutor(double percTutor) {
        this.percTutor = percTutor;
    }

    public double getPercVenditore() {
        return percVenditore;
    }

    public void setPercVenditore(double percVenditore) {
        this.percVenditore = percVenditore;
    }

    public double getPercAltro() {
        return percAltro;
    }

    public void setPercAltro(double percAltro) {
        this.percAltro = percAltro;
    }


    public String toCSV() {
        return id+";"+prezzo + ";" + tipo + ";" + percTutor + ";" + percVenditore + ";" + percAltro;
    }

    @Override
    public String toString() {
        return "Pacchetto{" +"id="+id+
                "prezzo=" + prezzo +
                ", tipo=" + tipo +
                ", percTutor=" + percTutor +
                ", percVenditore=" + percVenditore +
                ", percAltro=" + percAltro +
                '}';
    }

    public boolean exist() throws SQLException{
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM pacchetto WHERE prezzo = "+prezzo+" AND tipo='"+tipo+"'");
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
            statement.executeUpdate("INSERT INTO pacchetto(id,prezzo,tipo,percTutor,percVenditore,percAltro) values('"
                   +id+ "',"+prezzo+",'"+tipo+"',"+percTutor+","+percVenditore+","+percAltro+")");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void modify() throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("UPDATE pacchetto SET percTutor= "+percTutor+
                    ", percVenditore= "+percVenditore+", percAltro="+percAltro+" WHERE prezzo="+prezzo+" AND tipo='"+tipo+"'");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void delete() throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("DELETE FROM pacchetto WHERE prezzo="+prezzo+" AND tipo='"+tipo+"'");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void edit(String value,String newValue) throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("UPDATE pacchetto SET "+value+"='"+newValue+"' WHERE prezzo="+prezzo+" AND tipo='"+tipo+"'");

            System.out.println("UPDATE pacchetto SET "+value+"='"+newValue+"' WHERE prezzo="+prezzo+" AND tipo='"+tipo+"'");
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
