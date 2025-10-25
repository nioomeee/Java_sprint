import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;

public class AccessLogger {
	public static void main (String[] args) {
		Pattern desired = Pattern.compile("([a-zA-Z0-9]+),([a-zA-Z]+),([a-zA-Z]+)");

		Map<String, Integer> logs = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine())!= null) {
				Matcher m = desired.matcher(currentLine);

				if(m.matches()) {
					String user = m.group(1);
					String file = m.group(2);
					String action = m.group(3);
					int num = 0;

					if(!logs.containsKey(user)){
						logs.put(user, 1);
					} else if (logs.containsKey(user)) {
						num = logs.get(user);
						logs.put(user, num+1);
					}
				}
			}
		} catch (IOException e) {}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"))) {
			for(Map.Entry<String, Integer> entry: logs.entrySet()) {
				String user = entry.getKey();
				int num = entry.getValue();

				writer.write(user + ": " + num);
				writer.newLine();
			}
		} catch (IOException e) {}
	}
}