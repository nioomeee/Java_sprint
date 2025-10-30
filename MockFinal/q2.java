import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class q2 {
	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader("input2.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"))) {
			
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				Stack<Character> stack = new Stack<>();
				boolean isValid = true;

				for(char c: currentLine.toCharArray()) {
					if(c == '(' || c == '{' || c == '[') {
						stack.push(c);
					} else if (c == ')' ) {
						if(stack.isEmpty() || stack.pop() != '(') {
							isValid = false;
						}
					} else if(c == '}') {
						if(stack.isEmpty() || stack.pop() != '{') {
							isValid = false;
						}
					} else if(c == ']') {
						if(stack.isEmpty() || stack.pop() != '[') {
							isValid = false;
						}
					}	
				}
				if(!stack.isEmpty()) {
					isValid = false;
				}

				if(isValid) {
					writer.write("VALID");
				} else {
					writer.write("INVALID");
				}
				writer.newLine();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}