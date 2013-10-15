// Copyright 2011-2012 David A. Greenbaum
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
 * This JUnit test harness verifies that the data model correctly
 * initializes, stores and calculates values.
 */
package com.itllp.tipOnDiscount.modelimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.Persister;
import com.itllp.tipOnDiscount.modelimpl.DataModelImpl;

public class DataModelImplTests {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private DataModel model = null;
	private Persister mockPersister;
	private BigDecimal AMOUNT_5_61 = new BigDecimal("5.61");
	private BigDecimal AMOUNT_5_62 = new BigDecimal("5.62");
	private BigDecimal AMOUNT_5_25 = new BigDecimal("5.25");
	private BigDecimal AMOUNT_10_00 = new BigDecimal("10.00");
	private BigDecimal AMOUNT_17_37 = new BigDecimal("17.37");
	private BigDecimal SEVEN_PERCENT_RATE = new BigDecimal("0.07000");
	private BigDecimal ONE_HUNDRED_PERCENT_RATE = new BigDecimal("1.00000");
	private BigDecimal TAX_AMOUNT = AMOUNT_5_61
		.subtract(AMOUNT_5_61
		.divide(SEVEN_PERCENT_RATE.add(new BigDecimal("1.00")), 
				RoundingMode.HALF_UP));  // .37
	private BigDecimal BILL_SUBTOTAL = AMOUNT_5_61
		.subtract(TAX_AMOUNT);  // 5.24
	private BigDecimal DISCOUNT = BILL_SUBTOTAL
		.multiply(new BigDecimal(".20"))
		.setScale(2, RoundingMode.HALF_UP);  // 1.05
	private BigDecimal TIPPABLE_AMOUNT = AMOUNT_5_61.subtract(TAX_AMOUNT) 
		.add(DISCOUNT);
	private BigDecimal FIFTEEN_PERCENT_RATE = new BigDecimal("0.15000");
	private BigDecimal EIGHTEEN_PERCENT_RATE = new BigDecimal("0.18000");
	private BigDecimal TWENTY_PERCENT_RATE = new BigDecimal("0.20000");
	private BigDecimal TIP_AMOUNT = TIPPABLE_AMOUNT.multiply(EIGHTEEN_PERCENT_RATE)
		.setScale(2, RoundingMode.HALF_UP);
	private BigDecimal ROUND_UP_TO_NONE = new BigDecimal("0.01");
	private BigDecimal ROUND_UP_TO_NICKEL = new BigDecimal("0.05");
	private BigDecimal ROUND_UP_TO_DIME = new BigDecimal("0.10");
	private BigDecimal ROUND_UP_TO_QUARTER = new BigDecimal("0.25");
	private BigDecimal ROUND_UP_TO_HALF_DOLLAR = new BigDecimal("0.50");
	private BigDecimal ROUND_UP_TO_ONE_DOLLAR = new BigDecimal("1.00");
	private BigDecimal ROUND_UP_TO_TWO_DOLLARS = new BigDecimal("2.00");
	private BigDecimal ROUND_UP_TO_FIVE_DOLLARS = new BigDecimal("5.00");
	private BigDecimal ROUND_UP_TO_TEN_DOLLARS = new BigDecimal("10.00");
	private BigDecimal ROUND_UP_TO_TWENTY_DOLLARS = new BigDecimal("20.00");
	private BigDecimal ONE_CENT = new BigDecimal("0.01");
	private BigDecimal ZERO_RATE = BigDecimal.ZERO.setScale(5);
	private BigDecimal ZERO_AMOUNT = BigDecimal.ZERO.setScale(2);
	
	@Before
	public void initialize() {
		mockPersister = context.mock(Persister.class);
		model = new DataModelImpl(mockPersister);
	}

	/* Test the values fields have at start up. */
	@Test 
	public void testInitialization() {
		assertEquals("Incorrect initial bill total amount", ZERO_AMOUNT,
				model.getBillTotal());
		assertEquals("Incorrect initial bill subtotal amount", ZERO_AMOUNT, 
				model.getBillSubtotal());
		assertEquals("Incorrect initial discount", ZERO_AMOUNT, 
				model.getDiscount());
		assertEquals("Incorrect initial tippable amount", ZERO_AMOUNT,
				model.getTippableAmount());
		assertEquals("Incorrect initial tax amount", ZERO_AMOUNT, 
				model.getTaxAmount());
		assertEquals("Incorrect initial tax rate", ZERO_RATE, 
				model.getTaxRate());
		assertTrue("Initially should be using tax rate", 
				model.isUsingTaxRate());
		assertEquals("Incorrect initial planned tip rate", FIFTEEN_PERCENT_RATE, 
				model.getPlannedTipRate());
		assertEquals("Incorrect initial planned tip amount", ZERO_AMOUNT, 
				model.getPlannedTipAmount());
		assertEquals("Incorrect initial split between", 1,
				model.getSplitBetween());
		assertEquals("Incorrect initial round up to amount", ONE_CENT, 
				model.getRoundUpToAmount());
		assertEquals("Incorrect initial number of bumps", 0, model.getBumps());
		assertEquals("Incorrect initial actual tip rate", ZERO_RATE,
				model.getActualTipRate());
		assertEquals("Incorrect initial actual tip amount", ZERO_AMOUNT,
				model.getActualTipAmount());
		assertEquals("Incorrect initial total due", ZERO_AMOUNT,
				model.getTotalDue());
		assertEquals("Incorrect initial share due", ZERO_AMOUNT,
				model.getShareDue());
	}


	/* Test the values fields have after the initialize function is called. */
	@Test 
	public void testInitializeFunction() {
		model.setBillTotal(AMOUNT_10_00);
		model.setDiscount(AMOUNT_5_61);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		model.setRoundUpToAmount(ROUND_UP_TO_DIME);
		model.setSplitBetween(3);
		model.setTaxAmount(TAX_AMOUNT);
		model.setTaxRate(FIFTEEN_PERCENT_RATE);
		model.setBumps(2);
		model.initialize();
		assertEquals("Incorrect initial bill total amount", ZERO_AMOUNT,
				model.getBillTotal());
		assertEquals("Incorrect initial bill subtotal amount", ZERO_AMOUNT, 
				model.getBillSubtotal());
		assertEquals("Incorrect initial discount", ZERO_AMOUNT, 
				model.getDiscount());
		assertEquals("Incorrect tippable amount", ZERO_AMOUNT,
				model.getTippableAmount());
		assertEquals("Incorrect tax amount", ZERO_AMOUNT, 
				model.getTaxAmount());
		assertEquals("Incorrect tax rate", ZERO_RATE, 
				model.getTaxRate());
		assertTrue("Should be using tax rate", model.isUsingTaxRate());
		assertEquals("Incorrect tip rate", FIFTEEN_PERCENT_RATE, 
				model.getPlannedTipRate());
		assertEquals("Incorrect tip amount", ZERO_AMOUNT, 
				model.getPlannedTipAmount());
		assertEquals("Incorrect initial split between", 1,
				model.getSplitBetween());
		assertEquals("Incorrect round up to amount", ONE_CENT, 
				model.getRoundUpToAmount());
		assertEquals("Incorrect number of bumps", 0, model.getBumps());
		assertEquals("Incorrect actual tip rate", ZERO_RATE,
				model.getActualTipRate());
		assertEquals("Incorrect actual tip amount", ZERO_AMOUNT,
				model.getActualTipAmount());
		assertEquals("Incorrect total due", ZERO_AMOUNT,
				model.getTotalDue());
		assertEquals("Incorrect share due", ZERO_AMOUNT,
				model.getShareDue());
	}

	
	@Test
	public void testSaveStateWhenUsingTaxRate() {
		// Set up preconditions
		final int EXPECTED_SPLITS = 3;
		final int EXPECTED_BUMPS = 2;
		model.setBillTotal(AMOUNT_10_00);
		model.setDiscount(AMOUNT_5_61);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		model.setRoundUpToAmount(ROUND_UP_TO_DIME);
		model.setSplitBetween(EXPECTED_SPLITS);
		//model.setTaxAmount(TAX_AMOUNT);
		model.setTaxRate(FIFTEEN_PERCENT_RATE);
		model.setBumps(EXPECTED_BUMPS);
		
		// Verify postconditions
		context.checking(new Expectations() {{
			oneOf (mockPersister).save(Persister.BILL_TOTAL_KEY, AMOUNT_10_00);			
			oneOf (mockPersister).save(Persister.TAX_RATE_KEY,
					FIFTEEN_PERCENT_RATE);
			oneOf (mockPersister).save(Persister.DISCOUNT_KEY, AMOUNT_5_61);
			oneOf (mockPersister).save(Persister.PLANNED_TIP_RATE_KEY,
					EIGHTEEN_PERCENT_RATE);
			oneOf (mockPersister).save(Persister.SPLIT_BETWEEN_KEY, EXPECTED_SPLITS);
			oneOf (mockPersister).save(Persister.ROUND_UP_TO_NEAREST_AMOUNT,
					ROUND_UP_TO_DIME);
			oneOf (mockPersister).save(Persister.BUMPS_KEY, EXPECTED_BUMPS);
		}});
		
		// Run method under test
		model.saveState();
	}

	
	// TODO test saveState when using Tax amount
	
	
	// TODO test restore tax rate/amount depending on what was saved
	
	
	private BigDecimal calculateBillSubtotal
	(BigDecimal billTotal, BigDecimal taxRate) {
		BigDecimal taxRatio = taxRate.add(new BigDecimal("1"));
		return billTotal.divide(taxRatio, RoundingMode.HALF_UP);
	}

	
	/* Test that the actual tip amount is correctly calculated. 
	 * Actual Tip Amount = Total Due - Bill Total 
	 */
	@Test
	public void testActualTipAmount1() {
		model.setBillTotal(AMOUNT_5_62);
		model.setTaxRate(SEVEN_PERCENT_RATE);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		model.setDiscount(DISCOUNT); // 1.05
		model.setSplitBetween(3);
		model.setRoundUpToAmount(ONE_CENT);
		model.setBumps(0);
		BigDecimal billSubtotal = AMOUNT_5_25;
		BigDecimal expectedActualTip = billSubtotal.add(DISCOUNT)
			.multiply(EIGHTEEN_PERCENT_RATE).setScale(2, RoundingMode.HALF_UP); // 1.13
		assertEquals("Incorrect actual tip amount", expectedActualTip, 
				model.getActualTipAmount());
	}
	
	
	/** Sets up the data model for an Actual Tip Amount test.
	 * 
	 * @return The expected actual tip amount given this set up.
	 */
	private BigDecimal setUpActualTipAmountTest() {
		model.setBillTotal(AMOUNT_10_00);
		model.setTaxRate(ZERO_RATE);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		BigDecimal expectedActualTipAmount 
			= EIGHTEEN_PERCENT_RATE.multiply(model.getBillTotal());
		expectedActualTipAmount 
			= expectedActualTipAmount.setScale(2, RoundingMode.HALF_UP);
		return expectedActualTipAmount;
	}
	
	
	/* Test that the actual tip amount is correctly calculated. 
	 * Actual Tip Amount = Total Due - Bill Total 
	 */
	@Test
	public void testActualTipAmount2() {
		BigDecimal expectedActualTipAmount = setUpActualTipAmountTest();
		assertEquals("Incorrect actual tip amount", expectedActualTipAmount, 
				model.getActualTipAmount());
	}
	
	
	/* Test that the actual tip amount is correctly calculated when Total
	 * Due changes.  
	 * 
	 * To update Total Due without changing the Bill Total,
	 * Tip Rate is changed.  
	 */
	@Test
	public void testActualTipAmount_TotalDue() {
		BigDecimal doubler = new BigDecimal(2);
		BigDecimal expectedActualTipAmount = setUpActualTipAmountTest();
		BigDecimal tipRate = model.getPlannedTipRate();
		tipRate.multiply(doubler);
		model.setPlannedTipRate(tipRate);
		expectedActualTipAmount.multiply(doubler);
		assertEquals("Incorrect actual tip amount", expectedActualTipAmount, 
				model.getActualTipAmount());
	}
	
	
	/* Test that the actual tip amount is correctly calculated when Bill Total
	 * changes. 
	 */
	@Test
	public void testActualTipAmount_BillTotal() {
		BigDecimal doubler = new BigDecimal(2);
		BigDecimal expectedActualTipAmount = setUpActualTipAmountTest();
		BigDecimal billTotal = model.getBillTotal();
		billTotal.multiply(doubler);
		model.setBillTotal(billTotal);
		expectedActualTipAmount.multiply(doubler);
		assertEquals("Incorrect actual tip amount", expectedActualTipAmount, 
				model.getActualTipAmount());
	}
	
	
	/* Test that the actual tip rate is correctly calculated when 
	 * Tip Rate changes. 
	 */
	@Test
	public void testActualTipRate_TipRate() {
		model.setBillTotal(AMOUNT_10_00);
		model.setTaxRate(ZERO_RATE);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		
		BigDecimal expectedActualTipRate = FIFTEEN_PERCENT_RATE;
		model.setPlannedTipRate(FIFTEEN_PERCENT_RATE);
		assertEquals("Incorrect actual tip rate", expectedActualTipRate, 
				model.getActualTipRate());
	}
	
	
	/* Test that the actual tip rate is correctly calculated when 
	 * Bumps changes. 
	 */
	@Test
	public void testActualTipRate_Bumps() {
		model.setBillTotal(AMOUNT_10_00);
		model.setTaxRate(ZERO_RATE);
		model.setPlannedTipRate(FIFTEEN_PERCENT_RATE);
		model.setRoundUpToAmount(this.ROUND_UP_TO_ONE_DOLLAR);
		model.setBumps(0);
		BigDecimal expectedActualTipRate = TWENTY_PERCENT_RATE;
		
		assertEquals("Incorrect actual tip rate", expectedActualTipRate, 
				model.getActualTipRate());
	}
		
	
	/* Test that the bill subtotal amount is correctly calculated. */
	@Test 
	public void testBillSubtotal() {
		model.setBillTotal(AMOUNT_5_61);
		model.setTaxRate(SEVEN_PERCENT_RATE);
		assertEquals("Incorrect bill subtotal amount", BILL_SUBTOTAL, 
				model.getBillSubtotal());
	}
	
	
	/* Test that the bill subtotal and tax amount is correctly calculated 
	 * given the bill total and tax rate. */
	@Test 
	public void testBillSubtotalAndTaxAmount() {
		model.setBillTotal(new BigDecimal("210.39"));
		model.setTaxRate(new BigDecimal("0.07"));  // 7%
		assertEquals("Incorrect bill subtotal amount", new BigDecimal("196.63"),
				model.getBillSubtotal());
		assertEquals("Incorrect tax amount", new BigDecimal("13.76"),
				model.getTaxAmount());
	}
	
	
	/* Test that the bill total amount is correctly stored. */
	@Test 
	public void testBillTotal() {
		model.setBillTotal(AMOUNT_5_61);
		assertEquals("Incorrect bill total amount", AMOUNT_5_61, 
				model.getBillTotal());
	}

	
	/* When the bill total amount is changed when using tax amount 
	 * (not tax rate), bill total amount, tax rate, bill subtotal amount, 
	 * tippable amount, planned tip amount, actual tip amount and share due
	 * should be updated.
	 */
	@Test 
	public void testBillTotalChangeUsingTaxAmount() {
		// Preconditions
		final BigDecimal taxAmount = new BigDecimal("1.00");
		final BigDecimal plannedTipRate = new BigDecimal(".20000");
		final BigDecimal billSubtotal = new BigDecimal("42.00");
		final BigDecimal tippableAmount = billSubtotal;
		final BigDecimal billTotal = billSubtotal.add(taxAmount);
		final BigDecimal plannedTipAmount = tippableAmount.multiply
			(plannedTipRate).setScale(2, RoundingMode.HALF_UP);
		final BigDecimal actualTipAmount = plannedTipAmount;
		final BigDecimal taxRate = taxAmount.divide(billSubtotal, 
				5, RoundingMode.HALF_UP);
		final BigDecimal shareDue = billSubtotal.add( 
				actualTipAmount).add(taxAmount);
		final BigDecimal totalDue = shareDue;
		model.setTaxAmount(taxAmount);
		model.setPlannedTipRate(plannedTipRate);
		
		// Method under test
		model.setBillTotal(billTotal);

		// Postconditions
		assertEquals("Incorrect bill total", billTotal, model.getBillTotal());
		assertEquals("Incorrect tax rate", taxRate, model.getTaxRate());
		assertEquals("Incorrect bill subtotal", billSubtotal, 
				model.getBillSubtotal());
	    assertEquals("Incorrect tippable amount", tippableAmount,
	    		model.getTippableAmount());
	    assertEquals("Incorrect planned tip amount", plannedTipAmount,
	    		model.getPlannedTipAmount());
	    assertEquals("Incorrect actual tip amount", actualTipAmount,
	    		model.getActualTipAmount());
	    assertEquals("Incorrect share due", shareDue, model.getShareDue());
	    assertEquals("Incorrect total due", totalDue, model.getTotalDue());
	}

	
	/* When the bill total is changed when using tax rate,
	 * bill total, tax amount, bill subtotal, tippable amount, 
	 * planned tip amount, actual tip amount and share due should 
	 * be updated.
	 */
	@Test 
	public void testBillSubtotalChangeUsingTaxRate() {
		// Preconditions
		final BigDecimal billTotal = new BigDecimal("42.42");
		final BigDecimal taxRate = new BigDecimal(".10000");
		final BigDecimal plannedTipRate = new BigDecimal(".20000");
		final BigDecimal billSubtotal = new BigDecimal("38.56");
		final BigDecimal tippableAmount = billSubtotal;
		final BigDecimal plannedTipAmount = new BigDecimal("7.71");
		final BigDecimal taxAmount = billSubtotal.multiply(taxRate)
			.setScale(2, RoundingMode.HALF_UP);
		final BigDecimal actualTipAmount = plannedTipAmount;
		final BigDecimal actualTipRate = plannedTipAmount.divide
			(tippableAmount, 5, RoundingMode.HALF_UP);
		final BigDecimal shareDue = billTotal.add(actualTipAmount);
		final BigDecimal totalDue = shareDue;

		model.setBillTotal(billTotal);
		model.setTaxRate(taxRate);
		model.setPlannedTipRate(plannedTipRate);
		
		// Method under test
		model.setBillTotal(billTotal);

		// Postconditions
		assertEquals("Incorrect bill total", billTotal, 
				model.getBillTotal());
		assertEquals("Incorrect tax amount", taxAmount, 
				model.getTaxAmount());
		assertEquals("Incorrect bill subtotal", billSubtotal, 
				model.getBillSubtotal());
		assertEquals("Incorrect tippable amount", tippableAmount,
				model.getTippableAmount());
		assertEquals("Incorrect planned tip amount", plannedTipAmount,
				model.getPlannedTipAmount());
		assertEquals("Incorrect actual tip amount", actualTipAmount,
				model.getActualTipAmount());
		assertEquals("Incorrect actual tip rate", actualTipRate,
				model.getActualTipRate());
		assertEquals("Incorrect share due", shareDue,
				model.getShareDue());
		assertEquals("Incorrect total due", totalDue, 
				model.getTotalDue());
	}

	
	@Test
	public void testBumps() {
		model.setBumps(17);
		assertEquals("Incorrect number of bumps", 17,
				model.getBumps());
	}

	
	/* Test that the discount is correctly stored. */
	@Test 
	public void testDiscount() {
		model.setDiscount(DISCOUNT);
		assertEquals("Incorrect discount", DISCOUNT, 
				model.getDiscount());
	}

	
	/* When the discount is changed, check that discount, tippable amount, 
	 * tip amount,  actual tip amount and share amount are updated.
	 */
	@Test 
	public void testDiscountChange() {
		// Preconditions
		final BigDecimal amount10_00 = new BigDecimal("10.00");
		final BigDecimal zeroRate = new BigDecimal(".00000");
		final BigDecimal fifteenPercentRate = new BigDecimal(".15000");
		final BigDecimal expectedTippableAmount = new BigDecimal("13.00");
		final BigDecimal expectedPlannedTipAmount = new BigDecimal("1.95");
		final BigDecimal discount = new BigDecimal("3.00");
		final BigDecimal expectedShareDue = new BigDecimal("11.95"); 
		final BigDecimal expectedTotalDue = expectedShareDue; 

		model.setBillTotal(amount10_00);
		model.setTaxRate(zeroRate);
		model.setPlannedTipRate(fifteenPercentRate);

		// Method under test
		model.setDiscount(discount);
		
		// Postconditions
		assertEquals("Incorrect tippable amount", expectedTippableAmount,
				model.getTippableAmount());
		assertEquals("Incorrect planned tip amount", expectedPlannedTipAmount,
				model.getPlannedTipAmount());
		assertEquals("Incorrect actual tip amount", model.getPlannedTipAmount(),
				model.getActualTipAmount());
		assertEquals("Incorrect share due", expectedShareDue,
				model.getShareDue());
		assertEquals("Incorrect total due", expectedTotalDue,
				model.getTotalDue());
	}
	
	
	/** Sets up the data model for a ShareDue test.
	 * 
	 * ShareDue = RoundTo((BillTotal + TipAmount)/SplitBetween, RoundUpToAmount)
	 * + Bumps * RoundUpToAmount 
	 * 
	 * @return The ShareDue amount that should be in the data model
	 * given the inputs from this method.
	 */
	private BigDecimal getShareDueFor100Check15PercentTaxSplit5() {
		BigDecimal billTotal = new BigDecimal("100.00");
		model.setBillTotal(billTotal);
		model.setTaxRate(new BigDecimal("0.000"));
		model.setPlannedTipRate(new BigDecimal("0.150"));
		model.setSplitBetween(5);
		model.setBumps(0);
		
		BigDecimal totalDue = billTotal.add(model.getPlannedTipAmount());
		final BigDecimal splitBetween 
			= new BigDecimal(model.getSplitBetween());
		BigDecimal splitTotalDue = totalDue.divide(splitBetween, 
				2, RoundingMode.HALF_UP);
		BigDecimal roundMults = splitTotalDue.divide(model.getRoundUpToAmount(), 0,
				RoundingMode.UP);
		BigDecimal roundedSplitTotalDue = model.getRoundUpToAmount()
			.multiply(roundMults);
		BigDecimal bumps = new BigDecimal(model.getBumps());
		BigDecimal bumpAmount = bumps.multiply(model.getRoundUpToAmount());
		bumpAmount.setScale(2, RoundingMode.HALF_UP);

		BigDecimal shareDue = roundedSplitTotalDue.add(bumpAmount);
		return shareDue;
	}
	
	
	/* Test that the share of the bill due from each party
	 * is properly calculated. 
	 */
	@Test
	public void testShareDue() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();
		
		// Method under test
		assertEquals("Incorrect share due",  shareDue, model.getShareDue());
	}
	

	/** Tests that the Share Due is correctly updated when the Bill
	 * Total changes.
	 */
	@Test
	public void testShareDue_BillTotal() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();

		// Methods under test
		model.setBillTotal(new BigDecimal("200.00"));
		shareDue = shareDue.multiply(new BigDecimal(2));
		assertEquals("Incorrect share due when changing bill total", shareDue,
				model.getShareDue());

	}

	
	/** Tests that the Share Due is correctly updated when the Tip
	 * Amount changes.
	 */
	@Test
	public void testShareDue_TipAmount() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();
		
		// Methods under test
		// Change tip rate from 15% to 30%
		model.setPlannedTipRate(model.getPlannedTipRate().multiply(new BigDecimal(2)));
		shareDue = shareDue.add(new BigDecimal(3));
		assertEquals("Incorrect share due when changing tip amount", shareDue,
				model.getShareDue());
	}


	/** Tests that the Share Due is correctly updated when Split Between
	 * changes.
	 */
	@Test
	public void testShareDue_SplitBetween() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();
		
		// Methods under test
		// Change split between from 5 to 10
		model.setSplitBetween(10);
		shareDue = shareDue.divide(new BigDecimal(2), 2, RoundingMode.UP);
		assertEquals("Incorrect share due when changing split between", 
				shareDue, model.getShareDue());
	}

	
	/** Tests that the Share Due is correctly updated when Round Up To Amount
	 * changes.
	 */
	@Test
	public void testShareDue_RoundUpTo() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();
		
		// Methods under test
		// Change Round Up To Amount from .01 to 10.00
		BigDecimal newRoundUpTo = new BigDecimal("10.00");
		model.setRoundUpToAmount(newRoundUpTo);
		BigDecimal mults = shareDue.divide(newRoundUpTo, 0, RoundingMode.UP);
		shareDue = newRoundUpTo.multiply(mults);
		assertEquals("Incorrect share due when changing round up to amount", shareDue,
				model.getShareDue());
	}

	
	/** Tests that the Share Due is correctly updated when Bumps
	 * changes.
	 */
	@Test
	public void testShareDue_Bumps() {
		// Set preconditions and establish postconditions
		BigDecimal shareDue = getShareDueFor100Check15PercentTaxSplit5();
		
		// Methods under test
		// Change Bumps from 0 to 3
		model.setBumps(3);
		shareDue = shareDue.add(new BigDecimal("0.03"));
		assertEquals("Incorrect share due when changing bumps", shareDue,
				model.getShareDue());
	}
	
	
	/* Test that the bill share amount is correctly calculated when the tip
	 * rate is changed.
	 */
	@Test
	public void testShareWithChangedTipRate() {
		BigDecimal billTotal = new BigDecimal("107.00");
		BigDecimal discAmount = new BigDecimal("6.00");
		BigDecimal oldTipRate = new BigDecimal("0.700");
		BigDecimal newTipRate = new BigDecimal("0.140");
		BigDecimal taxRate = new BigDecimal("0.070");
		
		model.setBillTotal(billTotal);
		model.setDiscount(discAmount);
		model.setPlannedTipRate(oldTipRate);
		model.setTaxRate(taxRate);
		model.setPlannedTipRate(newTipRate);
		BigDecimal billSubtotal = calculateBillSubtotal
			(billTotal, taxRate);
		BigDecimal tip = billSubtotal.add(discAmount)
			.multiply(newTipRate).setScale(2);
		BigDecimal totalShare = billTotal.add(tip); 
		assertEquals("Incorrect bill share",  totalShare,
				model.getShareDue());
	}
	
	
	/* Test that the tax amount is properly calculated when its inputs, 
	 * bill total amount and tax rate change.
	 */
	@Test 
	public void testTaxAmount() {
		model.setTaxAmount(TAX_AMOUNT);
		assertEquals("Incorrect tax amount", TAX_AMOUNT, 
				model.getTaxAmount());
		
		// Test when setting tax rate, tax amount is updated
		model.setTaxAmount(new BigDecimal("0.00"));
		model.setBillTotal(AMOUNT_5_61);
		model.setTaxRate(SEVEN_PERCENT_RATE);
		assertEquals("Incorrect tax amount", TAX_AMOUNT, 
				model.getTaxAmount());

		model.setBillTotal(new BigDecimal("52.00"));
		model.setTaxAmount(new BigDecimal("0.00"));
		model.setTaxRate(new BigDecimal(".040"));
		assertEquals("Incorrect tax amount", new BigDecimal("2.00"), 
				model.getTaxAmount());	

		// Test when bill total is updated, tax amount is updated
		model.setTaxRate(SEVEN_PERCENT_RATE);
		model.setBillTotal(new BigDecimal("11.22"));
		BigDecimal taxAmount = new BigDecimal("0.73");
		assertEquals("Incorrect tax amount", taxAmount, 
				model.getTaxAmount());
		
		// Test when not using tax rate, tax amount doesn't change
		// when bill total changes.
		model.setTaxRate(new BigDecimal("0.100"));
		taxAmount = new BigDecimal("200.00");
		model.setTaxAmount(taxAmount);
		model.setBillTotal(new BigDecimal("100.00"));
		assertEquals("Incorrect tax amount", taxAmount, 
				model.getTaxAmount());
	}
	
	
	/* Test the tax rate is properly stored and that the tax rate is properly 
	 * calculated when the tax amount is set. 
	 */
	@Test 
	public void testTaxRate() {
		model.setTaxRate(SEVEN_PERCENT_RATE);
		assertEquals("Incorrect tax rate", SEVEN_PERCENT_RATE, model.getTaxRate());
		model.setBillTotal(new BigDecimal("53.00"));
		model.setTaxRate(new BigDecimal(".00000"));
		model.setTaxAmount(new BigDecimal("3.00"));
		assertEquals("Incorrect tax rate", new BigDecimal(".06000"), 
				model.getTaxRate());	
	}
	

	/* Verify that when the tax rate is changed, bill subtotal, 
	 * tax amount, tippable amount, planned tip amount, actual tip amount,
	 * share due and total due are correctly recalculated.
	 */
	@Test 
	public void testTaxRateChange() {
		// Preconditions
		final BigDecimal billTotal = new BigDecimal("1.50");
		final BigDecimal taxRate = new BigDecimal(".03000");
		final BigDecimal plannedTipRate = new BigDecimal(".20000");
		final BigDecimal expectedBillSubtotal = new BigDecimal("1.46");
		final BigDecimal expectedTaxAmount = new BigDecimal("0.04");
		final BigDecimal expectedTippableAmount = expectedBillSubtotal;
		final BigDecimal expectedPlannedTipAmount = expectedBillSubtotal
				.multiply(plannedTipRate).setScale(2, RoundingMode.HALF_UP);
		final BigDecimal expectedActualTipAmount = expectedPlannedTipAmount;
		final BigDecimal expectedActualTipRate 
			= expectedActualTipAmount.divide(expectedTippableAmount,
					5, RoundingMode.HALF_UP);
		final BigDecimal expectedShareDue = billTotal
				.add(expectedActualTipAmount);
		final BigDecimal expectedTotalDue = expectedShareDue;
		
		model.setBillTotal(billTotal);
		model.setPlannedTipRate(plannedTipRate);
		
		// Method under test
		model.setTaxRate(taxRate);
		
		// Postconditions
		assertEquals("Incorrect bill subtotal", expectedBillSubtotal,
				model.getBillSubtotal());
		assertEquals("Incorrect tax amount", expectedTaxAmount,
				model.getTaxAmount());
		assertEquals("Incorrect tippable amount", expectedTippableAmount,
				model.getTippableAmount());
		assertEquals("Incorrect planned tip amount", expectedPlannedTipAmount,
				model.getPlannedTipAmount());
		assertEquals("Incorrect actual tip amount", expectedActualTipAmount,
				model.getActualTipAmount());
		assertEquals("Incorrect actual tip rate", expectedActualTipRate,
				model.getActualTipRate());
		assertEquals("Incorrect share due", expectedShareDue,
				model.getShareDue());
		assertEquals("Incorrect total due", expectedTotalDue,
				model.getTotalDue());
	}
	
	
	/* Test the tippable amount is properly calculated when its
	 * inputs, bill total and discount are updated. 
	 */
	@Test
	public void testTippableAmount() {
		model.setBillTotal(AMOUNT_5_61);
		model.setDiscount(DISCOUNT);
		model.setTaxAmount(TAX_AMOUNT);
		assertEquals("Incorrect tippable amount", TIPPABLE_AMOUNT, 
				model.getTippableAmount());
		
		model.setDiscount(new BigDecimal(".00"));
		assertEquals("Incorrect tippable amount", 
				TIPPABLE_AMOUNT.subtract(DISCOUNT), 
				model.getTippableAmount());
		
		model.setTaxAmount(new BigDecimal(".00"));
		model.setBillTotal(new BigDecimal("1.00"));
		assertEquals("Incorrect tippable amount", new BigDecimal("1.00"), 
				model.getTippableAmount());
	}
	

	/* Test that the tip amount is correctly calculated. */
	@Test
	public void testTipAmount() {
		model.setBillTotal(AMOUNT_5_61);
		model.setTaxRate(SEVEN_PERCENT_RATE);
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		model.setDiscount(DISCOUNT);
		assertEquals("Incorrect tip amount", TIP_AMOUNT,
				model.getPlannedTipAmount());
	}
	
	
	/* Verify that when the tax amount is changed, tax rate, 
	 * tax amount, bill subtotal, tippable amount, tip amount, 
	 * actual tip amount, actual tip rate,
	 * share due and total due are correctly recalculated.
	 */
	@Test 
	public void testTaxAmountChange() {
		// Preconditions
		final BigDecimal taxAmount = new BigDecimal(".09");
		
		model.setBillTotal(new BigDecimal("60.00"));
		model.setPlannedTipRate(new BigDecimal(".20000"));
		
		// Method under test
		model.setTaxAmount(taxAmount);
		
		// Postconditions
		assertEquals("Incorrect tax rate", new BigDecimal(".00150"), 
				model.getTaxRate());
		assertEquals("Incorrect tax amount", taxAmount,
				model.getTaxAmount());
		assertEquals("Incorrect bill subtotal", new BigDecimal("59.91"),
				model.getBillSubtotal());
		assertEquals("Incorrect tippable amount", new BigDecimal("59.91"),
				model.getTippableAmount());
		assertEquals("Incorrect planned tip amount", new BigDecimal("11.98"),
				model.getPlannedTipAmount());
		assertEquals("Incorrect actual tip amount", new BigDecimal("11.98"),
				model.getActualTipAmount());
		assertEquals("Incorrect actual tip rate", new BigDecimal(".19997"),
				model.getActualTipRate());
		assertEquals("Incorrect share due", new BigDecimal("71.98"),
				model.getShareDue());
		assertEquals("Incorrect total due", new BigDecimal("71.98"),
				model.getTotalDue());
}

	
	/* Test the tip rate is properly stored. */
	@Test 
	public void testTipRate() {
		model.setPlannedTipRate(EIGHTEEN_PERCENT_RATE);
		assertEquals("Incorrect tip rate", EIGHTEEN_PERCENT_RATE, model.getPlannedTipRate());
	}
	

	/** Sets up the data model for a TotalDue test.
	 * 
	 * Total Due = Share Due * Split Between
	 * 
	 * @return The Total Due amount that should be in the data model
	 * given the inputs from this method.
	 */
	private BigDecimal getBillTotalFrom100BillNoTaxNoTipSplit2() {
		BigDecimal billTotal = new BigDecimal("100.00");
		model.setBillTotal(billTotal);
		model.setTaxRate(new BigDecimal("0.000"));
		model.setPlannedTipRate(new BigDecimal("0.000"));
		model.setSplitBetween(2);
		
		return billTotal;
	}
	
	
	/** Tests that the total due is calculated correctly.
	 */
	@Test
	public void testTotalDue() {
		// Set up preconditions and expected postconditions
		BigDecimal expectedTotalDue = getBillTotalFrom100BillNoTaxNoTipSplit2();
		
		// Method under test
		assertEquals("Incorrect total due", expectedTotalDue,
				model.getTotalDue());
	}
	
	
	/** Tests that the total due is calculated correctly when Share Due
	 * changes.
	 */
	@Test
	public void testTotalDue_ShareDue() {
		// Set up preconditions and expected postconditions
		BigDecimal expectedTotalDue = getBillTotalFrom100BillNoTaxNoTipSplit2();

		// Method under test
		// Double the Bill Total Amount and thus, Share Due
		model.setBillTotal(new BigDecimal("200.00"));
		expectedTotalDue = expectedTotalDue.multiply(new BigDecimal(2));
		assertEquals("Incorrect total due when share due changed", 
				expectedTotalDue, model.getTotalDue());
	}

	
	/** Tests that the total due is calculated correctly when Split Between
	 * changes but no rounding occurs.
	 */
	@Test
	public void testTotalDue_SplitBetweenNoRound() {
		// Set up preconditions and expected postconditions
		BigDecimal expectedTotalDue = getBillTotalFrom100BillNoTaxNoTipSplit2();

		// Method under test
		// Change split between from 2 to 5
		model.setSplitBetween(5);
		assertEquals("Incorrect total due when split between changed", 
				expectedTotalDue, model.getTotalDue());
	}

	
	/** Tests that the total due is calculated correctly when Split Between
	 * changes and rounding occurs.
	 */
	@Test
	public void testTotalDue_SplitBetweenRounding() {
		// Set up preconditions and expected postconditions
		BigDecimal expectedTotalDue = getBillTotalFrom100BillNoTaxNoTipSplit2();

		// Method under test
		model.setRoundUpToAmount(new BigDecimal("1.00"));
		// Change split between from 2 to 3
		model.setSplitBetween(3);
		// Share Due changed from 50 to 34.  Total due should now be $102.
		expectedTotalDue = new BigDecimal("102.00");
		assertEquals("Incorrect total due when split between changed", 
				expectedTotalDue, model.getTotalDue());
	}

	
	/* Test the "using tax rate" flag is set when the tax
	 * rate is updated and cleared when the tax amount is updated.
	 */
	@Test
	public void testUsingTaxRate() {
		model.setTaxAmount(new BigDecimal("1.00"));
		assertFalse("Should be using tax amount", model.isUsingTaxRate());
		
		model.setTaxRate(new BigDecimal("1.000"));
		assertTrue("Should be using tax rate", model.isUsingTaxRate());
	}
	
	
	/** Tests the split between value is stored correctly.
	 */
	@Test
	public void testSplitBetween() {
		model.setSplitBetween(42);
		assertEquals("Split between should be 42", 42, model.getSplitBetween());
	}
	
	
	/** Tests that split between single party doesn't affect bill share due. 
	 */
	@Test
	public void testSplitBetween_NoSplit() {
		model.setBillTotal(AMOUNT_5_61);
		model.setPlannedTipRate(ZERO_RATE);
		model.setSplitBetween(1);
		assertEquals("Incorrect share due for no split", AMOUNT_5_61,
				model.getShareDue());
	}
	
	
	/** Tests that split between 2 parties halves bill share due. */
	@Test
	public void testSplitBetween_TwoSplits() {
		model.setBillTotal(AMOUNT_5_61);
		model.setPlannedTipRate(ZERO_RATE);
		model.setSplitBetween(2);
		BigDecimal twoSplits = new BigDecimal("2");
		BigDecimal expectedShareDue 
			= AMOUNT_5_61.divide(twoSplits, RoundingMode.UP);
		assertEquals("Incorrect bill share for no split", expectedShareDue,
				model.getShareDue());
	}
	
	
	/* Verify that when split between is changed, share due
	 * is correctly recalculated.
	 */
	@Test 
	public void testSplitBetweenChange() {
		// Preconditions
		model.setBillTotal(new BigDecimal("50.00"));
		model.setPlannedTipRate(ZERO_RATE);
		model.setSplitBetween(2);
		
		// Method under test
		model.setSplitBetween(2);
		
		// Postconditions
		assertEquals("Incorrect share due", new BigDecimal("25.00"),
				model.getShareDue());
	}

	
	/** Tests RoundUpToNearestAmount data model entry.
	 */
	@Test
	public void testRoundUpToNearestAmount() {
		model.setRoundUpToAmount(ROUND_UP_TO_ONE_DOLLAR);
		assertEquals("Round Up To Amount incorrectly set", ROUND_UP_TO_ONE_DOLLAR,
				model.getRoundUpToAmount());
	}
	
	
	/** Resets model so that the bill total is $17.37,
     * tax is 0, tip is 100%, split between is 1 and bump is 0.
     */
    private void setEachShareDue34_74NoSplit() {
		model.setBillTotal(AMOUNT_17_37);
		model.setTaxRate(ZERO_RATE);
		model.setPlannedTipRate(ONE_HUNDRED_PERCENT_RATE);
		model.setSplitBetween(1);
		model.setBumps(0);
    }
    
    
	@Test
    public void testRoundUpToAmountNone() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(this.ROUND_UP_TO_NONE);
    	assertEquals("Incorrect amount for RoundUpToNearest None",
    			new BigDecimal("34.74"), model.getShareDue());
    }


	@Test
    public void testRoundUpToAmountNickel() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_NICKEL);
    	assertEquals("Incorrect amount for RoundUpToNearest Nickel",
    			new BigDecimal("34.75"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountDime() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_DIME);
    	assertEquals("Incorrect amount for RoundUpToNearest Dime",
    			new BigDecimal("34.80"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountQuarter() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_QUARTER);
    	assertEquals("Incorrect amount for RoundUpToNearest Quarter",
    			new BigDecimal("34.75"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountHalfDollar() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_HALF_DOLLAR);
    	assertEquals("Incorrect amount for RoundUpToNearest Half Dollar",
    			new BigDecimal("35.00"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountOneDollar() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_ONE_DOLLAR);
    	assertEquals("Incorrect amount for RoundUpToNearest $1",
    			new BigDecimal("35.00"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountTwoDollars() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_TWO_DOLLARS);
    	assertEquals("Incorrect amount for RoundUpToNearest $2",
    			new BigDecimal("36.00"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundToAmountFiveDollars() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_FIVE_DOLLARS);
    	assertEquals("Incorrect amount for RoundUpToNearest $5",
    			new BigDecimal("35.00"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountTenDollars() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_TEN_DOLLARS);
    	assertEquals("Incorrect amount for RoundUpToNearest $10",
    			new BigDecimal("40.00"), model.getShareDue());
    }
    
    
	@Test
    public void testRoundUpToAmountTwentyDollars() {
    	setEachShareDue34_74NoSplit();
    	model.setRoundUpToAmount(ROUND_UP_TO_TWENTY_DOLLARS);
    	assertEquals("Incorrect amount for RoundUpToNearest $20",
    			new BigDecimal("40.00"), model.getShareDue());
    }
   

}