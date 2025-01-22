import java.util.*;

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
    public static Player[] players = new Player[] {};
    public static int currentPlayerIndex;
    public static short RemainingPlayers;
    public static boolean isEnd = false;


    /**
     * Prompts the user to choose the number of players (2-4).
     * Assigns unique icons to each player.
     *
     * @param scanner the Scanner object to read user input
     * @return an array of Player objects representing the players
     */
    public static Player[] choose_player(Scanner scanner) {

        while (true) {
            System.out.println(YELLOW + "Choose a number of players (2-4):" + RESET);
            try {
                int numbers_of_players = Integer.parseInt(scanner.nextLine());
                players = new Player[numbers_of_players];
                for (int i = 0; i < numbers_of_players; i++) {
                    players[i] = new Player();
                }
                if (numbers_of_players == 2) {
                    players[0].icon = "🟩";
                    players[1].icon =  "🟦";
                    break;
                } else if (numbers_of_players == 3) {
                    players[0].icon = "🟩";
                    players[1].icon =  "🟦";
                    players[2].icon =  "🟨";
                    break;
                } else if (numbers_of_players == 4) {
                    players[0].icon = "🟩";
                    players[1].icon =  "🟦";
                    players[2].icon =  "🟨";
                    players[3].icon =  "🟥";
                    break;
                } else {
                    System.out.println(RED + "Invalid choice. Pick between 2 and 4." + RESET);
                }
            } catch (NumberFormatException | NegativeArraySizeException err) {
                System.out.println(RED + "Invalid input. Enter a number between 2 and 4." + RESET);
            }
        }
        return players;
    }

    /**
     * Prompts each player to choose a name.
     * Ensures names are unique and within the allowed length.
     *
     * @param scanner the Scanner object to read user input
     * @return an array of Player objects with assigned names
     */
    public static Player[] ask_name( Scanner scanner) {
        List<String> usedNames = new ArrayList<String>();
         for (short i = 0; i < players.length; i++) {
            System.out.println("Choose a name for player " + (i + 1) + ":");
            do {
                if(scanner.nextLine().length() < 10 && scanner.nextLine().length() > 2 && !usedNames.contains(scanner.nextLine())){
                    players[i].name=scanner.nextLine();
                    usedNames.add(players[i].name);
                    break;
                }
                else if (scanner.nextLine().length() >= 10 || scanner.nextLine().length() < 2){
                    System.out.println("Your name is either too long or too short!");
                }
                else if (usedNames.contains(scanner.nextLine())){
                    System.out.println("Your name has already been taken!");
                }
            } while (true);
        }
        return players;
    }

    /**
     * Displays the list of players and their assigned icons.
     */
    public static void show_players() {
        System.out.println(BLUE + "Players selected:" + RESET);
        for (int i = 0; i < players.length; i++) {
            System.out.println(CYAN + "Player " + (i + 1) + ": " + players[i].name + " (" + players[i].icon + ")" + RESET);
        }
    }

    /**
     * Selects the first player randomly to start the game.
     *
     * @return the index of the first player
     */
    public static int first_player() {
        Random random = new Random();
        int randomIndex = random.nextInt(players.length);
        System.out.println(PURPLE + "The first player is: " + players[randomIndex].name + " (" + players[randomIndex].icon + ")" + RESET);
        return randomIndex;
    }

    /**
     * Creates a maze with specified dimensions.
     * The maze is initialized with empty cells.
     *
     * @param height the height of the maze
     * @param width the width of the maze
     * @return a 2D array representing the maze
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
     * Places players in their starting positions within the maze.
     *
     * @param maze a 2D array representing the maze
     * @return the updated maze with player positions
     */
    public static String[][] fill_maze(String[][] maze) {
        switch (players.length) {
            case 2:
                maze[5][4] = "🟩"; // First player
                players[0].position[0] = 5;
                players[0].position[1] = 4;
                maze[5][6] = "🟦"; // Second player
                players[1].position[0] = 5;
                players[1].position[1] = 6;
                RemainingPlayers = 2;
                break;
            case 3:
                maze[4][4] = "🟩"; // First player
                players[0].position[0] = 4;
                players[0].position[1] = 4;
                maze[4][6] = "🟦"; // Second player
                players[1].position[0] = 4;
                players[1].position[1] = 6;
                maze[6][5] = "🟨"; // Third player
                players[2].position[0] = 6;
                players[2].position[1] = 5;
                RemainingPlayers = 3;
                break;
            case 4:
                maze[6][4] = "🟩"; // First player
                players[0].position[0] = 6;
                players[0].position[1] = 4;
                maze[4][4] = "🟦"; // Second player
                players[1].position[0] = 4;
                players[1].position[1] = 4;
                maze[4][6] = "🟨"; // Third player
                players[2].position[0] = 4;
                players[2].position[1] = 6;
                maze[6][6] = "🟥"; // Fourth player
                players[3].position[0] = 6;
                players[3].position[1] = 6;
                RemainingPlayers = 4;
                break;
        }
        return maze;
    }

    /**
     * Prints the current state of the maze to the console.
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
     * Allows the player to place a bomb in the maze.
     * Ensures the position is valid and updates the maze.
     *
     * @param maze a 2D array representing the maze
     * @param scanner the Scanner object to read user input
     * @return the updated maze with the bomb position marked
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
        System.out.println(GREEN + "Where do you want to move? (Z: Up, S: Down, Q: Left, D: Right)" + RESET);

        String direction = scanner.nextLine().toUpperCase();

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
        players[index].position[0] = new_position_X;
        players[index].position[1] = new_position_Y;
        return maze;
    }

    /**
     * Checks if a player is blocked in all four directions.
     * Marks the player as defeated if they are blocked.
     *
     * @param maze a 2D array representing the maze
     * @param player the Player object to check
     * @param test the index of the player being checked
     */
    public static void isBlocked(String[][] maze,Player player, int test ) {
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
                players[test].isalive= false;
                RemainingPlayers--;
            }
    }

    /**
     * Determines if the game has ended.
     * Declares a winner or a draw based on the remaining players.
     */
    public static void isVictory() {
        if(RemainingPlayers == 0) {
            System.out.println("Draw");
            isEnd = true;
        }
        else if(RemainingPlayers == 1){
            for (Player player : players) {
                if (player.isalive) {
                    System.out.println(player.name + " is the winner.");
                    isEnd = true;
                    break;
                }
            }
        }
    }


    /**
     * The main method to run the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
         players = new Player[0];
        Scanner scanner = new Scanner(System.in);
        // Step 1: Choose players
         players = choose_player(scanner);
        players = ask_name(scanner);
         show_players();

        // Step 2: Determine the first player
        int firstPlayerIndex = first_player();

        // Step 3: Create the maze
        String[][] maze = generate_maze(10, 11);
        maze = fill_maze(maze);

        // Initial display of the maze
        print_maze(maze);

        // Turn-based gameplay
        // Infinite loop to keep the game running
        while (!isEnd) {
            // Iterate through all players
            for (int i = 0; i < players.length; i++) {
                // Determine the current player's index
                currentPlayerIndex = (firstPlayerIndex + i) % players.length;

                if(players[currentPlayerIndex].isalive && !isEnd) {

                    // Get the current player's symbol
                    String currentPlayer = players[currentPlayerIndex].icon;
                    System.out.println(PURPLE + "It's " +currentPlayer + "'s turn!" + RESET);

                    maze = move(maze, players, scanner, (short) currentPlayerIndex);

                    place_bombs(maze, scanner);
                    print_maze(maze);
                    for (short test=0; test<(short)players.length; test++ ) {
                        isBlocked(maze, players[test],test);
                    }
                    isVictory();
                }
            }
            //check defeat
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