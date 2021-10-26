package app.dictionary;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class Dictionary {
    private HashMap<String, String> dictionary = new HashMap<>();
    Scanner scan = new Scanner(System.in);

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

    public void removeWord() {
        System.out.print("Enter word: ");
        String removedWord = scan.nextLine();
        for(String key : dictionary.keySet()) {
            if(key.equals(removedWord)) {
                dictionary.remove(key);
                return;
            }
        }
        System.out.println("Word is not exist!");
        return;
    }

    public void addWord() {
        System.out.print("Enter word: ");
        String addedWord = scan.nextLine();
        System.out.print("Enter meaning: ");
        String addWordMeaning = scan.nextLine();
        for(String key : dictionary.keySet()) {
            if(key.equals(addedWord)) {
                System.out.println(key + " exists in the dictionary!");
                return;
            }
        }
        dictionary.put(addedWord, addWordMeaning);
        return;
    }
}
