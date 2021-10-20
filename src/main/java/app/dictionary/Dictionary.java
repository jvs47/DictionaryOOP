package app.dictionary;

import java.util.Hashtable;

public class Dictionary {
    private Hashtable<Integer, Word> dictionary = new Hashtable<>();

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
    public Dictionary(Hashtable<Integer, Word> dictionary) {
        this.dictionary = dictionary;
    }

    public Hashtable<Integer, Word> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Hashtable<Integer, Word> dictionary) {
        this.dictionary = dictionary;
    }
}
