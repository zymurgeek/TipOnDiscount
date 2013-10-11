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
        mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			        mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }
    
    
    public void testPreconditions() {
        assertNotNull(billTotalEntryView);
        assertNotNull(discountEntryView);
        assertNotNull(taxPercentEntryView);
        assertNotNull(taxAmountEntryView);
        assertNotNull(plannedTipPercentEntryView);
        assertNotNull(splitBetweenEntryView);
        assertNotNull(roundUpToNearestSpinner);
        assertNotNull(bumpDownButton);
        assertNotNull(bumpsTextView);
        assertNotNull(bumpUpButton);
      }
    
    
    /* Verify the new action resets the bill total when the
     * bill total field is not in focus.
     */
    public void testNewActionWhenNotInBillTotal() {
    	// Preconditions
    	setFocusToView(bumpDownButton);
    	setBillTotalAmount();

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
        assertEquals("Incorrect value in field", zeroAmountText, 
        		billTotalEntryView.getText().toString());    	
        assertEquals("Incorrect value in data model", zeroAmount, 
        		model.getBillTotal());    	
    }


    /* Verify the new action resets the bill total when the
     * bill total field is in focus.
     */
    public void testNewActionWhenInBillTotal() {
    	setBillTotalAmount();
    	setFocusToView(billTotalEntryView);

    	// Method under test
		runOpenNewAction();
    	
    	// Postconditions
        assertEquals("Incorrect value in field", zeroAmountText, 
        		billTotalEntryView.getText().toString());    	
        assertEquals("Incorrect value in data model", zeroAmount, 
        		model.getBillTotal());    	
    }

    
    // TODO finish tests

    
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

	
	private void setBillTotalAmount() {
		View originalFocus = mActivity.getCurrentFocus();
		setFocusToView(this.bumpDownButton);
    	mInstrumentation.waitForIdleSync();
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	model.setBillTotal(oneDollarAmount);
    				}
    			}
    			);
    	mInstrumentation.waitForIdleSync();
    	setFocusToView(originalFocus);
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
