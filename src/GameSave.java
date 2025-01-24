import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
public class GameSave extends Globals implements Serializable{

    Player[] players ;
    String[][] maze = new String[11][10];
    short currentplayerindex;

}
