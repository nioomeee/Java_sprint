import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class versionMaster {
	static class Server implements Comparable<Server> {
		String name;
		String version;
		int line;

		public Server (String name, String version, int line) {
			this.name = name;
			this.version = version;
			this.line = line;
		}

		public int compareTo(Server other) {
			return compareVersions(this.version, other.version);
		}

		private int compareVersions(String v1, String v2) {
			String[] parts1 = v1.split("\\.");
			String[] parts2 = v2.split("\\.");
			
			int maxLength = Math.max(parts1.length, parts2.length);

			for(int i = 0; i < maxLength; i ++) {
				int part1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
				int part2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;
				if (part1 < part2) return -1;
				if (part1 > part2) return 1;  
			}

			return 0;
		}
	}

	public static void main(String[] args) {
		Server oldestServer = null;
		int lineNum = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader("input5.txt"))) {
			String currentLine;
			while((currentLine= reader.readLine()) != null) {
				lineNum++;

				String[] parts = currentLine.split(" ");
				String name = parts[0];
				String version = parts[1];

				Server currentServer = new Server(name, version, lineNum);

				if(oldestServer == null || currentServer.compareTo(oldestServer) < 0) {
					oldestServer = currentServer;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try(BufferedWriter writer = new BufferedWriter (new FileWriter("output5.txt"))) {
			if(oldestServer != null) {
				writer.write(oldestServer.name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}