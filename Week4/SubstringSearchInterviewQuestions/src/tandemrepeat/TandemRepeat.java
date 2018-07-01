package tandemrepeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

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
	
	public int search(String text, String pat, int startIndex) {
		final int[][] dfa = this.constructDFA(pat);
		final int N = text.length();
		final int M = pat.length();
		
		int curState = 0;
		int i = startIndex;
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

public class TandemRepeat {
	public static int maximumLengthTandemRepeat(String s, String b) {
		final int N = s.length();
		final int M = b.length();
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(256, (char)0);
		
		final HashSet<Integer> indices = new HashSet<Integer>();
		for (int i = 0; i < N-M; ++i) {
			final int index = kmp.search(s, b, i);
			if (index < N) {
				indices.add(index);
			}
		}

		boolean[] found = new boolean[N];
		int[] count = new int[N];
		for (int i = 0; i < N-M; ++i) {
			found[i] = false;
			count[i] = 0;
		}
		
		for (int index : indices) {
			found[index] = true;
			count[index] = 1;
		}

		int maxCount = 0;
		for (int index : indices) {
			if (found[index+M]) {
				++count[index+M];
				maxCount = Math.max(count[index+M], maxCount);
				}
		}
		
		return maxCount;
	}
}
