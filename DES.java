import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DES {
    public static void main(String[] args) {

        try {
            // Generate DES key
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            SecretKey mySecretKey = kg.generateKey();

            // Initialize Cipher
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Read plaintext from file
            String inputFilePath = "./input.txt";
            FileInputStream inputFile = new FileInputStream(inputFilePath);
            byte[] byteText = new byte[(int) new File(inputFilePath).length()];
            inputFile.read(byteText);
            inputFile.close();

            // Encrypt
            cipher.init(Cipher.ENCRYPT_MODE, mySecretKey);
            byte[] cipherByte = cipher.doFinal(byteText);

            // Write encrypted text to file
            String encryptedFilePath = "./encrypted.txt";
            FileOutputStream encryptedFile = new FileOutputStream(encryptedFilePath);
            encryptedFile.write(cipherByte);
            encryptedFile.close();

            // Decrypt
            cipher.init(Cipher.DECRYPT_MODE, mySecretKey);
            byte[] decryptedByte = cipher.doFinal(cipherByte);

            // Write decrypted text to file
            String decryptedFilePath = "./decrypted.txt";
            FileOutputStream decryptedFile = new FileOutputStream(decryptedFilePath);
            decryptedFile.write(decryptedByte);
            decryptedFile.close();

            // Display original, encrypted, and decrypted text
            System.out.println("Original Text: " + new String(byteText));
            System.out.println("Encrypted Text: " + new String(cipherByte));
            System.out.println("Decrypted Text: " + new String(decryptedByte));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
