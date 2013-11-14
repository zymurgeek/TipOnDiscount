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
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;


public class DataModelInitializerFromPersistedDefaultsTests {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private DefaultsPersister mockDefaultsPersister;
	@Mock private Defaults mockDefaults;
	private DataModelInitializerFromPersistedDefaults initializer;
	private static final BigDecimal EXPECTED_TAX_PERCENT = new BigDecimal("1");
	private static final BigDecimal EXPECTED_TAX_RATE = new BigDecimal(".01");
	private static final BigDecimal EXPECTED_TIP_PERCENT = new BigDecimal("2");
	private static final BigDecimal EXPECTED_TIP_RATE = new BigDecimal(".02");
	private static final BigDecimal EXPECTED_ROUND_UP_TO_AMOUNT = new BigDecimal("3");
	private android.content.Context dummyAndroidContext = new android.content
			.Context();
	private Sequence callSequence;
	private SimpleDataModelInitializer parentInitializer;
	
	
	@Before
	public void setUp() {
		initializer = new DataModelInitializerFromPersistedDefaults(
				mockDefaultsPersister, mockDefaults);
		parentInitializer = new SimpleDataModelInitializer();
		callSequence = context.sequence("callSequence");
	}
	
	
	@Test
	public void testGetTaxRateWithPersistedValue() {
		// Set up expectations for mocks
		setTaxRateExpectations(EXPECTED_TAX_PERCENT);
		
		// Call method under test
		BigDecimal actualRate = initializer.getTaxRate(dummyAndroidContext);
		
		// Verify postconditions
		assertEquals("Incorrect tax rate", EXPECTED_TAX_RATE, actualRate);
	}


	@Test
	public void testGetTaxRateWithoutPersistedValue() {
		// Set up expectations for mocks
		setTaxRateExpectations(null);
		BigDecimal expectedRate = parentInitializer.getTaxRate
				(dummyAndroidContext);
		
		// Call method under test
		BigDecimal actualRate = initializer.getTaxRate(dummyAndroidContext);
		
		// Verify postconditions
		assertEquals("Incorrect tax rate", expectedRate, actualRate);
	}
	
	
	private void setTaxRateExpectations(final BigDecimal expectedTaxPercent) {
		context.checking(new Expectations() {{
		    oneOf (mockDefaultsPersister).restoreState(mockDefaults, 
		    		dummyAndroidContext);
		    inSequence(callSequence);
		    allowing (mockDefaults).getTaxPercent();
			will(returnValue(expectedTaxPercent));
		    inSequence(callSequence);
		}});
	}
	
	
	@Test
	public void testGetTipRateWithPersistedValue() {
		// Set up expectations for mocks
		setTipRateExpectations(EXPECTED_TIP_PERCENT);
		
		// Call method under test
		BigDecimal actualRate = initializer.getTipRate(dummyAndroidContext);
		
		// Verify postconditions
		assertEquals("Incorrect tip rate", EXPECTED_TIP_RATE, actualRate);
	}


	@Test
	public void testGetTipRateWithoutPersistedValue() {
		// Set up expectations for mocks
		setTipRateExpectations(null);
		BigDecimal expectedRate = parentInitializer.getTipRate
				(dummyAndroidContext);
		
		// Call method under test
		BigDecimal actualRate = initializer.getTipRate(dummyAndroidContext);

		// Verify postconditions
		assertEquals("Incorrect tip rate", expectedRate, actualRate);
	}
	
	
	private void setTipRateExpectations(final BigDecimal expectedTipPercent) {
		context.checking(new Expectations() {{
		    oneOf (mockDefaultsPersister).restoreState(mockDefaults, 
		    		dummyAndroidContext);
		    inSequence(callSequence);
		    allowing (mockDefaults).getTipPercent();
			will(returnValue(expectedTipPercent));
		    inSequence(callSequence);
		}});
	}
	
	
	@Test
	public void testRoundUpToWithPersistedValue() {
		// Set up expectations for mocks
		setRoundUpToAmountExpectations(EXPECTED_ROUND_UP_TO_AMOUNT);
		
		// Call method under test
		BigDecimal actualAmount = initializer
				.getRoundUpToAmount(dummyAndroidContext);
		
		// Verify postconditions
		assertEquals("Incorrect round up to amount", 
				EXPECTED_ROUND_UP_TO_AMOUNT, actualAmount);
	}


	@Test
	public void testGetRoundUpToWithoutPersistedValue() {
		// Set up expectations for mocks
		setRoundUpToAmountExpectations(null);
		BigDecimal expectedAmount = parentInitializer.getRoundUpToAmount
				(dummyAndroidContext);
		
		// Call method under test
		BigDecimal actualAmount = initializer.getRoundUpToAmount
				(dummyAndroidContext);

		// Verify postconditions
		assertEquals("Incorrect round up to amount", expectedAmount, 
				actualAmount);
	}
	
	
	private void setRoundUpToAmountExpectations(final BigDecimal 
			expectedRoundUpToAmount) {
		context.checking(new Expectations() {{
		    oneOf (mockDefaultsPersister).restoreState(mockDefaults, 
		    		dummyAndroidContext);
		    inSequence(callSequence);
		    allowing (mockDefaults).getRoundUpToAmount();
			will(returnValue(expectedRoundUpToAmount));
		    inSequence(callSequence);
		}});
	}
	
	
}