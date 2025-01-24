import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

/**
 * The Game class contains the main logic for a multiplayer game where players
 * choose names, take turns moving on a maze, and perform actions like marking
 * positions.
 */
public class Game extends Globals {
    public static void playloop() {
        GameGeneration.print_maze(maze);
        firstPlayerIndex = currentPlayerIndex;
        scanner = new Scanner(System.in);
        RemainingPlayers = (short) players.length;
         while (!isEnd) {
            // Iterate through all players
            for (short i = 0; i < players.length; i++) {
                // Calculate the current player's index
                currentPlayerIndex = (short) ((firstPlayerIndex + i) % players.length);

                // Only proceed if the current player is alive and the game is not over
                if (players[currentPlayerIndex].isalive && !isEnd) {

                    // Get the current player's symbol and display the turn
                    String currentPlayer = players[currentPlayerIndex].icon;
                    System.out.println(PURPLE + "It's " + currentPlayer + "'s turn!" + RESET);

                    // Allow the player to move
                    maze = GameMechanic.move(maze, players, scanner, (short) currentPlayerIndex);

                    // Allow the player to place bombs
                    GameMechanic.place_bombs(maze, scanner);

                    // Display the updated maze
                    GameGeneration.print_maze(maze);

                    // Check if each player is blocked
                    for (short index = 0; index < (short) players.length; index++) {
                        if (players[index].isalive) {
                            GameMechanic.isBlocked(maze, players[index], index);
                        }
                    }

                    // Check for victory conditions
                    GameMechanic.isVictory();
                }
            }
        }
    }
    public static void play() {

    /**
     * The main method to run the game.
     */


        // Initialize players
        players = new Player[0];
        scanner = new Scanner(System.in);

        // Step 1: Choose players and assign names
        players = Player.choose_player(scanner);
        players = Player.ask_name(scanner);
        Player.show_players();

        // Step 2: Determine the first player
        currentPlayerIndex = Player.first_player();

        // Step 3: Create and initialize the maze
        maze = GameGeneration.generate_maze(10, 11);
        maze = GameGeneration.fill_maze(maze);

        // Display the initial maze


        // Turn-based gameplay
       playloop();
    }
}
