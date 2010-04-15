import java.util.Scanner;


public class Tokenizer {
	public static void main(String args[]) {
		String str = "begin a := a + b; end";
		str = str.replaceAll(";", " ;");
		Scanner scan = new Scanner(str).useDelimiter("\\s");
		while(scan.hasNext()) {
			System.out.println(scan.next());
		}
	}
}
