package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

import org.junit.Test;

import searchTree.EmptyTree;
import searchTree.SearchTreeMap;
import searchTree.Tree;

public class PublicTests{
	
	@Test
	public void testEasyBSTSearch() {
		Tree<Integer,String> t = EmptyTree.getInstance();
		assertTrue(t.search(0) == null);
		t = t.insert(5, "THIS IS 5");
		assertEquals("THIS IS 5", t.search(5));
		t = t.insert(8, "THIS IS 8");
		assertEquals("THIS IS 8", t.search(8));
	}
	
	@Test
	public void testBSTDelete() {
		Tree<Integer,String> t = EmptyTree.getInstance();
		t = t.insert(5, "THIS IS 5");
		t = t.insert(8, "THIS IS 8");
		t = t.insert(2, "THIS IS 2");
		t = t.delete(5);
		assertEquals(null, t.search(5));
	}
	
	@Test
	public void testEmptySearchTreeMap() {
		SearchTreeMap<String, Integer> s = new SearchTreeMap<String, Integer>();
		assertEquals(0, s.size());
		try {
			assertEquals(null, s.getMin());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		try {
			assertEquals(null, s.getMax());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		assertEquals(null, s.get("a"));
	}
	
	@Test
	public void testBasicSearchTreeMapStuff() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
	}
	
	@Test
	public void testAddKeys() {
		Tree<Integer, String> t = EmptyTree.getInstance();
		t = t.insert(3, "3");
		t = t.insert(6, "6");
		t = t.insert(1, "1");
		t = t.insert(2, "2");
		t = t.insert(7, "7");
		t = t.insert(4, "4");
		t = t.insert(5, "5");
		ArrayList<Integer> keyList = new ArrayList<>();
		t.addKeysToCollection(keyList);
		assertEquals(keyList.get(0), Integer.valueOf(1));
		assertEquals(keyList.get(1), Integer.valueOf(2));
		assertEquals(keyList.get(2), Integer.valueOf(3));
	}
	
	@Test
	public void testSubMap() {
		SearchTreeMap<Integer, Integer> s = new SearchTreeMap<Integer, Integer>();
		s.put(5, 5);
		s.put(2, 2);
		s.put(1, 1);
		s.put(4, 4);
		s.put(3, 3);
		s.put(8, 8);
		s.put(9, 9);
		s.put(7, 7);
		s.put(6, 6);
		s.put(0, 0);
		s = s.subMap(2, 6);
		System.out.print(s.keyList());
	}
	
}