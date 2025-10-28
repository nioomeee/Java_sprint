import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class q2 {
	public static void main(String[] args) {
		Pattern validPattern = Pattern.compile("([A-Z0-9]+),([a-z]+)");
		String winner = null;
		int maxLog = 0;
		int maxLine = -1;
		
		Map<String, Integer>logs = new HashMap<>(); 
		Map<String, Integer> lines = new HashMap<>();
		
		int lineNum = 0;

		try(BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine())!=null) {
				Matcher m = validPattern.matcher(currentLine.trim());
				lineNum++;

				if(m.matches()) {
					String name = m.group(1);
					String action = m.group(2);

					if(!logs.containsKey(name)) {
						logs.put(name, 1);
					} else {
						int num = logs.get(name);
						logs.put(name, num+1);
					}
					lines.put(name, lineNum);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"))) {
			for(Map.Entry<String, Integer> entry: logs.entrySet()) {
				String name = entry.getKey();
				int value = entry.getValue();
				int line = lines.get(name);
				
				if(value > maxLog) {
					maxLog = value;
					winner = name;
					maxLine = line;
				} else if(value == maxLog) {
					if(line > maxLine) {
						maxLine = line;
						winner = name;
					}
				}
			}
			
			writer.write(winner);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}