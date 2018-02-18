package HumorBot;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DatabaseInterfaceTest {

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
            result = db.addWhiteCard("Test");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestAddBlackCard() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            result = db.addBlackCard("Test", 1);
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

}