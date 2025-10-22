import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateTracker {
    public static void main(String[] args) {
        String regex = "([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)";
        Pattern liberalPattern = Pattern.compile(regex);

        Map<String, Integer> finalValues = new HashMap<>();
        Map<String, Integer> finalLines = new HashMap<>();

        int lowestAbsVal = Integer.MIN_VALUE;
        String lowestAbsWinner = "";

        int lineNum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                lineNum++;

                Matcher m = liberalPattern.matcher(currentLine);

                if(m.matches()) {
                    String name = m.group(1);
                    int value = Integer.parseInt(m.group(2));
                    int absValue = Math.abs(value);
                    
                    if(value != 0) {
                        if (absValue < lowestAbsVal) {
                            lowestAbsVal = absValue;
                            lowestAbsWinner = name;

                        }
                    }
                    finalValues.put(name, value);
                    finalLines.put(name, lineNum);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        int highestFinalValue = Integer.MIN_VALUE;
        String highestFinalWinner = "";
        int highestFinalLine = -1;

        for (Map.Entry<String, Integer> entry: finalValues.entrySet()) {
            String name = entry.getKey();
            int value = entry.getValue();
            int line = finalLines.get(name);

            if (value > highestFinalValue) {
                highestFinalValue = value;
                highestFinalWinner = name;
                highestFinalLine = line;
            } else if (value == highestFinalValue) {
                if (line > highestFinalLine) {
                    highestFinalWinner = name;
                    highestFinalLine = line;
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(lowestAbsWinner);
            writer.newLine();
            writer.write(highestFinalWinner);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}