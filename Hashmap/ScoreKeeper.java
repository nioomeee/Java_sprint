import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScoreKeeper {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("input1.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                try {
                    String[] parts = currentLine.split(" ");

                    String name = parts[0].toLowerCase();
                    int score = Integer.parseInt(parts[1]);

                    if(scores.containsKey(name)) {
                        scores.put(name, score);
                    } else {
                        scores.put(name, score);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping bad line: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
            for(Map.Entry<String, Integer> entry: scores.entrySet()) {
                String name = entry.getKey();
                int score = entry.getValue();

                writer.write(name + ": " + score);
                writer.newLine();

            }
        } catch (IOException e) {
            System.err.println("Error writing to the file:  "+ e.getMessage());
            e.printStackTrace();
        }
    }
}