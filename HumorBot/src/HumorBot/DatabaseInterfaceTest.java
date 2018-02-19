package HumorBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

public class DatabaseInterfaceTest {

    @Test
    // all other tests will fail if this test fails
    void TestConnection() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    void TestCloseConnection() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    void TestReConnect() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            db.connect();
            result = db.connected;
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    // this test may fail if since actual value is not actually checked in this test case
    void TestAddWhiteCard() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            result = db.addWhiteCard("test");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestAddWhiteCardFromClass() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            WhiteCard card = new WhiteCard("test");
            result = db.addWhiteCard(card);
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestAddWhiteCardNoConnection() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.addWhiteCard("test");
        } catch (ConnectionNotEstablishedException e) {
            result = true;
        }
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestAddBlackCard() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            result = db.addBlackCard("test", 1);
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestAddBlackCardNoConnection() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.addBlackCard("test", 1);
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @AfterClass
    public static void tearDown() {
        // this will clean up the database after the tests are run
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.executeCustomQuery("delete from white_card where (name='test');");
            db.executeCustomQuery("delete from black_card where (name='test');");
            db.executeCustomQuery("ALTER TABLE white_card AUTO_INCREMENT = 1;");
            db.executeCustomQuery("ALTER TABLE black_card AUTO_INCREMENT = 1;");
            System.out.println("\n\nDatabase Cleaned");
        } catch (ConnectionNotEstablishedException e) {
            System.err.println("No Connection Established!");
            System.err.println("Database in unknown state!");
            System.err.println("Manual check needed!");
        }
    }

}