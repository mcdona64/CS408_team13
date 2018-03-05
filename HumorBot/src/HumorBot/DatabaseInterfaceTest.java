package HumorBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

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
        tearDown();
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
        tearDown();
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
        tearDown();
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
        tearDown();
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
        tearDown();
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestGetWhiteCard() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.addWhiteCard("test");
            WhiteCard res = db.getWhiteCard("test");
            if (res != null && res.getAnswer().equals("test")){
                result = true;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetWhiteCardNotInDB() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            WhiteCard res = db.getWhiteCard("test");
            if (res != null && res.getAnswer().equals("test")){
                result = false;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetWhiteCardWithMultipleEntries() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            db.addWhiteCard("test");
            db.addWhiteCard("test");
            WhiteCard res = db.getWhiteCard("test");
            if (res != null && res.getAnswer().equals("test")){
                result = false;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = true;
        }
        tearDown();
        assert(result);
    }

    @Test
        // this test may fail if since actual value is not actually checked in this test case
    void TestGetBlackCard() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.addBlackCard("test",1);
            BlackCard res = db.getBlackCard("test");
            if (res != null && res.getQuestion().equals("test")){
                result = true;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetBlackCardNotInDB() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            BlackCard res = db.getBlackCard("test");
            if (res != null && res.getQuestion().equals("test")){
                result = false;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetBlackCardWithMultipleEntries() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            db.addBlackCard("test",1);
            db.addBlackCard("test",1);
            BlackCard res = db.getBlackCard("test");
            if (res != null && res.getQuestion().equals("test")){
                result = false;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = true;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestRemoveWhiteCard() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            db.addWhiteCard("test");
            result = db.removeWhiteCard("test");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestRemoveWhiteCardNotInDB() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.removeWhiteCard("test");
            if (!result) {result = true;}
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestRemoveBlackCard() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            db.addBlackCard("test", 1);
            result = db.removeBlackCard("test");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestRemoveBlackCardNotInDB() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.closeConnection();
            result = db.removeBlackCard("test");
            if (!result) {result = true;}
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestAddCombo() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            result = db.addCombo("test", "test");
            if (!result) {result = true;}
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }


    public static void tearDown() {
        // this will clean up the database after the tests are run
        System.out.println("Cleaning up");
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.executeCustomQuery("delete from white_card where (name='test');");
            db.executeCustomQuery("delete from black_card where (name='test');");
            db.executeCustomQuery("ALTER TABLE white_card AUTO_INCREMENT = 1;");
            db.executeCustomQuery("ALTER TABLE black_card AUTO_INCREMENT = 1;");
            System.out.println("Database Cleaned");
        } catch (ConnectionNotEstablishedException e) {
            System.err.println("No Connection Established!");
            System.err.println("Database in unknown state!");
            System.err.println("Manual check needed!");
        }
    }

}