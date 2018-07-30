import java.util.Scanner;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

	private static boolean DEBUG = false;

	private static short R = 256;
	private static byte W = 8;
	
	private static class Node {
		Node(char c, Node next) {
			this.c = c;
			this.next = next;
		}
		
		char c;
		Node next;
	}
		
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    
    	Node head = null;
    	Node tail = null;
	    for (short i = 0; i < MoveToFront.R; ++i) {
	    	final Node x = new Node((char)i, null);
	    	if (head == null) {
	    		head = tail = x;
	    	} else {
	    		tail.next = x;
	    		tail = tail.next;
	    	}
	    }
	    
	    while (!BinaryStdIn.isEmpty()) {
	    	
	    	final char c = BinaryStdIn.readChar();
	    	if (head.c == c) {
	    		
	    		if (DEBUG) {
	    			System.out.print(Integer.toHexString(0) + " ");
	    		} else {
	    			BinaryStdOut.write(0, MoveToFront.W);
	    		}
	    	}
	    	
	    	short count = 1;
	    	Node x = head;
	    	while (x.next != null) {
	    		if (x.next.c == c) {
	    			
	    			if (DEBUG) {
	    				System.out.print(Integer.toHexString(count) + " ");
	    			} else {
	    				BinaryStdOut.write(count, MoveToFront.W);
	    			}
	    			
	    			final Node tmp = x.next;
	    			x.next = tmp.next;
	    			tmp.next = head;
	    			head = tmp;
	    			break;
	    		} else {	    		
		    		x = x.next;
		    		++count;
	    		}
	    	}	    	
	    }
	    
	    BinaryStdOut.close();
    }
    
    private static int convertHexToShort(String s) {
    	int ret = 0;
    	for (int i = 0; i < s.length(); ++i) {
    		ret *= 16;
    		ret += s.charAt(i) - '0';
    	}
    	
    	return ret;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    	Node head = null;
    	Node tail = null;
	    for (short i = 0; i < MoveToFront.R; ++i) {
	    	final Node x = new Node((char)i, null);
	    	if (head == null) {
	    		head = tail = x;
	    	} else {
	    		tail.next = x;
	    		tail = tail.next;
	    	}
	    }

	    Scanner sc = null;
	    if (DEBUG) {
	    	sc = new Scanner(System.in);
	    }
	    
	    while (DEBUG ? sc.hasNext() : !BinaryStdIn.isEmpty()) {
	    	
	    	int i;
	    	if (DEBUG) {
	    		i = BinaryStdIn.readInt(MoveToFront.W);
	    	} else {
	    		i = MoveToFront.convertHexToShort(sc.next());
	    	}
	    	
	    	if (i == 0) {
	    		BinaryStdOut.write(head.c, MoveToFront.W);
	    	} else {
	    		
	    		short pos = 1;
	    		Node x = head;
	    		while (x.next != null) {
	    			if (pos == i) {
	    				BinaryStdOut.write(x.next.c, MoveToFront.W);	
	    				final Node tmp = x.next;
	    				x.next = tmp.next;
	    				tmp.next = head;
	    				head = tmp;
	    				break;
	    			} else {
		    			x = x.next;
		    			++pos;
	    			}
	    		}
	    	}
	    }
	    
	    if (DEBUG) {
	    	sc.close();
	    }
	    
	    BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	final String usage = "Usage: MoveToFront <{+|-}>\n";
    	if (args.length != 1) {
    		BinaryStdOut.write(usage);
    		throw new IllegalArgumentException("Must only provide one argument, " + args.length + " provided.");
    	} else if (args[0].isEmpty()) {
    		BinaryStdOut.write(usage);
    		throw new IllegalArgumentException("Must provide an operation.");    		
    	} else {
    		final char op = args[0].charAt(0);
    		if (op == '-') {
    			MoveToFront.encode();
    		} else if (op == '+') {
    			MoveToFront.decode();
    		} else {
        		BinaryStdOut.write(usage);
        		throw new IllegalArgumentException("Unrecognized operation " + op);    		
    		}
    	}	
    }
}





