import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;

class FatalErrorException extends Exception {
	public FatalErrorException(String msg) {
		super(msg);
	}
}

public class compoundTracker {
	public static void main(String[] args) {
		Pattern compound = Pattern.compile("([a-zA-Z]+)\\s*([+-]?=)\\s*([+-]?\\d+)");

		Map<String, Integer> values = new HashMap<>();
		Map<String, Integer> lines = new HashMap<>();
		int lineNum = 0;
		String fatalMsg = null;

		try(BufferedReader reader = new BufferedReader(new FileReader("input4.txt"))){
		
		String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				Matcher m = compound.matcher(currentLine);

				if(m.matches()) {
					String variable = m.group(1);
					String operator = m.group(2);
					int value =  Integer.parseInt(m.group(3));

					switch(operator) {
						case "=":
							values.put(variable, value);
							break;

						case "-=":

						case "+=":
							int oldVal;
							try {	
								if(values.containsKey(variable)) {
									oldVal = values.get(variable);
								} else {
									throw new FatalErrorException("Uninitialized variable");
								}
								if(operator.equals("+=")) {
									values.put(variable, oldVal+value);
								} else if (operator.equals("-=")) {
									values.put(variable, oldVal-value);
								}
							} catch (FatalErrorException e) {
								fatalMsg = e.getMessage();
								break;
							}
					}
					lines.put(variable, lineNum);
				}
			}	
		} catch (IOException e) {
		
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("output4.txt"))) {
			if(fatalMsg != null) {
				writer.write(fatalMsg);
			} else {
				int minVal = Integer.MAX_VALUE;
				String winner = "";
				int minLine = 100;
				
				for(Map.Entry<String, Integer> entry: values.entrySet()) {		
					String name = entry.getKey();
					int value = entry.getValue();
					int currLine = lines.get(name);
					int absValue = Math.abs(value);

					if(absValue == 0) {	
						continue;
					} else if(absValue < minVal) {
						minVal = absValue;
						minLine = currLine;
						winner = name;
					} else if(absValue == minVal) {
						if(currLine < minLine) {			
							minLine = currLine;
							winner = name;
						}
					}
				}
				writer.write(winner);

				
			}
		} catch (IOException e) {

		}
	}
}