import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Numbers {
    public static void main (String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
                String currentLine;

                while((currentLine = reader.readLine()) != null) {
                    try {
                        Integer.parseInt(currentLine);
                        writer.write(currentLine);
                    } catch (NumberFormatException e) {
                        writer.write("INVALID");
                    }
                    writer.newLine();
                }
        } catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Printed successfully");
    }
}