import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class q3 {
	public static void main(String[] args) throws IOException {
		Map<String, Integer> finalValues = new HashMap<>();
		Map<String, Integer> assignments = new HashMap<>();

		Pattern declare = Pattern.compile("^\\s*int\\s+([^;]);?");	
		Pattern var_to_var = Pattern.compile("^\\s*([a-zA-Z]+)\\s*=\\s*([a-zA-Z]+);?");
		Pattern assign = Pattern.compile("^\\s*([a-zA-Z]+)\\s*([-+*/]?=)\\s*(\\d+);?");

		Map<String, Long> values = new HashMap<>();
		Map<String, Integer> assignments = new HashMap<>();

		try(BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty() || line.startsWith("//")) continue;
				boolean processedLine = false;
				
				Matcher m = declare.matcher(currentLine);
				if(m.find()) {
					String declarations = m.group(1).trim();
					String[] parts = declarations.split(",");
					for(String part: parts) {
						String assign = parts.split("=");
					}
					
				}
			} 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
