package sample;

import dbconnector.DBManager;
import javafx.scene.control.PasswordField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    UUID uuid;
    String username;
    String password;

    Statement statement;

    public User(UUID uuid, String username, String password) {
        this.uuid=uuid;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.uuid=UUID.randomUUID();
        this.username = username;
        this.password = password;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toCSV() {
        return uuid + ";" + username + ";" + password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean exist() throws SQLException {
        boolean res=false;
        try {
            MySQLConnection();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM user WHERE BINARY username = '" + getUsername()
                    + "' AND BINARY password ='" + getPassword() + "'");
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

    public boolean isValidPassword() {

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < getPassword().length(); i++) {

            char ch = getPassword().charAt(i);

            if(ch >= 'A' && ch <= 'z'){
                charCount++;
            }else if(ch >= '0' && ch <= '9'){
                numCount++;
            }

        }
        if (numCount>=2 && charCount>=2){
            return true;

        }else{
            return false;
        }
    }


     public void insert() throws SQLException{
        try {
            MySQLConnection();
            statement.executeUpdate("INSERT INTO user(uuid,username,password) values('"
                    +getUuid()+"','"+getUsername()+"','"+getPassword()+"')");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

/*
    public static List<User> loadFromDB(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM user")) {
            try (ResultSet rs = getUsers.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getString("username"),
                            rs.getString("password")));
                }
            }
        }
        return users;
    }

    public static void saveToDB(List<User> users, Connection connection) throws SQLException {

        try (PreparedStatement insertUser = connection.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)")) {
            for (User user : users) {
                insertUser.setString(1, user.getUsername());
                insertUser.setString(2, user.getPassword());
            }
        }
    }
*/



    public void MySQLConnection() throws SQLException {
        DBManager.setConnection(
                DBManager.JDBC_Driver_MySQL,
                DBManager.JDBC_URL_MySQL);
        statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

}


