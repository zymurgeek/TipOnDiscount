package com.itllp.tipOnDiscount.test;

import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
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
    private TextView discountEntryView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView splitBetweenEntryView;

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

        mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			        mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }
    
    
    /* Sets focus to the given TextViewfield and sends keypresses to it.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void sendNextKeyToView(final TextView view) {
    	setFocusToView(view);
    	int forwardKey = KeyEvent.KEYCODE_DPAD_DOWN;
    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    		forwardKey = KeyEvent.KEYCODE_FORWARD;
    	}
    	this.sendKeys(forwardKey);
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


	private void sendDownKeyToView(final TextView view) {
		setFocusToView(view);
    	this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
    	mInstrumentation.waitForIdleSync();
    }


    public void testNextFromBillTotalField() {
    	// Call method under test
    	sendNextKeyToView(billTotalEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromBillTotalField() {
    	// Call method under test
    	sendDownKeyToView(billTotalEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextFromTaxPercentField() {
    	// Call method under test
    	sendNextKeyToView(taxPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxAmountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromTaxPercentField() {
    	// Call method under test
    	sendDownKeyToView(taxPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", taxAmountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextFromTaxAmountField() {
    	// Call method under test
    	sendNextKeyToView(taxAmountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", discountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromTaxAmountField() {
    	// Call method under test
    	sendDownKeyToView(taxAmountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", discountEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextFromDiscountField() {
    	// Call method under test
    	sendNextKeyToView(discountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", plannedTipPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromDiscountField() {
    	// Call method under test
    	sendDownKeyToView(discountEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", plannedTipPercentEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextFromPlannedTipPercentField() {
    	// Call method under test
    	sendNextKeyToView(plannedTipPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", splitBetweenEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromPlannedTipPercentField() {
    	// Call method under test
    	sendDownKeyToView(plannedTipPercentEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", splitBetweenEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testNextFromSplitBetweenField() {
    	// Call method under test
    	sendNextKeyToView(splitBetweenEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", splitBetweenEntryView,
        		mActivity.getCurrentFocus());
    }


    public void testDownFromSplitBetweenField() {
    	// Call method under test
    	sendDownKeyToView(splitBetweenEntryView);
    	
    	// Verify postconditions
        assertEquals("Incorrect field focused", splitBetweenEntryView,
        		mActivity.getCurrentFocus());
    }

    //FIXME:  Tests fail on Android 4.3
    //TODO: Test that edit texts have select all performed on entry
}
