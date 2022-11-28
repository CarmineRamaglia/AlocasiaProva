package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

public class Lezione {
    String data;
    String matrUuid;
    String uuid;
    Integer oreFatte;
    String id;

    Statement statement;


    public Lezione(String data, String matrUuid, String uuid, Integer oreFatte, String id) {
        this.data = data;
        this.matrUuid = matrUuid;
        this.uuid = uuid;
        this.oreFatte = oreFatte;
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMatrUuid() {
        return matrUuid;
    }

    public void setMatrUuid(String matrUuid) {
        this.matrUuid = matrUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOreFatte() {
        return oreFatte;
    }

    public void setOreFatte(Integer oreFatte) {
        this.oreFatte = oreFatte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toCSV() {
        return data+";"+matrUuid + ";" + uuid + ";" + oreFatte + ";" + id;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "data=" + data +
                ", matrUuid=" + matrUuid +
                ", uuid=" + uuid +
                ", oreFatte=" + oreFatte +
                ", id='" + id + '\'' +
                '}';
    }

    public boolean exist() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM lezione WHERE data='"+data+
                    "' AND matrUuid='"+matrUuid+"' AND uuid='"+uuid+"' AND id='"+id+"' ");
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
            statement.executeUpdate("INSERT INTO lezione(data,matrUuid,uuid,oreFatte,id) values('"
                    +data+ "','"+matrUuid+"','"+uuid+"',"+oreFatte+",'"+id+"')");
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