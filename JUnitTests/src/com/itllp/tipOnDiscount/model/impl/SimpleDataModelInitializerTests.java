// Copyright 2013 David A. Greenbaum
/*
This file is part of Tip On Discount.

Tip On Discount is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Tip On Discount is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with Tip On Discount.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.itllp.tipOnDiscount.model.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.model.DataModel;


public class SimpleDataModelInitializerTests {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private SimpleDataModelInitializer initializer;
	
	@Before
	public void setUp() {
		initializer = new SimpleDataModelInitializer();
	}

	@Test
	public void testInitialize() {
		// Set up preconditions
		final DataModel mockDataModel = context.mock(DataModel.class);
		final android.content.Context dummyAndroidContext = new android.content.Context();
		
		// Set up expectations
		context.checking(new Expectations() {{
		    oneOf(mockDataModel).setBillTotal(initializer.getBillTotal());
		    oneOf(mockDataModel).setTaxRate(initializer.getTaxRate
		    		(dummyAndroidContext));
		    oneOf(mockDataModel).setDiscount(initializer.getDiscount());
		    oneOf(mockDataModel).setPlannedTipRate(initializer.getTipRate
		    		(dummyAndroidContext));
		    oneOf(mockDataModel).setSplitBetween(initializer.getSplitBetween());
		    oneOf(mockDataModel).setRoundUpToAmount(initializer
		    		.getRoundUpToAmount(dummyAndroidContext));
		    oneOf(mockDataModel).setBumps(initializer.getBumps());
		}});

		// Call method under test
		initializer.initialize(mockDataModel, dummyAndroidContext);
	}
	
	@Test
	public void testGetBillTotal() {
		// Call method under test
		BigDecimal billTotal = initializer.getBillTotal();
		
		// Verify postconditions
		assertNotNull("Bill total may not be null", billTotal);
		assertEquals("Incorrect bill total", "0.00", billTotal.toPlainString());
	}
	
	@Test
	public void testGetTaxRate() {
		// Call method under test
		BigDecimal taxRate = initializer.getTaxRate(null);
		
		// Verify postconditions
		assertNotNull("Tax rate may not be null", taxRate);
		assertEquals("Incorrect tax rate", "0.00000", taxRate.toPlainString());
	}
	
	@Test
	public void testGetDiscount() {
		// Call method under test
		BigDecimal discount = initializer.getDiscount();
		
		// Verify postconditions
		assertNotNull("Discount may not be null", discount);
		assertEquals("Incorrect discount", "0.00", discount.toPlainString());
	}
	
	@Test
	public void testGetTipRate() {
		// Call method under test
		BigDecimal tipRate = initializer.getTipRate(null);
		
		// Verify postconditions
		assertNotNull("Tip rate may not be null", tipRate);
		assertEquals("Incorrect tip rate", "0.00000", tipRate.toPlainString());
	}
	
	@Test
	public void testGetSplitBetween() {
		// Call method under test
		int splitBetween = initializer.getSplitBetween();
		
		// Verify postconditions
		assertEquals("Incorrect split between", 1, splitBetween);
	}
	
	@Test
	public void testGetRoundUpToAmount() {
		// Call method under test
		BigDecimal roundUpToAmount = initializer.getRoundUpToAmount(null);
		
		// Verify postconditions
		assertNotNull("Round Up To Amount may not be null", roundUpToAmount);
		assertEquals("Incorrect round up to amount", "0.01", roundUpToAmount.toPlainString());
	}
	
	@Test
	public void testGetBumps() {
		// Call method under test
		int bumps = initializer.getBumps();
		
		// Verify postconditions
		assertEquals("Incorrect bumps", 0, bumps);
	}
	
}