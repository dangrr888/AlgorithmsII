import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

class BoggleSolverTest {

    private int generateScore(String dictFilename, String boardFilename) {
    	
    	final String prefix = "/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week4/Boggle/test/boggle/";
    	
    	// Initialize solver.
        final In in = new In(prefix + dictFilename);
        final String[] dictionary = in.readAllStrings();
        final BoggleSolver solver = new BoggleSolver(dictionary);
       
        // Initialize board.
        final BoggleBoard board = new BoggleBoard(prefix + boardFilename);
        
        // Calculate score.
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
        	StdOut.println(word);
            score += solver.scoreOf(word);    
        }
        
        return score;
    }

	@Test
	void test1() {
		StdOut.println("\n\nSTART TEST1");
		assertEquals(this.generateScore("dictionary-algs4.txt", "simple.txt"), 1);
		StdOut.println("END TEST1");
	}

	@Test
	void test2() {
		StdOut.println("\n\nSTART TEST2");		
		assertEquals(this.generateScore("dictionary-algs4.txt", "board4x4.txt"), 33);
		StdOut.println("END TEST2");
	}
    
	@Test
	void test3() {
		StdOut.println("\n\nSTART TEST3");				
		assertEquals(this.generateScore("dictionary-algs4.txt", "board-q.txt"), 84);
		StdOut.println("END TEST3");		
	}
	
	@Test
	void test4() {
		StdOut.println("\n\nSTART TEST4");		
		assertEquals(this.generateScore("dictionary-yawl.txt", "board-qwerty.txt"), 27);
		StdOut.println("END TEST4");
	}
	
}
