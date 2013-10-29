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

package com.itllp.tipOnDiscount.defaults.test;

import java.math.BigDecimal;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.test.StubPersister;

//TODO Implement
public class PauseTests extends
	ActivityInstrumentationTestCase2<SetDefaultsActivity>{

//	StubDataModelPersister stubDataModelPersister;
//	StubDataModel stubDataModel;
//	StubPersister stubPersister;
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
//    private TextView billTotalEntryView;
//    private TextView billSubtotalTextView;
//    private TextView discountEntryView;
//    private TextView tippableAmountTextView;
    private TextView taxPercentEntryView;
//    private TextView taxAmountEntryView;
//    private TextView plannedTipPercentEntryView;
//    private TextView plannedTipAmountTextView;
//    private TextView splitBetweenEntryView;
    private Spinner roundUpToNearestSpinner;
//    private TextView bumpsTextView;
//    private TextView actualTipPercentTextView;
//    private TextView actualTipAmountTextView;
//    private TextView totalDueTextView;
//    private TextView shareDueTextView;

    
	@SuppressWarnings("deprecation")
	public PauseTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", SetDefaultsActivity.class);
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

    @Override
    protected void setUp() throws Exception {
        super.setUp();

//        stubPersister = new StubPersister();
//        PersisterFactory.setPersisterForApp(stubPersister);
//        stubDataModelPersister = new StubDataModelPersister(); 
//        DataModelPersisterFactory.setDataModelPersister(
//        		stubDataModelPersister);
//        stubDataModel = new StubDataModel();
//        DataModelFactory.setDataModel(stubDataModel);
//        mInstrumentation = getInstrumentation();
//        mActivity = this.getActivity();
//
//        billTotalEntryView = (TextView) mActivity.findViewById
//    		(com.itllp.tipOnDiscount.R.id.bill_total_entry);
//        billSubtotalTextView = (TextView) mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
//        discountEntryView = (TextView) mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.discount_entry);
//        tippableAmountTextView = (TextView) mActivity.findViewById(
//        		com.itllp.tipOnDiscount.R.id.tippable_amount_text);
//        taxPercentEntryView = (TextView) mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
//        taxAmountEntryView = (TextView) mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
//        plannedTipPercentEntryView = (TextView) mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
//        plannedTipAmountTextView = (TextView) mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
//        splitBetweenEntryView = (TextView)mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.split_between_entry);
//        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
//        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
//        bumpsTextView = (TextView) mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.bumps_text);
//        actualTipPercentTextView = (TextView)mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
//        actualTipAmountTextView = (TextView)mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
//        totalDueTextView = (TextView) mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.total_due_text);
//        shareDueTextView = (TextView) mActivity.findViewById
//			(com.itllp.tipOnDiscount.R.id.share_due_text);
//        
//    	final DataModel model = mActivity.getDataModel();
//    	model.initialize();
    }
    
    
    private void pause() {
    	mInstrumentation.waitForIdleSync();

    	// Pause app
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mInstrumentation.callActivityOnPause(mActivity);
    				}
    			}
        	);

    	mInstrumentation.waitForIdleSync();
    }
    
    
//    private void updateAllFields() {
//    	mActivity.runOnUiThread(
//    			new Runnable() {
//    				public void run() {
//    			    	mActivity.updateAllFields();;
//    				}
//    			}
//        	);
//
//    	mInstrumentation.waitForIdleSync();
//    }
    
    
//    public void testActualTipPercentStatePause() {
//    	// Preconditions
//    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
//    	String textRate = ".15";
//    	BigDecimal rate = new BigDecimal(textRate);
//    	String textPercent = TipOnDiscount.formatRateToPercent(rate);
//    	model.setActualTipRate(rate);
//    	
//    	// Method under test
//    	pause();
//    	
//    	// Postconditions
//    	assertEquals("Incorrect actual tip percent data model value", rate,
//    			model.getActualTipRate());
//        assertEquals("Incorrect actual tip percent field value", textPercent, 
//        		actualTipPercentTextView.getText().toString());
//    }
	


    
//    public void testDataModelSaveAndRestoreOnPause() {
//    	// Method under test
//    	pause();
//    	
//    	// Postconditions
//    	assertEquals("Incorrect Data model saved", 
//    			stubDataModel,
//    			stubDataModelPersister.mock_getLastSavedDataModel());
//    	assertEquals("Incorrect Persister used to save",
//    			stubPersister,
//    			stubDataModelPersister.mock_getLastSavedPersister());
//    	assertEquals("Incorrect context used to save",
//    			getActivity(), 
//    			stubDataModelPersister.mock_getLastSavedContext());
//    	assertEquals("Incorrect Data model restored", 
//    			stubDataModel,
//    			stubDataModelPersister.mock_getLastRestoredDataModel());
//    	assertEquals("Incorrect Persister used to restore",
//    			stubPersister,
//    			stubDataModelPersister.mock_getLastRestoredPersister());
//    	assertEquals("Incorrect context used to restore",
//    			getActivity(), 
//    			stubDataModelPersister.mock_getLastRestoredContext());
//
//    }
    
    
//    public void testRoundUpToStatePause() {
//    	// Preconditions
//    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
//    	String textAmount = "2.00";
//    	String displayAmount = "$2";
//    	BigDecimal amount = new BigDecimal(textAmount);
//    	model.setRoundUpToAmount(amount);
//    	
//    	// Method under test
//    	pause();
//    	
//    	// Postconditions
//    	assertEquals("Incorrect round up to amount data model value", amount,
//    			model.getRoundUpToAmount());
//        assertEquals("Incorrect round up to amount field value", displayAmount, 
//        		roundUpToNearestSpinner.getSelectedItem().toString());
//    }        

    

    
//    public void testTaxPercentStatePause() {
//    	// Preconditions
//    	final DataModel model = mActivity.getDataModel();
//    	String textRate = ".05275";
//    	BigDecimal rate = new BigDecimal(textRate);
//    	String textPercent = TipOnDiscount.formatRateToPercent(rate);
//    	model.setTaxRate(rate);
//    	
//    	// Method under test
//    	pause();
//    	
//    	// Postconditions
//    	assertEquals("Incorrect tax percent data model value", rate,
//    			model.getTaxRate());
//        assertEquals("Incorrect tax percent field value", textPercent, 
//        		taxPercentEntryView.getText().toString());
//    }
	

}
