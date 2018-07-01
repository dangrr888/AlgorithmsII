package cyclicrotationsofastring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CyclicRotationOfAStringTest {

	@Test
	void test1() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAA";
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(3, 'A');
		
		assertEquals(kmp.search(text, pat), 14);
	}

	@Test
	void test2() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAC";
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(3, 'A');

		assertEquals(kmp.search(text, pat), 6);
	}

	@Test
	void test3() {

		final String text = "AABACAABABACAA";
		final String pat = "AAB";
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(3, 'A');

		assertEquals(kmp.search(text, pat), 0);
	}

	@Test
	void test4() {

		final String text = "AABACAABABACAA";
		final String pat = text;
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(3, 'A');

		assertEquals(kmp.search(text, pat), 0);
	}

	@Test
	void test5() {

		final String text = "AABACAABABACAA";
		final String pat = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		final KnuthMorrisPratt kmp = new KnuthMorrisPratt(3, 'A');

		assertEquals(kmp.search(text, pat), 14);
	}
	
	@Test
	void test6() {
		final String s = "winterbreak";
		final String t = "breakwinter";

		assertTrue(CyclicRotationOfAString.isCyclicRotation(s, t, 256, (char)0));		
	}
}
