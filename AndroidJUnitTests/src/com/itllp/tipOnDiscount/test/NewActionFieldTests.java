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
import java.text.NumberFormat;
import java.util.Currency;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.test.model.MockDataModel;

/* These test are related to the New menu action only. 
 */
public class NewActionFieldTests extends
		ActivityInstrumentationTestCase2<TipOnDiscount> {
	private NumberFormat currencyNumberFormat 
		= NumberFormat.getCurrencyInstance();
	private NumberFormat percentNumberFormat 
		= NumberFormat.getInstance();
	private Currency currency = currencyNumberFormat.getCurrency();
	String zeroCurrencyString = currencyNumberFormat.format(0).
		replace(currency.getSymbol(), "");
	String zeroPercentString = null;
	BigDecimal ZERO = new BigDecimal("0.00");
	// TODO pull up common stuff into BaseTest class
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private EditText billTotalEntryView;
    private TextView billTotalCurrencySymbolLabelView;
    private TextView billSubtotalLabelView;
    private String billSubtotalLabelString;
    private TextView billSubtotalCurrencySymbolLabelView;
    private TextView billSubtotalView;
    private TextView discountEntryView;
    private TextView tippableAmountView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountView;
    private TextView splitBetweenEntryView;
    private Button bumpDownButton;
    private TextView bumpsTextView;
    private Button bumpUpButton;
    private TextView actualTipAmountView;
    private TextView totalDueTextView;
    private TextView shareDueTextView;
	private Spinner roundUpToNearestSpinner;
    
    
	@SuppressWarnings("deprecation")
	public NewActionFieldTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
		percentNumberFormat.setMaximumFractionDigits(3);
		zeroPercentString = percentNumberFormat.format(0);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mInstrumentation = getInstrumentation();
        
        Intent intent = new Intent();
        intent.putExtra(TipOnDiscount.DATA_MODEL_KEY, 
        		"com.itllp.tipOnDiscount.test.model.MockDataModel");
        setActivityIntent(intent);
        
        mActivity = this.getActivity();

        billTotalEntryView = (EditText) mActivity.findViewById
    	(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billTotalCurrencySymbolLabelView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.bill_total_currency_symbol_label);
        billSubtotalLabelView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_label);
        billSubtotalLabelString = mActivity.getString
        	(com.itllp.tipOnDiscount.R.string.bill_subtotal_label);
        billSubtotalCurrencySymbolLabelView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_currency_symbol_label);
        billSubtotalView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableAmountView = (TextView) mActivity.findViewById(
        		com.itllp.tipOnDiscount.R.id.tippable_amount_text);
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
        bumpUpButton = (Button)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_up_button);
        actualTipAmountView = (TextView) mActivity.findViewById
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
    
    
    public void testPreconditions() {
        assertNotNull(billTotalEntryView);
        assertNotNull(billTotalCurrencySymbolLabelView);
        assertNotNull(billSubtotalLabelView);
        assertNotNull(billSubtotalLabelString);
        assertNotNull(billSubtotalCurrencySymbolLabelView);
        assertNotNull(billSubtotalView);
        assertNotNull(discountEntryView);
        assertNotNull(tippableAmountView);
        assertNotNull(taxPercentEntryView);
        assertNotNull(taxAmountEntryView);
        assertNotNull(plannedTipPercentEntryView);
        assertNotNull(plannedTipAmountView);
        assertNotNull(splitBetweenEntryView);
        assertNotNull(roundUpToNearestSpinner);
        assertNotNull(bumpDownButton);
        assertNotNull(bumpsTextView);
        assertNotNull(bumpUpButton);
        assertNotNull(actualTipAmountView);
        assertNotNull(totalDueTextView);
        assertNotNull(shareDueTextView);
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


    /* Verify the new action resets the bill total when the
     * bill total field is not in focus.
     */
    public void testNewActionWhenNotInBillTotal() {
    	// Preconditions
    	MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	setFocusToView(actualTipAmountView);
    	final String expectedAmountText = "0.00";
    	final BigDecimal expectedAmount = new BigDecimal(expectedAmountText);
    	final String amountText = "1.00";
    	final BigDecimal amount = new BigDecimal(amountText);
    	model.setBillTotal(amount);

    	// Method under test
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					mActivity.openNew();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    	
    	// Postconditions
        assertEquals("Incorrect value in field", expectedAmountText, 
        		billTotalEntryView.getText().toString());    	
        assertEquals("Incorrect value in data model", expectedAmount, 
        		model.getBillTotal());    	
    }
    
    /* Verify the new action resets the bill total when the
     * bill total field is in focus.
     */
    public void testNewActionWhenInBillTotal() {
    	// Preconditions
    	MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	setFocusToView(actualTipAmountView);
    	mInstrumentation.waitForIdleSync();
    	final String expectedAmountText = "0.00";
    	final BigDecimal expectedAmount = new BigDecimal(expectedAmountText);
    	final String initialAmountText = "1.00";
    	final BigDecimal initialAmount = new BigDecimal(initialAmountText);
    	model.setBillTotal(initialAmount);
    	mActivity.updateAllFields();
    	mInstrumentation.waitForIdleSync();
    	setFocusToView(billTotalEntryView);
    	mInstrumentation.waitForIdleSync();

    	// Method under test
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
//    					mActivity.openNew();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    	
    	// Postconditions
        assertEquals("Incorrect value in field", expectedAmountText, 
        		billTotalEntryView.getText().toString());    	
        assertEquals("Incorrect value in data model", expectedAmount, 
        		model.getBillTotal());    	
    }
    
    
    // TODO finish tests
}
