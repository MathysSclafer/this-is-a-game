import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Globals {

    // Color constants for terminal output
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // Main Game variables

    public static String[][] maze = new String[][] {};
    public static Player[] players = new Player[] {};
    public static short currentPlayerIndex;
    public static short RemainingPlayers;
    public static short firstPlayerIndex;
    public static boolean isEnd = false;
    public static boolean snakemod = false;
    public static Scanner scanner;
    public static GameSave gamesave = new GameSave();


    // Score variables

    /**
     * A map to store usernames and their scores.
     */
    public static Map<String, Integer> user_scores = new HashMap<>();
    /**
     * Boolean to decide sorting order.
     * True for ascending, false for descending.
     */
    static boolean isAscendingOrder = false;
}
