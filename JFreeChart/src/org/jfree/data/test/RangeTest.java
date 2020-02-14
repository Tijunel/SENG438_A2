package org.jfree.data.test;

import static org.junit.Assert.*;
import java.security.InvalidParameterException;
import org.jfree.data.Range;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.*;


public class RangeTest {
	private Range exampleRange;
	Mockery mockingLowerBound = new Mockery();
	
	//double getLowerBound() Test
	@Test
	public void testLowerBoundShouldBeZero() {
		Range range = new Range(0, 1);
		assertEquals(0, range.getLowerBound(), .000000001d);
	}
	
	//double getUpperBound() Test
	@Test 
	public void testUpperBoundShouldBeZero() {
		Range range = new Range(-1, 0);
		assertEquals(0, range.getUpperBound(), .000000001d);
	}
	
	//double getLength() Tests
	@Test
	public void testGetLengthWithPositives() {
		Range range = new Range(1, 10);
		assertEquals(9, range.getLength(), .000000001d);
	}
	@Test
	public void testLengthShouldBeZero() {
		Range range = new Range(0, 0);
		assertEquals(0, range.getLength(), .000000001d);
	}
	@Test
	public void testLengthShouldBeOne() {
		Range range = new Range(1, 2);
		assertEquals(1, range.getLength(), .000000001d);
	}
	@Test
	public void testGetLengthWithNegatives() {
		Range range = new Range(-10, -1);
		assertEquals(9, range.getLength(), .000000001d);
	}
	
	//boolean contains(double value) Tests
	@Test
	public void testContainsValueInRange() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(5);
		Assert.assertTrue(valueIsInRange);
	}
	@Test
	public void testContainsValueLessThanLowerBound() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(-1);
		Assert.assertFalse(valueIsInRange);
	}
	@Test
	public void testContainsValueGreaterThanUpperBound() {
		Range range = new Range(0, 10);
		boolean valueIsInRange = range.contains(15);
		Assert.assertFalse(valueIsInRange);
	}
	@Test
	public void testContainsValueEqualToUpperAndLowerBound() {
		Range range = new Range(0, 0);
		boolean valueIsInRange = range.contains(0);
		Assert.assertTrue(valueIsInRange);
	}
	
	//Range combine(Range range1, Range range2) Tests
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Test
	public void testSameRange() { //Equivalent to Range One Upper and Range One Lower Equal Range Two Upper and Range Two Lower
		Range r1 = new Range(1, 2);
		try {
			Range range = Range.combine(r1, r1);
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 1 && upperBound == 2;
			Assert.assertTrue(isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept Range 1 greater than Range 2, and threw an " + e.getClass());
		}
	}
	
	@Test
	public void testCombineNullRangeOne() {
		Range r2 = new Range(1, 2);
		try {
			Range range = Range.combine(null, r2);
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 1 && upperBound == 2;
			Assert.assertTrue(isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept Range 1 as null, and threw an Exception");
		}
	}
	
	@Test 
	public void testCombineNullRangeTwo() {
		Range r1 = new Range(1, 2);
		try {
			Range range = Range.combine(r1, null);
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 1 && upperBound == 2;
			Assert.assertTrue(isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept Range 2 as null, and threw an Exception");
		}
		
	}
	
	@Test
	public void testCombineNullRanges() {
		try {
			Range range = Range.combine(null, null);
			Assert.assertNull(range);
		} catch(Exception e) {
			fail("Combine did not accept two null ranges, and threw an " + e.getClass());
		}
	}
	
	@Test
	public void testCombineRangeOneLessThanRangeTwo() { //Equivalent to Range Two Greater Than Range One
		Range r1 = new Range(1, 2);
		Range r2 = new Range(3, 4);
		try {
			Range range = Range.combine(r1, r2);
			Assert.assertNull(range);
		} catch(Exception e) {
			fail("Combine did not accept Range 1 less than Range 2, and threw an " + e.getClass());
		}
	}
	
	@Test
	public void testCombineRangeOneGreaterThanRangeTwo() { //Equivalent to Range Two Less Than Range One
		Range r1 = new Range(3, 4);
		Range r2 = new Range(1, 2);
		try {
			Range range = Range.combine(r1, r2);
			Assert.assertNull(range);
		} catch(Exception e) {
			fail("Combine did not accept Range 2 greater than Range 1, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == 1 && upperBound == 4;
			Assert.assertTrue("The lower bound should be 1 and the upper bound should be 4", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where upper bounds were equal, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == 1 && upperBound == 4;
			Assert.assertTrue("The lower bound should be 1 and the upper bound should be 4", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one upper bound equals range two lower, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == 1 && upperBound == 5;
			Assert.assertTrue("The lower bound should be 1 and the upper bound should be 5", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one lower bound equals range two lower, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == -1 && upperBound == 4;
			Assert.assertTrue("The lower bound should be -1 and the upper bound should be 4", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one lower bound equals range two upper, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == 1 && upperBound == 10;
			Assert.assertTrue("The lower bound should be 1 and the upper bound should be 10", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range one contains range two, and threw an " + e.getClass());
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
			boolean isCorrect = lowerBound == 1 && upperBound == 10;
			Assert.assertTrue("The lower bound should be 1 and the upper bound should be 10", isCorrect);
		} catch(Exception e) {
			fail("Combine did not accept overlapping ranges where range two contains range one, and threw an " + e.getClass());
		}
	}
	
	//Range shift(Range range, double delta) Tests
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Test
	public void testShiftAllNegativeRangeNegativeDelta() {
		Range r1 = new Range(-5, -2);
		double delta = -2;
		try {
			Range range = Range.shift(r1, delta);
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -7 && upperBound == -4;
			Assert.assertTrue("Range (-5,-2) properly shifted to Range (-7, -4)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -5 && upperBound == -2;
			Assert.assertTrue("Range (-5,-2) properly shifted to Range (-5, -2)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -4 && upperBound == -1;
			Assert.assertTrue("Range (-5,-2) properly shifted to Range (-4, -1)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -3 && upperBound == -1;
			Assert.assertTrue("Range (-2, 2) properly shifted to Range (-3, 1)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -2 && upperBound == 2;
			Assert.assertTrue("Range (-2, 2) properly shifted to Range (-2, 2)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == -1 && upperBound == 3;
			Assert.assertTrue("Range (-2, 2) properly shifted to Range (-1, 3)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 1 && upperBound == 4;
			Assert.assertTrue("Range (2, 5) properly shifted to Range (1, 4)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 2 && upperBound == 5;
			Assert.assertTrue("Range (2, 5) properly shifted to Range (2, 5)",isCorrect);
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
			double lowerBound = range.getLowerBound();
			double upperBound = range.getLowerBound() + range.getLength();
			boolean isCorrect = lowerBound == 3 && upperBound == 6;
			Assert.assertTrue("Range (2, 5) properly shifted to Range (3, 6)",isCorrect);
		} catch(Exception e) {
			fail("Shift did not accept a fully positive range with a positive delta.");
		}
	}
	
	//boolean intersects(double lower, double upper) Tests
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













