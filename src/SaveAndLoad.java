import java.io.*;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.File;
import java.nio.file.Files;

public class SaveAndLoad {

    public SaveAndLoad() {
    }
    public HashMap<String,Integer> TryToLoadScore() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt");
        if (System.getProperty("os.name").contains("Mac OS") ) {
            fichier = new File("/Users/" + System.getProperty("user.name") + "/Desktop/Score.txt");
        }

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
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X") ) {
            fichier = new File("/Users/"+ System.getProperty("user.name") +"/Desktop/this-is-a-game/src/Digest.txt");
        }
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

    public static void TryToSave() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X") ) {
            fichier = new File("/Users/"+ System.getProperty("user.name") +"/Desktop/this-is-a-game/src/Digest.txt");
        }
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            oos.writeObject(hashFile());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void TryToSaveScore(Map<String, Integer> map) {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt");
        if (System.getProperty("os.name").contains("Mac OS X") ) {
            fichier = new File("/Users/" + System.getProperty("user.name") + "/Desktop/Score.txt");
        }
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

    public static String hashFile() throws NoSuchAlgorithmException {
        String fichier = new String("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt");
        if (System.getProperty("os.name").contains("Mac OS X") ) {
            fichier = new String("/Users/" + System.getProperty("user.name") + "/Desktop/Score.txt");
        }
        // On lit le contenu du fichier :
        byte[] fileContentBytes = null;
        try {
            fileContentBytes = Files.readAllBytes( Paths.get(fichier) );
        } catch (IOException e) {
            System.out.println("File DOES NOT EXIST");
            Map<String, Integer> user_scores = new HashMap<>();
            TryToSaveScore(user_scores);
            return null;
        }
        // On crée le MessageDigest avec l’algorithme désiré :
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // On calcule le hash :
        byte[] hash = digest.digest(fileContentBytes );
        // Que l'on encode en base64 (java 8 - sinon il faut une solution tierce)
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded;
    }



    public String TryToLoad2() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X") ) {
            fichier = new File("/Users/"+ System.getProperty("user.name") +"/Desktop/this-is-a-game/src/Digest.txt");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File inaccessible: " + fichier.getAbsolutePath());
            return null;
        }
    }

    public static boolean CompareDigest(String encoded) {
        String loadedDigest = new SaveAndLoad().TryToLoad2();
        if (encoded != null) {
            if (!encoded.equals(loadedDigest)) {
                System.out.println("Digest mismatch! Data may have been tampered with.");
                Map<String, Integer> user_scores = new HashMap<>();
                TryToSaveScore(user_scores);
                TryToSave();
                return false;
            } else {
                System.out.println("Digest matches. File is intact.");
                return true;
            }
        }
        return false;
    }


}

