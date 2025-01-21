import java.util.Scanner;

public class Game {
    // Color constants
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";

    /**
     * Ask for the number of players and set their symbols.
     *
     * @param scanner to read input
     * @return player symbols
     */
    public static String[] choose_player(Scanner scanner) {
        String[] players; // Players array

        while (true) {
            System.out.println(YELLOW + "Choose a number of players (2-4):" + RESET);
            // Get and check input
            try {
                int numbers_of_players = Integer.parseInt(scanner.nextLine());
                // Set players based on input
                if (numbers_of_players == 2) {
                    players = new String[]{"X", "O"};
                    break;
                } else if (numbers_of_players == 3) {
                    players = new String[]{"X", "O", "P"};
                    break;
                } else if (numbers_of_players == 4) {
                    players = new String[]{"X", "O", "P", "B"};
                    break;
                } else {
                    // Invalid input
                    System.out.println(RED + "Invalid choice. Pick between 2 and 4." + RESET);
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                System.out.println(RED + "Invalid input. Enter a number between 2 and 4." + RESET);
            }
        }

        return players;
    }

    /**
     * Show the selected players.
     *
     * @param players list of player symbols
     */
    public static void show_players(String[] players) {
        System.out.println(BLUE + "Players selected:" + RESET);
        for (int i = 0; i < players.length; i++) {
            System.out.println(CYAN + "Player " + (i + 1) + ": " + players[i] + RESET);
        }
    }

    /**
     * Main method to start the game.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] players = choose_player(scanner); // Get players
        show_players(players); // Display players
    }
}
