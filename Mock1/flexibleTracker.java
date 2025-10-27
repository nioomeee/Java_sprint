import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class flexibleTracker {
	public static void main (String[] args) {
		Pattern flexible = Pattern.compile("([a-zA-Z]+)\\s*=\\s*([+-]?\\d+)");
		String maxString = "";
		int maxVal = Integer.MIN_VALUE;

		try (BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))){
			String currentLine;

			while((currentLine = reader.readLine())!=null){
				Matcher m = flexible.matcher(currentLine);

				if(m.matches()) {
					String variable = m.group(1);
					int value = Integer.parseInt(m.group(2));

					if (value != 0) {
						int absValue = Math.abs(value);
						if(absValue >= maxVal) {
							maxVal = absValue;
							maxString = variable;
						}
					}
				}
			}
		} catch (IOException e) {}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"))) {
			writer.write(maxString);
		} catch (IOException e) {

		}
	}
}