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
package com.itllp.tipOnDiscount.defaults.impl.test;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.defaults.persistence.impl.DefaultsPersisterImpl;
import com.itllp.tipOnDiscount.persistence.Persister;

public class DefaultsPersisterImplTests {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private BigDecimal expectedTaxPercent = new BigDecimal("6.25");
	private BigDecimal expectedPlannedTipPercent = new BigDecimal("17.2");
	private BigDecimal expectedRoundUpToNearestAmount = new BigDecimal("0.5");
	private Defaults mockDefaults;
	private Persister mockPersister;
	private DefaultsPersister defaultsPersister;
	private final android.content.Context mockAndroidContext = new android.content.Context();
	
	
	@Before
	public void initialize() {
		mockDefaults = context.mock(Defaults.class);
		mockPersister = context.mock(Persister.class);
		defaultsPersister = new DefaultsPersisterImpl(mockPersister);
	}


	@Test
	public void testSaveState() {
		// Set up preconditions and postcondition expectations
		setDefaultsExpectationsForRetrievalOfFields();
		final Sequence saveSequence = context.sequence("saveSequence");
		setPersisterExpectationsForBeginSave(saveSequence);
		setPersisterExpectationsForSaveOfAllDefaultsFields();
		setPersisterExpectationsForEndSave(saveSequence);

		// Call method under test
		defaultsPersister.saveState(mockDefaults, mockAndroidContext);
	}


	@Test
	public void testRestoreWhenNoSavedDataIsAvailable() {
		// Set up preconditions and postcondition expectations
		setPersisterExpectationsToAlwaysRetrieveNull();
		// Implicitly disallow saving anything to the data model
		
		// Run method under test
		defaultsPersister.restoreState(mockDefaults, mockAndroidContext);
	}


	@Test
	public void testRestoreState() {
		// Set up preconditions and postcondition expectations
		setPersisterExpectationsForRetrievalOfAllFields();
		setDefaultsExpectationsForSetOfAllFields();
		
		// Run method under test
		defaultsPersister.restoreState(mockDefaults, mockAndroidContext);
	}


	private void setDefaultsExpectationsForRetrievalOfFields() {
		context.checking(new Expectations() {{
			allowing (mockDefaults).getTaxPercent();
			will(returnValue(expectedTaxPercent));
			allowing (mockDefaults).getTipPercent();
			will(returnValue(expectedPlannedTipPercent));
			allowing (mockDefaults).getRoundUpToAmount();
			will(returnValue(expectedRoundUpToNearestAmount));
		}});
	}
	

	private void setPersisterExpectationsForBeginSave(final Sequence saveSequence) {
		context.checking(new Expectations() {{
			oneOf (mockPersister).beginSave(mockAndroidContext);
			inSequence(saveSequence);
		}});
	}


	private void setPersisterExpectationsForSaveOfAllDefaultsFields() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(Defaults.TAX_PERCENT_KEY, 
						expectedTaxPercent);
				oneOf (mockPersister).save(Defaults.TIP_PERCENT_KEY, 
						expectedPlannedTipPercent);
				oneOf (mockPersister).save(Defaults.ROUND_UP_TO_AMOUNT_KEY,
						expectedRoundUpToNearestAmount);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationsForEndSave(final Sequence saveSequence) {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).endSave();
				inSequence(saveSequence);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationsToAlwaysRetrieveNull() {
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.TAX_PERCENT_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.TIP_PERCENT_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.ROUND_UP_TO_AMOUNT_KEY);
			will(returnValue(null));
		}});
	}

	
	private void setPersisterExpectationsForRetrievalOfAllFields() {
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.TAX_PERCENT_KEY);
			will(returnValue(expectedTaxPercent));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.TIP_PERCENT_KEY);
			will(returnValue(expectedPlannedTipPercent));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, Defaults.ROUND_UP_TO_AMOUNT_KEY);
			will(returnValue(expectedRoundUpToNearestAmount));
		}});
	}

	
	private void setDefaultsExpectationsForSetOfAllFields() {
		context.checking(new Expectations() {{
			oneOf (mockDefaults).setTaxPercent(expectedTaxPercent);
			oneOf (mockDefaults).setTipPercent(expectedPlannedTipPercent);
			oneOf (mockDefaults).setRoundUpToAmount
			(expectedRoundUpToNearestAmount);
		}});
	}


}
