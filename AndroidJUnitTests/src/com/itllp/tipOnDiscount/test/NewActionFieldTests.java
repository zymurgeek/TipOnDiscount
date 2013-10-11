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
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView splitBetweenEntryView;
    private Button bumpDownButton;
    private TextView bumpsTextView;
    private Button bumpUpButton;
	private Spinner roundUpToNearestSpinner;
	private DataModelImpl model;
	private String zeroAmountText;
	private BigDecimal zeroAmount;
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
        
        zeroAmountText = "0.00";
        zeroAmount = new BigDecimal(zeroAmountText);
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
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        taxPercentEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxAmountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        plannedTipPercentEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
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
        assertDiscountFieldAndModelAreZero();
        assertPlannedTipFieldAndModelAreFifteenPercent();
    }


	public void testNewActionWhenFocusIsInBillTotal() {
    	setFocusToView(billTotalEntryView);

    	// Method under test
		runOpenNewAction();
    	
    	assertBillTotalFieldAndModelAreZero();    	
    }

    
	public void testNewActionWhenFocusIsInDiscount() {
    	setFocusToView(discountEntryView);

    	// Method under test
		runOpenNewAction();
    	
    	assertDiscountFieldAndModelAreZero();    	
    }

    
	public void testNewActionWhenFocusIsInPlannedTipPercent() {
    	// Preconditions
    	setFocusToView(plannedTipPercentEntryView);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
    	assertPlannedTipFieldAndModelAreFifteenPercent();
	}


	public void testNewActionWhenFocusIsInTaxPercent() {
    	// Preconditions
    	setFocusToView(taxPercentEntryView);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
    	assertTaxPercentFieldAndTaxRateInModelAreZero();
	}


	public void testNewActionWhenFocusIsInTaxAmount() {
    	// Preconditions
    	setFocusToView(taxAmountEntryView);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
    	assertTaxAmountFieldAndModelAreZero();
	}


    // TODO finish tests

    
	private void assertBillTotalFieldAndModelAreZero() {
		assertEquals("Wrong value in bill total field", zeroAmountText, 
	    		billTotalEntryView.getText().toString());    	
	    assertEquals("Wrong value for bill total in data model", zeroAmount, 
	    		model.getBillTotal());
	}


	private void assertDiscountFieldAndModelAreZero() {
		assertEquals("Wrong value in discount field", zeroAmountText, 
	    		discountEntryView.getText().toString());    	
	    assertEquals("Wrong value for discount in data model", zeroAmount, 
	    		model.getDiscount());
	}


	private void assertPlannedTipFieldAndModelAreFifteenPercent() {
		assertEquals("Wrong value in planned tip field", fifteenPercentText, 
				plannedTipPercentEntryView.getText().toString());
	    assertTrue("Wrong value for planned tip rate in data model", 
	    		0 == fifteenPercentRate.compareTo(model.getPlannedTipRate()));
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
