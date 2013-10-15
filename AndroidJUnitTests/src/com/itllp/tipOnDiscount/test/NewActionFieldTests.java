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

package com.itllp.tipOnDiscount.test;

import java.math.BigDecimal;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.update.ActualTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.BillSubtotalUpdate;
import com.itllp.tipOnDiscount.model.update.TippableAmountUpdate;
import com.itllp.tipOnDiscount.modelimpl.DataModelImpl;

/* These test are related to the New menu action only. 
 */
public class NewActionFieldTests extends
		ActivityInstrumentationTestCase2<TipOnDiscount> {
	// TODO pull up common stuff into BaseTest class
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private EditText billTotalEntryView;
    private TextView discountEntryView;
    private TextView tippableAmountView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
	private TextView billSubtotalView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountView;
    private TextView splitBetweenEntryView;
    private Button bumpDownButton;
    private TextView bumpsTextView;
    private Button bumpUpButton;
	private Spinner roundUpToNearestSpinner;
	private TextView actualTipPercentView;
	private TextView actualTipAmountView;
	private TextView totalDueView;
	private DataModelImpl model;
	private String zeroText;
	private String zeroAmountText;
	private BigDecimal zeroAmount;
	private BigDecimal oneCentAmount;
	private String oneText;
	private String oneDollarAmountText;
	private BigDecimal oneDollarAmount;
	private String zeroPercentText;
	private String zeroPercentRateText;
	private BigDecimal zeroPercentRate;
	private String onePercentRateText;
	private BigDecimal onePercentRate;
	private String fifteenPercentText;
	private String fifteenPercentRateText;
	private BigDecimal fifteenPercentRate;
    
    
	@SuppressWarnings("deprecation")
	public NewActionFieldTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        zeroText = "0";
        zeroAmountText = "0.00";
        zeroAmount = new BigDecimal(zeroAmountText);
        oneText = "1";
        oneCentAmount = new BigDecimal("0.01");
        oneDollarAmountText = "1.00";
        oneDollarAmount = new BigDecimal(oneDollarAmountText);
        zeroPercentText = "0";
        zeroPercentRateText = "0";
    	zeroPercentRate = new BigDecimal(zeroPercentRateText);
        onePercentRateText = "0.01";
        onePercentRate = new BigDecimal(onePercentRateText);
        fifteenPercentText = "15";
        fifteenPercentRateText = "0.15";
        fifteenPercentRate = new BigDecimal(fifteenPercentRateText);
        mInstrumentation = getInstrumentation();
        
        mActivity = this.getActivity();

        billTotalEntryView = (EditText) mActivity.findViewById
    	(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billSubtotalView = (TextView) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableAmountView = (TextView) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.tippable_amount_text);
        taxPercentEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxAmountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        plannedTipPercentEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        plannedTipAmountView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
        splitBetweenEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.split_between_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        bumpDownButton = (Button)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_down_button);
        bumpsTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bumps_text);
        bumpUpButton = (Button)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_up_button);
        actualTipPercentView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.total_due_text);
        model = (DataModelImpl)mActivity.getDataModel();
    	
		initializeDataModel();
    }


	public void testNewActionWhenFocusIsNotInAnyEntryField() {
    	// Preconditions
    	setFocusToView(bumpDownButton);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
        assertBillTotalFieldAndModelAreZero();    	
        assertTaxPercentFieldAndTaxRateInModelAreZero();    	
        assertTaxAmountFieldAndModelAreZero();
        assertBillSubtotalFieldAndModelAreZero();
        assertDiscountFieldAndModelAreZero();
        assertTippableAmountFieldAndModelAreZero();
        assertPlannedTipPercentFieldAndModelAreFifteenPercent();
        assertPlannedTipAmountFieldAndModelAreZero();
        assertSplitBetweenFieldAndModelAreOne();
        assertRoundUpToNearestFieldAndModelAreNone();
        assertBumpsFieldAndModelAreZero();
        assertActualTipPercentFieldAndModelAreZero();
        assertActualTipAmountFieldAndModelAreZero();
        assertTotalDueFieldAndModelAreZero();
    }


	public void testTEMP_NewActionWhenFocusIsNotInAnyEntryField() {
    	// Preconditions
    	setFocusToView(bumpDownButton);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
    	//TODO Check these assertions and move them to the non-temp test
        //TODO assertShareDueFieldAndModelAreZero
    }


	public void testNewActionWhenFocusIsInBillTotal() {
		// Set up preconditions
    	setFocusToView(billTotalEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertBillTotalFieldAndModelAreZero();    	
    }

    
	public void testNewActionWhenFocusIsInDiscount() {
		// Set up preconditions
    	setFocusToView(discountEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertDiscountFieldAndModelAreZero();    	
    }

    
	public void testNewActionWhenFocusIsInPlannedTipPercent() {
    	// Set up preconditions
    	setFocusToView(plannedTipPercentEntryView);

    	// Run method under test
    	runOpenNewAction();
    	
    	// Verify postconditions
    	assertPlannedTipPercentFieldAndModelAreFifteenPercent();
	}


	public void testNewActionWhenFocusIsInTaxPercent() {
    	// Set up preconditions
    	setFocusToView(taxPercentEntryView);

    	// Run method under test
    	runOpenNewAction();
    	
    	// Verify postconditions
    	assertTaxPercentFieldAndTaxRateInModelAreZero();
	}


	public void testNewActionWhenFocusIsInTaxAmount() {
    	// Set up preconditions
    	setFocusToView(taxAmountEntryView);

    	// Run method under test
    	runOpenNewAction();
    	
    	// Verify postconditions
    	assertTaxAmountFieldAndModelAreZero();
	}


	public void testNewActionWhenFocusIsInSplitBetween() {
		// Set up preconditions
    	setFocusToView(splitBetweenEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertSplitBetweenFieldAndModelAreOne();    	
    }

    
	public void testNewActionWhenFocusIsInRoundUpToNearest() {
		// Set up preconditions
    	setFocusToView(roundUpToNearestSpinner);

    	// Run method under test
		//runOpenNewAction();
    	
		// Verify postconditions
    	//TODO assertRoundUpToNearestFieldAndModelAreNone();    	
    }

    
    // TODO finish tests
//TODO Bumps is not saved when TOD is closed and reopened
    
	private void assertActualTipAmountFieldAndModelAreZero() {
		assertEquals("Wrong value in actual tip amount field", zeroAmountText, 
	    		actualTipAmountView.getText().toString());    	
	    assertEquals("Wrong value for actual tip amount in data model", zeroAmount, 
	    		model.getActualTipAmount());
	}
	
	
	private void assertBillTotalFieldAndModelAreZero() {
		assertEquals("Wrong value in bill total field", zeroAmountText, 
	    		billTotalEntryView.getText().toString());    	
	    assertEquals("Wrong value for bill total in data model", zeroAmount, 
	    		model.getBillTotal());
	}


	private void assertBillSubtotalFieldAndModelAreZero() {
		assertEquals("Wrong value in bill subtotal field", zeroAmountText, 
	    		billSubtotalView.getText().toString());    	
	    assertEquals("Wrong value for bill subtotal in data model", zeroAmount, 
	    		model.getBillSubtotal());
	}


	private void assertBumpsFieldAndModelAreZero() {
		assertEquals("Wrong value in bumps field", zeroText, 
	    		bumpsTextView.getText().toString());    	
	    assertEquals("Wrong value for bumps in data model", 0, 
	    		model.getBumps());
	}

	
	private void assertActualTipPercentFieldAndModelAreZero() {
		assertEquals("Wrong value in actual tip percent field", zeroPercentText,
				actualTipPercentView.getText().toString());
	    assertTrue("Wrong value for actual tip rate in data model", 
	    		0 == zeroPercentRate.compareTo(model.getActualTipRate()));
	}
	
	
	private void assertDiscountFieldAndModelAreZero() {
		assertEquals("Wrong value in discount field", zeroAmountText, 
	    		discountEntryView.getText().toString());    	
	    assertEquals("Wrong value for discount in data model", zeroAmount, 
	    		model.getDiscount());
	}


	private void assertPlannedTipAmountFieldAndModelAreZero() {
		assertEquals("Wrong value in planned tip amount field", zeroAmountText, 
	    		plannedTipAmountView.getText().toString());    	
	    assertEquals("Wrong value for tippable amount in data model", zeroAmount, 
	    		model.getPlannedTipAmount());
		
	}
	
	
	private void assertPlannedTipPercentFieldAndModelAreFifteenPercent() {
		assertEquals("Wrong value in planned tip field", fifteenPercentText, 
				plannedTipPercentEntryView.getText().toString());
	    assertTrue("Wrong value for planned tip rate in data model", 
	    		0 == fifteenPercentRate.compareTo(model.getPlannedTipRate()));
	}


	private void assertSplitBetweenFieldAndModelAreOne() {
		assertEquals("Wrong value in split between field", oneText, 
	    		splitBetweenEntryView.getText().toString());    	
	    assertEquals("Wrong value for split between in data model", 1, 
	    		model.getSplitBetween());
	}

	
	private void assertRoundUpToNearestFieldAndModelAreNone() {
		assertEquals("Wrong value in round up to nearest spinner", "None", 
	    	roundUpToNearestSpinner.getSelectedItem().toString());
	    assertEquals("Wrong value for round up to nearest in data model", oneCentAmount, 
	    		model.getRoundUpToAmount());
	}

	
	private void assertTippableAmountFieldAndModelAreZero() {
		assertEquals("Wrong value in tippable amount field", zeroAmountText, 
	    		tippableAmountView.getText().toString());    	
	    assertEquals("Wrong value for tippable amount in data model", zeroAmount, 
	    		model.getTippableAmount());
		
	}
	
	
	private void assertTaxPercentFieldAndTaxRateInModelAreZero() {
		assertEquals("Wrong value in tax percent field", 
	    		zeroPercentText, taxPercentEntryView.getText().toString());
	    assertTrue("Wrong value for tax rate in data model", 
	    		0 == zeroPercentRate.compareTo(model.getTaxRate()));
	}


	private void assertTaxAmountFieldAndModelAreZero() {
		assertEquals("Wrong value in tax amount field", zeroAmountText, 
	    		this.taxAmountEntryView.getText().toString());    	
	    assertEquals("Wrong value for tax amount in data model", zeroAmount, 
	    		model.getTaxAmount());
	}


	private void assertTotalDueFieldAndModelAreZero() {
		assertEquals("Wrong value in total due field", zeroAmountText, 
	    		this.totalDueView.getText().toString());    	
	    assertEquals("Wrong value for total due in data model", zeroAmount, 
	    		model.getTotalDue());
		
	}
	
	
	private void initializeDataModel() {
        mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			        mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();

		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
				    	model.setBillTotal(oneDollarAmount);
				    	model.setTaxRate(onePercentRate);
				    	model.setDiscount(oneDollarAmount);
				    	model.setPlannedTipRate(onePercentRate);
				    	model.setSplitBetween(2);
				    	model.setRoundUpToAmount(oneDollarAmount);
				    	model.setBumps(4);
					}
				}
				);
		mInstrumentation.waitForIdleSync();
	}


	private void runOpenNewAction() {
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					mActivity.openNew();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
	}

	
	private void setFocusToView(final View view) {
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					view.requestFocus();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
	}


}
