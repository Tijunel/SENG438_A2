package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.Range;
import org.junit.Test;
import org.junit.*;


public class RangeTest {
	private Range exampleRange;
	//getCentralValue() Test
	@BeforeClass public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Before
	public void setUp() throws Exception{
		exampleRange = new Range(-1, 1);
		
	}
	
	@Test
	public void centralValueShouldBeZero() {
		assertEquals("The central value of -1 and 1 should be 0", 0, exampleRange.getCentralValue(), .000000001d);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	
	//getLowerBound() Test
	@Test
	public void testLowerBoundShouldBeZero() {
		Range range = new Range(0, 1);
		assertEquals(range.getLowerBound(), 0, .000000001d);
	}
	//getUpperBound() Test
	@Test 
	public void testUpperBoundShouldBeZero() {
		Range range = new Range(-1, 0);
		assertEquals(range.getUpperBound(), 0, .000000001d);
	}
	//getLength() Test
	@Test
	public void testGetLengthWithPositives() {
		Range range = new Range(1, 10);
		assertEquals(range.getLength(), 9, .000000001d);
	}
	@Test
	public void testLengthShouldBeZero() {
		Range range = new Range(0, 0);
		assertEquals(range.getLength(), 0, .000000001d);
	}
	@Test
	public void testGetLengthWithNegatives() {
		Range range = new Range(-10, -1);
		System.out.println(range.getLength());
		assertEquals(range.getLength(), 9, .000000001d);
	}
	
	//combine(Range range1, Range range2) tests
	@Test
	public void testSameRange() { //Equivalent to Range One Upper and Range One Lower Equal Range Two Upper and Range Two Lower
		Range r1 = new Range(1, 2);
		Range r2 = new Range(1, 2);
		Range range = Range.combine(r1, r2);
		assertEquals("The range should be equal to the equivanlent ranges", range, r1);
		assertEquals("The range should be equal to the equivanlent ranges", range, r2);
	}
	
	@Test
	public void testCombineNullRangeOne() {
		Range r1 = null;
		Range r2 = new Range(1, 2);
		Range range = Range.combine(r1, r2);
		assertEquals("The range should be equal to the non-null range", range, r2);
	}
	
	@Test 
	public void testCombineNullRangeTwo() {
		Range r1 = new Range(1, 2);
		Range r2 = null;
		Range range = Range.combine(r1, r2);
		assertEquals("The range should be equal to the non-null range", range, r1);
	}
	
	@Test
	public void testCombineNullRanges() {
		Range r1 = null;
		Range r2 = null;
		Range range = Range.combine(r1, r2);
		Assert.assertNull(range);
	}
	
	@Test
	public void testCombineRangeOneLessThanRangeTwo() { //Equivalent to Range Two Greater Than Range One
		Range r1 = new Range(1, 2);
		Range r2 = new Range(3, 4);
		try {
			Range range = Range.combine(r1, r2);
			fail("Combine should not accept ranges that do not overlap");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	
	@Test
	public void testCombineRangeOneGreaterThanRangeTwo() { //Equivalent to Range Two Less Than Range One
		Range r1 = new Range(3, 4);
		Range r2 = new Range(1, 2);
		try {
			Range range = Range.combine(r1, r2);
			fail("Combine should not accept ranges that do not overlap");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	
	@Test
	public void testCombineRangeOneUpperEqualsRangeTwoUpper() {
		Range r1 = new Range(1, 4);
		Range r2 = new Range(3, 4);
		try {
			Range range = Range.combine(r1, r2);
			Range expectedRange = new Range(1, 4);
			assertEquals(range, expectedRange);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where upper bounds were equal");
		}
	}
	
	@Test 
	public void testCombineRangeOneUpperEqualsRangeTwoLower() {
		Range r1 = new Range(1, 3);
		Range r2 = new Range(3, 4);
		try {
			Range range = Range.combine(r1, r2);
			Range expectedRange = new Range(1, 4);
			assertEquals(range, expectedRange);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one upper bound equals range two lower");
		}
	}
	
	@Test 
	public void testCombineRangeOneLowerEqualsRangeTwoLower() {
		Range r1 = new Range(1, 3);
		Range r2 = new Range(1, 5);
		try {
			Range range = Range.combine(r1, r2);
			Range expectedRange = new Range(1, 5);
			assertEquals(range, expectedRange);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one upper bound equals range two lower");
		}
	}
}
