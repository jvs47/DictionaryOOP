package app.dictionary;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Dictionary {
    Scanner scan = new Scanner(System.in);
    private TreeMap<String, String> dictionary = new TreeMap<>();

    /**
     * Default constructor
     */
    public Dictionary() {
        //Default constructor
    }

    /**
     * Constructor with parameters
     *
     * @param dictionary
     */
    public Dictionary(TreeMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public TreeMap<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(TreeMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public ArrayList<String> toArrayStringWord() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            String word = entry.getKey();
            result.add(word);
        }
        return result;
    }

    public boolean removeWord(Word deletedWord) {
        for (String key : dictionary.keySet()) {
            if (key.equals(deletedWord.getWord())) {
                return true;
            }
        }
        System.out.println("Word is not exist!");
        return false;
    }

    public boolean addWord(Word newWord) {
        for (String key : dictionary.keySet()) {
            if (key.equals(newWord.getWord())) {
                System.out.println(key + " exists in the dictionary!");
                return false;
            }
        }
        return true;
    }
}
