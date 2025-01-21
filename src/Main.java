import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    /**
     * @return
     */
    public static int menu() {

        int selection;

        Scanner input = new Scanner(System.in);

        while (true) { //infinite loop that stops only if a valid number is entered
            System.out.println("Select an option");
            System.out.println("*****************");
            System.out.println("1 / Play");
            System.out.println("2 / Rules");
            System.out.println("3 / Save");
            System.out.println("4 / Result");
            System.out.println("5 / Quit");

            if (input.hasNextInt()) { //if the field entered by the user is an int, the selection takes the int into account
                selection = input.nextInt();

                if (selection >= 1 && selection <= 5) { //if int is between 1 and 5, print “Thanks you”.
                    System.out.println("Thanks you !");
                    return selection;
                } else {
                    System.out.println("Enter a valid number");
                }
            }

            else{ //otherwise a valid element is requested.
                System.out.println("Please enter a valid item");
                input.next();
            }
        }
    }
}