import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Virus {
    public static void main(String[] args) {
        try {
            BufferedReader inputFile = new BufferedReader(new FileReader("text.txt"));
            String word = inputFile.readLine();
            inputFile.close();

            StringBuilder modifiedWord = new StringBuilder();
            for (char c : word.toCharArray()) {
                modifiedWord.append((char) (c + 1));
            }

            String reversedWord = modifiedWord.reverse().toString();

            BufferedWriter outputFile = new BufferedWriter(new FileWriter("text.txt"));
            outputFile.write(reversedWord);
            outputFile.close();
        } catch (IOException e) {
            System.err.println("Error handling files: " + e.getMessage());
        }
    }
}
