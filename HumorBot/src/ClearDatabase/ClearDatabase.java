package ClearDatabase;

import HumorBot.ConnectionNotEstablishedException;
import HumorBot.DatabaseInterface;

public class ClearDatabase {
    public static void main(String[] args){
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.executeCustomQuery("update losses set losses = 0;");
            db.executeCustomQuery("update wins set wins = 0;");
            db.executeCustomQuery("delete from black_card;");
            db.executeCustomQuery("delete from white_card;");
            db.executeCustomQuery("delete from combonations;");
            db.executeCustomQuery("delete from combonations2blanks;");
            db.executeCustomQuery("delete from combonations3blanks;");
            db.executeCustomQuery("delete from combonations4blanks;");
        } catch (ConnectionNotEstablishedException e) {
            System.out.println("Can not connect to the database!");
        }
    }
}
