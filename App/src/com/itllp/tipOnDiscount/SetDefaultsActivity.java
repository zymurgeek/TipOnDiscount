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

import java.math.BigDecimal;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class SetDefaultsActivity extends Activity {

	private TextView taxPercentEntry;
	private TextView plannedTipPercentEntry;
	private Spinner roundUpToNearestSpinner;
	private BigDecimalLabelMap map;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Unlock screen for Android JUnit tests
        Window window = getWindow();  
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.defaults);

        taxPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.defaults_tax_percent_entry);
        plannedTipPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.defaults_planned_tip_percent_entry);
        map = new BigDecimalLabelMap(
        		getResources().getStringArray(R.array.round_up_to_nearest_value_array),
        		getResources().getStringArray(R.array.round_up_to_nearest_label_array));
        roundUpToNearestSpinner = (Spinner)this.findViewById
			(com.itllp.tipOnDiscount.R.id.defaults_round_up_to_nearest_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        	this, R.array.round_up_to_nearest_label_array, 
        	android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundUpToNearestSpinner.setAdapter(adapter);
    }

    
    @Override
    public void onResume() {
    	super.onResume();
    	
        Defaults defaults = DefaultsFactory.getDefaults();
        DefaultsPersister defaultsPersister = 
        		DefaultsPersisterFactory.getDefaultsPersister();
        defaultsPersister.restoreState(defaults, this);
        taxPercentEntry.setText(defaults.getTaxPercent().toPlainString());
        plannedTipPercentEntry.setText(defaults.getTipPercent().toPlainString());
        BigDecimal defaultRoundUpToAmount = defaults.getRoundUpToAmount();
        String defaultRoundUpTo = map.getLabel(defaultRoundUpToAmount);
        int position = map.getPosition(defaultRoundUpTo);
        roundUpToNearestSpinner.setSelection(position);
    }

    
    @Override
    public void onPause() {
    	super.onPause();
    	
    	Defaults defaults = DefaultsFactory.getDefaults();
    	
    	String taxPercentString = taxPercentEntry.getText().toString();
    	try {
    		BigDecimal taxPercent = new BigDecimal(taxPercentString);
    		defaults.setTaxPercent(taxPercent);
    	} catch (NumberFormatException e) {
    		// Ignore bad number and don't save default
    	}
    	
    	String tipPercentString = plannedTipPercentEntry.getText().toString();
      	try {
    		BigDecimal tipPercent = new BigDecimal(tipPercentString);
    		defaults.setTipPercent(tipPercent);
    	} catch (NumberFormatException e) {
    		// Ignore bad number and don't save default
    	}
  
    	String selectedRoundUpToLabel = roundUpToNearestSpinner.getSelectedItem().toString();
    	BigDecimal roundUpToAmount = map.getValue(selectedRoundUpToLabel);
    	defaults.setRoundUpToAmount(roundUpToAmount);
    	
    	DefaultsPersister persister = DefaultsPersisterFactory.getDefaultsPersister();
    	persister.saveState(defaults, this);
    }
}
