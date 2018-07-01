package kmp;
public class KnuthMorrisPratt {

	static final int R = 3;
	static final char offset = 'A';

	private static int charAt(String s, int i) {
		return s.charAt(i) - KnuthMorrisPratt.offset;
	}
	
	private static int[][] constrctDFA(String pat) {
		final int M = pat.length();
		int[][] dfa = new int[KnuthMorrisPratt.R][M];
		for (int i = 0; i < dfa.length; ++i) {
			for (int j = 0; j < dfa[i].length; ++j) {
				dfa[i][j] = 0;
			}
		}
		
		dfa[pat.charAt(0)-offset][0] = 1;
		dfa[KnuthMorrisPratt.charAt(pat, 0)][0] = 1;
		for (int X = 0, j = 1; j < M; ++j) {
			for (int c = 0; c < KnuthMorrisPratt.R; ++c) {
				dfa[c][j] = dfa[c][X]; // mismatch transition.
			}
			dfa[KnuthMorrisPratt.charAt(pat, j)][j] = j+1;
			X = dfa[KnuthMorrisPratt.charAt(pat, j)][X];
		}
		
		return dfa;
	}
	
	public static int search(String text, String pat) {
		final int[][] dfa = KnuthMorrisPratt.constrctDFA(pat);
		final int N = text.length();
		final int M = pat.length();
		
		int curState = 0;
		int i = 0;
		while (i < N && curState < M) {
			curState = dfa[KnuthMorrisPratt.charAt(text, i++)][curState];
		}
		if (curState == M) {
			return i-M;
		}

		return N;
	}	
}
