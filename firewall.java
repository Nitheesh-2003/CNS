import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class firewall {

    private static final String HOSTS_FILE_PATH = "C:\\Windows\\System32\\drivers\\etc\\hosts";

    public static void main(String[] args) {
        String blockedWebsite = "example.com";

        blockWebsite(blockedWebsite);
        System.out.println("Website blocked successfully.");

        unblockWebsite(blockedWebsite);
        System.out.println("Website unblocked successfully.");
    }

    private static void blockWebsite(String blockedWebsite) {
        try (FileWriter fileWriter = new FileWriter(HOSTS_FILE_PATH, true);PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("127.0.0.1 " + blockedWebsite);
            printWriter.println("::1 " + blockedWebsite);

        } catch (IOException e) {
            System.out.println("Error blocking website: " + e.getMessage());
        }
    }

    private static void unblockWebsite(String blockedWebsite) {
        List<String> linesToRemove = new ArrayList<>();
        linesToRemove.add("127.0.0.1 " + blockedWebsite);
        linesToRemove.add("::1 " + blockedWebsite);

        try (BufferedReader reader = new BufferedReader(new FileReader(HOSTS_FILE_PATH))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!linesToRemove.contains(line.trim())) {
                    lines.add(line);
                }
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(HOSTS_FILE_PATH))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

        } catch (IOException e) {
            System.out.println("Error unblocking website: " + e.getMessage());
        }
    }
}
