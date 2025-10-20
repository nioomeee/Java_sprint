import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class wordCounter {
    public static void main(String[] args) {
        Map<String, Integer> wordCounts = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.split("\\s+");

                for (String word: words) {
                    String cleanWord = word.toLowerCase().replaceAll("[^a-zA-Z]", "");

                    if (cleanWord.isEmpty()) {
                        continue;
                    }

                    if(wordCounts.containsKey(cleanWord)) {
                        int currentCount = wordCounts.get(cleanWord);
                        wordCounts.put(cleanWord, currentCount + 1);
                    } else {
                        wordCounts.put(cleanWord, 1);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            for (Map.Entry<String, Integer> entry: wordCounts.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();

                writer.write(word + ": " + count);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}