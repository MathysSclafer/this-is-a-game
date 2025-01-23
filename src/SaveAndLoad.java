import java.io.*;
import java.util.*;

public class SaveAndLoad {
    String username = System.getProperty("user.name");

    public SaveAndLoad() {
    }
    public HashMap<String,Integer> TryToLoadScore() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\unnamed.txt");
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichier));
        } catch (IOException var7) {
            System.out.println("File inaccessible");
            return null;
        }

        HashMap<String,Integer> m = null;

        try {
            m = (HashMap<String,Integer>)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return m;
     }

    public void TryToLoad() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\unnamed.sss");
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichier));
        } catch (IOException var7) {
            System.out.println("File inaccessible");
            return;
        }

        String m = null;

        try {
            m = (String)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(m);
    }

    public void TryToSave() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\unnamed.txt");
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String m = "Gobe moi le zob";

        try {
            oos.writeObject(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void TryToSaveScore(Map<String,Integer> map) {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\unnamed.txt");
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            oos.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

