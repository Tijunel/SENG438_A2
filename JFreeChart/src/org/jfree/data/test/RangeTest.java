package org.jfree.data.test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
		double lowerBound = range.getLowerBound();
		double upperBound = lowerBound + range.getLength();
		assertEquals(lowerBound, 1, .000000001d);
		assertEquals(upperBound, 2, .000000001d);
	}
	
	@Test
	public void testCombineNullRangeOne() {
		Range r1 = null;
		Range r2 = new Range(1, 2);
		Range range = Range.combine(r1, r2);
		double lowerBound = range.getLowerBound();
		double upperBound = lowerBound + range.getLength();
		assertEquals(lowerBound, 1, .000000001d);
		assertEquals(upperBound, 2, .000000001d);
	}
	
	@Test 
	public void testCombineNullRangeTwo() {
		Range r1 = new Range(1, 2);
		Range r2 = null;
		Range range = Range.combine(r1, r2);
		double lowerBound = range.getLowerBound();
		double upperBound = lowerBound + range.getLength();
		assertEquals(lowerBound, 1, .000000001d);
		assertEquals(upperBound, 2, .000000001d);
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
			double lowerBound = range.getLowerBound();
			double upperBound = lowerBound + range.getLength();
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
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testShiftNullRange() {
		Range r1 = null;
		double delta = 0;
		
		thrown.expect(InvalidParameterException.class);
		
		Range range = Range.shift(r1, delta);
	}
	
	@Test
	public void testShiftAllNegativeRangeNegativeDelta() {
		Range r1 = new Range(-5, -2);
		double delta = -2;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-7, -4);
			assertEquals("Range (-5,-2) properly shifted to Range (-7, -4)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully negative range with a negative delta.");
		}
	}
	
	@Test
	public void testShiftAllNegativeRangeZeroDelta() {
		Range r1 = new Range(-5, -2);
		double delta = 0;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-5, -2);
			assertEquals("Range (-5,-2) properly shifted to Range (-5, -2)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully negative range with a zero delta.");
		}
	}
	
	@Test
	public void testShiftAllNegativeRangePositiveDelta() {
		Range r1 = new Range(-5, -2);
		double delta = 1;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-4, -1);
			assertEquals("Range (-5,-2) properly shifted to Range (-4, -1)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully negative range with a positive delta.");
		}
	}
	
	@Test
	public void testShiftAcrossZeroRangeNegativeDelta() {
		Range r1 = new Range(-2, 2);
		double delta = -1;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-3, 1);
			assertEquals("Range (-2, 2) properly shifted to Range (-3, 1)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a range across zero with a negative delta.");
		}
	}
	
	@Test
	public void testShiftAcrossZeroRangeZeroDelta() {
		Range r1 = new Range(-2, 2);
		double delta = 0;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-2, 2);
			assertEquals("Range (-2, 2) properly shifted to Range (-2, 2)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a range across zero with a zero delta.");
		}
	}
	
	@Test
	public void testShiftAcrossZeroRangePositiveDelta() {
		Range r1 = new Range(-2, 2);
		double delta = 1;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(-1, 3);
			assertEquals("Range (-2, 2) properly shifted to Range (-1, 3)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a range across zero with a poitive delta.");
		}
	}
	
	@Test
	public void testShiftAllPositiveRangeNegativeDelta() {
		Range r1 = new Range(2, 5);
		double delta = -1;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(1, 4);
			assertEquals("Range (2, 5) properly shifted to Range (1, 4)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully positive range with a negative delta.");
		}
	}
	
	@Test
	public void testShiftAllPositiveRangeZeroDelta() {
		Range r1 = new Range(2, 5);
		double delta = 0;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(2, 5);
			assertEquals("Range (2, 5) properly shifted to Range (2, 5)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully positive range with a zero delta.");
		}
	}
	
	@Test
	public void testShiftAllPositiveRangePositiveDelta() {
		Range r1 = new Range(2, 5);
		double delta = 1;
		try {
			Range range = Range.shift(r1, delta);
			Range expectedRange = new Range(3, 6);
			assertEquals("Range (2, 5) properly shifted to Range (3, 6)", expectedRange, range);
		} catch(Exception e) {
			fail("Shift did not accept a fully positive range with a positive delta.");
		}
	}
	
	@Test
	public void testIntersects_lowerLessThanThisLower_upperLessThanThisLower() {
		Range r1 = new Range(-2, 2);
		double lower = -5, upper = -4;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertFalse("Range (-5, -4) does not intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range lower than this Range.");
		}
	}
	
	@Test
	public void testIntersects_lowerLessThanThisLower_upperGreaterThanThisLower() {
		Range r1 = new Range(-2, 2);
		double lower = -5, upper = 0;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (-5, 0) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range surronding lower bound");
		}
	}
	
	@Test
	public void testIntersects_lowerEqualToThisLower_upperGreaterThanThisLower() {
		Range r1 = new Range(-2, 2);
		double lower = -2, upper = -1;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (-2, -1) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range with equal lower bound Range.");
		}
	}
	
	@Test
	public void testIntersects_lowerEqualToThisLower_upperEqualToThisUpper() {
		Range r1 = new Range(-2, 2);
		double lower = -2, upper = 2;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (-2, 2) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range equal to this Range.");
		}
	}
	
	@Test
	public void testIntersects_lowerGreaterThanThisLower_upperEqualToThisUpper() {
		Range r1 = new Range(-2, 2);
		double lower = -1, upper = 2;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (-1, 2) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range with equal upper bound Range.");
		}
	}
	
	@Test
	public void testIntersects_lowerGreaterThanThisLower_upperGreaterThanThisUpper() {
		Range r1 = new Range(-2, 2);
		double lower = 0, upper = 5;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (0, 5) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range surronding upper bound.");
		}
	}
	
	@Test
	public void testIntersects_lowerGreaterThanThisUpper_upperGreaterThanThisUpper() {
		Range r1 = new Range(-2, 2);
		double lower = 4, upper = 5;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertFalse("Range (4, 5) does not intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range greater than this Range.");
		}
	}
	
	@Test
	public void testIntersects_lowerLessThanThisLower_upperGreaterThanThisUpper() {
		Range r1 = new Range(-2, 2);
		double lower = -5, upper = 5;
		try {
			boolean intersectCheck = r1.intersects(lower, upper);
			assertTrue("Range (-5, 5) does intersect Range (-2, 2)", intersectCheck);
		}catch(Exception e) {
			fail("Intersect did not accept Range surronding this Range.");
		}
	}
}













