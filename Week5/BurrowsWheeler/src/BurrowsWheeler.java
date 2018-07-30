import java.util.Arrays;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	
	private static final int R = 256;
	
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
    	final String s = BinaryStdIn.readString();
    	final CircularSuffixArray csa = new CircularSuffixArray(s);
    	int first = -1;
    	for (int i = 0; i < csa.length(); ++i) {
    		if (csa.index(i) == 0) {
    			first = i;
    		}
    	}
    	BinaryStdOut.write(first);
    	for (int i = 0; i < s.length(); ++i) {
    		final int index = (s.length()-1+csa.index(i)) % s.length();
    		BinaryStdOut.write(s.charAt(index), 8);
    	}
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    	
    	// Read input.
    	final int first = BinaryStdIn.readInt(); // first
    	final String s = BinaryStdIn.readString(); // t[lastCol]
    	
    	//final int first = 3;
    	//final String s = "ARD!RCAAAABB";
    	
    	// Create t[lastCol].
    	final char[] lastCol = s.toCharArray();
    	
    	// Create t[firstCol].
    	final char[] firstCol = s.toCharArray();
    	Arrays.sort(firstCol);
    	    	    	   	
    	// Count
    	final int[] count = new int[BurrowsWheeler.R];
    	for (char c : firstCol) {
    		++count[c];
    	}
    	
    	int[][] lookup = new int[BurrowsWheeler.R][];
    	for (int i = 0; i < s.length(); ++i) {
    		final char c = lastCol[i];
    		if (lookup[c] == null) {
    			lookup[c] = new int[count[c]];
    		}
    		lookup[c][lookup[c].length-count[c]] = i;
    		--count[c];
    	}
    	
       	// Create next[].
    	final int[] next = new int[s.length()];
 
    	for (int i = 0; i < s.length(); ++i) {
    		for (int j = 0; j < lookup[firstCol[i]].length; ++j) {
        		next[i+j] = lookup[firstCol[i]][j];    			
    		}
    		i += lookup[firstCol[i]].length-1;
    	}

    	// Return the original string.
    	for(int i = first, j = 0; j < s.length(); i = next[i], ++j) {
    		BinaryStdOut.write(firstCol[i], 8);
    	}
    	
    	// Tidy up.
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	final String usage = "Usage: BurrowsWheeler <{+|-}>\n";
    	if (args.length != 1) {
    		BinaryStdOut.write(usage);
    		throw new IllegalArgumentException("Must only provide one argument, " + args.length + " provided.");
    	} else if (args[0].isEmpty()) {
    		BinaryStdOut.write(usage);
    		throw new IllegalArgumentException("Must provide an operation.");    		
    	} else {
    		final char op = args[0].charAt(0);
    		if (op == '-') {
    			BurrowsWheeler.transform();
    		} else if (op == '+') {
    			BurrowsWheeler.inverseTransform();
    		} else {
        		BinaryStdOut.write(usage);
        		throw new IllegalArgumentException("Unrecognized operation " + op);    		
    		}
    	}	
    }
}