import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class KnuthMorrisPrattTest {

	@Test
	void test1() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAA";
		
		assertEquals(KnuthMorrisPratt.search(text, pat), 14);
	}

	@Test
	void test2() {

		final String text = "AABACAABABACAA";
		final String pat = "ABABAC";
		
		assertEquals(KnuthMorrisPratt.search(text, pat), 6);
	}

	@Test
	void test3() {

		final String text = "AABACAABABACAA";
		final String pat = "AAB";
		
		assertEquals(KnuthMorrisPratt.search(text, pat), 0);
	}

	@Test
	void test4() {

		final String text = "AABACAABABACAA";
		final String pat = text;
		
		assertEquals(KnuthMorrisPratt.search(text, pat), 14);
	}

	@Test
	void test5() {

		final String text = "AABACAABABACAA";
		final String pat = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		
		assertEquals(KnuthMorrisPratt.search(text, pat), -32);
	}
}
