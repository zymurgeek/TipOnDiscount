// Copyright 2011-2013 David A. Greenbaum
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
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.DataModelInitializer;
import com.itllp.tipOnDiscount.model.DataModelInitializerFactory;
import com.itllp.tipOnDiscount.model.impl.SimpleDataModelInitializer;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

/* These test are related to the New menu action only. 
 */
//TODO Add tests to verify planned tip percent, tax percent and round-up-to are set from Defaults
public class NewActionTests extends
		ActivityInstrumentationTestCase2<TipOnDiscount> {
	private DataModelInitializer dataModelInitializer;
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private InputMethodManager imm;
    private EditText billTotalEntryView;
    private TextView discountEntryView;
    private TextView tippableAmountView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
	private TextView billSubtotalView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountView;
    private TextView splitBetweenEntryView;
    private Button bumpDownButton;
    private TextView bumpsTextView;
	private Spinner roundUpToNearestSpinner;
	private TextView actualTipPercentView;
	private TextView actualTipAmountView;
	private TextView totalDueView;
	private TextView shareDueView;
	private DataModel model;
	private String oneDollarAmountText;
	private BigDecimal oneDollarAmount;
	private String onePercentRateText;
	private BigDecimal onePercentRate;
    
    
	@SuppressWarnings("deprecation")
	public NewActionTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        DataModelPersisterFactory.setDataModelPersister(new StubDataModelPersister());
        StubDataModel stubDataModel = new StubDataModel();
        DataModelFactory.setDataModel(stubDataModel);
        dataModelInitializer = new SimpleDataModelInitializer();
        DataModelInitializerFactory.setDataModelInitializer(dataModelInitializer);

        oneDollarAmountText = "1.00";
        oneDollarAmount = new BigDecimal(oneDollarAmountText);
        onePercentRateText = "0.01";
        onePercentRate = new BigDecimal(onePercentRateText);
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        billTotalEntryView = (EditText) mActivity.findViewById
    	(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billSubtotalView = (TextView) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableAmountView = (TextView) mActivity.findViewById
        		(com.itllp.tipOnDiscount.R.id.tippable_amount_text);
        taxPercentEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxAmountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        plannedTipPercentEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        plannedTipAmountView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
        splitBetweenEntryView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.split_between_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        bumpDownButton = (Button)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_down_button);
        bumpsTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bumps_text);
        actualTipPercentView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.total_due_text);
        shareDueView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.share_due_text);
        model = mActivity.getDataModel();
    	imm = (InputMethodManager)mActivity.
    			getSystemService(Context.INPUT_METHOD_SERVICE);
    	
		initializeDataModel();
    }


    @Override
    protected void tearDown() throws Exception {
    	View focusedView = mActivity.getCurrentFocus();
    	final IBinder windowToken = focusedView.getWindowToken();
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        });
    	mInstrumentation.waitForIdleSync();
    	
        super.tearDown();
    }

    
    public void testAAA_ScreenMustNotBeLocked() {
    	KeyguardManager km = (KeyguardManager)mActivity.getSystemService
    			(Context.KEYGUARD_SERVICE);
    	boolean isScreenLocked = km.inKeyguardRestrictedInputMode();
    	assertFalse("Unlock the device screen to run these tests",
    			isScreenLocked);
    }
    
    
	public void testNewActionWhenFocusIsNotInAnyEntryField() {
    	// Preconditions
    	setFocusToView(bumpDownButton);

    	// Method under test
    	runOpenNewAction();
    	
    	// Postconditions
        assertBillTotalFieldAndModelAreCleared();    	
        assertTaxPercentFieldAndTaxRateInModelAreAtInitialValues();    	
        assertTaxAmountFieldMatchesModel();
        assertBillSubtotalMatchesModel();
        assertDiscountFieldAndModelAreAtInitialValues();
        assertTippableAmountFieldMatchesModel();
        assertPlannedTipPercentFieldAndModelAreAtInitialValues();
        assertPlannedTipAmountFieldMatchesModel();
        assertSplitBetweenFieldAndModelAreAtInitialValues();
        assertRoundUpToNearestFieldAndModelAreAtInitialValues();
        assertBumpsFieldAndModelAreAtInitialValues();
        assertActualTipPercentFieldMatchesModel();
        assertActualTipAmountFieldMatchesModel();
        assertTotalDueFieldMatchesModel();
        assertShareDueFieldMatchesModel();
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
        assertTrue("Bill total field is not empty", 
        		0==billTotalEntryView.getText().length());
    	assertSoftKeyboardIsShownIfExpected();
    }


	public void testNewActionWhenFocusIsInBillTotal() {
		// Set up preconditions
		setFocusToView(taxAmountEntryView);
    	setFocusToView(billTotalEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertBillTotalFieldAndModelAreCleared();    	
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
    }

    
	public void testNewActionWhenFocusIsInTaxPercent() {
		// Set up preconditions
		setFocusToView(taxPercentEntryView);
	
		// Run method under test
		runOpenNewAction();
		
		// Verify postconditions
		assertTaxPercentFieldAndTaxRateInModelAreAtInitialValues();
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
	}


	public void testNewActionWhenFocusIsInTaxAmount() {
		// Set up preconditions
		setFocusToView(taxAmountEntryView);
	
		// Run method under test
		runOpenNewAction();
		
		// Verify postconditions
		assertTaxAmountFieldMatchesModel();
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
	}


	public void testNewActionWhenFocusIsInDiscount() {
		// Set up preconditions
    	setFocusToView(discountEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertDiscountFieldAndModelAreAtInitialValues();    	
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
    }

    
	public void testNewActionWhenFocusIsInPlannedTipPercent() {
    	// Set up preconditions
    	setFocusToView(plannedTipPercentEntryView);

    	// Run method under test
    	runOpenNewAction();
    	
    	// Verify postconditions
    	assertPlannedTipPercentFieldAndModelAreAtInitialValues();
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
	}


	public void testNewActionWhenFocusIsInSplitBetween() {
		// Set up preconditions
    	setFocusToView(splitBetweenEntryView);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertSplitBetweenFieldAndModelAreAtInitialValues();    	
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
    }

    
	public void testNewActionWhenFocusIsInRoundUpToNearest() {
		// Set up preconditions
    	setFocusToView(roundUpToNearestSpinner);

    	// Run method under test
		runOpenNewAction();
    	
		// Verify postconditions
    	assertRoundUpToNearestFieldAndModelAreAtInitialValues();    	
        assertTrue("Focus not in Bill Total field", 
        		billTotalEntryView.isFocused());
    	assertSoftKeyboardIsShownIfExpected();
    }
    
    
	private void assertSoftKeyboardIsShownIfExpected() {
		if (isHardwareKeyboardInUse()) {
			return;
		}
		
		final StubResultReceiver resultReceiver = new StubResultReceiver(null);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                imm.showSoftInput(billTotalEntryView, 
                		InputMethodManager.SHOW_IMPLICIT,
                		resultReceiver);
            }
        });
    	mInstrumentation.waitForIdleSync();
    	int tries = 10;
    	while (tries > 0 &&	StubResultReceiver.NO_CODE == 
    			resultReceiver.stub_getLastResultCode()) {
            SystemClock.sleep(200);
            --tries;
    	}
        assertEquals("Soft keyboard was not shown", 
        		InputMethodManager.RESULT_UNCHANGED_SHOWN, 
        		resultReceiver.stub_getLastResultCode());
	}


	private void assertActualTipAmountFieldMatchesModel() {
		assertEquals("Wrong value in actual tip amount field", 
				model.getActualTipAmount().toPlainString(), 
	    		actualTipAmountView.getText().toString());    	
	}
	
	
	private void assertBillTotalFieldAndModelAreCleared() {
		assertEquals("Bill total field is not blank", 
				0, billTotalEntryView.getText().length());    	
	    assertEquals("Bill total not cleared in data model", 
	    		0, model.getBillTotal().compareTo(new BigDecimal("0")));
	}


	private void assertBillSubtotalMatchesModel() {
		assertEquals("Wrong value in bill subtotal field", 
				model.getBillSubtotal().toPlainString(), 
	    		billSubtotalView.getText().toString());    	
	}


	private void assertBumpsFieldAndModelAreAtInitialValues() {
		int expectedBumps = dataModelInitializer.getBumps();
		assertEquals("Wrong value in bumps field", 
				String.valueOf(expectedBumps), 
	    		bumpsTextView.getText().toString());    	
	    assertEquals("Wrong value for bumps in data model", 
	    		expectedBumps, model.getBumps());
	}

	
	private void assertActualTipPercentFieldMatchesModel() {
		assertEquals("Wrong value in actual tip percent field", 
				TipOnDiscount.formatRateToPercent(model.getActualTipRate()),
				actualTipPercentView.getText().toString());
	}
	
	
	private void assertDiscountFieldAndModelAreAtInitialValues() {
		assertEquals("Wrong value in discount field", 
				model.getDiscount().toPlainString(), 
	    		discountEntryView.getText().toString());    	
	    assertEquals("Wrong value for discount in data model", 
	    		dataModelInitializer.getDiscount(), model.getDiscount());
	}


	private void assertPlannedTipAmountFieldMatchesModel() {
		assertEquals("Wrong value in planned tip amount field", 
				model.getPlannedTipAmount().toPlainString(), 
	    		plannedTipAmountView.getText().toString());    	
	}
	
	
	private void assertPlannedTipPercentFieldAndModelAreAtInitialValues() {
		BigDecimal expectedTipRate = dataModelInitializer.getTipRate(null);
		assertEquals("Wrong value in planned tip field", 
				TipOnDiscount.formatRateToPercent(expectedTipRate), 
				plannedTipPercentEntryView.getText().toString());
	    assertTrue("Wrong value for planned tip rate in data model", 
	    		0 == expectedTipRate.compareTo(model.getPlannedTipRate()));
	}


	private void assertSplitBetweenFieldAndModelAreAtInitialValues() {
		int expectedSplitBetween = dataModelInitializer.getSplitBetween();
		assertEquals("Wrong value in split between field", 
				String.valueOf(expectedSplitBetween), 
	    		splitBetweenEntryView.getText().toString());    	
	    assertEquals("Wrong value for split between in data model", 
	    		expectedSplitBetween, 
	    		model.getSplitBetween());
	}

	
	private void assertRoundUpToNearestFieldAndModelAreAtInitialValues() {
		BigDecimal expectedValue = dataModelInitializer.getRoundUpToAmount(null); 
		String expectedLabel = getRoundUpToLabel(expectedValue);
		assertEquals("Wrong value in round up to nearest spinner", 
				expectedLabel, 
	    	roundUpToNearestSpinner.getSelectedItem().toString());
	    assertEquals("Wrong value for round up to nearest in data model", 
	    		expectedValue, 
	    		model.getRoundUpToAmount());
	}


	private String getRoundUpToLabel(BigDecimal roundUpToValue) {
		String[] valueArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
		String[] labelArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
		BigDecimalLabelMap map = new BigDecimalLabelMap(valueArray, labelArray);
		String label = map.getLabel(roundUpToValue);
		return label;
	}

	
	private void assertTippableAmountFieldMatchesModel() {
		assertEquals("Wrong value in tippable amount field", 
				model.getTippableAmount().toPlainString(), 
	    		tippableAmountView.getText().toString());    	
	}
	
	
	private void assertTaxPercentFieldAndTaxRateInModelAreAtInitialValues() {
		BigDecimal expectedTaxRate = dataModelInitializer.getTaxRate(null);
		assertEquals("Wrong value in tax percent field", 
	    		TipOnDiscount.formatRateToPercent(expectedTaxRate), 
	    		taxPercentEntryView.getText().toString());
	    assertTrue("Wrong value for tax rate in data model", 
	    		0 == expectedTaxRate.compareTo(model.getTaxRate()));
	}


	private void assertTaxAmountFieldMatchesModel() {
		assertEquals("Wrong value in tax amount field", 
				model.getTaxAmount().toPlainString(), 
	    		this.taxAmountEntryView.getText().toString());    	
	}


	private void assertTotalDueFieldMatchesModel() {
		assertEquals("Wrong value in total due field", 
				model.getTotalDue().toPlainString(), 
	    		this.totalDueView.getText().toString());    	
	}
	

	private void assertShareDueFieldMatchesModel() {
		assertEquals("Wrong value in share due field", 
				model.getShareDue().toPlainString(), 
	    		this.shareDueView.getText().toString());    	
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
				    	model.setSplitBetween(2);
				    	model.setRoundUpToAmount(oneDollarAmount);
				    	model.setBumps(4);
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


	private boolean isHardwareKeyboardInUse() {
		Configuration config = mActivity.getResources().getConfiguration();
		if (config.keyboard == Configuration.KEYBOARD_NOKEYS) {
			return false;
		}
		if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			return false;
		}
		return true;
	}
}