import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.Test;

class TernerySearchTreeTest {

	@Test
	void constructEmptyStringTST() {
		final StringTST<Integer> st = new StringTST<Integer>();
		assertEquals(st.get("foo"), null);
	}

	@Test
	void constructStringTST() {
		final StringTST<Integer> st = new StringTST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);		
	}

	@Test
	void constructStringTSTAndCheckContains() {
		final StringTST<Integer> st = new StringTST<Integer>();
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
	void constructStringTSTAndCheckGet() {
		final StringTST<Integer> st = new StringTST<Integer>();
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
	void constructStringTSTAndDeleteKeys() {
		final StringTST<Integer> st = new StringTST<Integer>();
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
	void constructStringTSTAndGetAllKeys() {
		final StringTST<Integer> st = new StringTST<Integer>();
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
	void constructStringTSTAndGetAllKeysWithPrefix() {
		final StringTST<Integer> st = new StringTST<Integer>();
		st.put("foo", 0);
		st.put("foobar", 1);
		st.put("fab", 2);
		st.put("fool", 3);
		st.put("find", 3);
		
		final ArrayList<String> al = new ArrayList<String>();
		for (String key : st.keysWithPrefix("foo")) {
			al.add(key);
		}
		
		assertEquals(al.size(), 3);
		assertEquals(al.get(0), "foo");
		assertEquals(al.get(1), "foobar");
		assertEquals(al.get(2), "fool");
	}

	@Test
	void constructStringTSTAndGetMatchingKeys() {
		final StringTST<Integer> st = new StringTST<Integer>();
		st.put("foo", 0);
		st.put("bar", 1);
		st.put("baz", 2);
		st.put("qux", 3);
		st.put("bap", 3);
		
		final ArrayList<String> al = new ArrayList<String>();
		for (String key : st.keysThatMatch("ba.")) {
			al.add(key);
		}
		
		assertEquals(al.size(), 3);
		assertEquals(al.get(0), "bar");
		assertEquals(al.get(1), "bap");
		assertEquals(al.get(2), "baz");
	}

	@Test
	void constructStringTSTAndGetMatchingKeys2() {
		final StringTST<Integer> st = new StringTST<Integer>();
		st.put("foop", 0);
		st.put("foobar", 1);
		st.put("fab", 2);
		st.put("fool", 3);
		st.put("foam", 3);
		
		final ArrayList<String> al = new ArrayList<String>();
		for (String key : st.keysThatMatch("fo..")) {
			al.add(key);
		}
		
		assertEquals(al.size(), 3);
		assertEquals(al.get(0), "foam");
		assertEquals(al.get(1), "foop");
		assertEquals(al.get(2), "fool");
	}

	@Test
	void constructStringTSTAndGetLongestPrefix() {
		final StringTST<Integer> st = new StringTST<Integer>();
		st.put("foop", 0);
		st.put("foobar", 1);
		st.put("fab", 2);
		st.put("fool", 3);
		st.put("foam", 3);
		
		final String lp = st.longestPrefixOf("foopghgjghgjgjgj");
		assertEquals(lp, "foop");
	}
}
