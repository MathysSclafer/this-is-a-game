import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * The Score class provides functionality for managing user scores,
 * sorting them, and displaying a leaderboard.
 */
public class Score {

    /**
     * A map to store usernames and their scores.
     */
    static Map<String, Integer> user_scores = new HashMap<>();

    /**
     * Boolean to decide sorting order.
     * True for ascending, false for descending.
     */
    static boolean isAscendingOrder = false;

    // ANSI color codes for console text
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    /**
     * Manages the score display and sorting process.
     * Displays the top 10 scores based on the selected order.
     */
    public static void score() {
        System.out.println(CYAN + "========================" + RESET);

        // Print the title based on sorting order
        if (!isAscendingOrder) {
            System.out.println(CYAN + "10 best scores (descending): " + RESET);
        } else {
            System.out.println(CYAN + "10 best scores (ascending): " + RESET);
        }
        System.out.printf("%s %10s %10s %n", GREEN, "Pseudo", "Scores");
        user_scores.put("Caca", 350);
        user_scores.put("Caca2", 750);
        user_scores.put("Caca3", 550);
        user_scores.put("Caca4", 1050);
        user_scores.put("Caca6", 1050);
        user_scores.put("Caca7", 1050);
        user_scores.put("Caca8", 1050);
        user_scores.put("Caca9", 1050);
        user_scores.put("Caca10", 1050);
        user_scores.put("Caca11", 1050);
        SaveAndLoad saveAndLoad = new SaveAndLoad();
        saveAndLoad.TryToSaveScore(user_scores);
        user_scores.clear();
        user_scores = saveAndLoad.TryToLoadScore();

        // Add example scores
        user_scores.put("Jean", 350);
        user_scores.put("Baptiste", 750);
        user_scores.put("Baptistefr", 550);

        // Sort and display scores
        List<Map.Entry<String, Integer>> sorted_user_scores = new ArrayList<>(user_scores.entrySet());
        quickSort(sorted_user_scores, 0, sorted_user_scores.size() - 1);
        showLeaderboard(sorted_user_scores);


        // User input for sorting preference
        returnLoopScore();
    }

    /**
     * Add a score to a user. If the user exists, updates their score.
     *
     * @param list       The map storing usernames and scores.
     * @param userName   The username of the player.
     * @param scoreToAdd The score to add or remove to the user.
     */
    public static void addScoreToPlayer(Map<String, Integer> list, String userName, int scoreToAdd) {
        if (!list.containsKey(userName)) {
            list.put(userName, scoreToAdd);
        } else {
            list.replace(userName, list.get(userName) + scoreToAdd);
        }
    }

    /**
     * Displays the top 10 scores from the sorted list.
     *
     * @param list The sorted list of scores.
     */
    public static void showLeaderboard(List<Map.Entry<String, Integer>> list) {
        for (short i = 0; i < Math.min(10, list.size()); i++) {
            Map.Entry<String, Integer> user = list.get(i);
            System.out.printf("%s%-5d%-12s%d%s%n", GREEN, (i + 1), user.getKey(), user.getValue(), RESET);
        }
        System.out.println(CYAN + "========================" + RESET);
    }

    /**
     * Partition the list for QuickSort based on a pivot element.
     *
     * @param list       The list to partition.
     * @param firstIndex The starting index of the segment.
     * @param lastIndex  The ending index of the segment.
     * @return The index of the pivot after partitioning.
     */
    public static int partition(List<Map.Entry<String, Integer>> list, int firstIndex, int lastIndex) {
        Map.Entry<String, Integer> pivot = list.get(lastIndex);
        int j = firstIndex - 1;

        if (!isAscendingOrder) {
            for (int i = firstIndex; i < lastIndex; i++) {
                if (list.get(i).getValue() >= pivot.getValue()) {
                    j++;
                    Collections.swap(list, j, i);
                }
            }
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                if (list.get(i).getValue() <= pivot.getValue()) {
                    j++;
                    Collections.swap(list, j, i);
                }
            }
        }

        Collections.swap(list, j + 1, lastIndex);
        return j + 1;
    }

    /**
     * Sorts a list of scores using the QuickSort algorithm.
     *
     * @param list       The list of scores to sort.
     * @param firstIndex The starting index of the segment to sort.
     * @param lastIndex  The ending index of the segment to sort.
     */
    public static void quickSort(List<Map.Entry<String, Integer>> list, int firstIndex, int lastIndex) {
        if (firstIndex < lastIndex) {
            int pivotIndex = partition(list, firstIndex, lastIndex);

            quickSort(list, firstIndex, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, lastIndex);
        }
    }

    /**
     * Loops to accept user input for sorting order or exit.
     */
    public static void returnLoopScore() {
        System.out.println(YELLOW + "Press (A) to sort by ascending results \n" +
                "Press (D) to sort by descending results \n" +
                "Press (E) to exit !");

        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext("e") || input.hasNext("E")) { // Exit the loop
                Main.menu();
                return;
            } else if (input.hasNext("a") || input.hasNext("A")) { // Sort ascending
                isAscendingOrder = true;
                score();
            } else if (input.hasNext("d") || input.hasNext("D")) { // Sort descending
                isAscendingOrder = false;
                score();
            } else if (input.hasNext()) {
                System.out.println(RED + "Enter the correct letter !");
            }
        } while (true);
    }
}