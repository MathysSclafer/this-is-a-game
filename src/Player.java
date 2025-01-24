import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;

public class Player extends Globals implements Serializable{
    String name = "";
    String icon = "";
    short[] position  = new short[2];
    short[] lastposition  = new short[2];
    boolean isalive = true;

    /**
     * Selects the first player randomly to start the game.
     *
     * @return the index of the first player
     */
    public static short first_player() {
        Random random = new Random();
        short randomIndex = (short) random.nextInt(players.length);
        System.out.println(PURPLE + "The first player is: " + players[randomIndex].name + " (" + players[randomIndex].icon + ")" + RESET);
        return (short) randomIndex;
    }

    /**
     * Displays the list of players and their assigned icons.
     */
    public static void show_players() {
        System.out.println(BLUE + "Players selected:" + RESET);
        for (short i = 0; i < players.length; i++) {
            System.out.println(CYAN + "Player " + (i + 1) + ": " + players[i].name + " (" + players[i].icon + ")" + RESET);
        }
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
                String tmp = scanner.nextLine();
                if(tmp.length() < 10 && tmp.length() >= 2 && !usedNames.contains(tmp)){
                    players[i].name=tmp;

                    if (players[i].name.equalsIgnoreCase("SNAKE"))
                    {
                        snakemod = true;
                        System.out.println("Snake mod is activated");
                    }
                    if(players[i].name.equalsIgnoreCase("clement"))
                    {
                        Menu.secretSound();
                        System.out.println("Easter Egg !");
                    }

                    usedNames.add(players[i].name);
                    break;
                }
                else if (tmp.length() >= 10 || tmp.length() < 2){
                    System.out.println("Your name is either too long or too short!");
                }
                else if (usedNames.contains(tmp)){
                    System.out.println("Your name has already been taken!");
                }
            } while (true);
        }
        return players;
    }

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
                short numbers_of_players = (short) Integer.parseInt(scanner.nextLine());
                players = new Player[numbers_of_players];
                for (short i = 0; i < numbers_of_players; i++) {
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
}
