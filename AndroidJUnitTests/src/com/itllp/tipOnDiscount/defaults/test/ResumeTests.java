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
import android.widget.EditText;
import android.widget.Spinner;

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.DefaultsImpl;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.defaults.persistence.test.StubDefaultsPersister;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

public class ResumeTests extends
		ActivityInstrumentationTestCase2<SetDefaultsActivity> {
	private Instrumentation mInstrumentation;
    private SetDefaultsActivity mActivity;
    private EditText taxPercentEntryView;
    private EditText plannedTipPercentEntryView;
	private Spinner roundUpToNearestSpinner;
	private Defaults defaults;
	private StubDefaultsPersister stubPersister;
	private BigDecimal expectedTaxPercent;
	private BigDecimal expectedTipPercent;
	private String expectedLabel;
	private BigDecimal expectedRoundUpToValue;
	
	@SuppressWarnings("deprecation")
	public ResumeTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", SetDefaultsActivity.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mInstrumentation = getInstrumentation();
        defaults = new DefaultsImpl();
        DefaultsFactory.setDefaults(defaults);
        stubPersister = new StubDefaultsPersister();
        DefaultsPersisterFactory.setDefaultsPersister(stubPersister);
        
        mActivity = getActivity();
        
        taxPercentEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        plannedTipPercentEntryView = (EditText) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        
        expectedTaxPercent = new BigDecimal("6.25");
        String notExpectedTaxPercentString = "999";
        expectedTipPercent = new BigDecimal("17.5");
    	String notExpectedTipPercentString = "888";
    	String[] labels = mActivity.getResources().getStringArray(
    			com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
    	String[] values = mActivity.getResources().getStringArray(
    			com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
    	BigDecimalLabelMap map = new BigDecimalLabelMap(values, labels);
    	int spinnerItemCount = labels.length;
    	int expectedPosition = spinnerItemCount / 2;
    	int notExpectedPosition = spinnerItemCount - 1;
    	expectedRoundUpToValue = new BigDecimal(values[expectedPosition]);
    	expectedLabel = map.getLabel(expectedRoundUpToValue);
    	
    	setText(taxPercentEntryView, notExpectedTaxPercentString);
		setText(plannedTipPercentEntryView, notExpectedTipPercentString);
    	setSpinnerSelection(roundUpToNearestSpinner, notExpectedPosition);

        pauseActivity();
    }


    public void testResume() {
    	// Set up preconditions
    	stubPersister.restoreState(null, null);
        defaults.setTaxPercent(expectedTaxPercent);
        defaults.setTipPercent(expectedTipPercent);
        defaults.setRoundUpToAmount(expectedRoundUpToValue);
    	
    	// Call method under test
        resumeActivity();

    	// Verify postconditions
    	verifyDefaultsAreLoadedFromPersister();
    	verifyTaxPercentFieldIsLoadedFromDefaults();
    	verifyPlannedTipPercentFieldIsLoadedFromDefaults();
    	verifyRoundUpToNearestSpinnerIsLoadedFromDefaults();
    }


	private void pauseActivity() {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					mInstrumentation.callActivityOnPause(mActivity);
    				}
    			}
    			);
    	mInstrumentation.waitForIdleSync();
	}
    
    
	private void resumeActivity() {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					mInstrumentation.callActivityOnResume(mActivity);
    				}
    			}
    			);
    	mInstrumentation.waitForIdleSync();
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

    private void verifyDefaultsAreLoadedFromPersister() {
    	assertEquals("Wrong defaults object restored", defaults,
    			stubPersister.stub_getLastRestoredDefaults());
    	assertEquals("Wrong context passed to restore", mActivity,
    			stubPersister.stub_getLastRestoredContext());
    }
    
    
    private void verifyTaxPercentFieldIsLoadedFromDefaults() {
    	String actualTaxPercentString = taxPercentEntryView.getText().toString();
    	BigDecimal actualTaxPercent = new BigDecimal(actualTaxPercentString);
    	String errorMessage = "Incorrect default tax percent: expected "
    			+ expectedTaxPercent + " but was " + actualTaxPercent;
    	assertTrue(errorMessage,
    			0==expectedTaxPercent.compareTo(actualTaxPercent));
    }
    
    
    private void verifyPlannedTipPercentFieldIsLoadedFromDefaults() {
    	String actualTipPercentString = plannedTipPercentEntryView.getText()
    			.toString();
    	BigDecimal actualTipPercent = new BigDecimal(actualTipPercentString);
    	String errorMessage = "Incorrect default tip percent: expected "
    			+ expectedTipPercent + " but was " + actualTipPercent;
    	assertTrue(errorMessage,
    			0==expectedTipPercent.compareTo(actualTipPercent));
    }
    
    
    private void verifyRoundUpToNearestSpinnerIsLoadedFromDefaults() {
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
    	String actualLabel = roundUpToNearestSpinner.getSelectedItem().
    			toString();
    	assertEquals("Incorrect default round up to nearest",
    			expectedLabel, actualLabel);
    }


}
