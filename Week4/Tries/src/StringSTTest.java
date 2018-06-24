import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Stack;

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
	
	@Test
	void constructTrieAndGetAllKeys() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);

		final ArrayList<String> al = new ArrayList<String>();
		for (String key : st.keys()) {
			al.add(key);
		}
		
		assertEquals(al.size(), 4);
		assertEquals(al.get(0), "bar");
		assertEquals(al.get(1), "baz");
		assertEquals(al.get(2), "foo");
		assertEquals(al.get(3), "qux");
	}
	
	@Test
	void constructTrieAndGetAllKeysWithPrefix() {
		final StringST<Integer> st = new StringST<Integer>();
		st.put("she", 0);
		st.put("sells", 1);
		st.put("shells", 3);
		st.put("by", 4);
		st.put("the", 5);
		st.put("sea", 6);
		st.put("shore", 7);
				
		// Prefix
		ArrayList<String> al = new ArrayList<String>();

		for (String key : st.keysWithPrefix("sh")) {
			al.add(key);
		}
		assertEquals(al.size(), 3);
		assertEquals(al.get(0), "she");
		assertEquals(al.get(1), "shells");
		assertEquals(al.get(2), "shore");
	}

}
