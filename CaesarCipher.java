import java.util.*;

public class CaesarCipher {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String plainText = sc.nextLine();
        int key = sc.nextInt();

        String cipherText = encrypt(plainText,key);
        String decryptedText = encrypt(cipherText,-key);

        System.out.println("Plain Text : " + plainText);
        System.out.println("Encrypted Text : " + cipherText);
        System.out.println("Decrypted Text : " + decryptedText);

        sc.close();
    }

    private static String encrypt(String text, int k){
        StringBuffer sb = new StringBuffer();

        for(char c : text.toCharArray()){
            if(Character.isLetter(c)){
                char base = Character.isUpperCase(c)?'A':'a';

                sb.append((char)((c - base + k) % 26 + base));
            }
            else{
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
