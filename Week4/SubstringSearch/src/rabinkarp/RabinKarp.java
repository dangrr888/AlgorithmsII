package rabinkarp;

public class RabinKarp {

	static final int R = 256;
	static final int mod = 307;
	
	public static int search(String text, String pattern) {
		
		// Common sense check on arguments.
		final int N = text.length();
		final int M = pattern.length();
		
		if (M > N) {
			return N;
		}
		
		// Construct initial pattern and text hashes.
		
		int patternHash = 0;
		int curTextHash = 0;
		for (int i = 0; i < M; ++i) {
			
			// Pattern Hash
			patternHash %= RabinKarp.mod;
			patternHash *= RabinKarp.R;
			patternHash %= RabinKarp.mod;			
			patternHash += pattern.charAt(i);
			patternHash %= RabinKarp.mod;

			// Text Hash
			curTextHash %= RabinKarp.mod;
			curTextHash *= RabinKarp.R;
			curTextHash %= RabinKarp.mod;
			curTextHash += text.charAt(i);
			curTextHash %= RabinKarp.mod;
		}
		
		// Calculate constant, c, incrementally to avoid overflow.
		int c = 1;
		for (int i = 0; i < M-1; ++i) {
			c *= RabinKarp.R;
			c %= RabinKarp.mod;
		}
		
		for (int i = 0; i < N-M; ++i) {
			
			if (curTextHash == patternHash) {
				return i;
			}

			// Roll the text hash by +1 position.
			curTextHash %= RabinKarp.mod;
			curTextHash += RabinKarp.mod;
			curTextHash -= ((text.charAt(i) * c) % RabinKarp.mod);
			curTextHash %= RabinKarp.mod;
			curTextHash *= RabinKarp.R;
			curTextHash %= RabinKarp.mod;
			curTextHash += text.charAt(i+M);
			curTextHash %= RabinKarp.mod;			
		}

		return N;
	}
	
	private static boolean hasPallindromeOfLength(String text, String reversedText, int M) {
		
		if (M < 2) {
			return true;
		}
		
		final int N = text.length();
		if (M > N) {
			return false;
		}
		
		final int L = M % 2 == 0 ? M/2 : (M-1)/2;		
		
		// Calculate initial hash
		int hash = 0;
		for (int i = 0; i < L; ++i) {
			
			// Hash
			hash %= RabinKarp.mod;
			hash *= RabinKarp.R;
			hash %= RabinKarp.mod;
			hash += text.charAt(i);
			hash %= RabinKarp.mod;
		}
		
		// Calculate constant, c, incrementally to avoid overflow.
		int c = 1;
		for (int i = 0; i < L-1; ++i) {
			c *= RabinKarp.R;
			c %= RabinKarp.mod;
		}
		
		// Calculate remaining hash's
		final int[] hashes = new int[N-M+1];
		hashes[0] = hash;
		for (int i = 0; i < N-M; ++i) {

			hash %= RabinKarp.mod;
			hash += RabinKarp.mod;
			hash -= ((text.charAt(i) * c) % RabinKarp.mod);
			hash %= RabinKarp.mod;
			hash *= RabinKarp.R;
			hash %= RabinKarp.mod;
			hash += text.charAt(i+L);
			hash %= RabinKarp.mod;
			hashes[i+1] = hash;
		}
		
		// Build initial RevHash
		int revHash = 0;
		for (int i = 0; i < L; ++i) {			
			revHash %= RabinKarp.mod;
			revHash *= RabinKarp.R;
			revHash %= RabinKarp.mod;
			revHash += reversedText.charAt(i);
			revHash %= RabinKarp.mod;			
		}
		
		// Check if initial revHash matches
		// corresponding hash.
		if (revHash == hashes[N-M-0]) {
			return true;
		} else {
			// Calculate remaining revHash's,
			// by rolling initial hash by i+1
			// positions, comparing with
			// corresponding hash each time.
			for (int i = 0; i < N-M; ++i) {
	
				revHash %= RabinKarp.mod;
				revHash += RabinKarp.mod;
				revHash -= ((reversedText.charAt(i) * c) % RabinKarp.mod);
				revHash %= RabinKarp.mod;
				revHash *= RabinKarp.R;
				revHash %= RabinKarp.mod;
				revHash += reversedText.charAt(i+L);
				revHash %= RabinKarp.mod;
				
				if (revHash == hashes[N-M-(i+1)]) {
					return true;
				}
			}
		}
		
		// No pallindrome of length M found.
		return false;
	}
	
	public static int longestPallindrome(String text) {
		
		/*
		 *	If there is a pallindrome of length
		 *	2L or 2L+1, then there must be a 
		 *	pallindrome of length 2M or 2M + 1
		 *	where L > M.
		 *	1. For a given L, conduct iterative
		 *	search for a pallindrome of length L.
		 *	2. Conduct subsequent binary search for
		 *	larger L until largest is found.
		 *	3. return L.
		 */
		
		final int N = text.length();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; ++i) {
			sb.append(text.charAt(N-1-i));
		}
		final String reversedText = sb.toString();
		
		// Set initial conditions.
		int maxL = 1;
		int lo = 1;
		int hi = N;
		
		// Conduct binary search.
		while (lo <= hi && lo >= 1 && hi <= N) {
			
			final int mid = (lo+hi)/2;
			
			// Check for pallindrome of length mid.		
			if (RabinKarp.hasPallindromeOfLength(text, reversedText, mid)) {
				// Found pallindrome of length, mid. Look for longer one.
				maxL = mid;
				lo = maxL+1;
			} else if (mid+1 <= N && RabinKarp.hasPallindromeOfLength(text, reversedText, mid+1)) {
				// Check that there is no pallindrome of length mid+1 if there is not one of
				// length mid, since it is possible (e.g., aba has no pallindrome of length 2,
				// but does have one of length 3).
				maxL = mid+1;
				lo = maxL+1;
			} else {
				// Did not find pallindrome of length mid. Look for shorter one.
				hi = mid-1;
			}			
		}

		// Return maximum length of pallindrome (1 indicates none were found).
		return maxL;
	}
}
 