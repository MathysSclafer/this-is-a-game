import java.util.Scanner;
import java.util.Timer;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";

    public static void main(String[] args) {
        menu();
    }

    /**
     * Displays the menu of the corresponding input
     */
    public static void menu() {

        int selection;

        Scanner input = new Scanner(System.in);

        do { //do while loop that stops when the user enters a valid element
            System.out.println(YELLOW + "Select an option");
            System.out.println(GREEN + "*****************");
            System.out.println(CYAN + "1 / Play");
            System.out.println("2 / Rules");
            System.out.println("3 / Load");
            System.out.println("4 / Score");
            System.out.println("5 / Quit");


            if (input.hasNextInt()) { //if the field entered by the user is an int, the selection takes the int into account
                selection = input.nextInt();

                if (selection == 1) { //if int is 1, print “Play”
                    System.out.println("Play");
                    Game game = new Game();
                    game.play();
                    return;
                }
                if (selection == 2) { //if int is 2, call the rules function
                    rules();
                }
                if (selection == 3) { //if int is 3, print “Load”
                    System.out.println("Load");
                    return;
                }
                if (selection == 4) { //if int is 4, print “Score”
                    Score.score();
                }
                if (selection == 5){ //if int is 5, print “Goodbye”
                    System.out.println("Goodbye");
                    return;
                }
                if(selection < 1 || selection > 5){
                    System.out.println(RED + "Enter a valid number !");
                }
            }
            else{ //otherwise a valid element is requested.
                System.out.println(RED + "Please enter a valid item !");
                input.next();
            }
        }while (true);
    }
Game game = new Game();
    /**
     * Displays game rules
     */
    public static void rules(){

        System.out.println(GREEN + "\n**** Welcome to This is a game **** \n" +
                        PURPLE + "\nGoal : The goal is to be the last player able to move on the board.\n" +
                        "\nMove : Move your pawn one square, either vertically or horizontally." +
                        "\nDestroy : an adjacent square (not occupied by a player).\n" +
                        "\nConstraints :\n" + "You cannot destroy a square occupied by a player.\n"
                        + "You cannot move onto a destroyed or already occupied square.\n" + "If you get stuck, you lose the game.\n" +
                        "\nPoints :\n" + "5 points for each victory.\n" + "-2 points for each defeat.\n");

        returnLoop();
    }

    public static void returnLoop(){
        System.out.println(YELLOW + "Press (E) to exit !");
        do{ //do while loop that stops when e is pressed
            Scanner input = new Scanner(System.in);
            if (input.hasNext("e") || input.hasNext("E")) //press e/E to exit the rules
                return;
            else if (input.hasNext())
                System.out.println(RED + "Enter the correct letter !");
        }while (true);
    }
}