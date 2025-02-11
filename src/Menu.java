import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Menu extends Globals{

    public static void main(String[] args) {

        SaveAndLoad saveAndLoad = new SaveAndLoad();
        try {
            if (saveAndLoad.CompareDigest(SaveAndLoad.hashFile())){
                saveAndLoad.TryToLoadScore();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        menu();

        secretSound();
    }


    //Fonction menu
    //Ajout d'un scanner pour prendre en compte la touche saisit par l'user
    //Print 'Choisit une option', choix 1, 2, 3, 4, 5
    //boucle do while qui s'arrete lorsque l'user selectionne une reponse valide
    //verifie si l'element entré par l'user est un int
    //si 1 tapé print Play, si 2 tapé print rules, etc...
    //sinon print 'entrez une nombre valide'
    /**
     * Displays the menu of the corresponding input
     */
    public static void menu() {

        short selection;

        Scanner input = new Scanner(System.in);

        isEnd = false;

        do { //do while loop that stops when the user enters a valid element
            System.out.println(YELLOW + "\n**** THIS IS A GAME ****" + RESET);
            System.out.println(BLUE + "Select an option" + RESET);
            System.out.println(GREEN + "*****************" + RESET);
            System.out.println(CYAN + "1 / Play");
            System.out.println("2 / Rules");
            System.out.println("3 / Load");
            System.out.println("4 / Score");
            System.out.println("5 / Quit");

            if (input.hasNextInt()) { //if the field entered by the user is an int, the selection takes the int into account
                selection = (short) input.nextInt();
                if (selection == 1) {//if int is 1, print “Play”
                    Game.play();
                }
                if (selection == 2) { //if int is 2, call the rules function
                    rules();
                }
                if (selection == 3) { //if int is 3, print “Load”
                    System.out.println("Load" + RESET);
                    SaveAndLoad.TryToLoadGame();
                    if (gamesave.players!=null)
                    {
                    Game.playloop();
                    }

                 }
                if (selection == 4) { //if int is 4, print “Score”
                    Score.score();
                }
                if (selection == 5){ ////if int is 5, print “Goodbye”
                    System.out.println("Goodbye");
                    System.exit(0);
                }
                if(selection < 1 || selection > 5){
                    System.out.println(RED + "Enter a valid number !" + RESET);
                }
            }
            else{//otherwise a valid element is requested.
                System.out.println(RED + "Please enter a valid item !" + RESET);
                input.next();
            }
        }while (true);
    }


    //Fonction rules
    //print les regles, etc...
    //print 'E pour sortir'
    //boucle do while s'arrete que si l'user entre element valide
    //si touche e / E entré, retourne au menu
    //si autre touche entré, print 'entrer une lettre corret'
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

    /**
     * Return option to be displayed
     */
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


    //Fonction secretSound
    //création d'un thread qui va permettre de lancer un son
    //lance la musique lorsque un player met le pseudo 'clement'
    //sinon envoie message erreur et lance pas musique
    /**
     * Play secretSound
     */
    public static void secretSound() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Menu.class.getResourceAsStream("Macron_voice.wav"));
                    clip.open(inputStream);
                    clip.start();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}

