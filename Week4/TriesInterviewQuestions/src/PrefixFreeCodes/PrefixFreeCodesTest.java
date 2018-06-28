package PrefixFreeCodes;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrefixFreeCodesTest {

	@Test
	void test1() {
		final String[] codes = {"1010101010101", "0000001", "01001001", "1001", "0100"};
		final PrefixFreeCodes pfc = new PrefixFreeCodes(codes);
		assertFalse(pfc.havePrefixFreeCodes());
	}

	@Test
	void test2() {
		final String[] codes = {"1010101010101", "0000001", "01001001", "1001", "01000"};
		final PrefixFreeCodes pfc = new PrefixFreeCodes(codes);
		assertTrue(pfc.havePrefixFreeCodes());
	}

	@Test
	void test3() {
		final String[] codes = {"10", "0"};
		final PrefixFreeCodes pfc = new PrefixFreeCodes(codes);
		assertTrue(pfc.havePrefixFreeCodes());
	}
}
