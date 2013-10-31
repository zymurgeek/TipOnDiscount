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
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.model.DataModel;


public class DataModelInitializerFromPersistedDefaultsTests {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private DataModelInitializerFromPersistedDefaults initializer = null;
	private DefaultsPersister mockDefaultsPersister;
	private Defaults mockDefaults;
	
	@Before
	public void setUp() {
		mockDefaultsPersister = context.mock(DefaultsPersister.class);
		mockDefaults = context.mock(Defaults.class);
		initializer = new DataModelInitializerFromPersistedDefaults(
				mockDefaultsPersister, mockDefaults);
	}


	@Test
	public void testInitializeWithPersistedDefaults() {
		// Set up preconditions
		final DataModel mockDataModel = context.mock(DataModel.class);
		final android.content.Context dummyAndroidContext = new android.content.Context();
		final BigDecimal billTotal = DataModelInitializerFromPersistedDefaults
				.DEFAULT_BILL_TOTAL;
		final BigDecimal taxPercent = new BigDecimal("1");
		final BigDecimal taxRate = new BigDecimal(".01");
		final BigDecimal discount = initializer.getDiscount();
		final BigDecimal tipPercent = new BigDecimal("2");
		final BigDecimal tipRate = new BigDecimal(".02");
		final int splitBetween = DataModelInitializerFromPersistedDefaults
				.DEFAULT_SPLIT_BETWEEN;
		final BigDecimal roundUpToAmount = new BigDecimal("3");
		final int bumps = DataModelInitializerFromPersistedDefaults
				.DEFAULT_BUMPS;
		
		// Set up expectations
		final States defaultsRestored = context.states("restored").startsAs("false");
		context.checking(new Expectations() {{
		    oneOf (mockDefaultsPersister).restoreState(mockDefaults, dummyAndroidContext);
		    then(defaultsRestored.is("true"));
		    allowing (mockDefaults).getTaxPercent(); when(defaultsRestored.is("true"));
		    will(returnValue(taxPercent));
		    allowing (mockDefaults).getTipPercent(); when(defaultsRestored.is("true"));
		    will(returnValue(tipPercent));
		    allowing (mockDefaults).getRoundUpToAmount(); when(defaultsRestored.is("true"));
		    will(returnValue(roundUpToAmount));
		    oneOf(mockDataModel).setBillTotal(billTotal);
		    oneOf(mockDataModel).setTaxRate(taxRate);
		    oneOf(mockDataModel).setDiscount(discount);
		    oneOf(mockDataModel).setPlannedTipRate(tipRate);
		    oneOf(mockDataModel).setSplitBetween(splitBetween);
		    oneOf(mockDataModel).setRoundUpToAmount(roundUpToAmount);
		    oneOf(mockDataModel).setBumps(bumps);
		}});

		// Call method under test
		initializer.initialize(mockDataModel, dummyAndroidContext);
	}
}