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
	@Test
	public void testGetLowerBoundOfNullRange() {
		Range range = null;
		try {
			double length = range.getLength();
			fail("Should not be able to length of a null range.");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	//getUpperBound() Test
	@Test 
	public void testUpperBoundShouldBeZero() {
		Range range = new Range(-1, 0);
		assertEquals(range.getUpperBound(), 0, .000000001d);
	}
	@Test
	public void testGetUpperBoundOfNullRange() {
		Range range = null;
		try {
			double length = range.getLength();
			fail("Should not be able to length of a null range.");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	//getLength() Tests
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
		assertEquals(range.getLength(), 9, .000000001d);
	}
	@Test
	public void testGetLengthOfNullRange() {
		Range range = null;
		try {
			double length = range.getLength();
			fail("Should not be able to length of a null range.");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	//bool contains(double value) Tests
	@Test
	public void testValueInRange() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(5);
		Assert.assertTrue(valueIsInRange);
	}
	@Test
	public void testValueLessThanLowerBound() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(-1);
		Assert.assertFalse(valueIsInRange);
	}
	@Test
	public void testValueGreaterThanUpperBound() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(15);
		Assert.assertFalse(valueIsInRange);
	}
	@Test
	public void testContainsOfNullRange() {
		Range range = null;
		try {
			boolean valueIsInRange = range.contains(1);
			fail("Combine should be able to call contains of a null value.");
		} catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	
	//combine(Range range1, Range range2) Tests
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
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 4, .000000001d);
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
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 4, .000000001d);
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
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 5, .000000001d);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one lower bound equals range two lower");
		}
	}
	
	@Test 
	public void testCombineRangeOneLowerEqualsRangeTwoUpper() {
		Range r1 = new Range(1, 4);
		Range r2 = new Range(-1, 1);
		try {
			Range range = Range.combine(r1, r2);
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 3, .000000001d);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one lower bound equals range two upper");
		}
	}
	@Test
	public void testCombineRangeOneContainsRangeTwo() {
		Range r1 = new Range(1, 10);
		Range r2 = new Range(4, 5);
		try {
			Range range = Range.combine(r1, r2);
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 10, .000000001d);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one contains range two");
		}
	}
	@Test
	public void testCombineRangeTwoContainsRangeOne() {
		Range r1 = new Range(4, 5);
		Range r2 = new Range(1, 10);
		try {
			Range range = Range.combine(r1, r2);
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
			assertEquals(lowerBound, 1, .000000001d);
			assertEquals(upperBound, 10, .000000001d);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range two contains range one");
		}
	}
}
