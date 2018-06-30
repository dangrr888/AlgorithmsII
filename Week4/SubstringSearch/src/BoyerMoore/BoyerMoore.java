package BoyerMoore;

public class BoyerMoore {

	private static final int R = 3;
	private static final char offset = 'A';
	
	private static int charAt(String s, int i) {
		return s.charAt(i) - BoyerMoore.offset;
	}
	
	private static int[] createSkipArray(String pat) {
		final int[] skipArray = new int[R];
		for (int i = 0; i < BoyerMoore.R; ++i) {
			skipArray[i] = -1;
		}
		for (int i = 0; i < pat.length(); ++i) {
			skipArray[BoyerMoore.charAt(pat, i)] = i;
		}
		return skipArray;
	}
	
	public static int search(String text, String pat) {
		final int[] right = BoyerMoore.createSkipArray(pat);
		final int N = text.length();
		final int M = pat.length();
		
		int skip = 0;
		for (int i = 0; i <= N-M; i+=skip) {
			skip = 0;
			for (int j = M-1; j >= 0; --j) {
				if (BoyerMoore.charAt(text, i+j) != BoyerMoore.charAt(pat, j)) {
					skip = Math.max(1, j - right[BoyerMoore.charAt(text, i+j)]);
					break;
				}
			}
			
			if (skip == 0) {
				// We got to the start of the pattern without mismatches.
				return i;
			}
		}
		
		// No match found.
		return N;
	}
}
