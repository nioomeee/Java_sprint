import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		@Override
		public int compareTo(Server other) {
			int res = compareVersion(this.version, other.version);
			if(res == 0) {
				return Integer.compare(this.line, other.line);
			}
			return res;
		}
		
		private int compareVersion(String v1, String v2) {
			String[] parts1 = v1.split("\\.");
			String[] parts2 = v2.split("\\.");

			int maxLength = Math.max(parts1.length, parts2.length);

			for(int i = 0; i < maxLength; i ++) {
				int part1 = (i < parts1.length) ? (Integer.parseInt(parts1[i])) : 0;
				int part2 = (i < parts2.length) ? (Integer.parseInt(parts2[i])) : 0;

				if(part1 > part2) return 1;
				if(part1 < part2) return -1;
			}
			return 0;
		}
	}
	public static void main(String[] args) {
		Server newestServer = null;

		Pattern validPattern = Pattern.compile("([a-zA-Z]+)\\s*(\\d+(?:\\.\\d+)){0,2}");
		
		int lineNum = 0;

		try(BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String currentLine;

			while((currentLine = reader.readLine())!=null) {
				lineNum++;
				Matcher m = validPattern.matcher(currentLine);

				if(m.matches()) {
					String name = m.group(1);
					String version = m.group(2);

					Server currentServer = new Server(name, version, lineNum);
					
					if(newestServer == null || currentServer.compareTo(newestServer) > 0) {
						newestServer = currentServer;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"))) {
			writer.write(newestServer.name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}