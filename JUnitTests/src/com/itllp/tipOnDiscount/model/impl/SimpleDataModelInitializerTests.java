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
		    oneOf(mockDataModel).setTaxRate(initializer.getTaxRate());
		    oneOf(mockDataModel).setDiscount(initializer.getDiscount());
		    oneOf(mockDataModel).setPlannedTipRate(initializer.getTipRate());
		    oneOf(mockDataModel).setSplitBetween(initializer.getSplitBetween());
		    oneOf(mockDataModel).setRoundUpToAmount(initializer.getRoundUpToAmount());
		    oneOf(mockDataModel).setBumps(initializer.getBumps());
		}});

		// Call method under test
		initializer.initialize(mockDataModel, dummyAndroidContext);
	}
	
	// TODO add tests to verify data returned by the get methods
}