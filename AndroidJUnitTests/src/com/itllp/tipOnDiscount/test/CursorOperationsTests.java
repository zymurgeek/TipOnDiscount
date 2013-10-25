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


    //TODO: Test that edit texts have select all performed on entry
}
