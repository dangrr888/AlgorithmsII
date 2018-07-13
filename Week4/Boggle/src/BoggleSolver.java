import java.util.ArrayList;
import java.util.HashSet;

public class BoggleSolver {
	
    // Radix (capital letters [A-Z] + 1 for Qu represented as 'Z'+1).
    static private int R = 27;
	
    // Character offset for calculating array indices.
    static private char offset = 'A';
	
    // Representation of Qu to distinguish
    // it from Q since not all dictionary words
    // have Q next to a u.
    static private char qU = 'Z'+1;
	
    // Current board's number of rows.
    private int N;

    // Current board's number of cols.
    private int M;
	
    // Root of graph representing dictionary;
    private Node root = new Node();
	
    // Class representing a dictionary node.
    private class Node {
	final Node[] next = new Node[BoggleSolver.R];
	int score = 0;
    }
	
    // Helper function to return what the board actually means.
    private char getBoardLetter(BoggleBoard board, int row, int col) {
	final char c = board.getLetter(row, col);
	return c == 'Q' ? BoggleSolver.qU : c;
    }
	
    // Helper method to set the score of a Node.
    private Node put(Node x, String s, int score, int d) {
	if (d == s.length()) {
	    x.score = score;
	} else {
	    final int c = s.charAt(d)-BoggleSolver.offset;
	    if (x.next[c] == null) {
		x.next[c] = new Node();
	    }
	    x.next[c] = this.put(x.next[c], s, score, d+1);
	}
		
	return x;
    }
	
    // Helper wrapper method to set the score of a Node.
    private void put(String s, int score) {
	this.root = this.put(this.root, s, score, 0);
    }
	
    // Helper method to retrieve the score of a Node.
    private Node get(Node x, String s, int d) {
		
	if (x == null) {
	    return null;
	}
	
	if (s.length() == d) {
	    return x;
	}
		
	final int c = s.charAt(d) - BoggleSolver.offset;
	return this.get(x.next[c], s, d+1);
    }
	
    // Helper method to calculate the score associated with a
    // (unmodified) word.
    private int calculateScore(String word) {
		
	final int length = word.length();		
	int score;
	if (length < 3) {
	    score = 0;
	} else if (length < 5) {
	    score = 1;
	} else if (length == 5) {
	    score = 2;
	} else if (length == 6) {
	    score = 3;
	} else if (length == 7) {
	    score = 5;
	} else {
	    score = 11;
	}
		
	return score;
    }
	    
    // A helper class to represent a location on a
    // BoggleBoard.
    private class Coordinate {
    	
    	Coordinate(int r, int c) {
	    this.row = r;
	    this.col = c;
    	}
    	
    	int row;
    	int col;
    }
    
    // Helper method to check if a given
    // set of board coordinates are valid.
    private boolean isValid(int row, int col) {
    	return row >= 0 && row < this.N && col >= 0 && col < this.M;
    }
    
    // Helper method to retrieve the adjacent coordinates
    // of a specified location.
    private Iterable<Coordinate> getNeighbours(int row, int col) {
    	
    	final ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
    	
    	for (int nrow = row-1; nrow <= row+1; ++nrow) {
	    for (int ncol = col-1; ncol <= col+1; ++ncol) {
		if (this.isValid(nrow, ncol)) {
		    if (nrow != row || ncol != col) {
			neighbours.add(new Coordinate(nrow,ncol));
		    }
		}
	    }
    	}
    	
    	return neighbours;
    }
    
    // Helper method to convert a ArrayList of Character's
    // representing a word into the String representing
    // that word.
    private String printWord(ArrayList<Character> word) {
    	
    	final StringBuilder sb = new StringBuilder();
    	
    	for (Character c : word) {
	    if (c == BoggleSolver.qU) {
		sb.append("QU");
	    } else {
		sb.append(c);
	    }
    	}
    	
    	return sb.toString();
    }
    
    private void findWords( BoggleBoard board
			    , HashSet<String> validWords
			    , ArrayList<Character> curWord
			    , boolean[][] marked
			    , int row
			    , int col
			    , Node x
			    ) {
    	
    	if (!this.isValid(row, col)) {
	    throw new ArrayIndexOutOfBoundsException("Invalid coordinates: [" + row + ", " + col + "]");
    	}

    	if (x == null) {
	    // Terminate subsequent DFS using curWord
	    // if that word fragment is not found in the
	    // dictionary.
	    return;
    	}
    	    	
    	// Add current character to current word.
    	final char c = this.getBoardLetter(board, row, col);
    	curWord.add(c);
    	
    	if (x.score > 0) {
	    // Add current dictionary word to the list
	    // of valid words.
	    validWords.add(this.printWord(curWord));
    	}
    	
    	for (Coordinate coord : this.getNeighbours(row, col)) {
    		
	    final int nrow = coord.row;
	    final int ncol = coord.col;
    		
	    if (!marked[nrow][ncol]) {
    			
		// Mark current neighbour.
		marked[nrow][ncol] = true; 
    			
		// Recurse.
		this.findWords( board
				, validWords
				, curWord
				, marked
				, nrow
				, ncol
				, x.next[this.getBoardLetter(board, nrow, ncol)-BoggleSolver.offset]
				);
    			
		// Unmark the current neighbour so that
		// we may access it via another route.
		marked[nrow][ncol] = false;
	    }
    	}
    	
	// Pop letter of x from current word (will be the last one).
	curWord.remove(curWord.size()-1);
    }
    
    // Helper method to remove change word to
    // uppercase and replace the Qu's from a
    // String with BoggleSolver.qU's.
    private String modifyWord(String word) {
    	
    	final String s = word.toUpperCase();
    	
	final int length = s.length();
	final StringBuilder sb = new StringBuilder();
	for (int i = 0; i < length; ++i) {
	    final char c = s.charAt(i);
	    if (i < length-1 && c == 'Q' && s.charAt(i+1) == 'U') {
		// Skip the subsequent 'U' and add BoggleSolver.qU
		sb.append(BoggleSolver.qU);
		++i;
	    } else {
		sb.append(c);
	    }
	}
	return sb.toString();
    }
    
    /* Initializes the data structure using
     * the given array of strings as the dictionary.
     * (You can assume each word in the dictionary
     * contains only the uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {

    	// Add the supplied words to the dictionary.
    	for (String word : dictionary) {
    		
	    final int length = word.length();
	    if (length > 2) {    			
		final int score = this.calculateScore(word);
    			
		// Replace "Qu" with 'BoogleSolver.qU' and add to dictionary.
		this.put(this.modifyWord(word), score);
	    }
    	}
    }    

    /* Returns the set of all valid words in
     * the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {

    	// Initialize board dimensions. These persist until
    	// this function is called again.
    	this.N = board.rows();
    	this.M = board.cols();
    	
    	if (this.root == null) {
	    return null;
    	}
    	    	
    	final HashSet<String> validWords = new HashSet<String>();
    	
    	// Conduct DFS', starting from each position in the board.
    	for (int row = 0; row < this.N; ++row) {
	    for (int col = 0; col < this.M; ++col) {
    	
		// Re-initialize marked array.
    	    	final boolean marked[][] = new boolean[this.N][this.M];
    			
		// Re-initialize current word.
    	    	final ArrayList<Character> curWord = new ArrayList<Character>();
    	    	
    	    	marked[row][col] = true;
    	    	this.findWords( board
				, validWords
				, curWord
				, marked
				, row
				, col
				, this.root.next[this.getBoardLetter(board, row, col)-BoggleSolver.offset]
				);
	    }
    	}
    	    	
    	return validWords;
    }

    /* Returns the score of the given word if it is
     * in the dictionary, zero otherwise.
     * (You can assume the word contains only the
     * uppercase letters A through Z.)
     */
    public int scoreOf(String word) {

	final Node x = this.get(this.root, this.modifyWord(word), 0);
		
	int score = 0;
		
	if (x != null) {
	    score = x.score;
	}
		
	return score;
    }
}
