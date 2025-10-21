import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrictRegex {
    public static void main(String[] args) {

        int total = 0;

        String regex = "([a-zA-Z]+)=(\\d+)";

        Pattern strictPattern = Pattern.compile(regex);

        try(BufferedReader reader = new BufferedReader (new FileReader("input1.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                Matcher m = strictPattern.matcher(currentLine);

                if(m.matches()) {
                    total ++;
                } 
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
            writer.write(String.valueOf(total));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}