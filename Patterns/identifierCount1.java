import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class identifierCount1 {
    public static void main(String[] args) {
        int totalIdentifiers = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                boolean inIdentifier = false;

                for(int i = 0; i < currentLine.length(); i ++) {
                    char c = currentLine.charAt(i);

                    if (Character.isLetter(c)) {
                        if(!inIdentifier) {
                            totalIdentifiers++;
                            inIdentifier = true;
                        }
                    } else {
                        inIdentifier = false;

                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(String.valueOf(totalIdentifiers));
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}