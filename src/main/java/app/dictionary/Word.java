package app.dictionary;

public class Word {
    private String word;
    private String wordExplain;

    /**
     * Default Constructor
     */
    public Word() {
        //Default
    }

    /**
     * Constructor with parameters
     * @param word
     * @param wordExplain
     */
    public Word(String word, String wordExplain) {
        this.word = word;
        this.wordExplain = wordExplain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
