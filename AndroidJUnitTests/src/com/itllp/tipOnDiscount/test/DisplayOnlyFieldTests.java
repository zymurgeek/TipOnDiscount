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

/** This Android JUnit test harness tests the values displayed
 * by fields that do not accept user input, i.e., they are
 * updated by the data model.
 */
package com.itllp.tipOnDiscount.test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.model.update.ActualTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.BillSubtotalUpdate;
import com.itllp.tipOnDiscount.model.update.ShareDueUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TippableAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TotalDueUpdate;
import com.itllp.tipOnDiscount.model.update.Update;

/* These test are related to user interface operation only, such as
 * data formatting and widget operations. 
 */
public class DisplayOnlyFieldTests extends
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
    private TextView billSubtotalView;
    private TextView tippableAmountView;
    private TextView plannedTipAmountView;
    private Button bumpDownButton;
    private TextView bumpsTextView;
    private Button bumpUpButton;
    private TextView actualTipAmountView;
    private TextView totalDueTextView;
    private TextView shareDueTextView;
	private Spinner roundUpToNearestSpinner;
    
    
	@SuppressWarnings("deprecation")
	public DisplayOnlyFieldTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
		percentNumberFormat.setMaximumFractionDigits(3);
		zeroPercentString = percentNumberFormat.format(0);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
    	DataModelFactory.setDataModel(new StubDataModel());
    	DataModelPersisterFactory.setDataModelPersister(
    			new StubDataModelPersister());
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        billSubtotalView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        tippableAmountView = (TextView) mActivity.findViewById(
        		com.itllp.tipOnDiscount.R.id.tippable_amount_text);
        plannedTipAmountView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
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
        assertNotNull(billSubtotalView);
        assertNotNull(tippableAmountView);
        assertNotNull(plannedTipAmountView);
        assertNotNull(roundUpToNearestSpinner);
        assertNotNull(bumpDownButton);
        assertNotNull(bumpsTextView);
        assertNotNull(bumpUpButton);
        assertNotNull(actualTipAmountView);
        assertNotNull(totalDueTextView);
        assertNotNull(shareDueTextView);
      }
    
    
    /* Test display of the bill subtotal field by data model update.
     */
    public void testBillSubtotalDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "42.50";
    	BigDecimal amount = new BigDecimal(amountText);
    	final BillSubtotalUpdate update = new BillSubtotalUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect bill subtotal", amountText, 
        	billSubtotalView.getText().toString());    	
    }


    /* Test display of the bill subtotal field by data model query.
     */
    public void testBillSubtotalDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "94.12";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setBillSubtotal(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect bill subtotal", amountText, 
        	this.billSubtotalView.getText().toString());    	
    }

    
    /* Test display of the tippable amount field by data model update.
     */
    public void testTippableAmountDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "156.82";
    	BigDecimal amount = new BigDecimal(amountText);
    	final TippableAmountUpdate update = new TippableAmountUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect tippable amount", amountText, 
        	tippableAmountView.getText().toString());    	
    }


    /* Test display of the tippable amount field by data model query.
     */
    public void testTippableAmountDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "67.89";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setTippableAmount(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect tippable amount", amountText, 
        	this.tippableAmountView.getText().toString());    	
    }

    
    /* Test display of the planned tip amount field by data model update.
     */
    public void testPlannedTipAmountDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "6.49";
    	BigDecimal amount = new BigDecimal(amountText);
    	final PlannedTipAmountUpdate update = new PlannedTipAmountUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect planned tip amount", amountText, 
        	plannedTipAmountView.getText().toString());
    }

    /* Test display of the planned tip amount field by data model query.
     */
    public void testPlannedTipAmountDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "2.50";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setPlannedTipAmount(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect planned tip amount", amountText, 
        	this.plannedTipAmountView.getText().toString());    	
    }

    
    /* Test display of update actual tip amount field by data model update.
     */
    public void testActualTipAmountDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "1.23";
    	BigDecimal amount = new BigDecimal(amountText);
    	final ActualTipAmountUpdate update = new ActualTipAmountUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect actual tip amount", amountText, 
        	actualTipAmountView.getText().toString());    	
    }


    /* Test display of the actual tip amount field by data model query.
     */
    public void testActualTipAmountDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "16.00";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setActualTipAmount(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect actual tip amount", amountText, 
        	this.actualTipAmountView.getText().toString());    	
    }

    
    /* Test display of the total due field by data model update.
     */
    public void testTotalDueDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "390.00";
    	BigDecimal amount = new BigDecimal(amountText);
    	final TotalDueUpdate update = new TotalDueUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect total due", amountText, 
        	totalDueTextView.getText().toString());    	
    }


    /* Test display of the total due field by data model query.
     */
    public void testTotalDueDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "77.78";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setTotalDue(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect total due", amountText, 
        	this.totalDueTextView.getText().toString());    	
    }

    


    /* Test display of the share due field by data model update.
     */
    public void testShareDueDisplayByDataModelUpdate() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String amountText = "491.23";
    	BigDecimal amount = new BigDecimal(amountText);
    	final ShareDueUpdate update = new ShareDueUpdate(amount);
    	
    	// Method under test
    	simulateDataModelUpdate(model, update);

    	// Postconditions
        assertEquals("Incorrect share due", amountText, 
        	this.shareDueTextView.getText().toString());    	
    }


    /* Test display of the share due field by data model query.
     */
    public void testShareDueDisplayByDataModelQuery() {
    	// Preconditions
    	final StubDataModel model = (StubDataModel)mActivity.getDataModel();
    	String amountText = "192.45";
    	BigDecimal amount = new BigDecimal(amountText);
    	model.setShareDue(amount);
    	
    	// Method under test
    	updateAllFieldsFromDataModel();

    	// Postconditions
        assertEquals("Incorrect share due", amountText, 
        	this.shareDueTextView.getText().toString());    	
    }


    private void updateAllFieldsFromDataModel() {
    	mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
			    	mActivity.updateAllFields();
				}
			}
		);
    	mInstrumentation.waitForIdleSync();
    }


	private void simulateDataModelUpdate(final DataModel model,
			final Update update) {
		mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mActivity.update(model, update);
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
	}


}
