package org.jfree.data.test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValue;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.*;
import static org.hamcrest.Matchers.*;


public class DataUtilitiesTest {
	Mockery mockingContext = new Mockery();
	// mocks an empty Values2D
	final Values2D ZeroV2D = mockingContext.mock(Values2D.class, "ZeroV2D");
	// mocks a 1x1 Values2D [(5.5)]
	final Values2D OneV2D = mockingContext.mock(Values2D.class, "OneV2D");
	// mocks a 4x4 Values2D [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	final Values2D ThreeV2D = mockingContext.mock(Values2D.class, "ThreeV2D");

	Mockery mockingKeyValues = new Mockery();
	//mocks a KeyedValues of [(0,5),(1, 9),(2, 2)]
	final KeyedValues keyedValues = mockingKeyValues.mock(KeyedValues.class, "keyedValue");

	final KeyedValues cumulative = mockingKeyValues.mock(KeyedValues.class, "cumulative");

	@Before
	public void setUp() throws Exception{
		// Add expectations for the mocks (expected return values for each method)
		mockingContext.checking(new Expectations() {{

			// ZEROV2D SETUP - setup mock for empty Values2D
			allowing(ZeroV2D).getRowCount();
		    will(returnValue(0));
		    allowing(ZeroV2D).getColumnCount();
		    will(returnValue(0));

		    // ONEV2D SETUP - setup mock for 1x1 Values2D as [(5.5)]
		    allowing(OneV2D).getRowCount();
		    will(returnValue(1));
		    allowing(OneV2D).getColumnCount();
		    will(returnValue(1));
		    allowing(OneV2D).getValue(0, 0);
		    will(returnValue(5.5));

		    // if index is less than 0 or greater than 0, return IndexOutOfBoundsException
		    allowing(OneV2D).getValue(with(lessThan(0)), with(any(Integer.class)));
		    will(throwException(new IndexOutOfBoundsException())); //return exception
		    allowing(OneV2D).getValue(with(any(Integer.class)), with(lessThan(0)));
		    will(throwException(new IndexOutOfBoundsException())); //return exception
		    allowing(OneV2D).getValue(with(greaterThan(0)), with(any(Integer.class)));
		    will(throwException(new IndexOutOfBoundsException())); //return exception
		    allowing(OneV2D).getValue(with(any(Integer.class)), with(greaterThan(0)));
		    will(throwException(new IndexOutOfBoundsException()));


		    // setup 3x3 Values2D as [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
		    allowing(ThreeV2D).getRowCount();
		    will(returnValue(3));
		    allowing(ThreeV2D).getColumnCount();
		    will(returnValue(3));

		    // if index is less than 0 or greater than 2, return IndexOutOfBoundsException
		    allowing(ThreeV2D).getValue(with(lessThan(0)), with(any(Integer.class)));
		    will(throwException(new InvalidParameterException())); //return exception
		    allowing(ThreeV2D).getValue(with(any(Integer.class)), with(lessThan(0)));
		    will(throwException(new InvalidParameterException())); //return exception
		    allowing(ThreeV2D).getValue(with(greaterThan(2)), with(any(Integer.class)));
		    will(throwException(new InvalidParameterException())); //return exception
		    allowing(ThreeV2D).getValue(with(any(Integer.class)), with(greaterThan(2)));
		    will(throwException(new InvalidParameterException())); //return exception

		    allowing(ThreeV2D).getValue(0, 0);
		    will(returnValue(5.3));
		    allowing(ThreeV2D).getValue(0, 1);
		    will(returnValue(4.3));
		    allowing(ThreeV2D).getValue(0, 2);
		    will(returnValue(3.3));
		    allowing(ThreeV2D).getValue(1, 0);
		    will(returnValue(1.8));
		    allowing(ThreeV2D).getValue(1, 1);
		    will(returnValue(7.5));
		    allowing(ThreeV2D).getValue(1, 2);
		    will(returnValue(4.2));
		    allowing(ThreeV2D).getValue(2, 0);
		    will(returnValue(2.4));
		    allowing(ThreeV2D).getValue(2, 1);
		    will(returnValue(5.3));
		    allowing(ThreeV2D).getValue(2, 2);
		    will(returnValue(6.7));
		}});

		mockingKeyValues.checking(new Expectations() {{
			allowing(keyedValues).getKey(0);
		    will(returnValue(0));
		    allowing(keyedValues).getKey(1);
		    will(returnValue(1));
		    allowing(keyedValues).getKey(2);
		    will(returnValue(2));

		    allowing(keyedValues).getKeys();
		    List<Integer> keys = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
 		    will(returnEnumeration(keys));

 		    allowing(keyedValues).getIndex(0);
		    will(returnValue(0));
		    allowing(keyedValues).getIndex(1);
		    will(returnValue(1));
		    allowing(keyedValues).getIndex(2);
		    will(returnValue(2));

		    allowing(keyedValues).getValue(0);
		    will(returnValue(5));
		    allowing(keyedValues).getValue(1);
		    will(returnValue(9));
		    allowing(keyedValues).getValue(2);
		    will(returnValue(2));

		    allowing(cumulative).getKey(0);
		    will(returnValue(0));
		    allowing(cumulative).getKey(1);
		    will(returnValue(1));
		    allowing(cumulative).getKey(2);
		    will(returnValue(2));

		    allowing(cumulative).getKeys();
 		    will(returnEnumeration(keys));

 		    allowing(cumulative).getIndex(0);
		    will(returnValue(0));
		    allowing(cumulative).getIndex(1);
		    will(returnValue(1));
		    allowing(cumulative).getIndex(2);
		    will(returnValue(2));

		    allowing(cumulative).getValue(0);
		    will(returnValue(0.3125));
		    allowing(cumulative).getValue(1);
		    will(returnValue(0.875));
		    allowing(cumulative).getValue(2);
		    will(returnValue(1.0));
		}});
	}

	// TESTS FOR testCalculateColumnTotal
	@Test
	// Test on an empty Value2D object
	public void testCalculateColumnTotalZeroV2D() {
		double result = DataUtilities.calculateColumnTotal(ZeroV2D, 0);
		assertEquals(0, result, .0000000001d);
	}

	@Test
	// Test on a 1x1 Value2D object [(5.5)]
	public void testCalculateColumnTotalOneV2D() {
		double result2 = DataUtilities.calculateColumnTotal(OneV2D, 0);
		assertEquals(5.5, result2, .0000000001d);
	}


	@Test (expected = InvalidParameterException.class)
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test invalid index - below lower bounds, expect to throw exception
	public void testCalculateColumnTotalThreeV2D_BLB() {
		double result1 = DataUtilities.calculateColumnTotal(ThreeV2D, -1);
		System.out.println(result1);
		assertEquals(0, result1, .000000001d);
	}

	@Test (expected = InvalidParameterException.class)
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test invalid index - above upper bounds, expect to throw exception
	public void testCalculateColumnTotalThreeV2D_AUB() {
		double result1 = DataUtilities.calculateColumnTotal(ThreeV2D, 3);
		System.out.println(result1);
		assertEquals(0, result1, .000000001d);
	}

	@Test
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test valid index ranges
	public void testCalculateColumnTotalThreeV2D() {
		double result2 = DataUtilities.calculateColumnTotal(ThreeV2D, 0);
		assertEquals(9.5, result2, .0000000001d);

		double result3 = DataUtilities.calculateColumnTotal(ThreeV2D, 1);
		assertEquals(17.1, result3, .0000000001d);

		double result4 = DataUtilities.calculateColumnTotal(ThreeV2D, 2);
		assertEquals(14.2, result4, .0000000001d);
	}


	//TESTS FOR testCalculateRowTotal
	@Test
	// Test on an empty Value2D object
	public void testCalculateRowTotalZeroV2D() {
		double result = DataUtilities.calculateRowTotal(ZeroV2D, 0);
		assertEquals(0, result, .0000000001d);
	}

	@Test
	// Test on a 1x1 Value2D object [(5.5)]
	public void testCalculateRowTotalOneV2D() {
		double result2 = DataUtilities.calculateRowTotal(OneV2D, 0);
		assertEquals(5.5, result2, .0000000001d);
	}
	
	@Test (expected = InvalidParameterException.class)
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test invalid index - below lower bounds, expect to throw exception
	public void testCalculateRowTotalThreeV2D_BLB() {
		double result1 = DataUtilities.calculateRowTotal(ThreeV2D, -1);
		System.out.println(result1);
		assertEquals(0, result1, .000000001d);
	}

	@Test (expected = InvalidParameterException.class)
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test invalid index - above upper bounds, expect to throw exception
	public void testCalculateRowTotalThreeV2D_AUB() {
		double result1 = DataUtilities.calculateRowTotal(ThreeV2D, 3);
		System.out.println(result1);
		assertEquals(0, result1, .000000001d);
	}

	@Test
	// Test on a 3x3 Value2D object [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	// Test valid index ranges
	public void testCalculateRowTotalThreeV2D() {
		double result2 = DataUtilities.calculateRowTotal(ThreeV2D, 0);
		assertEquals(12.9, result2, .0000000001d);

		double result3 = DataUtilities.calculateRowTotal(ThreeV2D, 1);
		assertEquals(13.5, result3, .0000000001d);

		double result4 = DataUtilities.calculateRowTotal(ThreeV2D, 2);
		assertEquals(14.4, result4, .0000000001d);
	}


	//TESTS FOR testCreateNumberArray

	@Test (expected = IllegalArgumentException.class)
	// Test converting an invalid double array, should throw exception
	public void testCreateNumberArrayZero(){
		double[] testArr = null;
		Number[] resultArr = DataUtilities.createNumberArray(testArr);
		for(Number n: resultArr) {
			System.out.println(n);
		}
	}

	@Test
	// Test converting a valid double array
	public void testCreateNumberArray() {
		Number[] expectedArr = new Number[4];
		expectedArr[0] = 1.0;
		expectedArr[1] = 2.0;
		expectedArr[2] = 3.0;
		expectedArr[3] = 4.0;

		double[] doubleArr = new double[4];
		doubleArr[0] = 1.0;
		doubleArr[1] = 2.0;
		doubleArr[2] = 3.0;
		doubleArr[3] = 4.0;

		Number[] resultArr = DataUtilities.createNumberArray(doubleArr);
		assertArrayEquals(expectedArr, resultArr);
	}

	@Test
	public void testCreateNumberArray2D_Populated2DArray() {
		Number [][] expectedArr = new Number [2][3];
		double [][] doubleArr = new double [2][3];

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				expectedArr[i][j] = i + j;
				doubleArr[i][j] = i + j;
			}
		}
		Number [][] resultArr = DataUtilities.createNumberArray2D(doubleArr);

		for (int i = 0; i < 2; i++)
			assertArrayEquals(expectedArr[i], resultArr[i]);
	}

	@Test
	public void testCreateNumberArray2D_empty2DArray() {
		Number [][] expectedArr = new Number [0][0];
		double [][] doubleArr = new double [0][0];

		Number [][] resultArr = DataUtilities.createNumberArray2D(doubleArr);

		assertArrayEquals(expectedArr[0], resultArr[0]);
	}

	@Test (expected = InvalidParameterException.class)
	public void testCreateNumberArray2D_invalidInput() throws InvalidParameterException{
			DataUtilities.createNumberArray2D(null);
			fail("Exception not thrown. ");
	}

	@Test
	public void testGetCumulativePercentages_validKeyedValue() {
		for (int i = 0; i < 3; i++)
			assertEquals(cumulative.getValue(i), keyedValues.getValue(i));
	}

	@Test (expected = InvalidParameterException.class)
	public void testGetCumulativePercentages_invalidInput() throws InvalidParameterException{
			DataUtilities.getCumulativePercentages(null);
			fail("Exception not thrown. ");
	}
}
