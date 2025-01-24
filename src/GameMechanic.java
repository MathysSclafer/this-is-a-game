import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMechanic extends Globals{

    /**
     * Moves the player to a new position based on input.
     * Ensures the movement is valid and updates the maze.
     *
     * @param maze a 2D array representing the maze
     * @param players the array of Player objects
     * @param scanner the Scanner object to read user input
     * @param index the index of the current player
     * @return the updated maze with the player's new position
     */
    public static String[][] move(String[][] maze, Player[] players, Scanner scanner, short index) {
        short position_X = -1, position_Y = -1;
        // Locate the player's current position

        position_X = players[index].position[0];
        position_Y = players[index].position[1];

        System.out.println(CYAN + "You are currently at (" + position_X + ", " + position_Y + ")." + RESET);
        System.out.println(GREEN + "Where do you want to move? (Z: Up, S: Down, Q: Left, D: Right) \n (you can save by entering 'save'." + RESET);

        String direction = scanner.nextLine().toUpperCase();
        if (direction.equals("SAVE")) {
            SaveAndLoad.TryToSaveGame(currentPlayerIndex);
            System.out.println("Game Saved!");
        }
        while (!direction.equals("Z") && !direction.equals("Q") &&
                !direction.equals("S") && !direction.equals("D")) {
            System.out.println(RED + "Invalid direction. Use Z, Q, S, or D." + RESET);
            direction = scanner.nextLine().toUpperCase();
        }

        short new_position_X = position_X, new_position_Y = position_Y;

        switch (direction) {
            case "S":
                new_position_X = (short) (position_X + 1); // Down
                break;
            case "Z":
                new_position_X = (short) (position_X - 1); // Up
                break;
            case "Q":
                new_position_Y = (short) (position_Y - 1); // Left
                break;
            case "D":
                new_position_Y = (short) (position_Y + 1); // Right
                break;
            default:
                System.out.println(RED + "Invalid direction. Use Z, Q, S, or D." + RESET);
        }

        // Check if the new position is within bounds
        if (new_position_X < 0 || new_position_X >= maze.length || new_position_Y < 0 || new_position_Y >= maze[0].length) {
            System.out.println(RED + "You can't move outside the maze!" + RESET);
            return move(maze, players, scanner,index);
        }

        // Check if the new position is empty
        if (!maze[new_position_X][new_position_Y].equals("⬜")) {
            System.out.println(RED + "This position is occupied!" + RESET);
            return move(maze, players, scanner,index);
        }

        // Move the player
        maze[position_X][position_Y] = "⬜"; // Clear the old position
        maze[new_position_X][new_position_Y] = players[index].icon;     // Set the new position
        players[index].lastposition[0] = players[index].position[0];
        players[index].lastposition[1] = players[index].position[1];
        players[index].position[0] = new_position_X;
        players[index].position[1] = new_position_Y;
        return maze;
    }


    /**
     * Allows the player to place a bomb in the maze.
     * Ensures the position is valid and updates the maze.
     *
     * @param maze a 2D array representing the maze
     * @param scanner the Scanner object to read user input
     * @return the updated maze with the bomb position marked
     */
    public static String[][] place_bombs(String[][] maze, Scanner scanner) {
        if (snakemod) {
            maze[players[currentPlayerIndex].lastposition[0]][players[currentPlayerIndex].lastposition[1]] = "💥";
        }
        else {

            try {
                System.out.println("Which BOUM would you like to do?");
                System.out.print("Enter position X (row): ");
                short position_boum_x = (short) scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                System.out.print("Enter position Y (column): ");
                short position_boum_y = (short) scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                if (position_boum_x < 0 || position_boum_x >= maze.length ||
                        position_boum_y < 0 || position_boum_y >= maze[0].length) {
                    System.out.println("Invalid position. Please try again.");
                    return place_bombs(maze, scanner);
                }

                if (maze[position_boum_x][position_boum_y].equals("⬜")) {
                    maze[position_boum_x][position_boum_y] = "💥";
                } else {
                    System.out.println("This position is not empty. Try again.");
                    return place_bombs(maze, scanner);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter integers only.");
                scanner.nextLine(); // Clean
                return place_bombs(maze, scanner);
            }
        }
        return maze;

    }

    /**
     * Checks if a player is blocked in all four directions.
     * Marks the player as defeated if they are blocked.
     *
     * @param maze a 2D array representing the maze
     * @param player the Player object to check
     * @param index the index of the player being checked
     */
    public static void isBlocked(String[][] maze,Player player, int index ) {
        int height = maze.length;
        int width = maze[0].length;
        // Vérifier les 4 directions (haut, bas, gauche, droite)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int i =0;
        for (int[] direction : directions) {
            int newX = player.position[0] + direction[0];
            int newY = player.position[1] + direction[1];
            // Si la case est dans les limites et vide, le joueur n'est pas bloqué
            if (newX >= 0 && newX < height && newY >= 0 && newY < width && maze[newX][newY].equals("⬜")) {
                i++;
            }
        }

        if(i==0){
            players[index].isalive= false;
            RemainingPlayers--;
        }
    }

    /**
     * Determines if the game has ended.
     * Declares a winner or a draw based on the remaining players.
     * Add the right points corresponding to the winner and looser
     */
    public static void isVictory() {
        if(RemainingPlayers == 0) {
            System.out.println("Draw");
            isEnd = true;
        }
        else if(RemainingPlayers == 1) {
            Player winner = null;

            for (Player player : players) {
                if (!player.isalive) {
                    Score.addScoreToPlayer(player.name, -2);
                } else {
                    winner = player;
                }
            }

            if (winner != null) {
                Score.addScoreToPlayer(winner.name, +5);
                System.out.println(GREEN + winner.name + " is the winner!" + RESET);
                System.out.println("\n \n");

                isEnd = true;
                snakemod = false;
                Menu.menu();
            }
        }

    }
}
