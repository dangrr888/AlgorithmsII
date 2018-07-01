package tandemrepeat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TandemRepeatTest {

	@Test
	void test1() {
		final String s = "abcabcababcaba";
		final String b = "abcab";
		
		assertEquals(TandemRepeat.maximumLengthTandemRepeat(s, b), 2);
	}
}
