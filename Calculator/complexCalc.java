import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.HashMap;

class FatalError extends Exception {
    public FatalError(String msg) {
        super(msg);
    }
}

public class complexCalc {
    public static void main(String[] args) {

        Pattern declare = Pattern.compile("([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)");

        Pattern assign = Pattern.compile("([a-zA-Z]+)\\s*([+\\-*/]?=)\\s*([+-]?\\d+|[a-zA-Z]+)\\s*;?");

        Map<String, Integer> Variables = new HashMap<>();

        Map<String, Integer> Lines = new HashMap<>();

        int lineNum = 0;

        String fatalMsg = null;

        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                lineNum++;

                try {

                    if(currentLine.trim().startsWith("int ")) {
                        Matcher m = declare.matcher(currentLine);

                        while(m.find()) {
                            String name = m.group(1);
                            int value = Integer.parseInt(m.group(2));

                            Variables.put(name, value);
                            Lines.put(name, lineNum);
                        }
                    } else {
                        Matcher m = assign.matcher(currentLine);

                        if(m.matches()) {
                            String name = m.group(1);
                            String operator = m.group(2);
                            String valString = m.group(3);

                            int value;

                            try {
                                value = Integer.parseInt(valString);
                            } catch (NumberFormatException e) {
                                if (!Variables.containsKey(valString)) {
                                    throw new FatalError("Uninitialized variable");
                                }
                                value = Variables.get(valString);
                            }

                            switch(operator) {
                                case "=":
                                    Variables.put(name, value);
                                    break;

                                case "+=":
                                case "-=":
                                case "*=":
                                case "/=":
                                    if(!Variables.containsKey(name)) {
                                        throw new FatalError("Uninitialized Variable");
                                    }

                                    int oldVal = Variables.get(name);

                                    if(operator.equals("+=")) {
                                        Variables.put(name, oldVal + value);
                                    }   
                                    if (operator.equals("-=")) {
                                        Variables.put(name, oldVal - value);
                                    } 
                                    if (operator.equals("*=")) {
                                        Variables.put(name, oldVal * value);
                                    } 
                                    if (operator.equals("/=")) {
                                        if(value == 0) {
                                            throw new FatalError("Division by 0");
                                        }
                                        Variables.put(name, oldVal / value);
                                    }
                                    break;
                            }

                            Lines.put(name, lineNum);
                        }
                    }

                } catch (FatalError e) {
                    fatalMsg = e.getMessage();
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            if(fatalMsg != null) {
                writer.write(fatalMsg);
            } else {
                String winner = "";
                int maxVal = Integer.MIN_VALUE;
                int winnerLine = -1;

                for(Map.Entry<String, Integer> entry: Variables.entrySet()) {
                    String name = entry.getKey();
                    int value = entry.getValue();
                    int line = Lines.get(name);

                    if(value > maxVal) {
                        maxVal = value;
                        winner = name;
                        winnerLine = line;
                    } else if(value == maxVal) {
                        if (line > winnerLine) {
                            winner = name;
                            winnerLine = line;
                        }
                    }
                }
                writer.write(String.valueOf(maxVal));
            }

        } catch(IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}