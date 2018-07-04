package rabinkarp;

public class RabinKarp {

	static final int R = 256;
	static final int mod = 101;
	
	private static int charAt(String s, int index) {
		return s.charAt(index);
	}
		
	public static int search(String text, String pattern) {
		final int N = text.length();
		final int M = pattern.length();
		
		if (M > N) {
			return N;
		}
		
		int patternHash = 0;
		int curTextHash = 0;
		int end = 0;
		for (end = 0; end <  M; ++end) {
			patternHash = ((RabinKarp.R * (patternHash % RabinKarp.mod)) + RabinKarp.charAt(pattern, end)) % RabinKarp.mod;
			curTextHash = ((RabinKarp.R * (curTextHash % RabinKarp.mod)) + RabinKarp.charAt(text, end)) % RabinKarp.mod;
		}
		
		// calculate c incrementally to avoid overflow
		int c = 1;
		for (int i = 0; i < M-1; ++i) {
			c = (c % RabinKarp.mod) * RabinKarp.R;
		}
		
		int start;
		for (start = 0; end < N && curTextHash != patternHash; ++start, ++end) {
			curTextHash = ((curTextHash + RabinKarp.mod - (((int)text.charAt(start) * c) % RabinKarp.mod)) * RabinKarp.R + text.charAt(end)) % RabinKarp.mod;
		}
		
		return patternHash == curTextHash ? start : N;
	}
}
 