package cyclicrotationsofastring;
class KnuthMorrisPratt
{
	final int R;
	final char offset;
	
	private int charAt(String s, int i) {
		return s.charAt(i) - this.offset;
	}
	
	private int[][] constructDFA(String pat) {
		final int M = pat.length();
		final int[][] dfa = new int[this.R][M];
		for (int i = 0; i < dfa.length; ++i) {
			for (int j = 0; j < dfa[i].length; ++j) {
				dfa[i][j] = 0;
			}
		}
		
		dfa[this.charAt(pat, 0)][0] = 1;
		for (int X = 0, j = 1; j < M; ++j) {
			for (int c = 0; c < this.R; ++c) {
				dfa[c][j] = dfa[c][X]; // mismatch transition.
			}
			dfa[this.charAt(pat, j)][j] = j+1; // match transition.
			X = dfa[this.charAt(pat, j)][X]; // Update X
		}
		
		return dfa;
	}
	
	public int search(String text, String pat) {
		final int[][] dfa = this.constructDFA(pat);
		final int N = text.length();
		final int M = pat.length();
		
		int curState = 0;
		int i = 0;
		while (i < N && curState < M) {
			curState = dfa[this.charAt(text, i++)][curState];
		}
		
		if (curState == M) {
			return i-M;
		}
		
		return N;
	}
	
	public KnuthMorrisPratt(int R, char offset) {
		this.R = R;
		this.offset = offset;
	}
}

public class CyclicRotationOfAString {
	public static boolean isCyclicRotation(String s, String t, int R, char offset) {	
		final String text = t + t;
		final String pat = s;
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(R, offset);
		
		final int result = kmp.search(text, pat);
		return result != text.length();		
	}
}
