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
import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.DefaultsImpl;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.defaults.persistence.test.StubDefaultsPersister;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;


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
        	(com.itllp.tipOnDiscount.R.id.defaults_tax_percent_entry);
        plannedTipPercentEntryView = (EditText) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.defaults_planned_tip_percent_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.defaults_round_up_to_nearest_spinner);
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

    
    public void testPauseWhenFieldsAreNotEmpty() {
    	// Set up preconditions
    	String expectedTaxPercentString = "4.25";
    	BigDecimal expectedTaxPercent = new BigDecimal(expectedTaxPercentString);
    	setText(taxPercentEntryView, expectedTaxPercentString);
    	String expectedTipPercentString = "9.33";
    	BigDecimal expectedTipPercent = new BigDecimal(expectedTipPercentString);
    	setText(plannedTipPercentEntryView, expectedTipPercentString);

    	String[] labels = mActivity.getResources().getStringArray(
    			com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
    	String[] values = mActivity.getResources().getStringArray(
    			com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
    	BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
    	int itemsInSpinner = labels.length;
    	int selectedItem = itemsInSpinner / 3;
    	setSpinnerSelection(roundUpToNearestSpinner, selectedItem);
    	String expectedLabel = labels[selectedItem];
    	BigDecimal expectedRoundUpToAmount = map.getValue(expectedLabel);
    			
    	// Call method under test
    	pauseActivity();
    	
    	// Verify postconditions
    	Defaults savedDefaults = stubDefaultsPersister.stub_getLastSavedDefaults();
    	verifyContextUsedToSave();
		verifySavedTaxPercent(expectedTaxPercent, savedDefaults);
    	verifySavedTipPercent(expectedTipPercent, savedDefaults);
		verifySavedRoundUpToAmount(expectedRoundUpToAmount, savedDefaults);
    }

    public void testPauseWhenFieldsAreEmpty() {
    	// Set up preconditions
    	String expectedTaxPercentString = "99.8";
    	BigDecimal expectedTaxPercent = new BigDecimal(expectedTaxPercentString);
    	defaults.setTaxPercent(expectedTaxPercent);
    	setText(taxPercentEntryView, "");

    	String expectedTipPercentString = "88.9";
    	BigDecimal expectedTipPercent = new BigDecimal(expectedTipPercentString);
    	defaults.setTipPercent(expectedTipPercent);
    	setText(plannedTipPercentEntryView, "");

    	// Call method under test
    	pauseActivity();
    	
    	// Verify postconditions
    	Defaults savedDefaults = stubDefaultsPersister.stub_getLastSavedDefaults();
    	verifyContextUsedToSave();
		verifySavedTaxPercent(expectedTaxPercent, savedDefaults);
    	verifySavedTipPercent(expectedTipPercent, savedDefaults);
    }

    
	private void verifySavedRoundUpToAmount(BigDecimal expectedRoundUpToAmount,
			Defaults savedDefaults) {
		BigDecimal actualRoundUpToAmount = savedDefaults.getRoundUpToAmount();
    	String errorMsg = "Incorrect round up to amount saved: expected " + 
    			expectedRoundUpToAmount.toPlainString() + " but was " +
    			actualRoundUpToAmount.toPlainString();
    	assertTrue(errorMsg, 0==expectedRoundUpToAmount.compareTo(actualRoundUpToAmount));
	}

	private void setSpinnerSelection(final Spinner spinner, final int selectedItem) {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					spinner.setSelection(selectedItem);
    				}
    			}
    			);
    	mInstrumentation.waitForIdleSync();
	}

	private void verifyContextUsedToSave() {
		Context expectedContext = mActivity;
    	Context actualContext = stubDefaultsPersister.stub_getLastSavedContext(); 
        assertEquals("Incorrect context used to save defaults", expectedContext, 
        		actualContext);
	}

	private void verifySavedTipPercent(BigDecimal expectedTipPercent,
			Defaults savedDefaults) {
		BigDecimal actualTipPercent = savedDefaults.getTipPercent();
    	String errorMsg = "Incorrect tip percent saved: expected " + 
    			expectedTipPercent.toPlainString() + " but was " +
    			actualTipPercent.toPlainString();
    	assertTrue(errorMsg, 0==expectedTipPercent.compareTo(actualTipPercent));
	}

	private void verifySavedTaxPercent(BigDecimal expectedTaxPercent,
			Defaults savedDefaults) {
		BigDecimal actualTaxPercent = savedDefaults.getTaxPercent();
    	String errorMsg = "Incorrect tax percent saved: expected " + 
    			expectedTaxPercent.toPlainString() + " but was " +
    			actualTaxPercent.toPlainString();
    	assertTrue(errorMsg, 0==expectedTaxPercent.compareTo(actualTaxPercent));
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
    

}
