package sample;

import dbconnector.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ha {
    String matrUuid;
    String id;

    Statement statement;

    public Ha(String matrUuid, String id) {
        this.matrUuid = matrUuid;
        this.id = id;
    }

    public String getMatrUuid() {
        return matrUuid;
    }

    public void setMatrUuid(String matrUuid) {
        this.matrUuid = matrUuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toCSV(){
        return matrUuid + ";" + id;
    }
    @Override
    public String toString() {
        return "Ha{" +
                "matrUuid='" + matrUuid + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public boolean exist() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM ha WHERE matrUuid= '"+matrUuid+"' AND id='"+id+"'");
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
            statement.executeUpdate("INSERT INTO ha(matrUuid,id) values('"
                    +matrUuid+"','"+id+"')");

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
