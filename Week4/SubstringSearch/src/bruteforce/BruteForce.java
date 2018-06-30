package bruteforce;

public class BruteForce {
	public static int searchWithBackup(String text, String s)  {
		final int textLen = text.length();
		final int sLen = s.length();
		
		if (textLen >= sLen) {
			for (int i = 0; i < textLen-sLen+1; ++i) {
				int j;
				for (j = 0; j < sLen; ++j) {
					if (text.charAt(i+j) != s.charAt(j)) {
						break;
					}
				}
				
				if (j == sLen) {
					return i;
				}		
			}
		}
		return textLen;
	}
	
	public static int searchWithoutBackup(String text, String s) {
		final int N = text.length();
		final int M = s.length();
		int i = 0;
		int j = 0;
		for (i = 0, j = 0; j < M && i < N; ++i) {	
			if (text.charAt(i) != s.charAt(j)) {
				i -= j;
				j = 0;
			} else {
				++j;
			}
		}
		
		if (j == M) {
			return i-M;
		}
		
		return N;
	}
}
