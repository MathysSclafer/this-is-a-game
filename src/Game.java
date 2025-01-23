import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Game class contains the main logic for a multiplayer game where players
 * choose names, take turns moving on a maze, and perform actions like marking
 * positions.
 */
public class Game {
    // Color constants for terminal output
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    /**
     * Prompts the user to choose the number of players and assigns symbols to each player.
     *
     * @param scanner the Scanner object to read user input
     * @return an array of symbols representing the players
     */
    public static String[] choose_player(Scanner scanner) {
        String[] players;
        while (true) {
            System.out.println(YELLOW + "Choose a number of players (2-4):" + RESET);
            try {
                int numbers_of_players = Integer.parseInt(scanner.nextLine());
                if (numbers_of_players == 2) {
                    players = new String[]{"🟩", "🟦"};
                    break;
                } else if (numbers_of_players == 3) {
                    players = new String[]{"🟩", "🟦", "🟨"};
                    break;
                } else if (numbers_of_players == 4) {
                    players = new String[]{"🟩", "🟦", "🟨", "🟥"};
                    break;
                } else {
                    System.out.println(RED + "Invalid choice. Pick between 2 and 4." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid input. Enter a number between 2 and 4." + RESET);
            }
        }
        return players;
    }

    /**
     * Prompts each player to choose a name.
     *
     * @param players an array of symbols representing the players
     * @param scanner the Scanner object to read user input
     * @return an array of names corresponding to each player
     */
    public static String[] ask_name(String[] players, Scanner scanner) {
        ArrayList<String> namesList = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            System.out.println("Choose a name for player " + (i + 1) + ":");
            String name = scanner.nextLine();
            namesList.add(name);
        }
        return namesList.toArray(new String[0]);
    }

    /**
     * Displays the selected players and their assigned symbols.
     *
     * @param players an array of symbols representing the players
     * @param name_list an array of names corresponding to the players
     */
    public static void show_players(String[] players, String[] name_list) {
        System.out.println(BLUE + "Players selected:" + RESET);
        for (int i = 0; i < players.length; i++) {
            System.out.println(CYAN + "Player " + (i + 1) + ": " + name_list[i] + " (" + players[i] + ")" + RESET);
        }
    }

    /**
     * Randomly selects the first player to start the game.
     *
     * @param players an array of symbols representing the players
     * @param name_list an array of names corresponding to the players
     * @return the index of the first player
     */
    public static int first_player(String[] players, String[] name_list) {
        Random random = new Random();
        int randomIndex = random.nextInt(players.length);
        System.out.println(PURPLE + "The first player is: " + name_list[randomIndex] + " (" + players[randomIndex] + ")" + RESET);
        return randomIndex;
    }

    /**
     * Creates an empty maze with specified dimensions.
     *
     * @param height the height of the maze
     * @param width the width of the maze
     * @return a 2D array representing the empty maze
     */
    public static String[][] generate_maze(int height, int width) {
        String[][] maze = new String[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                maze[x][y] = "⬜"; // white cell
            }
        }
        return maze;
    }

    /**
     * Places the players at their starting positions in the maze.
     *
     * @param maze a 2D array representing the maze
     * @param players an array of symbols representing the players
     * @return the updated maze with player positions
     */
    public static String[][] fill_maze(String[][] maze, String[] players) {
        switch (players.length) {
            case 2:
                maze[5][4] = "🟩"; // First player
                maze[5][6] = "🟦"; // Second player
                break;
            case 3:
                maze[4][4] = "🟩"; // First player
                maze[4][6] = "🟦"; // Second player
                maze[6][5] = "🟨"; // Third player
                break;
            case 4:
                maze[6][4] = "🟩"; // First player
                maze[4][4] = "🟦"; // Second player
                maze[4][6] = "🟨"; // Third player
                maze[6][6] = "🟥"; // Fourth player
                break;
        }
        return maze;
    }

    /**
     * Prints the maze to the console.
     *
     * @param maze a 2D array representing the maze
     */
    public static void print_maze(String[][] maze) {
        for (int x = 0; x < maze.length; x++) {
            System.out.println(" ");
            for (int y = 0; y < maze[x].length; y++) {
                System.out.print(" " + maze[x][y] + " ");
            }
        }System.out.println("");
    }

    /**
     * Marks a position in the maze with a "BOUM" symbol.
     *
     * @param maze a 2D array representing the maze
     * @param scanner the Scanner object to read user input
     * @return the updated maze with the "BOUM" position marked
     */
    public static String[][] place_bombs(String[][] maze, Scanner scanner) {
        try {
            System.out.println("Which BOUM would you like to do?");
            System.out.print("Enter position X (row): ");
            int position_boum_x = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            System.out.print("Enter position Y (column): ");
            int position_boum_y = scanner.nextInt();
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
        return maze;
    }

    /**
     * Moves a player to a new position in the maze based on user input.
     *
     * @param maze a 2D array representing the maze
     * @param player the symbol representing the player
     * @param scanner the Scanner object to read user input
     * @return the updated maze with the player's new position
     */
    public static String[][] move(String[][] maze, String player, Scanner scanner) {
        int position_X = -1, position_Y = -1;
        // Locate the player's current position
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y].equals(player)) {
                    position_X = x;
                    position_Y = y;
                }
            }
        }

        System.out.println(CYAN + "You are currently at (" + position_X + ", " + position_Y + ")." + RESET);
        System.out.println(GREEN + "Where do you want to move? (Z: Up, S: Down, Q: Left, D: Right)" + RESET);

        String direction = scanner.nextLine().toUpperCase();

        while (!direction.equals("Z") && !direction.equals("Q") &&
                !direction.equals("S") && !direction.equals("D")) {
            System.out.println(RED + "Invalid direction. Use Z, Q, S, or D." + RESET);
            direction = scanner.nextLine().toUpperCase();
        }

        int new_position_X = position_X, new_position_Y = position_Y;

        switch (direction) {
            case "S":
                new_position_X = position_X + 1; // Down
                break;
            case "Z":
                new_position_X = position_X - 1; // Up
                break;
            case "Q":
                new_position_Y = position_Y - 1; // Left
                break;
            case "D":
                new_position_Y = position_Y + 1; // Right
                break;
            default:
                System.out.println(RED + "Invalid direction. Use Z, Q, S, or D." + RESET);

        }

        // Check if the new position is within bounds
        if (new_position_X < 0 || new_position_X >= maze.length || new_position_Y < 0 || new_position_Y >= maze[0].length) {
            System.out.println(RED + "You can't move outside the maze!" + RESET);
            return move(maze, player, scanner);
        }

        // Check if the new position is empty
        if (!maze[new_position_X][new_position_Y].equals("⬜")) {
            System.out.println(RED + "This position is occupied!" + RESET);
            return move(maze, player, scanner);
        }

        // Move the player
        maze[position_X][position_Y] = "⬜"; // Clear the old position
        maze[new_position_X][new_position_Y] = player;     // Set the new position

        return maze;
    }

    public static boolean isBlocked(String[][] maze, int x, int y) {
        int height = maze.length;
        int width = maze[0].length;
        boolean isblocked=true;
        // Vérifier les 4 directions (haut, bas, gauche, droite)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            // Si la case est dans les limites et vide, le joueur n'est pas bloqué
            if (newX >= 0 && newX < height && newY >= 0 && newY < width && maze[newX][newY].equals("⬜")) {
                System.out.println("pas Blocked at (" + newX + ", " + newY + ")." + RESET);
                isblocked= false;
            }

        }
        System.out.println("finish");

        return true; // Toutes les cases autour sont bloquées
    }

    public static void victory(String[][] maze, String[] players) {
        int activePlayers = 0 ;
        String lastPlayer = "";

        for (String player : players) {
            boolean playerBlocked = true;

            // Parcourir le labyrinthe pour trouver le joueur
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[x].length; y++) {
                    if (maze[x][y].equals(player)) {
                        if (!isBlocked(maze, x, y)) {
                            playerBlocked = false; // Le joueur peut encore se déplacer
                        }
                    }
                }
            }

            if (!playerBlocked) {
                activePlayers++;
                lastPlayer = player;
            }
        }

        if (activePlayers == 0) {
            System.out.println(GREEN + "Victory! Player " + lastPlayer + " has won!" + RESET);
            System.exit(0); // Terminer le jeu
        }
    }


    /**
     * The main method to run the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        // Step 1: Choose players
        String[] players = choose_player(scanner);
        String[] name_list = ask_name(players, scanner);
        show_players(players, name_list);

        // Step 2: Determine the first player
        int firstPlayerIndex = first_player(players, name_list);

        // Step 3: Create the maze
        String[][] maze = generate_maze(10, 11);
        maze = fill_maze(maze, players);

        // Initial display of the maze
        print_maze(maze);

        // Turn-based gameplay
        // Infinite loop to keep the game running
        while (running) {
            // Iterate through all players
            for (int i = 0; i < players.length; i++) {
                // Determine the current player's index
                int currentPlayerIndex = (firstPlayerIndex + i) % players.length;
                // Get the current player's symbol
                String currentPlayer = players[currentPlayerIndex];
                System.out.println(PURPLE + "It's " + currentPlayer + "'s turn!" + RESET);

                maze = move(maze, currentPlayer, scanner);
                place_bombs(maze, scanner);
                print_maze(maze);

                // Add win or exit condition here
                victory(maze, players);
            }
        }

    }
}


// Début du programme
//
//Définir la méthode "choose_player" :
//    - Demander au joueur de choisir le nombre de joueurs (entre 2 et 4)
//    - Gérer les erreurs 
//    - Associer un symbole à chaque joueur
//    - Retourner la liste des symboles
//
//Définir la méthode "ask_name" :
//    - Pour chaque joueur, demander un nom, gérer les erreurs
//    - Retourner la liste des noms
//
//Définir la méthode "show_players" :
//    - Afficher les joueurs et leurs symboles
//
//Définir la méthode "first_player" :
//    - Choisir un joueur aléatoire comme premier joueur
//    - Afficher le premier joueur
//    - Retourner l'index du premier joueur
//
//Définir la méthode "generate_maze" :
//    - Créer un labyrinthe avec des cases blanches avec des dimensions spécifiées
//    - Retourner le labyrinthe
//
//Définir la méthode "fill_maze" :
//    - Placer les joueurs dans leurs positions initiales
//    - Retourner le labyrinthe mis à jour
//
//Définir la méthode "print_maze" :
//    - Afficher le labyrinthe dans la console avec une boucle
//
//Définir la méthode "boum" :
//    - Demander une position (x, y) pour marquer une explosion
//    - Vérifier si la position est valide et vide
//    - Si valide, marquer avec une explosion sinon recommencer
//    - Retourner le labyrinthe mis à jour
//
//Définir la méthode "move" :
//    - Trouver la position actuelle du joueur
//    - Demander une direction (haut, bas, gauche, droite)
//    - Vérifier si le mouvement est possible
//    - Mettre à jour la position du joueur
//    - Retourner le labyrinthe mis à jour
//
//Dans la méthode principale :
//    - Initialiser un scanner pour les entrées utilisateur
//    - Choisir le nombre de joueurs et leurs noms
//    - Afficher les joueurs
//    - Déterminer le premier joueur
//    - Créer un labyrinthe
//    - Placer les joueurs dans le labyrinthe
//    - Afficher le labyrinthe
//
//    Boucle de jeu infinie :
//        - Pour chaque joueur :
//            - Annoncer le tour du joueur actuel
//            - Déplacer le joueur
//            - Ajouter une explosion
//            - Afficher le labyrinthe mis à jour
//
//Fin du programme