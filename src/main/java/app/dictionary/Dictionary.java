package app.dictionary;

import java.util.HashMap;
import java.util.Hashtable;

public class Dictionary {
    private HashMap<String, String> dictionary = new HashMap<>();

    /**
     * Default constructor
     */
    public Dictionary() {
        //Default constructor
    }

    /**
     * Constructor with parameters
     * @param dictionary
     */
    public Dictionary(HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public HashMap<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public void removeWord(String removedWord) {
        dictionary.remove(removedWord);
    }

    public void addWord(String addedWord, String addWordMeaning) {
        dictionary.put(addedWord, addWordMeaning);
    }
}
