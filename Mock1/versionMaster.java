import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

class versionMaster {

	static class Server implements Comparable<Server> {
		String name;
		int line;
		String version;

		public Server(String name, String version, int line) {
			this.name = name;
			this.version = version;
			this.line = line;
		}

		@Override
		public int compareTo(Server other) {
			return compareVersion(this.version, other.version);
		}

		private int compareVersion(String v1, String v2) {
			String[] parts1 = v1.split("\\.");
			String[] parts2 = v2.split("\\.");

			int maxLength = Math.max(parts1.length, parts2.length);

			for(int i = 0; i < maxLength; i ++) {
				int part1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
				int part2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;

				if(part1 < part2) {
					return -1;
				} else if(part2 < part1) {
					return 1;
				}
			}
			return 0;
		}

	}

	public static void main(String[] args) {
		String oldestServer = null;
		int lineNum=0;
		
	}
}