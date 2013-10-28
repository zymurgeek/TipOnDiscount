package com.itllp.tipOnDiscount.util.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

public class BigDecimalLabelMapTest {

	private String[] nullLabelArray = null;
	private String[] emptyValueStringArray = {};
	private String[] emptyLabelArray = {};
	private String[] nullValueStringArray = null;


	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testInitializeWithNullLabelArray() {
		// Call method under test
		try {
			new BigDecimalLabelMap(emptyValueStringArray, nullLabelArray);
			fail("Did not throw exception");
		} catch (RuntimeException e) {
			// OK
		}
	}
	
	@Test
	public void testInitializeWithNullValueArray() {
		// Call method under test and verify postconditions
		try {
			new BigDecimalLabelMap(nullValueStringArray, emptyLabelArray);
			fail("Did not throw exception");
		} catch (RuntimeException e) {
			// OK
		}
	}
	
	
	@Test
	public void testInequalInitialization() {
		// Set up preconditions
		String[] labels = { "one" };
		String[] values = { };

		// Call method under test
		try {
			new BigDecimalLabelMap(values, labels);
			fail("Did not throw exception");
		} catch (RuntimeException e) {
			// OK
		}
	}
	
	@Test
	public void testGetLabelWhenMapIsEmpty() {
		// Set up preconditions
		BigDecimalLabelMap map = new BigDecimalLabelMap(emptyValueStringArray, emptyLabelArray);
		BigDecimal value = new BigDecimal("0");
		
		// Call method under test
		String actualLabel = map.getLabel(value);
		
		// Verify postconditions
		assertNull("Label should not be found", actualLabel);
	}

	@Test
	public void testGetLabelNotInMap() {
		// Set up preconditions
		String[] values = { "1" };
		String[] labels = { "one" };
		BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
		BigDecimal value = new BigDecimal("0");
		
		// Call method under test
		String actualLabel = map.getLabel(value);
		
		// Verify postconditions
		assertNull("Label should not be found", actualLabel);
	}


	@Test
	public void testGetLabelWhenExactlyInMap() {
		// Set up preconditions
		String expectedLabel = "One";
		String expectedValueString = "1";
		String[] labels = { expectedLabel, "Two" };
		String[] values = { expectedValueString, "2" };
		BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
		BigDecimal expectedValue = new BigDecimal(expectedValueString);
		
		// Call method under test
		String actualLabel = map.getLabel(expectedValue);
		
		// Verify postconditions
		assertEquals("Wrong label", expectedLabel, actualLabel);
	}
	
	@Test
	public void testGetLabelWhenInMapWithDifferentPrecision() {
		// Set up preconditions
		String expectedLabel = "One";
		String expectedValueString = "1";
		String[] labels = { expectedLabel, "Two" };
		String[] values = { expectedValueString, "2" };
		BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
		BigDecimal expectedValue = new BigDecimal("1.0");
		
		// Call method under test
		String actualLabel = map.getLabel(expectedValue);
		
		// Verify postconditions
		assertEquals("Wrong label", expectedLabel, actualLabel);
		
	}

	@Test
	public void testGetValueWhenMapIsEmpty() {
		// Set up preconditions
		BigDecimalLabelMap map = new BigDecimalLabelMap(emptyValueStringArray, emptyLabelArray);
		String label = "none";
		
		// Call method under test
		BigDecimal actualValue = map.getValue(label);
		
		// Verify postconditions
		assertNull("Value should not be found", actualValue);
	}


	@Test
	public void testGetValueNotInMap() {
		// Set up preconditions
		String[] values = { "1" };
		String[] labels = { "one" };
		BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
		String label = "does not exist";
		
		// Call method under test
		BigDecimal actualValue = map.getValue(label);
		
		// Verify postconditions
		assertNull("Value should not be found", actualValue);
	}

	@Test
	public void testGetValueWhenInMap() {
		// Set up preconditions
		String expectedLabel = "One";
		String expectedValueString = "1";
		String[] labels = { expectedLabel, "Two" };
		String[] values = { expectedValueString, "2" };
		BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
		BigDecimal expectedValue = new BigDecimal(expectedValueString);
		
		// Call method under test
		BigDecimal actualValue = map.getValue(expectedLabel);
		
		// Verify postconditions
		assertTrue("Wrong value", 0==expectedValue.compareTo(actualValue));
	}
	

}
