import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GREP {
	public static void main(String[] args) throws FileNotFoundException {
		
		final String regex = ".*" + args[0] + ".*";		
		
		final File file = new File(args[1]);
		if (!file.exists()) {
			throw new FileNotFoundException("input file doesn't exist.");
		}
		
		final NFA nfa = new NFA(regex);
		
		final Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			final String line = sc.nextLine();
			if (nfa.recognizes(line)) {
				System.out.println(line);
			}
		}
		sc.close();
	}
}
