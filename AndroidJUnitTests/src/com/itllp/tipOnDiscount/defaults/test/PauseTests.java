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
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.DefaultsImpl;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.defaults.persistence.test.StubDefaultsPersister;

//TODO Finish implementing
public class PauseTests extends
	ActivityInstrumentationTestCase2<SetDefaultsActivity>{

	StubDefaultsPersister stubDefaultsPersister;
	DefaultsImpl defaults;
	private Instrumentation mInstrumentation;
    private SetDefaultsActivity mActivity;
    private EditText taxPercentEntryView;
    private EditText plannedTipPercentEntryView;
    private Spinner roundUpToNearestSpinner;

    
	@SuppressWarnings("deprecation")
	public PauseTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", SetDefaultsActivity.class);
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        stubDefaultsPersister = new StubDefaultsPersister(); 
        DefaultsPersisterFactory.setDefaultsPersister(stubDefaultsPersister);
        defaults = new DefaultsImpl();
        DefaultsFactory.setDefaults(defaults);
        mInstrumentation = getInstrumentation();
        mActivity = getActivity();

        taxPercentEntryView = (EditText) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        plannedTipPercentEntryView = (EditText) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
    }
    
    
    private void pauseActivity() {
    	mInstrumentation.waitForIdleSync();

    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mInstrumentation.callActivityOnPause(mActivity);
    				}
    			}
        	);

    	mInstrumentation.waitForIdleSync();
    }
    
//todo test pause when taxpercent is empty    
    public void testPause() {
    	// Preconditions
    	String expectedTaxPercentString = "4.25";
    	BigDecimal expectedTaxPercent = new BigDecimal(expectedTaxPercentString);
    	setText(taxPercentEntryView, expectedTaxPercentString);
    	
    	// Method under test
    	pauseActivity();
    	
    	// Postconditions
    	Defaults savedDefaults = stubDefaultsPersister.stub_getLastSavedDefaults();
    	BigDecimal actualTaxPercent = savedDefaults.getTaxPercent();
    	String errorMsg = "Incorrect tax percent saved: expected " + 
    			expectedTaxPercent.toPlainString() + " but was " +
    			actualTaxPercent.toPlainString();
    	assertTrue(errorMsg, 0==expectedTaxPercent.compareTo(actualTaxPercent));
    	Context expectedContext = mActivity;
    	Context actualContext = stubDefaultsPersister.stub_getLastSavedContext(); 
        assertEquals("Incorrect context used to save defaults", expectedContext, 
        		actualContext);
        //TODO test planned tip and round up to
    }
	

    private void setText(final EditText view, final String text) {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					view.setText(text);
    				}
    			}
    			);
    	mInstrumentation.waitForIdleSync();
    } 
    
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
