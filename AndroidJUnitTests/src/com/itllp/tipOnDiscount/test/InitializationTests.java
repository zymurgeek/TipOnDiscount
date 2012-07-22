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

import java.text.NumberFormat;
import java.util.Currency;
import android.app.Instrumentation;
import android.test.SingleLaunchActivityTestCase;
import android.view.Gravity;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;

/* These tests cover the initial values displayed in each field.
 * 
 */
public class InitializationTests extends
		SingleLaunchActivityTestCase<TipOnDiscount> {
		//ActivityInstrumentationTestCase2<TipOnDiscount> {

	private NumberFormat currencyNumberFormat 
		= NumberFormat.getCurrencyInstance();
	private NumberFormat percentNumberFormat 
		= NumberFormat.getInstance();
	private Currency currency = currencyNumberFormat.getCurrency();
	String zeroCurrencyString = currencyNumberFormat.format(0).
		replace(currency.getSymbol(), "");
	String oneCentCurrencyString = currencyNumberFormat.format(0.01).
		replace(currency.getSymbol(), "");
	String zeroPercentString = null;
	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private TextView billTotalEntryView;
    private TextView billSubtotalLabelView;
    private String billSubtotalLabelString;
    private TextView currencySymbolLabelView;
    private TextView billSubtotalTextView;
    private TextView discountEntryView;
    private TextView tippableTextView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountTextView;
    private TextView splitBetweenEntryView;
    private Spinner roundUpToNearestSpinner;
    private TextView actualTipAmountTextView;
    private TextView shareDueTextView;
    //private Button bumpUpButton;
    private TextView bumpsTextView;
    //private Button bumpDownButton;
    private TextView actualTipPercentTextView;
    private TextView totalDueTextView;
    
    
	public InitializationTests() {
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
		percentNumberFormat.setMaximumFractionDigits(3);
		zeroPercentString = percentNumberFormat.format(0);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        billTotalEntryView = (TextView) mActivity.findViewById
    		(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billSubtotalLabelView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_label);
        billSubtotalLabelString = mActivity.getString
        	(com.itllp.tipOnDiscount.R.string.bill_subtotal_label);
        currencySymbolLabelView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.currency_symbol_label);
        billSubtotalTextView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableTextView = (TextView) mActivity.findViewById(
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
        //bumpUpButton = (Button)mActivity.findViewById
    	//	(com.itllp.tipOnDiscount.R.id.bump_up_button);
        bumpsTextView  = (TextView)mActivity.findViewById
    		(com.itllp.tipOnDiscount.R.id.bumps_text);
        //bumpDownButton = (Button)mActivity.findViewById
		//	(com.itllp.tipOnDiscount.R.id.bump_down_button);
        actualTipPercentTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.total_due_text);
        shareDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.share_due_text);
        
        // TODO Delete the prefs file before starting test
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					taxAmountEntryView.requestFocus();
    			        mActivity.reset();
    					billTotalEntryView.requestFocus();
    			        mActivity.reset();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }
    
    public void testInitialBillTotal() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		billTotalEntryView.getText().toString());    	
    }


    public void testBillSubtotalLabelText() {
        assertEquals("Incorrect bill subtotal label", billSubtotalLabelString,
        		(String)billSubtotalLabelView.getText());
      }
    
    
    public void testBillSubtotalCurrencySymbolText() {
        assertEquals("Incorrect currency symbol", currency.getSymbol(),
        		(String)currencySymbolLabelView.getText());    	
    }
    
    
    public void testInitialBillSubtotal() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		billSubtotalTextView.getText().toString());    	
    }


    public void testInitialDiscount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		discountEntryView.getText().toString());    	
    }

    
    public void testInitialTippableAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		tippableTextView.getText().toString());    	
    }

    
    public void testInitialTaxPercentage() {
        assertEquals("Incorrect percentage format", zeroPercentString, 
        		taxPercentEntryView.getText().toString());    	
    }

    
    public void testInitialTaxAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		taxAmountEntryView.getText().toString());    	
    }

    
    public void testInitialPlannedTipPercentage() {
        assertEquals("Incorrect percentage format", "15", 
        		plannedTipPercentEntryView.getText().toString());    	
    }

    
    public void testInitialPlannedTipAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		plannedTipAmountTextView.getText().toString());    	
    }

    
    public void testInitialSplitBetween() {
        assertEquals("Incorrect split between", "1", 
        		splitBetweenEntryView.getText().toString());    	
    }

    
    public void testInitialRoundUpToNearest() {
    	/* This field is a spinner with None, Nickel, Dime,
    	Quarter, Half Dollar, $1, $2, $5, $10, $20. */
    	String selectedItem
    		= roundUpToNearestSpinner.getSelectedItem().toString();
        assertEquals("Initial value of Round Up To Nearest is not None", 
        		"None", selectedItem);    	
    }

    
    public void testInitialBumps() {
        assertEquals("Incorrect bumps", "0", 
        		bumpsTextView.getText().toString());    	
    }

    
    public void testInitialActualTipPercent() {
        assertEquals("Incorrect actual tip percent", zeroPercentString, 
        		actualTipPercentTextView.getText().toString());    	
    }

    
    public void testInitialActualTipAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		actualTipAmountTextView.getText().toString());    	
    }

    
    public void testInitialTotalDueAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		totalDueTextView.getText().toString());    	
    }


    public void testInitialBillShareAmount() {
        assertEquals("Incorrect currency format", zeroCurrencyString, 
        		shareDueTextView.getText().toString());    	
    }

    
    /* Verifies the gravity of the given text view or edit view is 
     * right-aligned.
     */
    private void verifyRightGravity(TextView view) {
    	int g = view.getGravity() & Gravity.RIGHT;
    	String errMsg = "Field ID " + view.getId() 
    		+ " does not have right-aligned gravity"; 
    	assertEquals(errMsg, Gravity.RIGHT, g); 
    }
    
    
    public void testGravity() {
    	verifyRightGravity(billTotalEntryView);
    	verifyRightGravity(taxPercentEntryView);
    	verifyRightGravity(taxAmountEntryView);
    	verifyRightGravity(billSubtotalTextView);
    	verifyRightGravity(discountEntryView);
    	verifyRightGravity(tippableTextView);
    	verifyRightGravity(plannedTipPercentEntryView);
    	verifyRightGravity(plannedTipAmountTextView);
    	verifyRightGravity(actualTipAmountTextView);
    	verifyRightGravity(totalDueTextView);
    	verifyRightGravity(shareDueTextView);
    }
    
    

}
