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
import org.jmock.States;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.model.DataModel;


public class DataModelInitializerFromPersistedDefaultsTests {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private DefaultsPersister mockDefaultsPersister;
	@Mock private Defaults mockDefaults;
	private DataModelInitializerFromPersistedDefaults initializer;
	private static final BigDecimal EXPECTED_TAX_PERCENT = new BigDecimal("1");
	private static final BigDecimal EXPECTED_TAX_RATE = new BigDecimal(".01");
	
	@Before
	public void setUp() {
		initializer = new DataModelInitializerFromPersistedDefaults(
				mockDefaultsPersister, mockDefaults);
	}

	@Test
	public void testInitialize() {
		// Set up preconditions
		final DataModel mockDataModel = context.mock(DataModel.class);
		final android.content.Context dummyAndroidContext = new android.content.Context();
		final BigDecimal tipPercent = new BigDecimal("2");
		final BigDecimal tipRate = new BigDecimal(".02");
		final BigDecimal roundUpToAmount = new BigDecimal("3");
		
		// Set up expectations
		final States defaultsRestored = context.states("restored").startsAs("false");
		context.checking(new Expectations() {{
		    oneOf (mockDefaultsPersister).restoreState(mockDefaults, dummyAndroidContext);
		    then(defaultsRestored.is("true"));
		    allowing (mockDefaults).getTaxPercent(); when(defaultsRestored.is("true"));
		    will(returnValue(EXPECTED_TAX_PERCENT));
		    allowing (mockDefaults).getTipPercent(); when(defaultsRestored.is("true"));
		    will(returnValue(tipPercent));
		    allowing (mockDefaults).getRoundUpToAmount(); when(defaultsRestored.is("true"));
		    will(returnValue(roundUpToAmount));
		    oneOf(mockDataModel).setBillTotal(initializer.getBillTotal());
		    oneOf(mockDataModel).setTaxRate(EXPECTED_TAX_RATE);
		    oneOf(mockDataModel).setDiscount(initializer.getDiscount());
		    oneOf(mockDataModel).setPlannedTipRate(tipRate);
		    oneOf(mockDataModel).setSplitBetween(initializer.getSplitBetween());
		    oneOf(mockDataModel).setRoundUpToAmount(roundUpToAmount);
		    oneOf(mockDataModel).setBumps(initializer.getBumps());
		}});

		// Call method under test
		initializer.initialize(mockDataModel, dummyAndroidContext);
	}
	
	//TODO add tests for persisted default get methods when they exist

	@Test
	public void testGetTaxRateWithPersistedValue() {
		// Set up expectations for mocks
		context.checking(new Expectations() {{
		    allowing (mockDefaults).getTaxPercent(); 
		    will(returnValue(EXPECTED_TAX_PERCENT));
		}});
		
		// Call method under test
		BigDecimal actualRate = initializer.getTaxRate();
		
		// Verify postconditions
		assertEquals("Incorrect tax rate", EXPECTED_TAX_RATE, actualRate);
	}
	
	//TODO add tests for persisted default get methods when they don't exist 
}