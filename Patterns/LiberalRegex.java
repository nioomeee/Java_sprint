import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiberalRegex {
    public static void main(String[] args) {
        int total = 0;

        String regex = "([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)";

        Pattern liberalPattern = Pattern.compile(regex);

        try(BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                Matcher m = liberalPattern.matcher(currentLine);

                if(m.matches()) {
                    total ++;
                    String name = m.group(1);
                    String value = m.group(2);

                    System.out.println("Found match! \nName: " + name + ", Value: " + value);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading: " + e.getMessage());
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"))) {
            writer.write(String.valueOf(total));
        } catch (IOException e) {
            System.err.println("Error writing: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}