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

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.DataModelInitializerFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.model.test.StubDataModelInitializer;

public class DestroyTests extends
	ActivityInstrumentationTestCase2<TipOnDiscount>{

	private StubDataModel stubDataModel;
	private StubDataModelPersister stubDataModelPersister;
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private TextView billTotalEntryView;
    private TextView billSubtotalTextView;
    private TextView discountEntryView;
    private TextView tippableAmountTextView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountTextView;
    private TextView splitBetweenEntryView;
    private Spinner roundUpToNearestSpinner;
    private TextView bumpsTextView;
    private TextView actualTipPercentTextView;
    private TextView actualTipAmountTextView;
    private TextView totalDueTextView;
    private TextView shareDueTextView;

    
	@SuppressWarnings("deprecation")
	public DestroyTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        stubDataModelPersister = new StubDataModelPersister();
        DataModelPersisterFactory.setDataModelPersister(
        		stubDataModelPersister);
        stubDataModel = new StubDataModel();
        DataModelFactory.setDataModel(stubDataModel);
        DataModelInitializerFactory.setDataModelInitializer(
        		new StubDataModelInitializer());
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        billTotalEntryView = (TextView) mActivity.findViewById
    		(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billSubtotalTextView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableAmountTextView = (TextView) mActivity.findViewById(
        		com.itllp.tipOnDiscount.R.id.tippable_amount_text);
        taxPercentEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxAmountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        plannedTipPercentEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        plannedTipAmountTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
        splitBetweenEntryView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.split_between_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        bumpsTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bumps_text);
        actualTipPercentTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.total_due_text);
        shareDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.share_due_text);
        
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }

	
    private void destroyAndRestart() {
    	mInstrumentation.waitForIdleSync();
    	
    	// Exit app
    	mActivity.finish();
    	mInstrumentation.waitForIdleSync();
    	
    	// Restart app
        mActivity = this.getActivity();
    	mInstrumentation.waitForIdleSync();
    }


    private void setFieldValue(final TextView field, final String value) {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	field.setText(value);
    				}
    			}
        	);

    	mInstrumentation.waitForIdleSync();
    }
    
    
    public void testActualTipAmountStateDestroy() {
    	// Preconditions
    	final String amountText = "0.17";
    	setFieldValue(actualTipAmountTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored actual tip amount", amountText, 
        	actualTipAmountTextView.getText().toString());
    }

    
    public void testActualTipPercentStateDestroy() {
    	// Preconditions
    	final String percentText = "17.333";
    	setFieldValue(actualTipPercentTextView, percentText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored actual tip percent", percentText, 
        	actualTipPercentTextView.getText().toString());
    }

    
    public void testBillSubtotalStateDestroy() {
    	// Preconditions
    	final String amountText = "3.81";
    	setFieldValue(billSubtotalTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored bill subtotal amount", amountText, 
        	billSubtotalTextView.getText().toString());
    }
    
    
    public void testBillTotalStateDestroy() {
    	// Preconditions
    	final String amountText = "36.99";
    	setFieldValue(billTotalEntryView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored bill total amount", amountText, 
        		billTotalEntryView.getText().toString());
    }
    

    public void testBumpsStateDestroy() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textBumps = "-4";
    	int bumps = Integer.parseInt(textBumps);
    	model.setBumps(bumps);
    	updateAllFields();
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect bumps field value", textBumps, 
        		bumpsTextView.getText().toString());
    }
    


    public void testDataModelSaveAndRestoreOnDestroy() {
    	// Method under test
    	this.destroyAndRestart();
    	
    	// Postconditions
    	assertEquals("Incorrect Data model saved", 
    			stubDataModel,
    			stubDataModelPersister.mock_getLastSavedDataModel());
    	assertEquals("Incorrect context used to save",
    			getActivity(), 
    			stubDataModelPersister.mock_getLastSavedContext());
    	// When the app is destroyed, the restore method will not be called because
    	// it's a new activity that's created, not the old one restarted.
    	// assertTrue("Data model was not restored", model.wasDataModelRestored());
    }
    
    
    public void testDiscountStateDestroy() {
    	// Preconditions
    	final String amountText = "4.00";
    	setFieldValue(discountEntryView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored discount", amountText, 
        		discountEntryView.getText().toString());
    }
    
    
    public void testPlannedTipAmountStateDestroy() {
    	// Preconditions
    	final String amountText = "0.02";
    	setFieldValue(plannedTipAmountTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored planned tip amount", amountText, 
        		plannedTipAmountTextView.getText().toString());
    }

    
    public void testPlannedTipPercentStateDestroy() {
    	// Preconditions
    	final String percentText = "5";
    	setFieldValue(plannedTipPercentEntryView, percentText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored planned tip percent", percentText, 
        		plannedTipPercentEntryView.getText().toString());
    }
    
    
    public void testRoundUpToNearestStateDestroy() {
    	// Preconditions
    	final int position = 4;
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					roundUpToNearestSpinner.setSelection(position);
    				}
    			}
        	);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect Round Up To Nearest selection", position,
        	roundUpToNearestSpinner.getSelectedItemPosition());
    }

    
    public void testShareDueStateDestroy() {
    	// Preconditions
    	final String amountText = "2.13";
    	setFieldValue(shareDueTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored share due", amountText, 
        	shareDueTextView.getText().toString());
    }

    
    public void testSplitBetweenStateDestroy() {
    	// Preconditions
    	final String amountText = "3";
    	setFieldValue(splitBetweenEntryView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored split between #", amountText, 
        		splitBetweenEntryView.getText().toString());
    }

    
    public void testTaxAmountStateDestroy() {
    	// Preconditions
    	final String amountText = "3.00";
    	setFieldValue(taxAmountEntryView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored tax amount", amountText, 
        		taxAmountEntryView.getText().toString());
    }
    

    public void testTaxPercentStateDestroy() {
    	// Preconditions
    	final String percentText = "2";
    	setFieldValue(taxPercentEntryView, percentText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored tax percent", percentText, 
        		taxPercentEntryView.getText().toString());
    }
    
    
    public void testTippableAmountStateDestroy() {
    	// Preconditions
    	final String amountText = "5.45";
    	setFieldValue(tippableAmountTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored tippable amount", amountText, 
        		tippableAmountTextView.getText().toString());
    }

    
    public void testTotalDueStateDestroy() {
    	// Preconditions
    	final String amountText = "255.78";
    	setFieldValue(totalDueTextView, amountText);
    	
    	// Method under test
    	destroyAndRestart();
    	
    	// Postconditions
        assertEquals("Incorrect restored total due", amountText, 
        	totalDueTextView.getText().toString());
    }

    
    private void updateAllFields() {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mActivity.updateAllFields();;
    				}
    			}
        	);

    	mInstrumentation.waitForIdleSync();
    }
    
    
}
