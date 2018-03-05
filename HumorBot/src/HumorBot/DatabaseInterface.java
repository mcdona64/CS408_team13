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

    public BlackCard getBlackCard(BlackCard card) throws ConnectionNotEstablishedException {
        return getBlackCard(card.getQuestion());
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

    public boolean removeWhiteCard(String name) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // check that the card is actually in the database
        if (null == getWhiteCard(name)){
            System.out.println("Card not in database!");
            return false;
        }

        // create the query to remove a white card to the table
        String query = "DELETE FROM white_card WHERE name='" + name + "'";
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("white card \"" + name + "\" removed from database");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return false;
        }
    }

    public boolean removeWhiteCard(WhiteCard card) throws ConnectionNotEstablishedException {
        return removeWhiteCard(card.getAnswer());
    }

    public boolean removeBlackCard(String name) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // check that the card is actually in the database
        if (null == getBlackCard(name)){
            System.out.println("Card not in database!");
            return false;
        }

        // create the query to remove a white card to the table
        String query = "DELETE FROM black_card WHERE name='" + name + "'";
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("black card \"" + name + "\" removed from database");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return false;
        }
    }

    public boolean removeBlackCard(BlackCard card) throws ConnectionNotEstablishedException {
        return removeBlackCard(card.getQuestion());
    }

    // returns -1 on not found
    public int getWeight(String winner, String blackcard) throws ConnectionNotEstablishedException{
        return -1;
    }

    public int getWeight(WhiteCard winner, String blackcard) throws ConnectionNotEstablishedException{
        return getWeight(winner.getAnswer(), blackcard);
    }

    public int getWeight(String winner, BlackCard blackcard) throws ConnectionNotEstablishedException{
        return getWeight(winner, blackcard.getQuestion());
    }

    public int getWeight(WhiteCard winner, BlackCard blackcard) throws ConnectionNotEstablishedException{
        // check to see if the combo is already added
        return getWeight(winner.getAnswer(), blackcard.getQuestion());
    }

    public int[] getBestPermitation(String blackcard, String[] whiteCards, int numberOfBlanks) throws ConnectionNotEstablishedException {
        // TODO
        int[] bad = {};
        return bad;
    }

    public int[] getBestPermitation(String blackcard, WhiteCard[] whiteCards, int numberOfBlanks) throws ConnectionNotEstablishedException{
        String[] cards = new String[numberOfBlanks];
        for (int i = 0; i < numberOfBlanks; i++){
            cards[i] = whiteCards[i].getAnswer();
        }
        return getBestPermitation(blackcard, cards, numberOfBlanks);
    }

    public int[] getBestPermitation(BlackCard blackcard, String[] whiteCards, int numberOfBlanks) throws ConnectionNotEstablishedException{
        return getBestPermitation(blackcard.getQuestion(), whiteCards, numberOfBlanks);
    }

    public int[] getBestPermitation(BlackCard blackcard, WhiteCard[] whiteCards, int numberOfBlanks) throws ConnectionNotEstablishedException{
        String[] cards = new String[numberOfBlanks];
        for (int i = 0; i < numberOfBlanks; i++){
            cards[i] = whiteCards[i].getAnswer();
        }
        return getBestPermitation(blackcard.getQuestion(), cards, numberOfBlanks);
    }


    // wc stands for weight change
    // pass in the amount you want the weight to be adjusted negative for less
    public boolean adjustWeights(String winner, String blackcard) throws ConnectionNotEstablishedException {
        // check to see if the combo is already added
        if (getWeight(winner, blackcard) == -1){
            System.out.println("card combo not in database");
            // add combo if it is not in the database
            boolean res = addCombo(winner, blackcard);
            // check that it was added correctly
            if (res){
                System.out.println("failed to add combo");
                return false;
            }

        }

        // get the ids of the cards we want
        int whiteCardID = getWhiteCardID(winner);
        int blackCardID = getBlackCardID(blackcard);

        // check for bad values
        if (whiteCardID == -1) {
            System.out.println("white card not in database");
            boolean res  = addWhiteCard(winner);
            if (!res){
                System.out.println("unable to add white card to database");
                return false;
            }
        } else if (whiteCardID < -1){
            System.out.println("Error with getting white card database could be depricated");
            return false;
        }
        if (blackCardID == -1) {
            System.out.println("Black card not in database");
            boolean res = addBlackCard(blackcard, 1);
            if (!res) {
                System.out.println("unable to add black card to database");
                return false;
            }
        }else if (blackCardID < -1){
            System.out.println("Error with getting black card database could be depricated");
            return false;
        }

        // create the query to remove a white card to the table
        String query = "UPDATE combonations SET weight = weight+1 WHERE white_card_id='" + whiteCardID + "' AND black_card_id='"
                + blackCardID + "';";
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("combo of " + winner + " and " + blackcard + " weight updated");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return false;
        }
    }

    public boolean adjustWeights(String winner, BlackCard blackcard) throws ConnectionNotEstablishedException {
        return adjustWeights(winner, blackcard.getQuestion());
    }

    public boolean adjustWeights(WhiteCard winner, String blackcard) throws ConnectionNotEstablishedException{
        return adjustWeights(winner.getAnswer(), blackcard);
    }
    public boolean adjustWeights(WhiteCard whitecard, BlackCard blackcard) throws ConnectionNotEstablishedException{
        return adjustWeights(whitecard.getAnswer(), blackcard.getQuestion());
    }

    public boolean adjustWeightsMultiple(WhiteCard[] whitecard, BlackCard blackcard, int numberOfBlanks) throws ConnectionNotEstablishedException{
        return false;
    }

    public int getWhiteCardID(String name) throws ConnectionNotEstablishedException {
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
                int res = rs.getInt("id");
                // check we have not found multiple
                if(rs.next()){
                    // handle if multiple cards are added to the database
                    System.out.println("Multiple white cards in database with that name");
                    System.out.println("Database deprecated!");
                    return -2;
                }
                // return result if no other errors
                System.out.println("ID: " + res);
                return res;
            } else {
                // handle no white card not in database
                System.out.println("No white card with that name!");
                return -1;
            }
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return -3;
        }
    }

    public int getBlackCardID(String name) throws ConnectionNotEstablishedException {
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
                int res = rs.getInt("id");
                // check we have not found multiple
                if(rs.next()){
                    // handle if multiple cards are added to the database
                    System.out.println("Multiple white cards in database with that name");
                    System.out.println("Database deprecated!");
                    return -2;
                }
                // return result if no other errors
                System.out.println("ID: " + res);
                return res;
            } else {
                // handle no white card not in database
                System.out.println("No black card with that name!");
                return -1;
            }
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            closeConnection();
            return -3;
        }
    }

    public boolean addCombo(String whitecard, String blackcard) throws ConnectionNotEstablishedException {
        // if we are currently unconnected connect
        if (!connected){
            connect();
        }

        // get the ids of the cards we want
        int whiteCardID = getWhiteCardID(whitecard);
        int blackCardID = getBlackCardID(blackcard);

        // check for bad values
        if (whiteCardID == -1) {
            System.out.println("white card not in database");
            boolean res  = addWhiteCard(whitecard);
            if (!res){
                System.out.println("unable to add white card to database");
                return false;
            }
        } else if (whiteCardID < -1){
            System.out.println("Error with getting white card database could be depricated");
            return false;
        }
        if (blackCardID == -1) {
            System.out.println("Black card not in database");
            boolean res = addBlackCard(blackcard, 1);
            if (!res) {
                System.out.println("unable to add black card to database");
                return false;
            }
        }else if (blackCardID < -1){
            System.out.println("Error with getting black card database could be depricated");
            return false;
        }

        // create the query to add an item to the table
        String query = "INSERT INTO combonations (white_card_id, black_card_id, weight) VALUES ("
                + whiteCardID + ", " + blackCardID + ", 0);";
        System.out.println(query);
        try {
            // executes the query and returns true on success
            stmt.execute(query);
            System.out.println("combo of " + whitecard + " and " + blackcard + " added to database");
            return true;
        } catch (SQLException e) {
            // catch errors with the connection
            System.out.println("Error with connection!");
            System.out.println(e.getMessage());
            closeConnection();
            return false;
        }
    }

    public boolean addCombo(String whitecard, BlackCard blackcard) throws ConnectionNotEstablishedException {
        return addCombo(whitecard, blackcard.getQuestion());
    }

    public boolean addCombo(WhiteCard whitecard, String blackcard) throws ConnectionNotEstablishedException{
        return addCombo(whitecard.getAnswer(), blackcard);
    }

    public boolean addCombo(WhiteCard whitecard, BlackCard blackcard)throws ConnectionNotEstablishedException{
        return addCombo(whitecard.getAnswer(), blackcard.getQuestion());
    }

    public boolean addComboMultipleBlanks(WhiteCard[] whiteCards, BlackCard blackCard, int numberOfBlanks) throws ConnectionNotEstablishedException{
        // TODO
        return false;
    }

    public int getAverageWeight(String whitecard) throws ConnectionNotEstablishedException{
        //TODO
        return -1;
    }

    public int getAverageWeight(WhiteCard whiteCard) throws ConnectionNotEstablishedException{
        return getAverageWeight(whiteCard.getAnswer());
    }


}
