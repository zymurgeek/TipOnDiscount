package com.itllp.tipOnDiscount.model.persistence.test;

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
import com.itllp.tipOnDiscount.persistence.Persister;

public class DataModelPersisterTests {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private BigDecimal expectedBillTotal = new BigDecimal("10.00");
	private BigDecimal expectedTaxRate = new BigDecimal("0.15000");
	private BigDecimal expectedTaxAmount = new BigDecimal(".71");
	private BigDecimal expectedDiscount = new BigDecimal("5.61");
	private BigDecimal expectedPlannedTipRate = new BigDecimal("0.18000");
	private int expectedSplitBetween = 3;
	private BigDecimal expectedRoundUpToNearestAmount = new BigDecimal("0.10");
	private int expectedBumps = 2;
	private DataModel mockDataModel;
	private Persister mockPersister;
	private DataModelPersister dataModelPersister;
	private final android.content.Context mockAndroidContext = new android.content.Context();
	
	@Before
	public void initialize() {
		mockDataModel = context.mock(DataModel.class);
		mockPersister = context.mock(Persister.class);
		dataModelPersister = new DataModelPersister();
	}


	@Test
	public void testSaveStateWhenUsingTaxRate() {
		// Set up preconditions and postcondition expectations
		setAllDataModelExpectationsExceptForTax();
		setDataModelExpectationsForTaxRate();
		final Sequence saveSequence = context.sequence("saveSequence");
		setPersisterExpectationForBeginSave(saveSequence);
		setPersisterExpectationsForAllDataModelFieldsExceptTax();
		setPersisterExpectationsForDataModelTaxRate();
		setPersisterExpectationForEndSave(saveSequence);

		// Call method under test
		dataModelPersister.saveState(mockDataModel, mockPersister, mockAndroidContext);
	}


	@Test
	public void testSaveStateWhenUsingTaxAmount() {
		// Set up preconditions and postcondition expectations
		setAllDataModelExpectationsExceptForTax();
		setDataModelExpectationsForTaxAmount();
		final Sequence saveSequence = context.sequence("saveSequence");
		setPersisterExpectationForBeginSave(saveSequence);
		setPersisterExpectationsForAllDataModelFieldsExceptTax();
		setPersisterExpectationsForDataModelTaxAmount();
		setPersisterExpectationForEndSave(saveSequence);

		// Call method under test
		dataModelPersister.saveState(mockDataModel, mockPersister, mockAndroidContext);
	}


	//FIXME Update test
	@Test
	public void testRestoreWhenNoSavedDataIsAvailable() {
		// Set up preconditions
		/*
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.BILL_TOTAL_KEY); 
				will(returnValue(null));			
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.TAX_AMOUNT_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal(DataModel.TAX_RATE_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.TAX_AMOUNT_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal(DataModel.DISCOUNT_KEY); 
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.PLANNED_TIP_RATE_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveInteger(DataModel.SPLIT_BETWEEN_KEY); 
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
				will(returnValue(null));
			allowing (mockPersister).retrieveInteger(DataModel.BUMPS_KEY);
				will(returnValue(null));
		}});
		*/
		// Run method under test
//		model.restoreState();
		
		// Verify postconditions
//		verifyDataModelContainsInitialValues();
	}

	
	//FIXME update test
	@Test
	public void testRestoreStateWhenUsingTaxRate() {
		// Set up preconditions
		/*
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.BILL_TOTAL_KEY); 
				will(returnValue(expectedBillTotal));			
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.TAX_AMOUNT_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal(DataModel.TAX_RATE_KEY);
				will(returnValue(expectedTaxRate));
			allowing (mockPersister).retrieveBigDecimal(DataModel.DISCOUNT_KEY); 
				will(returnValue(expectedDiscount));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.PLANNED_TIP_RATE_KEY);
				will(returnValue(expectedTipRate));
			allowing (mockPersister).retrieveInteger(DataModel.SPLIT_BETWEEN_KEY); 
				will(returnValue(expectedSplits));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
				will(returnValue(expectedRoundTo));
			allowing (mockPersister).retrieveInteger(DataModel.BUMPS_KEY);
				will(returnValue(expectedBumps));
		}});
		*/
		// Run method under test
//		model.restoreState();
		
		// Verify postconditions
//		assertEquals("Incorrect bill total", expectedBillTotal, model.getBillTotal());
//		assertTrue("Incorrect using tax rate", model.isUsingTaxRate());
//		assertEquals("Incorrect tax rate", expectedTaxRate, model.getTaxRate());
//		assertEquals("Incorrect discount", expectedDiscount, model.getDiscount());
//		assertEquals("Incorrect tip rate", expectedTipRate, model.getPlannedTipRate());
//		assertEquals("Incorrect split between", expectedSplits, model.getSplitBetween());
//		assertEquals("Incorrect round up to", expectedRoundTo, model.getRoundUpToAmount());
//		assertEquals("Incorrect bumps", expectedBumps, model.getBumps());
	}
	
	//FIXME update test
	@Test
	public void testRestoreStateWhenUsingTaxAmount() {
		// Set up preconditions
		/*
		context.checking(new Expectations() {{
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.BILL_TOTAL_KEY); 
				will(returnValue(expectedBillTotal));			
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.TAX_AMOUNT_KEY);
				will(returnValue(expectedTaxAmount));
			allowing (mockPersister).retrieveBigDecimal(DataModel.TAX_RATE_KEY);
				will(returnValue(null));
			allowing (mockPersister).retrieveBigDecimal(DataModel.DISCOUNT_KEY); 
				will(returnValue(expectedDiscount));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.PLANNED_TIP_RATE_KEY);
				will(returnValue(expectedTipRate));
			allowing (mockPersister).retrieveInteger(DataModel.SPLIT_BETWEEN_KEY); 
				will(returnValue(expectedSplits));
			allowing (mockPersister).retrieveBigDecimal
			(DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
				will(returnValue(expectedRoundTo));
			allowing (mockPersister).retrieveInteger(DataModel.BUMPS_KEY);
				will(returnValue(expectedBumps));
		}});
		*/
		// Run method under test
//		model.restoreState();
		
		// Verify postconditions
//		assertEquals("Incorrect bill total", expectedBillTotal, model.getBillTotal());
//		assertFalse("Incorrect using tax rate", model.isUsingTaxRate());
//		assertEquals("Incorrect tax amount", expectedTaxAmount, model.getTaxAmount());
//		assertEquals("Incorrect discount", expectedDiscount, model.getDiscount());
//		assertEquals("Incorrect tip rate", expectedTipRate, model.getPlannedTipRate());
//		assertEquals("Incorrect split between", expectedSplits, model.getSplitBetween());
//		assertEquals("Incorrect round up to", expectedRoundTo, model.getRoundUpToAmount());
//		assertEquals("Incorrect bumps", expectedBumps, model.getBumps());
	}

	
	private void setAllDataModelExpectationsExceptForTax() {
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
	

	private void setDataModelExpectationsForTaxRate() {
		context.checking(new Expectations() {{
			allowing(mockDataModel).isUsingTaxRate();
			will(returnValue(true));
			allowing (mockDataModel).getTaxRate();
			will(returnValue(expectedTaxRate));
		}});
	}


	private void setDataModelExpectationsForTaxAmount() {
		context.checking(new Expectations() {{
			allowing(mockDataModel).isUsingTaxRate();
			will(returnValue(false));
			allowing (mockDataModel).getTaxAmount();
			will(returnValue(expectedTaxAmount));
		}});
	}

	
	private void setPersisterExpectationForBeginSave(final Sequence saveSequence) {
		context.checking(new Expectations() {{
			oneOf (mockPersister).beginSave(mockAndroidContext);
			inSequence(saveSequence);
		}});
	}


	private void setPersisterExpectationsForAllDataModelFieldsExceptTax() {
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


	private void setPersisterExpectationsForDataModelTaxRate() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(DataModel.TAX_RATE_KEY, 
						expectedTaxRate);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationsForDataModelTaxAmount() {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).save(DataModel.TAX_AMOUNT_KEY, 
						expectedTaxAmount);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


	private void setPersisterExpectationForEndSave(final Sequence saveSequence) {
		context.checking(new Expectations() {{
			try {
				oneOf (mockPersister).endSave();
				inSequence(saveSequence);
			} catch (Exception e) {
				fail("Persister save threw exception");
			}
		}});
	}


}
