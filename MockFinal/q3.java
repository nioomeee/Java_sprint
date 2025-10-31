import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class q3 {
	public static void main(String[] args) {
		Map<String, String> maxVersions = new HashMap<>(); // app name and version
		Map<String, Integer>outdated = new HashMap<>(); // server, version
		Map<String, Integer>apps = new HashMap<>(); // Server - number of apps
		

		try(BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String currentLine = null;

			while((currentLine = reader.readLine()) != null) {
				String[] parts = currentLine.split(",");
				if(parts.length != 3) continue;

				String server = parts[0];
				String app = parts[1];
				String version = parts[2];
				
				apps.put(server, apps.getOrDefault(server, 0) + 1);
				if(!maxVersions.containsKey(app)) {
					maxVersions.put(app, version);
					
				} else {
					
					String maxVersion = maxVersions.get(app);
					if(compareVersions(version, maxVersion) > 0) {
						maxVersions.put(app, version);
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}


		try(BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				String[] parts = currentLine.split(",");
				if(parts.length != 3) continue;

				String server = parts[0];
				String app = parts[1];
				String version = parts[2];

				String maxVersion = maxVersions.get(app);
				if(compareVersions(version, maxVersion) < 0) {
					outdated.put(server, outdated.getOrDefault(server, 0) + 1);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		List<String> sorted = new ArrayList<>(outdated.keySet());

		sorted.sort((a,b) -> {
			int countComp = Integer.compare(outdated.get(b), outdated.get(a));
			if(countComp != 0) return countComp;
			return Integer.compare(apps.get(a), apps.get(b));
		});

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"))) {
			if(!sorted.isEmpty()) {
				writer.write(sorted.get(0));
			} else {
				writer.write("Empty");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static int compareVersions(String v1, String v2) {
		String[] parts1 = v1.split("\\.");
		String[] parts2 = v2.split("\\.");

		int len = Math.max(parts1.length, parts2.length);

		for(int i = 0; i < len; i ++) {
			int part1 = (i < parts1.length) ? (Integer.parseInt(parts1[i])) : 0;
			int part2 = (i < parts2.length) ? (Integer.parseInt(parts2[i])) : 0;

			if(part1 > part2) return 1;
			if(part1 < part2) return -1;
		}
			
		return 0;
	}
}
