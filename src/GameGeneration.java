public class GameGeneration extends Globals{

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
                maze[5][4] = "\uD83D\uDFE9"; // First player
                players[0].position[0] = 5;
                players[0].position[1] = 4;
                maze[5][6] = "\uD83D\uDFE6"; // Second player
                players[1].position[0] = 5;
                players[1].position[1] = 6;
                break;
            case 3:
                maze[4][4] = "\uD83D\uDFE9"; // First player
                players[0].position[0] = 4;
                players[0].position[1] = 4;
                maze[4][6] = "\uD83D\uDFE6"; // Second player
                players[1].position[0] = 4;
                players[1].position[1] = 6;
                maze[6][5] = "🟨"; // Third player
                players[2].position[0] = 6;
                players[2].position[1] = 5;
                break;
            case 4:
                maze[6][4] = "\uD83D\uDFE9"; // First player
                players[0].position[0] = 6;
                players[0].position[1] = 4;
                maze[4][4] = "\uD83D\uDFE6"; // Second player
                players[1].position[0] = 4;
                players[1].position[1] = 4;
                maze[4][6] = "🟨"; // Third player
                players[2].position[0] = 4;
                players[2].position[1] = 6;
                maze[6][6] = "🟥"; // Fourth player
                players[3].position[0] = 6;
                players[3].position[1] = 6;
                break;
        }
        return maze;
    }


    /**
     * Prints the maze in the console.
     *
     * @param maze a 2D array representing the maze
     */
    public static void print_maze(String[][] maze) {
        System.out.print("   ");
        for (int i = 0; i <= maze.length; i++) {
            System.out.print(" "+ i + "  ");
        }
        for (int x = 0; x < maze.length; x++) {
            System.out.println(" ");
            System.out.print(" "+x+" ");
            for (int y = 0; y < maze[x].length; y++) {
                System.out.print(" " + maze[x][y] + " ");
            }
        }System.out.println();
    }
}
