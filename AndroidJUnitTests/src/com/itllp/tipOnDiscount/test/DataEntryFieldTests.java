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
import java.util.Set;

import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

/* These test are related to user interface operation only, such as
 * data formatting and widget operations. 
 */
public class DataEntryFieldTests extends
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
	private final int[] keyCodes1Dot23 = {
        	KeyEvent.KEYCODE_1,
        	KeyEvent.KEYCODE_PERIOD,
        	KeyEvent.KEYCODE_2,
        	KeyEvent.KEYCODE_3 }; 
	private final int[] keyCodes7Dot = {
        	KeyEvent.KEYCODE_7,
        	KeyEvent.KEYCODE_PERIOD }; 
	private final int[] keyCodes7Dot125 = {
        	KeyEvent.KEYCODE_7,
        	KeyEvent.KEYCODE_PERIOD,
        	KeyEvent.KEYCODE_1,
        	KeyEvent.KEYCODE_2,
        	KeyEvent.KEYCODE_5 }; 
	private final int [] keyCodes07 = {
    		KeyEvent.KEYCODE_0,
    		KeyEvent.KEYCODE_7 };
	private final int[] keyCodes7 = {
			KeyEvent.KEYCODE_7,
			KeyEvent.KEYCODE_PERIOD };
	private final int[] keyCodes7Dot0 = {
    		KeyEvent.KEYCODE_7,
    		KeyEvent.KEYCODE_PERIOD,
    		KeyEvent.KEYCODE_0 };
	private final int[] keyCodes4Enter = {
    		KeyEvent.KEYCODE_4,
    		KeyEvent.KEYCODE_ENTER };

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
	public DataEntryFieldTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
		percentNumberFormat.setMaximumFractionDigits(3);
		zeroPercentString = percentNumberFormat.format(0);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DataModel stubDataModel = new StubDataModel();
    	DataModelFactory.setDataModel(stubDataModel);
    	DataModelPersisterFactory.setDataModelPersister(
    			new StubDataModelPersister());
        mInstrumentation = getInstrumentation();
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
    
    
    /* Sets focus to the given TextViewfield and sends keypresses to it.
     */
    private void sendKeysToView(final TextView view, int[] keyCodes) {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					view.requestFocus();
    					view.setText("");
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    	for (int keyCode : keyCodes) {
    		this.sendKeys(keyCode);
    	}
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					if (view != billTotalEntryView) {
    						billTotalEntryView.requestFocus();
    					} else {
    						taxPercentEntryView.requestFocus();
    					}
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    }

    /* Verify proper formatting of currency fields.
     */
    public void verifyAmountFieldFormatting(TextView view, 
    		final String viewName) {
    	String assertErrorMessage = "Incorrect " + viewName
    		+ " amount";
    	
        // TODO Update tests to work with non-US dollar currency
        // Verify input "1.23" formats to "1.23"
    	sendKeysToView(view, keyCodes1Dot23);
        assertEquals(assertErrorMessage, "1.23", view.getText().toString());    	

        // Verify input "7" formats to "7.00"
    	sendKeysToView(view, keyCodes7);
        assertEquals(assertErrorMessage, "7.00", view.getText().toString());    	

        // Verify input "7." formats to "7.00"
    	sendKeysToView(view, keyCodes7Dot);
        assertEquals(assertErrorMessage, "7.00", view.getText().toString());    	

        // Verify input "7.0" formats to "7.00"
    	sendKeysToView(view, keyCodes7Dot0);
        assertEquals(assertErrorMessage, "7.00", view.getText().toString());    	

        // Verify input "07" formats to "7.00"
    	sendKeysToView(view, keyCodes07);
        assertEquals(assertErrorMessage, "7.00", view.getText().toString());
        
        // Verify input "7.125" formats to "7.13"
    	sendKeysToView(view, keyCodes7Dot125);
        assertEquals(assertErrorMessage, "7.13", view.getText().toString());
    }
    
    
    /* Verify proper formatting of percent fields.
     */
    public void testPercentFieldFormatting(TextView view, 
    		final String viewName) {
    	String assertErrorMessage = "Incorrect " + viewName
    		+ " percent";

        // Verify input "7.125" formats to "7.125"
    	sendKeysToView(view, keyCodes7Dot125);
        assertEquals(assertErrorMessage, "7.125", view.getText().toString());    	

        // Verify input "07" formats to "7"
    	sendKeysToView(view, keyCodes07);
        assertEquals(assertErrorMessage, "7", view.getText().toString());
        
        // Verify input "7" formats to "7"
    	sendKeysToView(view, keyCodes7);
        assertEquals(assertErrorMessage, "7", view.getText().toString());    	

        // Verify input "7.0" formats to "7"
    	sendKeysToView(view, keyCodes7Dot0);
        assertEquals(assertErrorMessage, "7", view.getText().toString());
    }
    
    
    public void testAAA_ScreenMustNotBeLocked() {
    	KeyguardManager km = (KeyguardManager)mActivity.getSystemService
    			(Context.KEYGUARD_SERVICE);
    	boolean isScreenLocked = km.inKeyguardRestrictedInputMode();
    	assertFalse("Unlock the device screen to run these tests",
    			isScreenLocked);
    }
    
    
    /* Verify proper formatting of values in the bill total field.
     */
    public void testBillTotalFormatting() {
    	verifyAmountFieldFormatting(billTotalEntryView, "bill total");
    }
    
   

    /* Verify when data is pasted into the bill total field that the value
     * is properly formatted in the field and the value is passed to
     * the data model.
     */
    public void testBillTotalPaste() {
    	// Preconditions
    	StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	final String amountText = "120.56";
    	final BigDecimal amount = new BigDecimal(amountText);

    	// Method under test
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					billTotalEntryView.setText(amountText);
        			}
        		}
            );
    	mInstrumentation.waitForIdleSync();

    	// Postconditions
        assertEquals("Incorrect value in field", amountText, 
        		billTotalEntryView.getText().toString());    	
        assertEquals("Incorrect value in data model", amount, 
        		model.getBillTotal());    	
    }
    
    
    /* Test formatting of values in the discount field.
     */
    public void testDiscountFormatting() {
    	verifyAmountFieldFormatting(discountEntryView, "discount");
    }

    
    /* Verify proper formatting of data in the tax percent field.
     */
    public void testTaxPercentFormatting() {
    	testPercentFieldFormatting(taxPercentEntryView, "tax percent");
    }
    

    /* Test formatting of values in the tax amount field.
     */
    public void testTaxAmountFormatting() {
    	verifyAmountFieldFormatting(this.taxAmountEntryView, "tax amount");
    }
    
    
    
    /* Test formatting of values in the planned tip amount field.
     */
    public void testPlannedTipPercentFormatting() {
    	testPercentFieldFormatting(plannedTipPercentEntryView, "tip");
    }

    
    /* Tests that the split-between field only accepts numbers. */
    public void testSplitBetweenNumeric() {
       	sendKeysToView(this.splitBetweenEntryView, keyCodes4Enter);
        assertEquals("Split between should not accept non-numeric characters", 
        		"4", this.splitBetweenEntryView.getText().toString());
    }
    
    
    private void setRoundUpToAmount(final String amount) {
    		mActivity.runOnUiThread(
        			new Runnable() {
        				public void run() {
        					int itemCount = roundUpToNearestSpinner.getCount();
        					for (int position = 0; position < itemCount;
        						++position) {
        						if (roundUpToNearestSpinner.getItemAtPosition(position)
        								.equals(amount)) {
            						roundUpToNearestSpinner.setSelection(position);
            						break;
        						}
        					}
        				}
        			}
            	);
        	mInstrumentation.waitForIdleSync();
    }
    
    
    /* Test that the Round Up To Nearest spinner sets the round up to amount in 
     * the data model. */
    public void testRoundUptoAmounts() {
    	// Set up preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
		String[] valueStringArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
		String[] labelArray = mActivity.getResources().getStringArray
				(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
		BigDecimalLabelMap map = new BigDecimalLabelMap(valueStringArray, labelArray);
		Set<BigDecimal> valueSet = map.keySet();
		setRoundUpToAmount(labelArray[1]);  // spinner must change to activate test
		for (BigDecimal value : valueSet) {
			String label = map.getLabel(value);

	    	// Method under test
	    	setRoundUpToAmount(label);
	    	
	    	// Postconditions
	    	assertTrue("Incorrect amount for RoundUpToNearest "+label,
	    			0==value.compareTo(model.getRoundUpToAmount()));
		}
    }
    
    
    /** Test that the bump down button tells the data model to reduce
     * the bump count.
     */
    public void testBumpDownButtonUpdatesDataModel() {
    	// Set up preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	
    	// Method under test
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	bumpDownButton.performClick();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    	
    	
    	// Check postconditions
    	assertEquals("Bump Down button didn't call data model bumpDown",
    			1, model.getBumpDownCallCount());
    }


    /** Test that the bump up button tells the data model to increase
     * the bump count.
     */
    public void testBumpUpButtonUpdatesDataModel() {
    	// Set up preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	
    	// Method under test
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	bumpUpButton.performClick();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
    	
    	
    	// Check postconditions
    	assertEquals("Bump Up button didn't call data model bumpUp",
    			1, model.getBumpUpCallCount());
    }


}
