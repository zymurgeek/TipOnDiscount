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
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.test.model.MockDataModel;

public class PauseTests extends
	ActivityInstrumentationTestCase2<TipOnDiscount>{

	private Instrumentation mInstrumentation;
    private TipOnDiscount mActivity;
    private TextView billTotalEntryView;
    private TextView billSubtotalTextView;
    private TextView discountEntryView;
    private TextView tippableAmountTextView;
    private TextView taxPercentEntryView;
    private TextView taxAmountEntryView;
    private TextView plannedTipPercentEntryView;
    private TextView plannedTipAmountTextView;
    private TextView splitBetweenEntryView;
    private Spinner roundUpToNearestSpinner;
    private TextView bumpsTextView;
    private TextView actualTipPercentTextView;
    private TextView actualTipAmountTextView;
    private TextView totalDueTextView;
    private TextView shareDueTextView;

    
	public PauseTests() {
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
		setActivityInitialTouchMode(false);  // Enable sending key events
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

        // TODO How to disable keylock without TipOnDiscount having DISABLE_KEYGUARD permission
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					KeyguardManager mKeyGuardManager 
    						= (KeyguardManager) mActivity.getSystemService
    						(android.content.Context.KEYGUARD_SERVICE);
    					KeyguardManager.KeyguardLock mLock = mKeyGuardManager
    						.newKeyguardLock("com.itllp.tipOnDiscount");
    					mLock.disableKeyguard();
    				}
    			}
        	);
    	mInstrumentation.waitForIdleSync();
        
        billTotalEntryView = (TextView) mActivity.findViewById
    		(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billSubtotalTextView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);
        discountEntryView = (TextView) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.discount_entry);
        tippableAmountTextView = (TextView) mActivity.findViewById(
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
        bumpsTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.bumps_text);
        actualTipPercentTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        actualTipAmountTextView = (TextView)mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        totalDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.total_due_text);
        shareDueTextView = (TextView) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.share_due_text);
        
    	final DataModel model = mActivity.getDataModel();
    	model.initialize();
    }
    
    
    private void pauseAndResume() {
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

    	// Resume app
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mInstrumentation.callActivityOnResume(mActivity);
    				}
    			}
        	);
    	
    	mInstrumentation.waitForIdleSync();
    }
    
    
    private void updateAllFields() {
    	mActivity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    			    	mActivity.updateAllFields();;
    				}
    			}
        	);

    	mInstrumentation.waitForIdleSync();
    }
    
    
    public void testActualTipPercentStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textRate = ".15";
    	BigDecimal rate = new BigDecimal(textRate);
    	String textPercent = TipOnDiscount.formatRateToPercent(rate);
    	model.setActualTipRate(rate);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect actual tip percent data model value", rate,
    			model.getActualTipRate());
        assertEquals("Incorrect actual tip percent field value", textPercent, 
        		actualTipPercentTextView.getText().toString());
    }
	


    
    public void testActualTipAmountStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "6.50";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setActualTipAmount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect actual tip amount data model value", amount,
    			model.getActualTipAmount());
        assertEquals("Incorrect actual tip amount field value", textAmount, 
        		actualTipAmountTextView.getText().toString());
    }
    
    
    
    
    public void testBillSubtotalStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "24.32";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setBillSubtotal(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect bill subtotal data model value", amount,
    			model.getBillSubtotal());
        assertEquals("Incorrect bill subtotal field value", textAmount, 
        		billSubtotalTextView.getText().toString());
    }
    
    
    public void testBillTotalStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textAmount = "987.65";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setBillTotal(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect bill total data model value", amount,
    			model.getBillTotal());
        assertEquals("Incorrect bill total field value", textAmount, 
            	billTotalEntryView.getText().toString());
    }
    

    public void testBumpsStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textBumps = "-1";
    	int bumps = Integer.parseInt(textBumps);
    	model.setBumps(bumps);
    	updateAllFields();
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect bumps data model value", bumps,
    			model.getBumps());
        assertEquals("Incorrect bumps field value", textBumps, 
        		bumpsTextView.getText().toString());
    }
    

    public void testDataModelSaveAndRestoreOnPause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertTrue("Data model was not saved", model.wasDataModelSaved());
    	assertTrue("Data model was not restored", model.wasDataModelRestored());
    }
    
    
    public void testDiscountStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textAmount = "4.50";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setDiscount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect discount data model value", amount,
    			model.getDiscount());
        assertEquals("Incorrect discount field value", textAmount, 
        		discountEntryView.getText().toString());
    }        

    
    public void testPlannedTipAmountStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "5.00";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setPlannedTipAmount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect planned tip amount data model value", amount,
    			model.getPlannedTipAmount());
        assertEquals("Incorrect planned tip amount field value", textAmount, 
        		plannedTipAmountTextView.getText().toString());
    }
    
    
    public void testPlannedTipPercentStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textRate = ".17000";
    	BigDecimal rate = new BigDecimal(textRate);
    	String textPercent = TipOnDiscount.formatRateToPercent(rate);
    	model.setPlannedTipRate(rate);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect planned tip percent data model value", 
    			rate, model.getPlannedTipRate());
        assertEquals("Incorrect planned tip percent field value", textPercent, 
        		plannedTipPercentEntryView.getText().toString());
    }

    
    public void testRoundUpToStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "2.00";
    	String displayAmount = "$2";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setRoundUpToAmount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect round up to amount data model value", amount,
    			model.getRoundUpToAmount());
        assertEquals("Incorrect round up to amount field value", displayAmount, 
        		roundUpToNearestSpinner.getSelectedItem().toString());
    }        

    

    
    public void testShareDueStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "54.10";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setShareDue(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect share due amount data model value", amount,
    			model.getShareDue());
        assertEquals("Incorrect share due amount field value", textAmount, 
        		shareDueTextView.getText().toString());
    }        

    
    public void testSplitBetweenStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textSplits = "3";
    	int splits = Integer.parseInt(textSplits);
    	model.setSplitBetween(splits);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect split between data model value", splits,
    			model.getSplitBetween());
        assertEquals("Incorrect tax amount field value", textSplits, 
        		splitBetweenEntryView.getText().toString());
    }
    

    public void testTaxAmountStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textAmount = "6.00";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setTaxAmount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect tax amount data model value", amount,
    			model.getTaxAmount());
        assertEquals("Incorrect tax amount field value", textAmount, 
        		taxAmountEntryView.getText().toString());
    }
    

    public void testTaxPercentStatePause() {
    	// Preconditions
    	final DataModel model = mActivity.getDataModel();
    	String textRate = ".05275";
    	BigDecimal rate = new BigDecimal(textRate);
    	String textPercent = TipOnDiscount.formatRateToPercent(rate);
    	model.setTaxRate(rate);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect tax percent data model value", rate,
    			model.getTaxRate());
        assertEquals("Incorrect tax percent field value", textPercent, 
        		taxPercentEntryView.getText().toString());
    }
	

    public void testTippableAmountStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "54.19";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setTippableAmount(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect tippable amount data model value", amount,
    			model.getTippableAmount());
        assertEquals("Incorrect tippable amount field value", textAmount, 
        		tippableAmountTextView.getText().toString());
    }        

    
    public void testTotalDueStatePause() {
    	// Preconditions
    	final MockDataModel model = (MockDataModel)mActivity.getDataModel();
    	String textAmount = "219.08";
    	BigDecimal amount = new BigDecimal(textAmount);
    	model.setTotalDue(amount);
    	
    	// Method under test
    	pauseAndResume();
    	
    	// Postconditions
    	assertEquals("Incorrect total due amount data model value", amount,
    			model.getTotalDue());
        assertEquals("Incorrect total due amount field value", textAmount, 
        		totalDueTextView.getText().toString());
    }        


}
