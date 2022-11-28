package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vendita {
    Integer num;
    String data;
    String matrUuid;
    String uuid;
    Integer oreAcquistate;
    String id;

    Statement statement;


    public Vendita(String data, String matrUuid, String uuid, Integer oreAcquistate, String id) {

        this.data = data;
        this.matrUuid = matrUuid;
        this.uuid = uuid;
        this.oreAcquistate = oreAcquistate;
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public Integer getOreAcquistate() {
        return oreAcquistate;
    }

    public void setOreAcquistate(Integer oreAcquistate) {
        this.oreAcquistate = oreAcquistate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toCSV() {
        return num+";"+data + ";" + matrUuid + ";" + uuid + ";" + oreAcquistate+";"+id;
    }


    @Override
    public String toString() {
        return "Vendita{" +
                "num=" + num +
                ", data='" + data + '\'' +
                ", matrUuid='" + matrUuid + '\'' +
                ", uuid='" + uuid + '\'' +
                ", oreAcquistate=" + oreAcquistate +
                ", id='" + id + '\'' +
                '}';
    }

    public boolean studenteHasPacchetto() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM ha WHERE matrUuid = '"+matrUuid+"'AND id='"+id+"' ");
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    //System.out.println("ce l'ha");
                    res=true;
                } else {
                    res= false;
                    //System.out.println("non ce l'ha");
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
            statement.executeUpdate("INSERT INTO vendita(data,matrUuid,uuid,oreAcquistate,id) values('"
                    +data+ "','"+matrUuid+"','"+uuid+"',"+oreAcquistate+",'"+id+"')");
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
