import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class q5 {
	public static void main(String[] args) {
		Pattern validPattern = Pattern.compile("(MSG_ID|REPLY_TO)_(\\d+)\\s+\\[.*?\\]\\s+\\S+:\\s+(.*)");

		Map<Integer, Integer> threadLength = new HashMap<>();

		try(BufferedReader reader = new BufferedReader(new FileReader("input5.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				Matcher m = validPattern.matcher(currentLine);
				if(m.matches()) {
					String action = m.group(1);
					int id = Integer.parseInt(m.group(2));
					String message = m.group(3);
					int length = message.length();

					if(action.equals("MSG_ID")) {
						threadLength.put(id, threadLength.getOrDefault(id, 0) + length);
					} else if(action.equals("REPLY_TO")) {
						threadLength.put(id, threadLength.getOrDefault(id, 0) + length);
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		int maxLength = 0;
		for(Map.Entry<Integer, Integer> entry: threadLength.entrySet()) {
			int value = entry.getValue();
			if(value > maxLength) {
				maxLength = value;
			}
		}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output5.txt"))) {
			if(threadLength.isEmpty()) {
				writer.write("NO DATA");
			} else {
				writer.write(String.valueOf(maxLength));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}