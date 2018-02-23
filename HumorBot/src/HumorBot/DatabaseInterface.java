package HumorBot;

import java.sql.*;

public class DatabaseInterface {

    // this variable will hold if the connection is currently established
    public boolean connected = false;

    // This variable stores the connection to the database
    private Connection conn;

    // This variable stores the sql statment to be sent to the database
    private Statement stmt;

    // Constructor for the Interface
    public DatabaseInterface() throws ConnectionNotEstablishedException {
        connect();
    }

    // this function will either connect to the database or throw an error no need for a return value
    public void connect() throws ConnectionNotEstablishedException {
        System.out.println("establishing connection...");
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // this will ceate the connection
            this.conn = DriverManager.getConnection("jdbc:mysql://128.211.255.58:3306/humorbot", "sqluser", "sqluserpw");
            // this will create the statment through which queries are passed
            stmt = conn.createStatement();
        } catch (SQLException e) {
            // this exception occurs if the connection can not be established for any reason
            // we print information to the console for ease of debuging, and then throw a more general exception up
            System.out.println("connection failed to be established");
            System.out.println(e.getMessage());
            throw new ConnectionNotEstablishedException();
        } catch (ClassNotFoundException e) {
            // This exception happens if the JDBC library can not be found. Check that the project is set up correctly
            System.out.println("failed to load JDBC drivers");
            throw new ConnectionNotEstablishedException();
        }
        // if no excceptions have been called we are okay and we should tell the user
        System.out.println("connection established");
        connected = true;
    }

    public void closeConnection(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("closing invalid connection");
            }
        }
        System.out.println("connection closed");
        connected = false;
    }

    protected void executeCustomQuery(String query) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("executed: " + query);
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
        }
    }

    public WhiteCard getWhiteCard(String name) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // create the query to add an item to the table
        String query = "Select * from white_card where name='" + name + "'";
        try {
            // executes the query and returns true on success
            ResultSet rs = stmt.executeQuery(query);
            // check that we have found it
            if(rs.next()){
                // make the white card
                WhiteCard res = new WhiteCard(rs.getString("name"));
                // check we have not found multiple
                if(rs.next()){
                    // handle if multiple cards are added to the database
                    System.out.println("Multiple white cards in database with that name");
                    System.out.println("Database deprecated!");
                    return null;
                }
                // return result if no other errors
                return res;
            } else {
                // handle no white card not in database
                System.out.println("No white card with that name!");
                return null;
            }
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return null;
        }
    }

    public WhiteCard getWhiteCard(WhiteCard card) throws ConnectionNotEstablishedException {
        return getWhiteCard(card.getAnswer());
    }

    public BlackCard getBlackCard(String name) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // create the query to add an item to the table
        String query = "Select * from black_card where name='" + name + "'";
        try {
            // executes the query and returns true on success
            ResultSet rs = stmt.executeQuery(query);
            // check that we have found it
            if(rs.next()){
                // make the white card
                BlackCard res = new BlackCard(rs.getInt("number_of_blanks"), rs.getString("name"));
                // check we have not found multiple
                if(rs.next()){
                    // handle if multiple cards are added to the database
                    System.out.println("Multiple black cards in database with that name");
                    System.out.println("Database deprecated!");
                    return null;
                }
                // return result if no other errors
                return res;
            } else {
                // handle no white card not in database
                System.out.println("No black card with that name!");
                return null;
            }
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return null;
        }
    }

    public BlackCard getBlackCard(BlackCard card){
        // TODO
        return null;
    }

    public boolean addWhiteCard(String name) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // create the query to add an item to the table
        String query = "INSERT INTO white_card (name) VALUES ('" + name + "');";
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("white card \"" + name + "\" added to database");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return false;
        }
    }

    // overloaded function to add from class
    public boolean addWhiteCard(WhiteCard card) throws ConnectionNotEstablishedException {
        return addWhiteCard(card.getAnswer());
    }

    public boolean addBlackCard(String name, int number_of_blanks) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // create the query to add an item to the table
        String query = "INSERT INTO black_card (name, number_of_blanks) VALUES ('" + name + "', " + number_of_blanks + ");";
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("black card \"" + name + "\" added to database");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return false;
        }
    }

    // overloaded function to add from class
    public boolean addBlackCard(BlackCard card) throws ConnectionNotEstablishedException {
        return addBlackCard(card.getQuestion(), card.getBlanks());
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
