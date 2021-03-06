// Copyright 2011-2013 David A. Greenbaum
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

/**
 * This JUnit test harness verifies that the data model sends out
 * notifications to registered DataModelObservers when data is updated. 
 */
package com.itllp.tipOnDiscount.model.impl;

import java.math.BigDecimal;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.model.DataModelInitializer;
import com.itllp.tipOnDiscount.model.DataModelObserver;
import com.itllp.tipOnDiscount.model.impl.DataModelImpl;
import com.itllp.tipOnDiscount.model.update.ActualTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.ActualTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.BillSubtotalUpdate;
import com.itllp.tipOnDiscount.model.update.BillTotalUpdate;
import com.itllp.tipOnDiscount.model.update.BumpsUpdate;
import com.itllp.tipOnDiscount.model.update.DiscountUpdate;
import com.itllp.tipOnDiscount.model.update.RoundUpToNearestUpdate;
import com.itllp.tipOnDiscount.model.update.ShareDueUpdate;
import com.itllp.tipOnDiscount.model.update.SplitBetweenUpdate;
import com.itllp.tipOnDiscount.model.update.TaxAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TaxRateUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TippableAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TotalDueUpdate;
import com.itllp.tipOnDiscount.model.update.Update;

public class DataModelImplNotificationTests {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private DataModelImpl dataModel = null;
	private final BigDecimal taxRate = new BigDecimal(".10000");
	private final BigDecimal taxAmount = new BigDecimal("3.86");
	private final BigDecimal billTotal = new BigDecimal("42.42");
	private final BigDecimal plannedTipRate = new BigDecimal(".20000");
	private final BigDecimal zeroAmount = new BigDecimal("0.00");
	private final BigDecimal oneDollarAmount = new BigDecimal("1.00");
	private final BigDecimal oneCentAmount = new BigDecimal("0.01");
		
	private BillTotalUpdate expectedBillTotalUpdate;
	private BillSubtotalUpdate expectedBillSubtotalUpdate;
	private DiscountUpdate expectedDiscountUpdate;
	private TippableAmountUpdate expectedTippableAmountUpdate;
	private PlannedTipAmountUpdate expectedPlannedTipAmountUpdate;
	private ActualTipAmountUpdate expectedActualTipAmountUpdate;
	private ActualTipRateUpdate expectedActualTipRateUpdate;
	private SplitBetweenUpdate expectedSplitBetweenUpdate;
	private RoundUpToNearestUpdate expectedRoundUpToNearestUpdate;
	private TaxAmountUpdate	expectedTaxAmountUpdate;
	private TaxRateUpdate expectedTaxRateUpdate;
	private ShareDueUpdate expectedShareDueUpdate;
	private TotalDueUpdate expectedTotalDueUpdate;
	

	@Before
	public void initialize() {
		dataModel = new DataModelImpl();
		DataModelInitializer dataModelInitializer = new SimpleDataModelInitializer();
		dataModelInitializer.initialize(dataModel, null);
		dataModel.setPlannedTipRate(new BigDecimal(".09"));
	}

	
	private void setExpectedUpdateValuesFromDataModel() {
		expectedBillTotalUpdate 
			= new BillTotalUpdate(dataModel.getBillTotal());
		expectedBillSubtotalUpdate 
			= new BillSubtotalUpdate(dataModel.getBillSubtotal());
		expectedDiscountUpdate
			= new DiscountUpdate(dataModel.getDiscount());
		expectedTippableAmountUpdate
			= new TippableAmountUpdate(dataModel.getTippableAmount());
		expectedPlannedTipAmountUpdate 
			= new PlannedTipAmountUpdate(dataModel.getPlannedTipAmount());
		expectedSplitBetweenUpdate = new SplitBetweenUpdate
				(dataModel.getSplitBetween());
		expectedRoundUpToNearestUpdate = new RoundUpToNearestUpdate
				(dataModel.getRoundUpToAmount());
		expectedActualTipAmountUpdate
			= new ActualTipAmountUpdate(dataModel.getActualTipAmount());
		expectedActualTipRateUpdate
			= new ActualTipRateUpdate(dataModel.getActualTipRate());
		expectedTaxAmountUpdate 
			= new TaxAmountUpdate(dataModel.getTaxAmount());
		expectedTaxRateUpdate = new TaxRateUpdate(dataModel.getTaxRate());
		expectedShareDueUpdate = new ShareDueUpdate(dataModel.getShareDue());
		expectedTotalDueUpdate = new TotalDueUpdate(dataModel.getTotalDue());
	}


	/* When the bill total is changed when using tax amount 
	 * (not tax rate), an update notification should be sent out for 
	 * bill total, tax rate, bill subtotal, tippable amount, 
	 * planned tip amount, actual tip amount and share due.
	 * Note this test does not verify correct values in the data model.  That's
	 * done in the data model tests.
	 */
	@Test 
	public void testBillTotalChangeUsingTaxAmount() {
		// Preconditions
		// Set up data model to have expected values
		dataModel.setTaxAmount(taxAmount);
		dataModel.setPlannedTipRate(plannedTipRate);
		dataModel.setBillTotal(billTotal);
	
		setExpectedUpdateValuesFromDataModel();

		// Reset bill total to force change notifications later
		dataModel.setBillTotal(this.zeroAmount);

		final DataModelObserver observer = context.mock(DataModelObserver.class);
		dataModel.addObserver(observer);
				
		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedBillTotalUpdate);
		    oneOf (observer).update(dataModel, expectedTaxRateUpdate);
		    oneOf (observer).update(dataModel, expectedBillSubtotalUpdate);
		    oneOf (observer).update(dataModel, expectedTippableAmountUpdate);
		    oneOf (observer).update(dataModel, expectedPlannedTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    allowing (observer).update(dataModel, expectedActualTipRateUpdate); // rounding
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		}});
		
		// Method under test
		dataModel.setBillTotal(billTotal);
		dataModel.setBillTotal(billTotal);  // Test no-change
	}

	
	/* Test that when the bill total amount is changed when using tax rate,
	 * an update notification should be sent out for bill total, 
	 * tax amount, bill subtotal amount, tippable amount, planned tip amount, 
	 * actual tip amount and share amount.
	 * Test that if the bill total amount is set to the same value, no change
	 * notifications are sent.
	 * Note this test does not verify correct values in the data model.  That's
	 * done in the data model tests.
	 */
	@Test 
	public void testBillSubtotalChangeUsingTaxRate() {
		// Preconditions
		// Set up data model
		dataModel.setBillTotal(billTotal);
		dataModel.setTaxRate(taxRate);
		dataModel.setPlannedTipRate(plannedTipRate);
		
		setExpectedUpdateValuesFromDataModel();
		
		// Reset bill total to force notifications later
		dataModel.setBillTotal(this.zeroAmount);  

		final DataModelObserver observer = context.mock(DataModelObserver.class);
		dataModel.addObserver(observer);

		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedBillTotalUpdate);
		    oneOf (observer).update(dataModel, expectedTaxAmountUpdate);
		    oneOf (observer).update(dataModel, expectedBillSubtotalUpdate);
		    oneOf (observer).update(dataModel, expectedTippableAmountUpdate);
		    oneOf (observer).update(dataModel, expectedPlannedTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipRateUpdate);
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		}});
		
		// Method under test
		dataModel.setBillTotal(billTotal);
		dataModel.setBillTotal(billTotal); // Test no-change
	}

	
	/**
	 * Tests that when bump down is pressed, a notification is received
	 * that the bumps count was decremented.
	 */
	@Test 
	public void testBumpDown() {
		final DataModelObserver observer = context.mock(DataModelObserver.class);
		context.checking(new Expectations() {{
			oneOf (observer).update(with(any(DataModelImpl.class)), with(any(BumpsUpdate.class)));
		    allowing (observer).update(with(any(DataModelImpl.class)), with(any(Update.class)));
		}});
		dataModel.addObserver(observer);
		
		// Method under test
		dataModel.bumpDown();
	}
	
	
	/**
	 * Tests that when bump up is pressed, a notification is received
	 * that the bumps count was incremented.
	 */
	@Test 
	public void testBumpUp() {
		final DataModelObserver observer = context.mock(DataModelObserver.class);
		context.checking(new Expectations() {{
			oneOf (observer).update(with(any(DataModelImpl.class)), with(any(BumpsUpdate.class)));
		    allowing (observer).update(with(any(DataModelImpl.class)), with(any(Update.class)));
		}});
		dataModel.addObserver(observer);
		
		// Method under test
		dataModel.bumpUp();
	}

	
	/* When the discount is changed, an update notification 
	 * should be sent for discount, tippable amount, planned tip amount, 
	 * actual tip amount, share due and total due.
	 */
	@Test 
	public void testDiscountChange() {
		// Preconditions
		final BigDecimal discount = new BigDecimal("3.00");
		dataModel.setBillTotal(new BigDecimal("10.00"));
		dataModel.setDiscount(discount);
		
		setExpectedUpdateValuesFromDataModel();

		dataModel.setDiscount(new BigDecimal("0.00"));
		
		final DataModelObserver observer = context.mock(DataModelObserver.class);
		
		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedDiscountUpdate);
			oneOf (observer).update(dataModel, expectedTippableAmountUpdate);
		    oneOf (observer).update(dataModel, expectedPlannedTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		}});
		dataModel.addObserver(observer);
		
		// Method under test
		dataModel.setDiscount(discount);
		dataModel.setDiscount(discount); // test no-change
	}
	
	
	/* When "round up to nearest" is changed, an update notification 
	 * should be sent out for round up to nearest, actual tip percent,
	 * actual tip amount, total due and share due.
	 */
	@Test 
	public void testRoundUpToNearestChange() {
		// Set up preconditions
		dataModel.setBillTotal(new BigDecimal("3.00"));
		dataModel.setSplitBetween(2);
		dataModel.setRoundUpToAmount(oneDollarAmount);

		setExpectedUpdateValuesFromDataModel();
		
		dataModel.setRoundUpToAmount(oneCentAmount);  // reset so change kicks off notification

		final DataModelObserver observer = context.mock(DataModelObserver.class);

		// Verify postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedRoundUpToNearestUpdate);
			oneOf (observer).update(dataModel, expectedActualTipRateUpdate);
			oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		}});
		
		// Run method under test
		dataModel.addObserver(observer);
		dataModel.setRoundUpToAmount(oneDollarAmount);
		dataModel.setRoundUpToAmount(oneDollarAmount);  // test no-change
	}

	
	/* When split between is changed, an update notification 
	 * should be sent out for split between and share due.
	 */
	@Test 
	public void testSplitBetweenChange() {
		// Set up preconditions
		dataModel.setBillTotal(new BigDecimal("50.00"));
		dataModel.setSplitBetween(2);

		setExpectedUpdateValuesFromDataModel();
		
		dataModel.setSplitBetween(1);  // reset so change kicks off notification

		final DataModelObserver observer = context.mock(DataModelObserver.class);

		// Verify postconditions
		context.checking(new Expectations() {{
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedSplitBetweenUpdate);
		}});
		
		// Run method under test
		dataModel.addObserver(observer);
		dataModel.setSplitBetween(2);
		dataModel.setSplitBetween(2);  // test no-change
	}

	
	/* When the tax rate is changed, an update notification 
	 * should be sent out for bill subtotal, tax rate, tax amount,
	 * tippable amount, planned tip amount, actual tip amount, possibly actual
	 * tip rate (due to rounding), share due and total due.
	 */
	@Test 
	public void testTaxRateChange() {
		// Preconditions
		BigDecimal BILL_TOTAL = new BigDecimal("1.50");
		BigDecimal TAX_RATE = new BigDecimal(".03000");
		
		dataModel.setBillTotal(BILL_TOTAL);
		dataModel.setTaxRate(TAX_RATE);
		
		setExpectedUpdateValuesFromDataModel();
		
		dataModel.setTaxRate(new BigDecimal(".00000"));  // reset to trigger notification on change

		final DataModelObserver observer = context.mock(DataModelObserver.class);
		
		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedBillSubtotalUpdate);
			oneOf (observer).update(dataModel, expectedTaxRateUpdate);
		    oneOf (observer).update(dataModel, expectedTaxAmountUpdate);
		    oneOf (observer).update(dataModel, expectedTippableAmountUpdate);
		    oneOf (observer).update(dataModel, expectedPlannedTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    allowing (observer).update(dataModel, expectedActualTipRateUpdate); // rounding
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		}});
		
		// Method under test
		dataModel.addObserver(observer);
		dataModel.setTaxRate(TAX_RATE);
		dataModel.setTaxRate(TAX_RATE);  // test no-change
	}
	
	
	/* When the tax amount is changed, an update notification 
	 * should be sent out for tax rate, tax amount, bill subtotal,
	 * tippable amount, planned tip amount, actual tip amount,
	 * actual tip rate (possibly due to rounding), share due
	 * and total due..
	 */
	@Test 
	public void testTaxAmountChange() {
		// Preconditions
		BigDecimal BILL_TOTAL = new BigDecimal("60.00");
		BigDecimal TAX_AMOUNT = new BigDecimal(".09");
		BigDecimal TIP_RATE = new BigDecimal(".20000");
		
		dataModel.setBillTotal(BILL_TOTAL);
		dataModel.setTaxAmount(TAX_AMOUNT);
		dataModel.setPlannedTipRate(TIP_RATE);
		
		setExpectedUpdateValuesFromDataModel();
		
		dataModel.setTaxAmount(new BigDecimal("0.00"));  // reset to trigger notification on change

		final DataModelObserver observer = context.mock(DataModelObserver.class);

		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(dataModel, expectedTaxRateUpdate);
		    oneOf (observer).update(dataModel, expectedTaxAmountUpdate);
		    oneOf (observer).update(dataModel, expectedBillSubtotalUpdate);
		    oneOf (observer).update(dataModel, expectedTippableAmountUpdate);
		    oneOf (observer).update(dataModel, expectedPlannedTipAmountUpdate);
		    oneOf (observer).update(dataModel, expectedActualTipAmountUpdate);
		    allowing (observer).update(dataModel, expectedActualTipRateUpdate);
		    oneOf (observer).update(dataModel, expectedShareDueUpdate);
		    oneOf (observer).update(dataModel, expectedTotalDueUpdate);
		}});
		
		// Method under test
		dataModel.addObserver(observer);
		dataModel.setTaxAmount(TAX_AMOUNT);
		dataModel.setTaxAmount(TAX_AMOUNT);  // test no-change
	}
	
}