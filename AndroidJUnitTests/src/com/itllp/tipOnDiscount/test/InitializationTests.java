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

import java.text.NumberFormat;
import java.util.Currency;
import android.app.Instrumentation;
import android.test.SingleLaunchActivityTestCase;
import android.view.Gravity;
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
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

/* These tests cover the initial values displayed in each field.
 * 
 */
public class InitializationTests extends
		SingleLaunchActivityTestCase<TipOnDiscount> {

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
    private TextView billTotalCurrencySymbolLabelView;
    private TextView billSubtotalLabelView;
    private String billSubtotalLabelString;
    private TextView billSubtotalCurrencySymbolLabelView;
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
    private TextView bumpsTextView;
    private TextView actualTipPercentTextView;
    private TextView totalDueTextView;
    private DataModel dataModel;
    private StubDataModelInitializer stubDataModelInitializer;
    
    
	public InitializationTests() {
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
    	DataModelPersisterFactory.setDataModelPersister(
    			new StubDataModelPersister());
    	DataModelFactory.setDataModel(new StubDataModel());
    	stubDataModelInitializer = new StubDataModelInitializer();
    	DataModelInitializerFactory.setDataModelInitializer(stubDataModelInitializer);
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
        billTotalCurrencySymbolLabelView = (TextView)mActivity.findViewById
            	(com.itllp.tipOnDiscount.R.id.bill_total_currency_symbol_label);
        billSubtotalLabelView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_label);
        billSubtotalLabelString = mActivity.getString
        	(com.itllp.tipOnDiscount.R.string.bill_subtotal_label);
        billSubtotalCurrencySymbolLabelView = (TextView)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_currency_symbol_label);
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
        bumpsTextView  = (TextView)mActivity.findViewById
    		(com.itllp.tipOnDiscount.R.id.bumps_text);
        actualTipPercentTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.total_due_text);
        shareDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.share_due_text);
        
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
    	
    	dataModel = DataModelFactory.getDataModel();
    }

    
    public void testDataModelInitialization() {
    	// Verify postconditions
    	assertTrue("Data model was not initialized", stubDataModelInitializer.
    			stub_wasInitializeCalled());
    }
    
    public void testInitialBillTotal() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_BILL_TOTAL.toPlainString(), 
        		billTotalEntryView.getText().toString());    	
    }


    public void testBillTotalCurrencySymbolText() {
        assertEquals("Incorrect currency symbol", currency.getSymbol(),
        		(String)billTotalCurrencySymbolLabelView.getText());    	
    }
    
    
    public void testBillSubtotalLabelText() {
        assertEquals("Incorrect bill subtotal label", billSubtotalLabelString,
        		(String)billSubtotalLabelView.getText());
      }
    
    
    public void testBillSubtotalCurrencySymbolText() {
        assertEquals("Incorrect currency symbol", currency.getSymbol(),
        		(String)billSubtotalCurrencySymbolLabelView.getText());    	
    }
    
    
    public void testInitialBillSubtotal() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_BILL_SUBTOTAL.toPlainString(), 
        		billSubtotalTextView.getText().toString());    	
    }


    public void testInitialDiscount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_DISCOUNT.toPlainString(), 
        		discountEntryView.getText().toString());    	
    }

    
    public void testInitialTippableAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_TIPPABLE_AMOUNT.toPlainString(), 
        		tippableTextView.getText().toString());    	
    }

    
    public void testInitialTaxPercentage() {
        assertEquals("Incorrect percentage format", 
        		TipOnDiscount.formatRateToPercent(StubDataModel.INITIAL_TAX_RATE), 
        		taxPercentEntryView.getText().toString());    	
    }

    
    public void testInitialTaxAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_TAX_AMOUNT.toPlainString(), 
        		taxAmountEntryView.getText().toString());    	
    }

    
    public void testInitialPlannedTipPercentage() {
    			
    	String expectedPlannedTipPercentage = 
    			TipOnDiscount.formatRateToPercent(dataModel.getPlannedTipRate());
        assertEquals("Incorrect percentage format", 
        		expectedPlannedTipPercentage, 
        		plannedTipPercentEntryView.getText().toString());    	
    }

    
    public void testInitialPlannedTipAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_PLANNED_TIP_AMOUNT.toPlainString(), 
        		plannedTipAmountTextView.getText().toString());    	
    }

    
    public void testInitialSplitBetween() {
        assertEquals("Incorrect split between", 
        		String.valueOf(StubDataModel.INITIAL_SPLIT_BETWEEN), 
        		splitBetweenEntryView.getText().toString());    	
    }

    
    public void testInitialRoundUpToNearest() {
    	// Set up preconditions
		String[] valueArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
		String[] labelArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
		BigDecimalLabelMap map = new BigDecimalLabelMap(valueArray, labelArray);
		String expectedLabel = map.getLabel(StubDataModel.INITIAL_ROUND_UP_TO_AMOUNT);

		// Verify postconditions
    	String selectedItem
    		= roundUpToNearestSpinner.getSelectedItem().toString();
        assertEquals("Incorrect initial value of Round Up To Nearest", 
        		expectedLabel, selectedItem);    	
    }

    
    public void testInitialBumps() {
        assertEquals("Incorrect bumps",
        		String.valueOf(StubDataModel.INITIAL_BUMPS), 
        		bumpsTextView.getText().toString());    	
    }

    
    public void testInitialActualTipPercent() {
        assertEquals("Incorrect actual tip percent", 
        		TipOnDiscount.formatRateToPercent(StubDataModel.INITIAL_ACTUAL_TIP_RATE), 
        		actualTipPercentTextView.getText().toString());    	
    }

    
    public void testInitialActualTipAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_ACTUAL_TIP_AMOUNT.toPlainString(), 
        		actualTipAmountTextView.getText().toString());    	
    }

    
    public void testInitialTotalDueAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_TOTAL_DUE.toPlainString(), 
        		totalDueTextView.getText().toString());    	
    }


    public void testInitialBillShareAmount() {
        assertEquals("Incorrect currency format", 
        		StubDataModel.INITIAL_SHARE_DUE.toPlainString(), 
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
