package BoyerMoore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoyerMooreTest {

	@Test
	void test1() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAA";
		
		assertEquals(BoyerMoore.search(text, pat), 14);
	}

	@Test
	void test2() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAC";
		
		assertEquals(BoyerMoore.search(text, pat), 6);
	}

	@Test
	void test3() {

		final String text = "AABACAABABACAA";
		final String pat = "AAB";
		
		assertEquals(BoyerMoore.search(text, pat), 0);
	}

	@Test
	void test4() {

		final String text = "AABACAABABACAA";
		final String pat = text;
		
		assertEquals(BoyerMoore.search(text, pat), 0);
	}

	@Test
	void test5() {

		final String text = "AABACAABABACAA";
		final String pat = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		
		assertEquals(BoyerMoore.search(text, pat), 14);
	}

}
