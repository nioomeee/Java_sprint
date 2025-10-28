import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class q1 {
	public static void main(String[] args) {
		Map<String, Set<String>> userActions = new HashMap<>();
		Map<String, Integer> lines = new HashMap<>();
		int lineNum = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader("input1.txt"))) {
			String currentLine;
			while((currentLine = reader.readLine()) != null) {	
				lineNum++;
				String[] parts = currentLine.split(",");
				if(parts.length != 2) continue;
				String user = parts[0];
				String action = parts[1];

				userActions.putIfAbsent(user, new HashSet<>());
				Set<String> actions = userActions.get(user);

				if(!actions.contains(action)) {
					actions.add(action);
					lines.put(user, lineNum);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int maxActions = 0;
		int minLine = 0;
		String winner = null;
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
			for(String user: userActions.keySet()) {
				int value = userActions.get(user).size();
				int line = lines.get(user);

				if(value > maxActions) {
					maxActions = value;
					winner = user;
					minLine = line;
				} else if (value == maxActions) {
					if(line < minLine) {
						winner = user;
						minLine = line;
					}
				}
			}
			writer.write(winner);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}