package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.*;

public class DataUtilitiesTest {
	Mockery mockingContext = new Mockery();
	// mocks an empty Values2D 
	final Values2D ZeroV2D = mockingContext.mock(Values2D.class, "ZeroV2D");	
	// mocks a 1x1 Values2D [(5.5)]
	final Values2D OneV2D = mockingContext.mock(Values2D.class, "OneV2D");
	// mocks a 4x4 Values2D [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
	final Values2D ThreeV2D = mockingContext.mock(Values2D.class, "ThreeV2D");
	
	@Before
	public void setUp() throws Exception{
		mockingContext.checking(new Expectations() {{
		    // setup empty Values2D
			allowing(ZeroV2D).getRowCount();
		    will(returnValue(0)); 
		    allowing(ZeroV2D).getColumnCount();
		    will(returnValue(0));
		    
		    // setup 1x1 Values2D as [(5.5)]
		    allowing(OneV2D).getRowCount();
		    will(returnValue(1));
		    allowing(OneV2D).getColumnCount();
		    will(returnValue(1));
		    allowing(OneV2D).getValue(0, 0);
		    will(returnValue(5.5));
		    
		    // setup 4x4 Values2D as [(5.3, 4.3, 3.3), (1.8, 7.5, 4.2), (2.4, 5.3, 6.7)]
		    allowing(ThreeV2D).getRowCount();
		    will(returnValue(3));
		    allowing(ThreeV2D).getColumnCount();
		    will(returnValue(3));
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
	}
	
	//testCalculateColumnTotal
	
	@Test
	public void testCalculateColumnTotalZeroV2D() {
		double result = DataUtilities.calculateColumnTotal(ZeroV2D, 0);
		assertEquals(0, result, .0000000001d);
	}
		
	@Test
	public void testCalculateColumnTotalOneV2D() {
		double result2 = DataUtilities.calculateColumnTotal(OneV2D, 0);
		assertEquals(5.5, result2, .0000000001d);
	}
		
	@Test
	public void testCalculateColumnTotalThreeV2D() {
		double result2 = DataUtilities.calculateColumnTotal(ThreeV2D, 0);
		assertEquals(9.5, result2, .0000000001d);
			
		double result3 = DataUtilities.calculateColumnTotal(ThreeV2D, 1);
		assertEquals(17.1, result3, .0000000001d);
			
		double result4 = DataUtilities.calculateColumnTotal(ThreeV2D, 2);
		assertEquals(14.2, result4, .0000000001d);
	}

	@Test
	public void testCalculateRowTotal() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCreateNumberArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNumberArray2D() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCumulativePercentages() {
		fail("Not yet implemented");
	}

}
