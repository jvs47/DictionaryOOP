package app.actions;

import app.dictionary.DictionaryManagement;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryAct extends DictionaryManagement {

    public ArrayList<String> getStringFoundWord(String foundWord) {
        ArrayList<String> results = new ArrayList<>();
        TreeMap<String, String> found = findWord(foundWord);
        for (Map.Entry<String, String> entry : found.entrySet()) {
            results.add(entry.getKey());
        }
        return results;
    }

}