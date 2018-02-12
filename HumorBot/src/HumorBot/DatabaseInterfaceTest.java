package HumorBot;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DatabaseInterfaceTest {

    @Test
    void TestConnection() {
        boolean result = true;
        try {
            DatabaseInterface db = new DatabaseInterface();
        } catch (ConnectionNotEstablishedException e) {
            result = false;
        }
        assert(result);
    }

}