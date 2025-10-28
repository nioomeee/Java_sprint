import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class q1 {
	public static void main(String[] args) {
		Pattern validPattern = Pattern.compile("\\d+");
		int sum = 0;
		int total = 0;
		int avg = 0;
		int n;
		int valid = 0;
		String fatalMsg = null;
		
		try (BufferedReader reader = new BufferedReader(new FileReader("input1.txt"))) {
			String currentLine;
			n = Integer.parseInt(reader.readLine());
			if(n <= 0) {
				fatalMsg = "Invalid count";
				return;
			} else {
				int[] arr = new int[n];
				while((currentLine = reader.readLine())!=null && valid < n) {
				Matcher m = validPattern.matcher(currentLine.trim());
				
				if(m.matches()) {
					int load = Integer.parseInt(currentLine.trim());
					arr[valid] = load;
					sum += load;
					valid++;
					}
				}
				if(valid == 0) {
					fatalMsg = "No valid loads";
				} else {
					avg = sum / (valid);
					for(int i = 0; i < valid; i ++) {
					total += Math.abs(arr[i] - avg);
				}
				
			}
		}
			
		} catch(IOException e) {
			fatalMsg = "File Error";
		}	
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
			if(fatalMsg != null) {
				writer.write(fatalMsg);
			} else {
				writer.write(String.valueOf(total));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}