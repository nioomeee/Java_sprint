import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class q4 {
	static int compareVersions(String v1, String v2) {
		String[] parts1 = v1.split("\\.");
		String[] parts2 = v2.split("\\.");

		int maxLen = Math.max(parts1.length, parts2.length);

		for(int i = 0; i < maxLen; i ++) {
			int part1 = (i < parts1.length) ? (Integer.parseInt(parts1[i])) : 0;
			int part2 = (i < parts2.length) ? (Integer.parseInt(parts2[i])) : 0;

			if(part1 > part2) return 1;
			if(part1 < part2) return -1;
		}
		
		return 0;
	}

	public static void main(String[] args) {
		Pattern validPattern = Pattern.compile("(\\S+)\\s*(\\S+)\\s*(\\d+(?:\\.\\d+)*)");
		
		Map<String, String> latestVersions = new HashMap<>();

		try(BufferedReader reader = new BufferedReader(new FileReader("input4.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				Matcher m = validPattern.matcher(currentLine);
				if(m.matches()) {
					String comp = m.group(1);
					String app = m.group(2);
					String version = m.group(3);

					if(!latestVersions.containsKey(app)) {
						latestVersions.put(app, version);
					} else {
						String currentMax = latestVersions.get(app);
						if(compareVersions(version, currentMax) > 0) {
							latestVersions.put(app, version);
						}
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Set<String> outdated = new HashSet<>();
		try(BufferedReader reader = new BufferedReader (new FileReader("input4.txt"))) {
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				Matcher m = validPattern.matcher(currentLine);
				if(m.matches()) {
					String comp = m.group(1);
					String app = m.group(2);
					String version = m.group(3);

					String latestVersion = latestVersions.get(app);
					
					if(compareVersions(version, latestVersion) < 0) {
						outdated.add(comp);
					}
					
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output4.txt"))) {
			if(outdated.isEmpty()) {
				writer.write("No outdated computers");
			} else {
				for(String item: outdated) {
					writer.write(item);
					writer.newLine();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}