import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EdgeTest {

	@Test
	void test() {

		Edge e = new Edge(1, 2, 3.7);
		assertEquals(e.either(), 1);
		assertEquals(e.other(), 2);
		assertEquals(e.weight(), 3.7);
		assertEquals(e.toString(), "1 - 2 : 3.7");
	}
}
