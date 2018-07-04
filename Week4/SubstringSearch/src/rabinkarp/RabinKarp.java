package rabinkarp;

public class RabinKarp {

	static final int R = 10;
	static final char offset = '0';
	static final int mod = 997;
	
	private static int charAt(String s, int index) {
		return s.charAt(index) - RabinKarp.offset;
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
			patternHash = ((RabinKarp.R * patternHash) + RabinKarp.charAt(pattern, end)) % RabinKarp.mod;
			curTextHash = ((RabinKarp.R * curTextHash) + RabinKarp.charAt(text, end)) % RabinKarp.mod;
		}
		
		final int c = (int)Math.pow(RabinKarp.R, M-1);
		int start;
		for (start = 0; end < N-1 && curTextHash != patternHash; ++start, ++end) {
			final int p1 = (RabinKarp.R * curTextHash);
			final int p2 = (int)(RabinKarp.charAt(text, start) * c);
			final int p3 = RabinKarp.charAt(text, end);
			curTextHash = (p1 - p2 + p3) % RabinKarp.mod;
		}
		
		return patternHash == curTextHash ? start : N;
	}
}
