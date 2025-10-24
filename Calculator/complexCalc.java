import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

class FatalError extends Exception {
    public FatalError(String msg) {
        super(msg);
    }
}

public class complexCalc {
    public static void main(String[] args) {
        Pattern declare = Pattern.compile("([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)\\s*;?");

        Pattern assign = Pattern.compile("([a-zA-Z]+)\\s*([+\\-*/]?=)\\s*([+-]?\\d+|[a-zA-Z]+)\\s*;?");

        Map<String, Integer> values = new HashMap<>();
        Map<String, Integer> lines = new HashMap<>();

        int line = 0;

        String fatalMsg = null;

        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                line++;

                try {

                    if(currentLine.trim().startsWith("int ")) {
                        Matcher m = declare.matcher(currentLine);

                        while(m.find()) {
                            String name = m.group(1);
                            int value = Integer.parseInt(m.group(2));

                            values.put(name, value);
                            lines.put(name, line);
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
                                if(!values.containsKey(valString)) {
                                    throw new FatalError("Uninitialized variable");
                                }
                                value = values.get(valString);
                            }

                            switch(operator) {
                                case "=":
                                    values.put(name, value);
                                    break;

                                case "+=":

                                case "-=":

                                case "*=":

                                case "/=":
                                    if(!values.containsKey(name)) {
                                        throw new FatalError("Uninnitialized Variable");
                                    }
                                    int oldVal = values.get(name);

                                    if(operator.equals("+=")) {
                                        values.put(name, oldVal + value);
                                    } 
                                    if (operator.equals("-=")) {
                                        values.put(name, oldVal - value);
                                    }
                                    if(operator.equals("*=")) {
                                        values.put(name, oldVal * value);
                                    }
                                    if(operator.equals("/=")) {
                                        if(value == 0) {
                                            throw new FatalError("division by 0");
                                        } else {
                                            values.put(name, oldVal / value);
                                        }
                                    }
                                    break;
                            }
                            lines.put(name, line);
                        }
                    }

                } catch (FatalError e) {
                    fatalMsg = e.getMessage();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            if(fatalMsg != null) {
                writer.write(fatalMsg);
            } else {
                int maxVal = Integer.MIN_VALUE;
                int maxLine = -1;
                String winner = "";
                for(Map.Entry<String, Integer> entry : values.entrySet()) {
                    String name = entry.getKey();
                    int value = entry.getValue();
                    int currLine  = lines.get(name);

                    if (value > maxVal) {
                        winner = name;
                        maxVal = value;
                        maxLine = currLine;
                    } else if(value == maxVal) {
                        if(currLine > maxLine) {
                            maxLine = currLine;
                            winner = name;
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