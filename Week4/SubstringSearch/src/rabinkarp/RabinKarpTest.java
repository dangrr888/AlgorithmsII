package rabinkarp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RabinKarpTest {

	@Test
	void test1() {

		final String text = "3249753424234837";
		final String pattern = "53424";
		
		assertEquals(RabinKarp.search(text, pattern), 5);
	}

}
