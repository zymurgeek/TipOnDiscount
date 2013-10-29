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
package com.itllp.tipOnDiscount.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;

public class CursorOperationsTests extends
ActivityInstrumentationTestCase2<TipOnDiscount> {
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private EditText billTotalEntryView;
    private EditText discountEntryView;
    private EditText taxPercentEntryView;
    private EditText taxAmountEntryView;
    private EditText plannedTipPercentEntryView;
    private EditText splitBetweenEntryView;

	@SuppressWarnings("deprecation")
	public CursorOperationsTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
    	DataModelFactory.setDataModel(new StubDataModel());
    	DataModelPersisterFactory.setDataModelPersister(
    			new StubDataModelPersister());
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        billTotalEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        discountEntryView = (EditText) mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.discount_entry);
        taxPercentEntryView = (EditText) mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxAmountEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        plannedTipPercentEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        splitBetweenEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.split_between_entry);

        mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			        mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }
    
    
	private void simulateNextButtonPress(EditText editText) {
		setFocusToView(editText);
		editText.onEditorAction(EditorInfo.IME_ACTION_NEXT);
    	mInstrumentation.waitForIdleSync();
	}

	
	private void setFocusToView(final TextView view) {
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					view.requestFocus();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
	}


    public void testNextButtonFromBillTotalField() {
    	// Call method under test
    	simulateNextButtonPress(billTotalEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextButtonFromTaxPercentField() {
    	// Call method under test
    	simulateNextButtonPress(taxPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxAmountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextButtonFromTaxAmountField() {
    	// Call method under test
    	simulateNextButtonPress(taxAmountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", discountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextButtonFromDiscountField() {
    	// Call method under test
    	simulateNextButtonPress(discountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", plannedTipPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextButtonFromPlannedTipPercentField() {
    	// Call method under test
    	simulateNextButtonPress(plannedTipPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", splitBetweenEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testEntryToBillTotalField() {
    	// Set up preconditions
    	setFocusToView(taxAmountEntryView);

    	// Set up preconditions
    	setFocusToView(taxPercentEntryView);
    	
    	// Call method under test
    	setFocusToView(billTotalEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(billTotalEntryView);
    }

    
    public void testEntryToTaxPercentField() {
    	// Set up preconditions
    	setFocusToView(billTotalEntryView);

    	// Call method under test
    	setFocusToView(taxPercentEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(taxPercentEntryView);
    }


    public void testEntryToTaxAmountField() {
    	// Set up preconditions
    	setFocusToView(taxPercentEntryView);

    	// Call method under test
    	setFocusToView(taxAmountEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(taxAmountEntryView);
    }


    public void testEntryToDiscountField() {
    	// Set up preconditions
    	setFocusToView(taxAmountEntryView);

    	// Call method under test
    	setFocusToView(discountEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(discountEntryView);
    }


    public void testEntryToPlannedTipPercentField() {
    	// Set up preconditions
    	setFocusToView(discountEntryView);

    	// Call method under test
    	setFocusToView(plannedTipPercentEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(plannedTipPercentEntryView);
    }


    public void testEntryToSplitBetweenField() {
    	// Set up preconditions
    	setFocusToView(plannedTipPercentEntryView);

    	// Call method under test
    	setFocusToView(splitBetweenEntryView);
    	
    	// Verify postconditions
    	verifyAllTextIsSelected(splitBetweenEntryView);
    }


    private void verifyAllTextIsSelected(TextView textView) {
    	int expectedSelectionEnd = textView.getEditableText().length();
        assertEquals("Incorrect selection start", 0,
        		textView.getSelectionStart());
        assertEquals("Incorrect selection end", expectedSelectionEnd,
        		textView.getSelectionEnd());
    }
    
    

}
