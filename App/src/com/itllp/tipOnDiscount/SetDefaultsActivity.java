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

package com.itllp.tipOnDiscount;

import com.itllp.tipOnDiscount.TipOnDiscount.AmountTextWatcher;
import com.itllp.tipOnDiscount.TipOnDiscount.NonZeroIntegerTextWatcher;
import com.itllp.tipOnDiscount.TipOnDiscount.PercentTextWatcher;
import com.itllp.tipOnDiscount.TipOnDiscount.RoundUpToNearestOnItemSelectedListener;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class SetDefaultsActivity extends Activity {

	public static final String DEFAULT_TAX_PERCENT = "0";
	public static final String DEFAULT_PLANNED_TIP_PERCENT = "15";
	private TextView taxPercentEntry;
	private TextView plannedTipPercentEntry;
	private Spinner roundUpToNearestSpinner;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Unlock screen for Android JUnit tests
        Window window = getWindow();  
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.defaults);

        taxPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
//        taxPercentEntry.addTextChangedListener(new PercentTextWatcher
//        		(taxPercentEntry));
//        taxPercentEntry.setOnFocusChangeListener(focusChangeListener);
        taxPercentEntry.setText(DEFAULT_TAX_PERCENT);

        plannedTipPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
//        tipPercentEntry.addTextChangedListener(new PercentTextWatcher
//        		(tipPercentEntry));
//        tipPercentEntry.setOnFocusChangeListener(focusChangeListener);
        plannedTipPercentEntry.setText(DEFAULT_PLANNED_TIP_PERCENT);
        
        roundUpToNearestSpinner = (Spinner)this.findViewById
			(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        	this, R.array.round_up_to_nearest_label_array, 
        	android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundUpToNearestSpinner.setAdapter(adapter);
//        roundUpToNearestSpinner.setOnItemSelectedListener
//        	(new RoundUpToNearestOnItemSelectedListener());
        String defaultRoundUpTo = getResources().getString
        		(R.string.default_round_up_to_nearest_label);
        BigDecimalLabelMap map = new BigDecimalLabelMap(
        		getResources().getStringArray(R.array.round_up_to_nearest_value_array),
        		getResources().getStringArray(R.array.round_up_to_nearest_label_array));
        int position = map.getPosition(defaultRoundUpTo);
        roundUpToNearestSpinner.setSelection(position);
    }

    

}