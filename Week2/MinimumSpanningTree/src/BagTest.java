import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BagTest {
	
	@Test
	void testSizeEmptyBag() {
		Bag<Integer> b = new Bag<Integer>();
		assertEquals(b.size(), 0);
	}
	
	@Test
	void testSizeNonEmptyBag1() {
		Bag<Integer> b = new Bag<Integer>();
		b.add(1);
		assertEquals(b.size(), 1);
	}

	@Test
	void testSizeNonEmptyBag2() {
		Bag<Integer> b = new Bag<Integer>();
		b.add(1);
		b.add(2);
		assertEquals(b.size(), 2);
	}

	@Test
	void testSizeNonEmptyBagMany() {
		Bag<Integer> b = new Bag<Integer>();
		final int N = 1000;
		for (int i = 0; i < N; ++i) {
			b.add(1);			
		}

		assertEquals(b.size(), N);
	}

	@Test
	void testIterator() {
		
		Bag<Integer> b = new Bag<Integer>();		
		b.add(8);
		b.add(9);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		b.add(0);
		
		StringBuilder sb = new StringBuilder();
		for (Integer i : b) {
			sb.append(i);
		}
		assertEquals(sb.toString(), "000000000098");
	}
}
