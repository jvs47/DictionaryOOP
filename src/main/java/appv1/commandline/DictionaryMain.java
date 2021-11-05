package appv1.commandline;

import app.actions.FavoriteAct;
import app.actions.HistoryAct;
import app.dictionary.DictionaryManagement;
import app.dictionary.Word;
import app.helper.IODatabase;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DictionaryMain {
    static Scanner scan = new Scanner(System.in);
    static IODatabase ioDatabase = new IODatabase();
    static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    static HistoryAct historyAct = new HistoryAct();
    static FavoriteAct favoriteAct = new FavoriteAct();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
            String yourChoice;
            System.out.println("1. Search Word");
            System.out.println("2. Show All Word");
            System.out.println("3. History");
            System.out.println("4. Favorite");
            System.out.println("5. Edit Word");
            System.out.println("6. Add Word");
            System.out.println("7. Delete Word");
            System.out.println("8. About us");
            System.out.println("9. Exit");
            int choice = 0;
            do {
                try {
                    System.out.print("Enter your choice: ");
                    yourChoice = scan.nextLine();
                    choice = Integer.parseInt(yourChoice);
                } catch (Exception e) {
                    System.out.println("Invalid input! Please try again!");
                }
            } while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7 && choice != 8 && choice != 9);

            switch (choice) {
                case 1:
                    clearScreen();
                    search();
                    break;
                case 2:
                    clearScreen();
                    showAll();
                    break;
                case 3:
                    clearScreen();
                    history();
                    break;
                case 4:
                    clearScreen();
                    favorite();
                    break;
                case 5:
                    clearScreen();
                    editWord();
                    break;
                case 6:
                    clearScreen();
                    addWord();
                    break;
                case 7:
                    clearScreen();
                    deleteWord();
                    break;
                case 8:
                    clearScreen();
                    about();
                    break;
                default:
                    return;
            }
        
    }

    public static void search() {
        System.out.print("Enter Word: ");
        String foundWord = scan.nextLine();
        Word word = dictionaryManagement.binarySearch(foundWord);
        System.out.println("|" + word.getWord() + "    " + "|" + word.getWordExplain());
        historyAct.saveWordToHistoryDatabase(foundWord);

        System.out.println("1. Add To Favorite");
        System.out.println("2. Back to menu");
        String yourChoice;
        int choice = 0;
        do {
            try {
                System.out.print("Enter your choice: ");
                yourChoice = scan.nextLine();
                choice = Integer.parseInt(yourChoice);
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (choice != 1 && choice != 2);
        if(choice == 1) {
            favoriteAct.saveToFavoriteDatabase(foundWord);
            finishAddFav();
        } else {
            clearScreen();
            menu();
        }
    }

    public static void finishAddFav() {
        int choice = 0;
        System.out.println("1. Back to menu");
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (choice != 1);
        if(choice == 1) {
            menu();
        }
    }

    public static void showAll() {
        dictionaryManagement.showAllWord();
        int choice = 0;
        String yourChoice;
        System.out.println("1. Back to menu");
        do{
            try {
                System.out.print("Enter your choice: ");
                yourChoice = scan.nextLine();
                choice = Integer.parseInt(yourChoice);
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while(choice != 1);

        if(choice == 1) {
            clearScreen();
            menu();
        }
    }

    public static void history() {
        historyAct.showHistoryWord();
        int choice = 0;
        System.out.println("1. Delete History");
        System.out.println("2. Search Word");
        System.out.println("3. Back to menu");
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while(choice != 1 && choice != 2 && choice != 3);
        switch (choice) {
            case 1:
                historyAct.deleteAllHistory();
                history();
                break;
            case 2:
                clearScreen();
                System.out.print("Enter word: ");
                String foundWord = scan.nextLine();
                Word word = historyAct.binarySearchHistory(foundWord);
                if(word == null) {
                    System.out.println(foundWord + " is not exist!");
                    finishAddFav();
                } else {
                    System.out.println("|" + word.getWord() + "    " + "|" + word.getWordExplain());
                    finishAddFav();
                }
                break;
            case 3:
                clearScreen();
                menu();
                break;
        }
    }

    public static void favorite() {
        favoriteAct.showAllFavorite();
        int choice = 0;
        System.out.println("1. Delete From Favorite");
        System.out.println("2. Search Word");
        System.out.println("3. Back to menu");
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while(choice != 1 && choice != 2 && choice != 3);
        switch (choice) {
            case 1:
                System.out.print("Enter word want to delete: ");
                String removedWord = scan.nextLine();
                favoriteAct.removeFavouriteWordFromDatabase(removedWord);
                clearScreen();
                favorite();
                break;
            case 2:
                clearScreen();
                System.out.print("Enter word: ");
                String foundWord = scan.nextLine();
                Word word = favoriteAct.binarySearchFavorite(foundWord);
                if(word == null) {
                    System.out.println(foundWord + " is not exist!");
                    finishAddFav();
                } else {
                    System.out.println("|" + word.getWord() + "    " + "|" + word.getWordExplain());
                    finishAddFav();
                }
                break;
            case 3:
                clearScreen();
                menu();
                break;
        }
    }

    public static void addWord() {
        dictionaryManagement.insertFromCommandline();
        System.out.println("1. Back to menu");
        int choice = 0;
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (choice != 1);
        if(choice == 1) {
            clearScreen();
            menu();
        }
    }

    public static void deleteWord() {
        dictionaryManagement.deleteWord();
        System.out.println("1. Back to menu");
        int choice = 0;
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (choice != 1);
        if(choice == 1) {
            clearScreen();
            menu();
        }
    }

    public static void editWord() {
        dictionaryManagement.editWordMeaning();
        System.out.println("1. Back to menu");
        int choice = 0;
        do {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        } while (choice != 1);
        if(choice == 1) {
            clearScreen();
            menu();
        }
    }

    public static void about() {
        System.out.println("Created by");
        System.out.println("Nguyen Duc Anh 20020074");
        System.out.println("Vu Duc Cuong 20020282");
        System.out.println("Khuat Nguyen Cuong 20020131");
        finishAddFav();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}