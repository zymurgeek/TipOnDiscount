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
package com.itllp.tipOnDiscount.model.persistence.impl.test;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersister;
import com.itllp.tipOnDiscount.model.persistence.impl.DataModelPersisterImpl;
import com.itllp.tipOnDiscount.persistence.Persister;

public class DataModelPersisterImplTests {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private BigDecimal expectedBillTotal = new BigDecimal("12.34");
	private BigDecimal expectedTaxRate = new BigDecimal("0.07000");
	private BigDecimal expectedTaxAmount = new BigDecimal("1.73");
	private BigDecimal expectedDiscount = new BigDecimal("2.61");
	private BigDecimal expectedPlannedTipRate = new BigDecimal("0.18000");
	private int expectedSplitBetween = 3;
	private BigDecimal expectedRoundUpToNearestAmount = new BigDecimal("0.25");
	private int expectedBumps = 2;
	private DataModel mockDataModel;
	private Persister mockPersister;
	private DataModelPersister dataModelPersister;
	private final android.content.Context mockAndroidContext = new android.content.Context();
	
	
	@Before
	public void initialize() {
		mockDataModel = context.mock(DataModel.class);
		mockPersister = context.mock(Persister.class);
		dataModelPersister = new DataModelPersisterImpl(mockPersister);
	}


	@Test
	public void testSaveStateWhenUsingTaxRate() {
		// Set up preconditions and postcondition expectations
		setDataModelExpectationsForRetrievalOfAllFieldsExceptForTax();
		setDataModelExpectationsForRetrievalOfTaxRate();
		final Sequence saveSequence = context.sequence("saveSequence");
		setPersisterExpectationsForBeginSave(saveSequence);
		setPersisterExpectationsForSaveOfAllDataModelFieldsExceptTax();
		setPersisterExpectationsForSaveOfDataModelTaxRate();
		setPersisterExpectationsForEndSave(saveSequence);

		// Call method under test
		dataModelPersister.saveState(mockDataModel, mockAndroidContext);
	}


	@Test
	public void testSaveStateWhenUsingTaxAmount() {
		// Set up preconditions and postcondition expectations
		setDataModelExpectationsForRetrievalOfAllFieldsExceptForTax();
		setDataModelExpectationsForRetrievalOfTaxAmount();
		final Sequence saveSequence = context.sequence("saveSequence");
		setPersisterExpectationsForBeginSave(saveSequence);
		setPersisterExpectationsForSaveOfAllDataModelFieldsExceptTax();
		setPersisterExpectationsForSaveOfDataModelTaxAmount();
		setPersisterExpectationsForEndSave(saveSequence);

		// Call method under test
		dataModelPersister.saveState(mockDataModel, mockAndroidContext);
	}


	@Test
	public void testRestoreWhenNoSavedDataIsAvailable() {
		// Set up preconditions and postcondition expectations
		setPersisterExpectationsToAlwaysRetrieveNull();
		// Implicitly disallow saving anything to the data model
		
		// Run method under test
		dataModelPersister.restoreState(mockDataModel, mockAndroidContext);
	}


	@Test
	public void testRestoreStateWhenUsingTaxRate() {
		// Set up preconditions and postcondition expectations
		setPersisterExpectationsForRetrievalOfAllFieldsExceptTax();
		setPersisterExpectationsForRetrievalOfTaxRate();
		setDataModelExpectationsForSetOfAllFieldsExceptTax();
		setDataModelExpectationsForSetTaxRate();
		
		// Run method under test
		dataModelPersister.restoreState(mockDataModel, mockAndroidContext);
	}


	@Test
	public void testRestoreStateWhenUsingTaxAmount() {
		// Set up preconditions and postcondition expectations
		setPersisterExpectationsForRetrievalOfAllFieldsExceptTax();
		setPersisterExpectationsForRetrievalOfTaxAmount();
		setDataModelExpectationsForSetOfAllFieldsExceptTax();
		setDataModelExpectationsForSetTaxAmount();

		// Run method under test
		dataModelPersister.restoreState(mockDataModel, mockAndroidContext);
	}

	
	private void setDataModelExpectationsForRetrievalOfAllFieldsExceptForTax() {
		context.checking(new Expectations() {{
			allowing (mockDataModel).getBillTotal(); 
			will(returnValue(expectedBillTotal));
			allowing (mockDataModel).getDiscount(); 
			will(returnValue(expectedDiscount));
			allowing (mockDataModel).getPlannedTipRate();
			will(returnValue(expectedPlannedTipRate));
			allowing (mockDataModel).getSplitBetween(); 
			will(returnValue(expectedSplitBetween));
			allowing (mockDataModel).getRoundUpToAmount();
			will(returnValue(expectedRoundUpToNearestAmount));
			allowing (mockDataModel).getBumps();
			will(returnValue(expectedBumps));
		}});
	}
	

	private void setDataModelExpectationsForRetrievalOfTaxRate() {
		context.checking(new Expectations() {{
			allowing(mockDataModel).isUsingTaxRate();
			will(returnValue(true));
			allowing (mockDataModel).getTaxRate();
			will(returnValue(expectedTaxRate));
		}});
	}


	private void setDataModelExpectationsForRetrievalOfTaxAmount() {
		context.checking(new Expectations() {{
			allowing(mockDataModel).isUsingTaxRate();
			will(returnValue(false));
			allowing (mockDataModel).getTaxAmount();
			will(returnValue(expectedTaxAmount));
		}});
	}

	
	private void setPersisterExpectationsForBeginSave(final Sequence saveSequence) {
		context.checking(new Expectations() {{
			oneOf (mockPersister).beginSave(mockAndroidContext);
			inSequence(saveSequence);
		}});
	}


	private void setPersisterExpectationsForSaveOfAllDataModelFieldsExceptTax() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(DataModel.BILL_TOTAL_KEY, 
						expectedBillTotal);
				oneOf (mockPersister).save(DataModel.DISCOUNT_KEY,
						expectedDiscount);
				oneOf (mockPersister).save(DataModel.PLANNED_TIP_RATE_KEY, 
						expectedPlannedTipRate);
				oneOf (mockPersister).save(DataModel.SPLIT_BETWEEN_KEY, 
						expectedSplitBetween);
				oneOf (mockPersister).save(DataModel.ROUND_UP_TO_NEAREST_AMOUNT,
						expectedRoundUpToNearestAmount);
				oneOf (mockPersister).save(DataModel.BUMPS_KEY, expectedBumps);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationsForSaveOfDataModelTaxRate() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(DataModel.TAX_RATE_KEY, 
						expectedTaxRate);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationsForSaveOfDataModelTaxAmount() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(DataModel.TAX_AMOUNT_KEY, 
						expectedTaxAmount);
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
			(mockAndroidContext, DataModel.BILL_TOTAL_KEY); 
			will(returnValue(null));			
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_AMOUNT_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_RATE_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_AMOUNT_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.DISCOUNT_KEY); 
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.PLANNED_TIP_RATE_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveInteger
			(mockAndroidContext, DataModel.SPLIT_BETWEEN_KEY); 
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
			will(returnValue(null));
			allowing (mockPersister).retrieveInteger
			(mockAndroidContext, DataModel.BUMPS_KEY);
			will(returnValue(null));
		}});
	}

	
	private void setPersisterExpectationsForRetrievalOfAllFieldsExceptTax() {
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.BILL_TOTAL_KEY); 
			will(returnValue(expectedBillTotal));			
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.DISCOUNT_KEY); 
			will(returnValue(expectedDiscount));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.PLANNED_TIP_RATE_KEY);
			will(returnValue(expectedPlannedTipRate));
			allowing (mockPersister).retrieveInteger
			(mockAndroidContext, DataModel.SPLIT_BETWEEN_KEY); 
			will(returnValue(expectedSplitBetween));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
			will(returnValue(expectedRoundUpToNearestAmount));
			allowing (mockPersister).retrieveInteger
			(mockAndroidContext, DataModel.BUMPS_KEY);
			will(returnValue(expectedBumps));
		}});
	}

	
	private void setPersisterExpectationsForRetrievalOfTaxRate() {
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_AMOUNT_KEY);
			will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_RATE_KEY);
			will(returnValue(expectedTaxRate));
		}});
	}


	private void setPersisterExpectationsForRetrievalOfTaxAmount() {
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_AMOUNT_KEY);
			will(returnValue(expectedTaxAmount));
			allowing (mockPersister).retrieveBigDecimal
			(mockAndroidContext, DataModel.TAX_RATE_KEY);
			will(returnValue(null));
		}});
	}


private void setDataModelExpectationsForSetOfAllFieldsExceptTax() {
		context.checking(new Expectations() {{
			oneOf (mockDataModel).setBillTotal(expectedBillTotal);
			oneOf (mockDataModel).setDiscount(expectedDiscount);
			oneOf (mockDataModel).setPlannedTipRate(expectedPlannedTipRate);
			oneOf (mockDataModel).setSplitBetween(expectedSplitBetween);
			oneOf (mockDataModel).setRoundUpToAmount
			(expectedRoundUpToNearestAmount);
			oneOf (mockDataModel).setBumps(expectedBumps);
		}});
	}


	private void setDataModelExpectationsForSetTaxRate() {
		context.checking(new Expectations() {{
			oneOf (mockDataModel).setTaxRate(expectedTaxRate);
		}});
	}


	private void setDataModelExpectationsForSetTaxAmount() {
		context.checking(new Expectations() {{
			oneOf (mockDataModel).setTaxAmount(expectedTaxAmount);
		}});
	}


}
