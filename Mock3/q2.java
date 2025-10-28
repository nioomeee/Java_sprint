import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collections;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class q2 {
	public static void main (String[] args) throws IOException {
		Map<String, List<Integer>> serverLoads = new HashMap<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String[] parts = currentLine.split(" ");

				try {
					String server = parts[0];
					int value = Integer.parseInt(parts[1]);
					serverLoads.computeIfAbsent(server, k->new ArrayList<>()).add(value);
				} catch(Exception e) {}
			}
		} 

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
			String winner = null;
			int maxCount = Integer.MIN_VALUE;
			int maxMedian = Integer.MIN_VALUE;
			for(Map.Entry<String, List<Integer>> entry: serverLoads.entrySet()) {
				String server = entry.getKey();
				List<Integer> loads = entry.getValue();	
				int currentCount = loads.size();
				
				int medianIdx = (currentCount - 1) / 2;
				int median = loads.get(medianIdx);

				if(median > maxMedian) {
					maxMedian = median;
					maxCount = currentCount;
					winner = server;
				} else if(median == maxMedian && currentCount > maxCount) {
					maxCount = currentCount;
					winner = server;
				}
			}
			if(winner != null) {
				writer.write(winner);
			}else {
				writer.write("No winner server");
			}
		}
	}	
}