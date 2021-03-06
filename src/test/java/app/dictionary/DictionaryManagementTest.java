package app.dictionary;

import app.helper.IODatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryManagementTest {
    IODatabase ioDatabase = new IODatabase();
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();

    @Test
    void findWord() {
    }

    @Test
    void binarySearch() {
        assertAll(() -> assertEquals("hello", dictionaryManagement.binarySearch("hello").getWord()),
                () -> assertEquals("abstract", dictionaryManagement.binarySearch("abstract").getWord()),
                () -> assertNull(dictionaryManagement.binarySearch("jdfshajdafjafh")),
                () -> assertEquals("go", dictionaryManagement.binarySearch("go").getWord())
                );
    }

    @Test
    void searchUseQuery() {
        assertAll(() -> assertEquals("hello", dictionaryManagement.searchUseQuery("hello").getWord()),
                () -> assertEquals("abstract", dictionaryManagement.searchUseQuery("abstract").getWord()),
                () -> assertNull(dictionaryManagement.searchUseQuery("jdfshajdafjafh")),
                () -> assertEquals("go", dictionaryManagement.searchUseQuery("go").getWord())
        );
    }
}