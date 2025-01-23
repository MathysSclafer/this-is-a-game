import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * The Score class provides functionality to manage, sort, and display user scores.
 */
public class Score extends Globals {

    static SaveAndLoad saveAndLoad = new SaveAndLoad();

    /**
     * Handles the display and sorting of scores.
     * Displays the top 10 scores in ascending or descending order based on user choice.
     */
    public static void score() {
        System.out.println(CYAN + "========================" + RESET);

        // Display title based on sorting order
        if (!isAscendingOrder) {
            System.out.println(CYAN + "10 best scores (descending): " + RESET);
        } else {
            System.out.println(CYAN + "10 best scores (ascending): " + RESET);
        }

        System.out.printf("%s %10s %10s %n", GREEN, "Pseudo", "Scores");

        //saveAndLoad.TryToSaveScore(user_scores);
        //user_scores.clear();
        // Load scores from storage
        user_scores = saveAndLoad.TryToLoadScore();

        // Add example scores
//        user_scores.clear();
//        user_scores.put("Inès", 108);
//        user_scores.put("Mathys", 1333);
//        user_scores.put("Adam", 1298);
//        user_scores.put("Théo", 3);
//        user_scores.put("Bernard", 5);
//        user_scores.put("Clément", 110);
//        saveAndLoad.TryToSaveScore(user_scores);

        // Sort scores
        List<Map.Entry<String, Integer>> sorted_user_scores = new ArrayList<>(user_scores.entrySet());
        quickSort(sorted_user_scores, 0, sorted_user_scores.size() - 1);

        // Display leaderboard
        showLeaderboard(sorted_user_scores);

        // Handle user input for sorting preferences
        returnLoopScore();
    }

    /**
     * Adds or updates a score for a specific user.
     *
     * @param userName   The username of the player.
     * @param scoreToAdd The score to add or update for the user.
     */
    public static void addScoreToPlayer(String userName, int scoreToAdd) {
        user_scores = saveAndLoad.TryToLoadScore();
        if (!user_scores.containsKey(userName)) {
            user_scores.put(userName, scoreToAdd);
        } else {
            user_scores.replace(userName, user_scores.get(userName) + scoreToAdd);
        }
        System.out.println(PURPLE + userName + YELLOW
                + " got " + scoreToAdd + " scores (Updated score: "
                + CYAN + user_scores.get(userName) + YELLOW + ")" + RESET);
        saveAndLoad.TryToSaveScore(user_scores);
    }

    /**
     * Displays the top 10 scores from a sorted list.
     *
     * @param list The sorted list of user scores.
     */
    public static void showLeaderboard(List<Map.Entry<String, Integer>> list) {
        for (short i = 0; i < Math.min(10, list.size()); i++) {
            Map.Entry<String, Integer> user = list.get(i);
            System.out.printf("%s%-5d%-12s%d%s%n", GREEN, (i + 1), user.getKey(), user.getValue(), RESET);
        }
        System.out.println(CYAN + "========================" + RESET);
    }

    /**
     * Partitions the list for QuickSort using a pivot.
     *
     * @param list       The list to partition.
     * @param firstIndex The start index of the partition.
     * @param lastIndex  The end index of the partition.
     * @return The pivot index after partitioning.
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
     * Sorts a list of user scores using the QuickSort algorithm.
     *
     * @param list       The list to sort.
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
     * Handles user input for sorting preferences or exiting the menu.
     */
    public static void returnLoopScore() {
        System.out.println(YELLOW + "Press (A) to sort by ascending results \n" +
                "Press (D) to sort by descending results \n" +
                "Press (E) to exit !");

        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext("e") || input.hasNext("E")) { // Exit
                Menu.menu();
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
