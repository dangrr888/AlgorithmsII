import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringSTTest {

	@Test
	void constructEmptyTrie() {
		final StringST<Integer> st = new StringST<Integer>();
		assertEquals(st.get("foo"), null);
	}

	@Test
	void constructTrie() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);		
	}

	@Test
	void constructTrieAndCheckContains() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);
		
		assertTrue(st.contains("foo"));
		assertTrue(st.contains("bar"));
		assertTrue(st.contains("baz"));
		assertTrue(st.contains("qux"));
	}

	@Test
	void constructTrieAndCheckGet() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);
		
		assertEquals(st.get("foo").intValue(), 0);		
		assertEquals(st.get("bar").intValue(), 1);
		assertEquals(st.get("baz").intValue(), 2);
		assertEquals(st.get("qux").intValue(), 3);
	}
	
	@Test
	void constructTrieAndDeleteKeys() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);
		
		assertEquals(st.get("foo").intValue(), 0);		
		assertEquals(st.get("bar").intValue(), 1);
		assertEquals(st.get("baz").intValue(), 2);
		assertEquals(st.get("qux").intValue(), 3);

		st.delete("foo");
		assertFalse(st.contains("foo"));		
		assertEquals(st.get("bar").intValue(), 1);
		assertEquals(st.get("baz").intValue(), 2);
		assertEquals(st.get("qux").intValue(), 3);	
		
		st.delete("bar");
		assertFalse(st.contains("bar"));		
		assertEquals(st.get("baz").intValue(), 2);
		assertEquals(st.get("qux").intValue(), 3);	

		st.delete("baz");
		assertFalse(st.contains("baz"));
		assertEquals(st.get("qux").intValue(), 3);	

		st.delete("qux");
		assertFalse(st.contains("aux"));
	}
}