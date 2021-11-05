package app.actions;

import app.helper.IODatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddActTest {
    IODatabase ioDatabase = new IODatabase();
    private final AddAct addAct = new AddAct();

    @Test
    void convertToHTML() {
        assertAll(
                () -> assertEquals("<h1>abortifacient</h1><h3><i>/ə'bɔ:ti'feiʃənt/</i></h3><h2>phá thai, làm sẩy thai</h2>", addAct.convertToHTML("abortifacient", "ə'bɔ:ti'feiʃənt", "phá thai, làm sẩy thai"))
        );
    }

    @Test
    void checkValidWord() {
    }

    @Test
    void getDictionary() {
    }
}