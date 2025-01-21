import java.util.Scanner;
import java.util.ArrayList;

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


    public static String[] choose_player(Scanner scanner) {
        String[] players;
        while (true) {
            System.out.println(YELLOW + "Choose a number of players (2-4):" + RESET);
            try {
                int numbers_of_players = Integer.parseInt(scanner.nextLine());
                if (numbers_of_players == 2) {
                    players = new String[]{"\uD83D\uDFEA", "\uD83D\uDFEC"};
                    break;
                } else if (numbers_of_players == 3) {
                    players = new String[]{"\uD83D\uDFEA", "\uD83D\uDFEC", "\uD83D\uDFE8"};
                    break;
                } else if (numbers_of_players == 4) {
                    players = new String[]{"\uD83D\uDFEA", "\uD83D\uDFEC", "\uD83D\uDFE8", "\uD83D\uDFE5"};
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

    public static String[] ask_name(String[] players, Scanner scanner) {
        ArrayList<String> namesList = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            System.out.println("Choose a name for player " + (i + 1) + ":");
            String name = scanner.nextLine();
            namesList.add(name);
        }
        return namesList.toArray(new String[0]);
    }

    public static void show_players(String[] players, String[] name_list) {
        System.out.println(BLUE + "Players selected:" + RESET);
        for (int i = 0; i < players.length; i++) {
            System.out.println(CYAN + "Player " + (i + 1) + ": " + name_list[i] + " (" + players[i] + ")" + RESET);
        }
    }

    public static String[][] generate_maze(int width, int height, String[] player) {
        String[][] maze = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = "⬜"; // Empty cell
            }
        }
        return maze;
    }

    public static String[][] fill_maze(String[][] maze, String[] players) {
        switch (players.length) {
            case 2:
                maze[6][6] = "\u2B1B";  // First player (black square)
                maze[7][7] = "\uD83D\uDFEA";  // Second player (yellow square)
                break;
            case 3:
                maze[6][6] = "\uD83D\uDFE5";  // First player (red square)
                maze[7][7] = "\uD83D\uDFE6";  // Second player (orange square)
                maze[8][8] = "\uD83D\uDFE8";  // Third player (green square)
                break;
            case 4:
                maze[6][6] = "\uD83D\uDFE9";  // First player (blue square)
                maze[7][7] = "\uD83D\uDFEA";  // Second player (yellow square)
                maze[8][8] = "\uD83D\uDFEB";  // Third player (light green square)
                maze[9][9] = "\uD83D\uDFEC";  // Fourth player (purple square)
                break;
            default:
                break;
        }
        return maze;
    }

    public static void print_maze(String[][] maze) {
        for (int y = 0; y < maze.length; y++) {
            System.out.println(" ");
            for (int x = 0; x < maze[y].length; x++) {
                System.out.print(" " + maze[y][x] + " ");
            }
        }
    }

    // Fonction pour déplacer les joueurs avec ZQSD
    public static void move_players(String[][] maze, String[] players, Scanner scanner) {
        int[] playerPositionsX = new int[players.length];
        int[] playerPositionsY = new int[players.length];

        // Initialiser les positions des joueurs
        for (int i = 0; i < players.length; i++) {
            playerPositionsX[i] = 6 + i; // Exemple : les positions initiales sont 6,6,7,7,...
            playerPositionsY[i] = 6 + i;
        }

        while (true) {
            // Afficher l'état actuel du labyrinthe
            print_maze(maze);

            System.out.println("\nUse Z (up), Q (left), S (down), D (right) to move players");
            String move = scanner.nextLine();

            // Déplacer les joueurs
            for (int i = 0; i < players.length; i++) {
                // Vérifier le déplacement de chaque joueur
                int newX = playerPositionsX[i];
                int newY = playerPositionsY[i];

                if (move.equalsIgnoreCase("Z")) newY--; // Haut
                else if (move.equalsIgnoreCase("Q")) newX--; // Gauche
                else if (move.equalsIgnoreCase("S")) newY++; // Bas
                else if (move.equalsIgnoreCase("D")) newX++; // Droite

                // Vérifier si le déplacement est possible (limites du labyrinthe)
                if (newX >= 0 && newX < maze[0].length && newY >= 0 && newY < maze.length) {
                    // Effacer l'ancienne position
                    maze[playerPositionsY[i]][playerPositionsX[i]] = "⬜";

                    // Mettre à jour la nouvelle position
                    playerPositionsX[i] = newX;
                    playerPositionsY[i] = newY;
                    maze[playerPositionsY[i]][playerPositionsX[i]] = players[i];
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] players = choose_player(scanner); // Get players
        String[] name_list = ask_name(players, scanner); // Ask for names
        show_players(players, name_list); // Display players and names
        String[][] maze = generate_maze(10, 11, players); // Generate maze
        String[][] maze_board = fill_maze(maze, players); // Fill maze with players

        // Lancer la boucle de déplacement des joueurs
        move_players(maze_board, players, scanner); // Déplacer les joueurs
    }
}
