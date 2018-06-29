import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BruteForceTest {

	@Test
	void test1() {
		final String text = "ABACADABRAC";
		final String s = "ABRA";
		assertEquals(BruteForce.searchWithBackup(text, s), 6);
	}
	
	@Test
	void test2() {
		final String text = "ABACADABRAC";
		final String s = "ABRAD";
		assertEquals(BruteForce.searchWithBackup(text, s), 11);
	}

	@Test
	void test3() {
		final String text = "ABACADABRAC";
		final String s = "ABRA";
		assertEquals(BruteForce.searchWithBackup(text, s), 6);
	}
	
	@Test
	void test4() {
		final String text = "ABACADABRAC";
		final String s = "ABRA";
		assertEquals(BruteForce.searchWithoutBackup(text, s), 6);
	}
	
	@Test
	void test5() {
		final String text = "ABACADABRAC";
		final String s = "ABRAD";
		assertEquals(BruteForce.searchWithoutBackup(text, s), 11);
	}

	@Test
	void test6() {
		final String text = "ABACADABRAC";
		final String s = "ABRA";
		assertEquals(BruteForce.searchWithoutBackup(text, s), 6);
	}

}
