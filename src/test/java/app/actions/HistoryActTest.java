package app.actions;

import app.helper.IODatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryActTest {
    IODatabase ioDatabase = new IODatabase();
    private final HistoryAct historyAct = new HistoryAct();

    @Test
    void getHistoryString() {

    }

    @Test
    void foundWordFromHistoryDatabase() {
    }

    @Test
    void binarySearchHistory() {
        assertAll(
                () -> assertEquals("hello", historyAct.binarySearchHistory("hello").getWord())
        );
    }

    @Test
    void searchUseQueryHistory() {
    }
}