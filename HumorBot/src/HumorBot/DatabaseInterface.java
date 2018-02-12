package HumorBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInterface {

    // This variable stores the connection to the database
    private Connection con;

    // Constructor for the Interface
    public DatabaseInterface() throws ConnectionNotEstablishedException {
        System.out.println("establishing connection...");
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // this will ceate the connection
            this.con = DriverManager.getConnection("jdbc:mysql://128.211.255.58:3306/humorbot", "sqluser", "sqluserpw");

        } catch (SQLException e) {
            System.out.println("connection failed to be established");
            System.out.println(e.getMessage());
            throw new ConnectionNotEstablishedException();
        } catch (ClassNotFoundException e) {
            System.out.println("failed to load JDBC drivers");
            throw new ConnectionNotEstablishedException();
        }
        System.out.println("connection established");
    }

    public void closeConnection(){
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("closing invalid connection");
            }
        }
        System.out.println("connection closed");
    }

    public WhiteCard getWhiteCard(String name){
        // TODO
        return null;
    }

    public WhiteCard getWhiteCard(WhiteCard card){
        // TODO
        return null;
    }

    public BlackCard getBlackCard(String name){
        // TODO
        return null;
    }

    public BlackCard getBlackCard(BlackCard card){
        // TODO
        return null;
    }

    public int addWhiteCard(){
        // TODO
        return -1;
    }

    public int addBlackCard(){
        // TODO
        return -1;
    }

    public int removeWhiteCard() {
        // TODO
        return -1;
    }

    public int removeBlackCard() {
        // TODO
        return -1;
    }

    public int resetDB() {
        // TODO
        return -1;
    }

    public int adjustWeights(WhiteCard winner, BlackCard blackcard){
        // TODO
        return -1;
    }

    public int adjustWeights(String winner, BlackCard blackcard){
        // TODO
        return -1;
    }

    public int adjustWeights(WhiteCard winner, String blackcard){
        // TODO
        return -1;
    }

    public int adjustWeights(String winner, String blackcard){
        // TODO
        return -1;
    }
}
