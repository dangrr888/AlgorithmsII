package rabinkarp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RabinKarpTest {

	@Test
	void testSearch1() {

		final String text = "3249753424234837";
		final String pattern = "53424";
		
		assertEquals(RabinKarp.search(text, pattern), 5);
	}

	@Test
	void testLongestPallindrome1() {

		final String text = "aba";	
		assertEquals(RabinKarp.longestPallindrome(text), 3);
	}

	@Test
	void testLongestPallindrome2() {

		final String text = "abc";	
		assertEquals(RabinKarp.longestPallindrome(text), 1);
	}

	@Test
	void testLongestPallindrome3() {

		final String text = "abba";	
		assertEquals(RabinKarp.longestPallindrome(text), 4);
	}

	@Test
	void testLongestPallindrome4() {

		final String text = "abcba";	
		assertEquals(RabinKarp.longestPallindrome(text), 5);
	}
}
