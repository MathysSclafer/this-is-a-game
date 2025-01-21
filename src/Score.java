import java.util.*;

public class Score {

    /**
     * A static map to store usernames and their corresponding scores.
     */
    static Map<String, Integer> user_scores = new HashMap<String, Integer>();

    static boolean isAscendingOrder = false;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";

    public static void main(String[] args) {

        System.out.println(CYAN + "========================" + RESET);
        System.out.println(CYAN + "Scores des 10 meilleurs: " + RESET);
        System.out.println(GREEN + "   Pseudo  Scores");

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

        addScoreToPlayer(user_scores, "Caca5", 5);

        List<Map.Entry<String, Integer>> sorted_user_scores = new ArrayList<>(user_scores.entrySet());
        // Sorting the user scores using QuickSort
        quickSort(sorted_user_scores, 0, sorted_user_scores.size() - 1);

        // Displaying the top 10 users
        showLeaderboard(sorted_user_scores);
    }

    public static void addScoreToPlayer(Map<String, Integer> list, String userName, int scoreToAdd){
        if(!list.containsKey(userName)){
            list.put(userName, scoreToAdd);
        }
        else{
            list.replace(userName, (list.get(userName) + scoreToAdd));
        }
    }

    public static void showLeaderboard(List<Map.Entry<String, Integer>> list){
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            Map.Entry<String, Integer> user = list.get(i);
            System.out.println(GREEN + (i+1) + ".  " + user.getKey() + "   " + user.getValue() + RESET);
        }
        System.out.println(CYAN + "========================" + RESET);
    }

    /**
     * Partitions the list around a pivot element for the QuickSort algorithm.
     * Elements greater than or equal to the pivot are placed before it,
     * and smaller elements are placed after it.
     *
     * @param list       the list of entries to partition.
     * @param firstIndex the starting index of the partition.
     * @param lastIndex  the ending index of the partition.
     * @return the index of the pivot element after partitioning.
     */
    public static int partition(List<Map.Entry<String, Integer>> list, int firstIndex, int lastIndex) {
        Map.Entry<String, Integer> pivot = list.get(lastIndex);
        int j = firstIndex - 1;

        if(!isAscendingOrder){
            for (int i = firstIndex; i < lastIndex; i++) {
                if (list.get(i).getValue() >= pivot.getValue()) {
                    j++;
                    Collections.swap(list, j, i);
                }
            }
        }
        else{
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
     * Sorts a list of entries using the QuickSort algorithm.
     *
     * @param list       the list of entries to sort.
     * @param firstIndex the starting index of the portion to sort.
     * @param lastIndex  the ending index of the portion to sort.
     */
    public static void quickSort(List<Map.Entry<String, Integer>> list, int firstIndex, int lastIndex) {
        if (firstIndex < lastIndex) {
            int pivotIndex = partition(list, firstIndex, lastIndex);

            // Recursively sort the left and right partitions
            quickSort(list, firstIndex, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, lastIndex);
        }
    }

}
