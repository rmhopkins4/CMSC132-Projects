package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {

	/* Write a lot of tests! */
	
	@Test
	public void addToEndTest() {
		BasicLinkedList<Integer> aList = new BasicLinkedList<>();
		aList.addToEnd(1).addToEnd(2).addToEnd(3);
		assertEquals(aList.getSize(), 3);
		assertEquals(aList.getFirst(), Integer.valueOf(1));
		assertEquals(aList.getLast(), Integer.valueOf(3));
	}
	
	@Test
	public void addToFrontTest() {
		BasicLinkedList<Integer> aList = new BasicLinkedList<>();
		aList.addToFront(1).addToFront(2).addToFront(3);
		assertEquals(aList.getSize(), 3);
		assertEquals(aList.getFirst(), Integer.valueOf(3));
		assertEquals(aList.getLast(), Integer.valueOf(1));
	}
	
	@Test
	public void getBothTest() {
		BasicLinkedList<Integer> aList = new BasicLinkedList<>();
		aList.addToEnd(1);
		assertEquals(aList.retrieveFirstElement(), Integer.valueOf(1));
		assertEquals(aList.getLast(), null);
	}
	
	@Test
	public void retriveBothTest() {
		BasicLinkedList<Integer> aList = new BasicLinkedList<>();
		assertEquals(aList.retrieveLastElement(), null);
		aList.addToEnd(1);
		assertEquals(aList.retrieveLastElement(), Integer.valueOf(1));
		aList.addToEnd(1);
		aList.addToEnd(2);
		aList.addToEnd(3);
		assertEquals(aList.retrieveLastElement(), Integer.valueOf(3));
		assertEquals(aList.retrieveLastElement(), Integer.valueOf(2));
		
	}
	
	@Test
	public void removeInstancesTest() {
		
		// Check empty list
		BasicLinkedList<Integer> aList = new  BasicLinkedList<>();
		aList.removeAllInstances(1);
		assertTrue(aList.getSize() == 0);
		assertEquals(aList.getLast(), null);
		assertEquals(aList.getFirst(), null);
		
		// Check 1 size list
		aList = new BasicLinkedList<>();
		aList.addToEnd(1);
		aList.removeAllInstances(0);
		assertEquals(aList.getLast(), Integer.valueOf(1));
		assertEquals(aList.getFirst(), Integer.valueOf(1));
		aList.removeAllInstances(1);
		assertEquals(aList.getLast(), null);
		assertEquals(aList.getFirst(), null);
		
		// Check removing bad head
		aList = new BasicLinkedList<>();
		aList.addToEnd(1);
		aList.addToEnd(1);
		aList.addToEnd(2);
		aList.removeAllInstances(1);
		assertEquals(aList.getFirst(), Integer.valueOf(2));
		assertEquals(aList.getLast(), Integer.valueOf(2));
		
		// Check removing bad tail
		aList = new BasicLinkedList<>();
		aList.addToEnd(1);
		aList.addToEnd(2);
		aList.addToEnd(2);
		aList.removeAllInstances(2);
		assertEquals(aList.getFirst(), Integer.valueOf(1));
		assertEquals(aList.getLast(), Integer.valueOf(1));
		
		// Standard test
		aList = new BasicLinkedList<>();
		aList.addToEnd(1);
		aList.addToEnd(0);
		aList.addToEnd(0);
		aList.addToEnd(0);
		aList.addToEnd(1);
		aList.removeAllInstances(0);
		assertEquals(aList.getFirst(), Integer.valueOf(1));
		assertEquals(aList.getLast(), Integer.valueOf(1));
	}
}
