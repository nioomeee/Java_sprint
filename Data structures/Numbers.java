import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class Numbers {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                try {
                    int num = Integer.parseInt(currentLine);
                    
                    if(num >= 0) {
                        numbers.add(num);
                    }
                } catch (NumberFormatException e) {

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        Collections.sort(numbers);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (int num: numbers) {
                writer.write(String.valueOf(num));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Successful");
    }
}