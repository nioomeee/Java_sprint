import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class q1 {
	public static void main(String[] args) {
		Map<String, Integer> counts = new HashMap<>();
		Map<String, Integer>lines = new HashMap<>();

		int lineNum = 0;
		
		try(BufferedReader reader = new BufferedReader(new FileReader("input1.txt"))) {
			String currentLine;
			
			while((currentLine = reader.readLine()) != null) {
				lineNum++;
				String[] parts = currentLine.split(",");
				if(parts.length != 2) continue;

				String name = parts[0].trim();
				
				counts.put(name, counts.getOrDefault(name, 0) + 1);
				lines.put(name, lineNum);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		List<String> users = new ArrayList<>(counts.keySet());

		users.sort((a,b) -> {
			int countCompare = Integer.compare(counts.get(b), counts.get(a));
			if(countCompare != 0) return countCompare;
			return Integer.compare(lines.get(b), lines.get(a));
		});

		List<String> top3 = users.subList(0, Math.min(3, users.size()));

		Collections.sort(top3);

		try(BufferedWriter writer = new BufferedWriter (new FileWriter("output1.txt"))) {
			for(String top: top3) {
				writer.write(top);
				writer.newLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}