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
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Set;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.test.StubPersister;
import com.itllp.tipOnDiscount.test.R;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

/* These test are related to user interface operation only, such as
 * data formatting and widget operations. 
 */
public class SetDefaultsInitializationFromPersistenceTests extends
		ActivityInstrumentationTestCase2<SetDefaultsActivity> {
	private Instrumentation mInstrumentation;
    private SetDefaultsActivity mActivity;
    private EditText taxPercentEntryView;
    private EditText plannedTipPercentEntryView;
	private Spinner roundUpToNearestSpinner;
	private StubPersister stubPersister;
	
	@SuppressWarnings("deprecation")
	public SetDefaultsInitializationFromPersistenceTests() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", SetDefaultsActivity.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // TODO Should there be a DefaultsPersister?
        stubPersister = new StubPersister();
        PersisterFactory.setPersisterForDefaults(stubPersister);
        stubPersister.save("DEFAULT_TAX_PERCENT", new BigDecimal("15"));
        mInstrumentation = getInstrumentation();
        mActivity = this.getActivity();

        taxPercentEntryView = (EditText) mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        plannedTipPercentEntryView = (EditText) mActivity.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        roundUpToNearestSpinner = (Spinner)mActivity.findViewById
        	(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
    }
    
    
    public void testPreconditions() {
    	assertNotNull(taxPercentEntryView);
    	assertNotNull(plannedTipPercentEntryView);
    	assertNotNull(roundUpToNearestSpinner);
    }
    

    public void testInitialDefaultTaxPercent() {
    	// Verify postconditions
    	String actualTaxPercentString = taxPercentEntryView.getText().toString();
    	BigDecimal actualTaxPercent = new BigDecimal(actualTaxPercentString);
    	BigDecimal expectedTaxPercent = stubPersister.retrieveBigDecimal(mActivity, "DEFAULT_TAX_PERCENT");
    	assertTrue("Incorrect default tax percent",
    			0==expectedTaxPercent.compareTo(actualTaxPercent));
    }
    
    
//    public void testInitialDefaultTipPercent() {
//    	assertEquals("Incorrect default planned tip percent",
//    			SetDefaultsActivity.DEFAULT_PLANNED_TIP_PERCENT,
//    			plannedTipPercentEntryView.getText().toString());
//    }
//
//    
//    public void testInitialDefaultRoundUpToNearest() {
//    	String expectedLabel = mActivity.getResources().getString(com.itllp
//    			.tipOnDiscount.R.string.default_round_up_to_nearest_label);
//    	String actualLabel = roundUpToNearestSpinner.getSelectedItem().
//    			toString();
//    	assertEquals("Incorrect default round up to nearest",
//    			expectedLabel, actualLabel);
//    }


}
