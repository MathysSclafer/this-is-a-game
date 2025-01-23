import java.io.*;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.nio.file.Files;

/**
 * The SaveAndLoad class provides methods for saving and loading game data,
 * including scores and file digests, to ensure data integrity.
 */
public class SaveAndLoad {

    /**
     * Default constructor for SaveAndLoad.
     */
    public SaveAndLoad() {
    }

    /**
     * Attempts to load the scores from the file.
     *
     * @return A HashMap containing the scores, or null if the file is inaccessible.
     */
    public HashMap<String, Integer> TryToLoadScore() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt");
        if (System.getProperty("os.name").contains("Mac OS")) {
            fichier = new File("/Users/" + System.getProperty("user.name") + "/Desktop/Score.txt");
        }

        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichier));
        } catch (IOException var7) {
            System.out.println("File inaccessible");
            return null;
        }

        HashMap<String, Integer> m = null;

        try {
            m = (HashMap<String, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    /**
     * Attempts to load the digest file and prints its content.
     */
    public void TryToLoad() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X")) {
            fichier = new File(System.getProperty("user.dir") + "/src/Digest.txt");
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
            m = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(m);
    }

    /**
     * Saves the file digest to a specific file path.
     */
    public static void TryToSave() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X")) {
            fichier = new File(System.getProperty("user.dir") + "/src/Digest.txt");
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

    /**
     * Saves the scores to a specific file path.
     *
     * @param map A map containing the scores to save.
     */
    public static void TryToSaveScore(Map<String, Integer> map) {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt");
        if (System.getProperty("os.name").contains("Mac OS X")) {
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

    /**
     * Calculates and returns the hash of the score file using SHA-256.
     *
     * @return A string containing the encoded hash, or null if the file does not exist.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    public static String hashFile() throws NoSuchAlgorithmException {
        String fichier = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Score.txt";
        if (System.getProperty("os.name").contains("Mac OS X")) {
            fichier = "/Users/" + System.getProperty("user.name") + "/Desktop/Score.txt";
        }

        // Read the file content
        byte[] fileContentBytes = null;
        try {
            fileContentBytes = Files.readAllBytes(Paths.get(fichier));
        } catch (IOException e) {
            System.out.println("File DOES NOT EXIST");
            Map<String, Integer> user_scores = new HashMap<>();
            TryToSaveScore(user_scores);
            return null;
        }

        // Create the MessageDigest and compute the hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(fileContentBytes);

        // Encode the hash in Base64
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Loads the digest file and returns its content as a string.
     *
     * @return The digest string, or null if the file is inaccessible.
     */
    public String TryToLoad2() {
        File fichier = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\this-is-a-game\\src\\Digest.txt");
        if (System.getProperty("os.name").contains("Mac OS X")) {
            fichier = new File(System.getProperty("user.dir") + "/src/Digest.txt");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File inaccessible: " + fichier.getAbsolutePath());
            return null;
        }
    }

    /**
     * Compares the given digest with the one loaded from the file.
     *
     * @param encoded The encoded digest to compare.
     * @return True if the digests match, false otherwise.
     */
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
