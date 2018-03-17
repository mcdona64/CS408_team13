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
    void TestgetWhiteCardID() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            if (db.getWhiteCardID("test", false) >= 0){
                result = true;
            }
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestgetBlackCardID() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addBlackCard("test", 1);
            if (db.getBlackCardID("test", false) >= 0){
                result = true;
            }
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
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestAddComboMultipleEntriesWithSameName() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            result = db.addCombo("test", "test");
        } catch (ConnectionNotEstablishedException e) {
            result = true;
        }
        tearDown();
        assert(!result);
    }

    @Test
    void TestAdjustWeights() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            db.addCombo("test", "test");
            result = db.adjustWeights("test", "test");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetWeight() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            db.addCombo("test", "test");
            result = db.getWeight("test", "test") == 0;
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetWeightTwo() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test", 1);
            db.addCombo("test", "test");
            db.adjustWeights("test", "test");
            result = db.getWeight("test", "test") == 1;
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestAddComboMultipleBlanks() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addBlackCard("test", 2);
            String[] whitecards = {"test1", "test2"};
            result = db.addCombo(whitecards, "test", 2);
            db.executeCustomQuery("delete from combonations2blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }

    @Test
    void TestAddComboMultipleBlanksThreeBlanks() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addWhiteCard("test3");
            db.addBlackCard("test", 2);
            String[] whitecards = {"test1", "test2", "test3"};
            result = db.addCombo(whitecards, "test", 3);
            db.executeCustomQuery("delete from combonations3blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }


    @Test
    void TestGetAverageWeight() {
        boolean result = false;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test");
            db.addBlackCard("test1", 1);
            db.addCombo("test", "test1");
            db.adjustWeights("test", "test1");
            db.adjustWeights("test", "test1");
            db.addBlackCard("test2", 1);
            db.addCombo("test", "test2");
            db.adjustWeights("test", "test2");
            db.adjustWeights("test", "test2");
            db.addBlackCard("test3", 1);
            db.addCombo("test", "test3");
            db.adjustWeights("test", "test3");
            db.adjustWeights("test", "test3");
            int res = db.getAverageWeight("test");
            System.out.println(res);
            result = res == 2;
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        tearDown();
        assert(result);
    }

    @Test
    void TestGetWeightsMultipleBlanksThreeBlanks() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addWhiteCard("test3");
            db.addBlackCard("test", 2);
            String[] whitecards = {"test1", "test2", "test3"};
            db.addCombo(whitecards, "test", 3);
            result = db.getWeight(whitecards, "test") == 0;
            db.executeCustomQuery("delete from combonations3blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }

    @Test
    void TestAdjustWeightsMultipleBlanksThreeBlanks() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addWhiteCard("test3");
            db.addBlackCard("test", 2);
            String[] whitecards = {"test1", "test2", "test3"};
            db.addCombo(whitecards, "test", 3);
            result = db.adjustWeights(whitecards, "test", 3);
            db.executeCustomQuery("delete from combonations3blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }

    @Test
    void TestAdjustWeightsThenGetWeightsMultipleBlanksThreeBlanks() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addWhiteCard("test3");
            db.addBlackCard("test", 2);
            String[] whitecards = {"test1", "test2", "test3"};
            db.addCombo(whitecards, "test", 3);
            db.adjustWeights(whitecards, "test", 3);
            result = 1 == db.getWeight(whitecards, "test");
            db.executeCustomQuery("delete from combonations3blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }

    @Test
    void TestGetBestPermiatation() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            db.addWhiteCard("test1");
            db.addWhiteCard("test2");
            db.addWhiteCard("test3");
            db.addBlackCard("test", 2);
            String[] whitecards1 = {"test1", "test2", "test3"};
            String[] whitecards2 = {"test1", "test3", "test2"};
            String[] whitecards3 = {"test3", "test2", "test1"};
            db.addCombo(whitecards1, "test", 3);
            db.addCombo(whitecards2, "test", 3);
            db.addCombo(whitecards3, "test", 3);
            db.adjustWeights(whitecards1, "test", 3);
            int[] res = db.getBestPermitation(whitecards1, "test", 3);
            result = res[0] == 1 && res[1] == 2 && res[2] == 3;
            db.executeCustomQuery("delete from combonations3blanks where (black_card_id=" + db.getBlackCardID("test", false) + ");");
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }

        tearDown();
        assert(result);
    }

    @Test
    void Testgetwins() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            System.out.println(db.getwins());
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    void Testincwins() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            System.out.println(db.getwins());
            db.incwins();
            System.out.println(db.getwins());
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    void Testgetlosses() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            System.out.println(db.getlosses());
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    @Test
    void Testinclosses() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
            System.out.println(db.getlosses());
            db.inclosses();
            System.out.println(db.getlosses());
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

    public static void tearDown() {
        // this will clean up the database after the tests are run
        System.out.println("Cleaning up");
        try {
            DatabaseInterface db = new DatabaseInterface();
            while (db.getBlackCardID("REGEXP '^test'", true) >= 0 || db.getWhiteCardID("REGEXP '^test'", true) >=0) {
                int blackCardID = db.getBlackCardID("REGEXP '^test'", true);
                int whiteCardID = db.getWhiteCardID("REGEXP '^test'", true);
                db.executeCustomQuery("delete from combonations where (white_card_id=" + whiteCardID + " OR black_card_id=" + blackCardID + ");");
                db.executeCustomQuery("delete from white_card where (id='" + whiteCardID + "');");
                db.executeCustomQuery("delete from black_card where (id='" + blackCardID + "');");
            }
            db.executeCustomQuery("delete from white_card where (name REGEXP '^test');");
            db.executeCustomQuery("delete from black_card where (name REGEXP '^test');");
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