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
    public static void main (String[] args) {

        Pattern declare = Pattern.compile("([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)\\s*;?");
        Pattern assign = Pattern.compile("([a-zA-Z]+)\\s*([+\\-*/]?=)\\s*([+-]?\\d+|[a-zA-Z]+)\\s*;");

        Map<String, Integer> Values = new HashMap<>();
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

                            Values.put(name, value);
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
                            } catch(NumberFormatException e) {
                                if(!Values.containsKey(valString)) {
                                    throw new FatalError("Uninitialized Variable");
                                }
                                value = Values.get(valString);
                            }

                            switch(operator) {
                                case "=":
                                    Values.put(name, value);
                                    break;

                                case "+=":
                                case "-=":
                                case "*=":
                                case "/=":
                                    if(!Values.containsKey(name)) {
                                        throw new FatalError("Uninitialized variable");
                                    }

                                    int oldVal = Values.get(name);

                                    if(operator.equals("+=")) {
                                        Values.put(name, oldVal + value);
                                    } 
                                    if(operator.equals("-=")) {
                                        Values.put(name, oldVal - value);
                                    }
                                    if(operator.equals("*=")) {
                                        Values.put(name, oldVal * value);
                                    }
                                    if(operator.equals("/=")) {
                                        if(value == 0) {
                                            throw new FatalError("Error: Division by 0");
                                        }
                                        Values.put(name, oldVal / value);
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
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            if(fatalMsg != null) {
                writer.write(fatalMsg);
            } else {
                String winner = "";
                int maxVal = Integer.MIN_VALUE;
                int maxLine =  -1;


                for(Map.Entry<String, Integer> entry : Values.entrySet()) {
                    String name = entry.getKey();
                    int value = entry.getValue();
                    int line = Lines.get(name);

                    if(value > maxVal) {
                        maxVal = value;
                        winner = name;
                        maxLine = line;
                    } else if(value == maxVal) {
                        if(line > maxLine) {
                            maxLine = line;
                            winner = name;
                        }
                    }
                }

                writer.write(String.valueOf(maxVal));
            }
        } catch (IOException e) {
            
        }
    }
}