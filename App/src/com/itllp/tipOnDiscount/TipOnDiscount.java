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

package com.itllp.tipOnDiscount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.DataModelInitializer;
import com.itllp.tipOnDiscount.model.DataModelInitializerFactory;
import com.itllp.tipOnDiscount.model.DataModelObserver;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersister;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.update.ActualTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.ActualTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.BillSubtotalUpdate;
import com.itllp.tipOnDiscount.model.update.BillTotalUpdate;
import com.itllp.tipOnDiscount.model.update.BumpsUpdate;
import com.itllp.tipOnDiscount.model.update.DiscountUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.RoundUpToNearestUpdate;
import com.itllp.tipOnDiscount.model.update.ShareDueUpdate;
import com.itllp.tipOnDiscount.model.update.SplitBetweenUpdate;
import com.itllp.tipOnDiscount.model.update.TaxAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TaxRateUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TippableAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TotalDueUpdate;
import com.itllp.tipOnDiscount.model.update.Update;
import com.itllp.tipOnDiscount.util.BigDecimalLabelMap;

// TODO Set defaults for TIP%, Tax and Rounding

public class TipOnDiscount extends ActionBarActivity implements DataModelObserver {
	private DataModel dataModel;
	private DataModelPersister dataModelPersister;
	private TextView billTotalEntry;
	private TextView billSubtotalText;
	private TextView bumpsText;
	private TextView discountEntry;
	private TextView tippableText;
	private TextView taxPercentEntry;
	private TextView taxAmountEntry;
	private TextView tipPercentEntry;
	private TextView tipAmountText;
	private TextView splitBetweenEntry;
	private Spinner roundUpToNearestSpinner;
	BigDecimalLabelMap spinnerMap;
	private Button bumpDownButton;
	private Button bumpUpButton;
	private TextView actualTipAmountText;
	private TextView actualTipPercentText;
	private TextView totalDueText;
	private TextView shareDueText;
    public static final String NO_VALUE = "";
	

	// Create an anonymous implementation of OnFocusListener to detect when a view
	// loses focus.  Views are formatted when the focus leaves them.
	private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			if (hasFocus) {
				return;
			}
			if (view == billTotalEntry) {
				updateBillTotalEntry(null);
			}
			if (view == discountEntry) {
				updateDiscountEntry(null);
			}
			if (view == taxPercentEntry) {
		        updateTaxPercentEntry(null);
	    	}
			if (view == taxAmountEntry) {
				updateTaxAmountEntry(null);
			}
			if (view == tipPercentEntry) {
		        updateTipPercentEntry(null);
	    	}
			if (view == splitBetweenEntry) {
		        updateSplitBetweenEntry(null);
	    	}
		}
	};
	
	
	class AmountTextWatcher implements TextWatcher {
		private final TextView textView;
		
		public AmountTextWatcher(TextView textView) {
			this.textView = textView;
		}
		
		public void afterTextChanged(Editable s) {
			String text = textView.getText().toString();
			BigDecimal amount = new BigDecimal(".00");
			try {
				amount = new BigDecimal(text)
					.setScale(2, RoundingMode.HALF_UP);
	    	} catch (NumberFormatException e) {
	    		// Invalid numbers use default amount above
	    	}
	    	if (textView == discountEntry) {
	    		dataModel.setDiscount(amount);
	    	}
			if (textView == billTotalEntry) {
	    		dataModel.setBillTotal(amount);
	    	}
			if (textView == taxAmountEntry) {
	    		dataModel.setTaxAmount(amount);
	    	}
			
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,	
				int after) {}
		public void onTextChanged(CharSequence s, int start, int before, 
				int count) {}
	}
	
	
	class NonZeroIntegerTextWatcher implements TextWatcher {
		private final TextView textView;
		
		public NonZeroIntegerTextWatcher(TextView textView) {
			this.textView = textView;
		}
		
		public void afterTextChanged(Editable s) {
			String text = textView.getText().toString();
			int integer = 1;
			try {
				integer = Integer.parseInt(text);
				if (0 == integer) {
					integer = 1;
				}
	    	} catch (NumberFormatException e) {
	    		// Invalid numbers use default amount above
	    	}
	    	if (textView == splitBetweenEntry) {
	    		dataModel.setSplitBetween(integer);
	    	}
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,	
				int after) {}
		public void onTextChanged(CharSequence s, int start, int before, 
				int count) {}
	}
	
	
	class PercentTextWatcher implements TextWatcher {
		private TextView textView;
		
		public PercentTextWatcher(TextView textView) {
			this.textView = textView;
		}
		
		public void afterTextChanged(Editable s) {
	    	String text = textView.getText().toString();
			BigDecimal rate = new BigDecimal(".000");
	    	try {
	    		rate = new BigDecimal(text)
	    			.divide(new BigDecimal("100.000"), 
	    					5, RoundingMode.HALF_UP);
	    	} catch (NumberFormatException e) {
	    		// Invalid numbers use default rate above
	    	}
			if (textView == taxPercentEntry) {
	    		dataModel.setTaxRate(rate);
	    	}
			if (textView == tipPercentEntry) {
	    		dataModel.setPlannedTipRate(rate);
	    	}
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,	
				int after) {}
		public void onTextChanged(CharSequence s, int start, int before, 
				int count) {}
	}
	
	
	public class RoundUpToNearestOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long id) {
			String key = parent.getItemAtPosition(pos).toString();
			BigDecimal amount = spinnerMap.getValue(key);
			dataModel.setRoundUpToAmount(amount);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// Do nothing
		}
	}
	
	
	/** Returns the data model.  This function is for testing only.
	 * @return 
	 * 
	 */
	public DataModel getDataModel() {
		return dataModel;
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Unlock screen for Android JUnit tests
        Window window = getWindow();  
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        String[] valueStringArray = getResources().getStringArray
        		(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_value_array);
        String[] labelArray = getResources().getStringArray
        		(com.itllp.tipOnDiscount.R.array.round_up_to_nearest_label_array);
		spinnerMap = new BigDecimalLabelMap(valueStringArray, labelArray);

        dataModel = DataModelFactory.getDataModel();
        dataModelPersister = DataModelPersisterFactory.getDataModelPersister();
        
        setContentView(R.layout.main);

        dataModel.addObserver(this);
        
        billTotalEntry = (TextView)this.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_total_entry);
        billTotalEntry.addTextChangedListener(new AmountTextWatcher
        		(billTotalEntry));
        billTotalEntry.setOnFocusChangeListener(focusChangeListener);

        billSubtotalText = (TextView)this.findViewById
        	(com.itllp.tipOnDiscount.R.id.bill_subtotal_text);

        discountEntry = (TextView)this.findViewById
    		(com.itllp.tipOnDiscount.R.id.discount_entry);
        discountEntry.addTextChangedListener(new AmountTextWatcher
        		(discountEntry));
        discountEntry.setOnFocusChangeListener(focusChangeListener);

        tippableText = (TextView)this.findViewById
        	(com.itllp.tipOnDiscount.R.id.tippable_amount_text);
        
        taxPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.tax_percent_entry);
        taxPercentEntry.addTextChangedListener(new PercentTextWatcher
        		(taxPercentEntry));
        taxPercentEntry.setOnFocusChangeListener(focusChangeListener);

        taxAmountEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.tax_amount_entry);
        taxAmountEntry.addTextChangedListener(new AmountTextWatcher
        		(taxAmountEntry));
        taxAmountEntry.setOnFocusChangeListener(focusChangeListener);

        tipPercentEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_percent_entry);
        tipPercentEntry.addTextChangedListener(new PercentTextWatcher
        		(tipPercentEntry));
        tipPercentEntry.setOnFocusChangeListener(focusChangeListener);

        tipAmountText = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.planned_tip_amount_text);
        
        splitBetweenEntry = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.split_between_entry);
        splitBetweenEntry.addTextChangedListener(new NonZeroIntegerTextWatcher
        		(splitBetweenEntry));
        splitBetweenEntry.setOnFocusChangeListener(focusChangeListener);

        roundUpToNearestSpinner = (Spinner)this.findViewById
			(com.itllp.tipOnDiscount.R.id.round_up_to_nearest_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        	this, R.array.round_up_to_nearest_label_array, 
        	android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundUpToNearestSpinner.setAdapter(adapter);
        roundUpToNearestSpinner.setOnItemSelectedListener
        	(new RoundUpToNearestOnItemSelectedListener());
        
        bumpDownButton = (Button)this.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_down_button);
        bumpDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dataModel.bumpDown();
            }
        });
        bumpsText = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.bumps_text);
        bumpUpButton = (Button)this.findViewById
			(com.itllp.tipOnDiscount.R.id.bump_up_button);
        bumpUpButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		dataModel.bumpUp();
        	}
        });

        actualTipAmountText = (TextView)this.findViewById
			(com.itllp.tipOnDiscount.R.id.actual_tip_amount_text);
        
        actualTipPercentText = (TextView)this.findViewById
		(com.itllp.tipOnDiscount.R.id.actual_tip_percent_text);
        
        totalDueText = (TextView)this.findViewById
        	(com.itllp.tipOnDiscount.R.id.total_due_text);
    
        shareDueText = (TextView)this.findViewById
        	(com.itllp.tipOnDiscount.R.id.share_due_text);
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tipondiscount_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_new:
                openNew();
                return true;
            case R.id.action_set_defaults:
            	startSetDefaults();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void openNew() {
    	reset();

    	setFocusToBillTotalClearFieldAndOpenSoftKeyboard();
    }

	private void setFocusToBillTotalClearFieldAndOpenSoftKeyboard() {
		billTotalEntry.requestFocus();
		billTotalEntry.setText("");
		openSoftKeyboardIfApplicable();
	}

	private void openSoftKeyboardIfApplicable() {
		InputMethodManager imm = (InputMethodManager) getSystemService
				(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(billTotalEntry, InputMethodManager.SHOW_FORCED);
	}
    
    
    /**
     * Store the current state of the app.
     * onPause() is always called when an Activity is about to be hidden, 
     * or destroyed.
     *
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
        super.onPause();
        saveInstanceState(this);
    }


    /**
     * Restores the current state of the app by restoring the data model,
     * then calling the UI update functions to retrieve data model values.
     * onResume() is always called when an Activity is starting, 
     * or after being hidden.
     *
     * Attempts to read the state from a preferences file. If this read fails,
     * assume it was just installed, so do an initialization. 
     * Regardless, change the state of the app to be the previous position.
     *
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        restoreInstanceState(this);        
    }
    
    
    /**
     * Processes an update from the data model.
     * Each attribute of the data model has its own data type to identify 
     * which field in the UI needs to be updated.
     * @param updatedModel should always be our data model
     * @param updatedData contains the updated value
     */
	public void update(DataModel updatedModel, Update updatedData) {
		if (updatedModel == this.dataModel) {
			if (updatedData instanceof BillTotalUpdate) {
				updateBillTotalEntry((BillTotalUpdate)updatedData);
			}
			if (updatedData instanceof BillSubtotalUpdate) {
				updateBillSubtotalEntry((BillSubtotalUpdate)updatedData);
			}
			if (updatedData instanceof BumpsUpdate) {
				updateBumpsText((BumpsUpdate)updatedData);
			}
			if (updatedData instanceof PlannedTipRateUpdate) {
				updateTipPercentEntry((PlannedTipRateUpdate)updatedData);
			}
			if (updatedData instanceof PlannedTipAmountUpdate) {
				updateTipAmountEntry((PlannedTipAmountUpdate)updatedData);
			}
			if (updatedData instanceof ActualTipAmountUpdate) {
				updateActualTipAmountText((ActualTipAmountUpdate)updatedData);
			}
			if (updatedData instanceof ActualTipRateUpdate) {
				updateActualTipPercentText((ActualTipRateUpdate)updatedData);
			}
			if (updatedData instanceof DiscountUpdate) {
				updateDiscountEntry((DiscountUpdate)updatedData);
			}
			if (updatedData instanceof TippableAmountUpdate) {
				updateTippableEntry((TippableAmountUpdate)updatedData);
			}
			if (updatedData instanceof TaxAmountUpdate) {
				updateTaxAmountEntry((TaxAmountUpdate)updatedData);
			}
			if (updatedData instanceof TaxRateUpdate) {
				updateTaxPercentEntry((TaxRateUpdate)updatedData);
			}
			if (updatedData instanceof TotalDueUpdate) {
				updateTotalDueText((TotalDueUpdate)updatedData);
			}
			if (updatedData instanceof ShareDueUpdate) {
				updateShareDueText((ShareDueUpdate)updatedData);
			}
			if (updatedData instanceof SplitBetweenUpdate) {
				updateSplitBetweenEntry((SplitBetweenUpdate)updatedData);
			}
			if (updatedData instanceof RoundUpToNearestUpdate) {
				updateRoundUpToNearestEntry((RoundUpToNearestUpdate)updatedData);
			}
		}
	}
	
	
	/** Formats a rate as a percentage.  Trailing zeroes are removed. */
	public static String formatRateToPercent(BigDecimal rate) {
		rate = rate.multiply(new BigDecimal("100"));
		
		/* Drop scale down to remove trailing zeroes.
		Scale will not be allowed to reduce precision--if the scale
		gets too small, an exception is throw and no change happens. */
		for (int scale=3; scale>=0; --scale) {
			try {
				rate = rate.setScale(scale);
			} catch (ArithmeticException x) {
				// No further scaling
			}
		}
		return rate.toPlainString();
	}
	
	
	/**
	 * Resets all attributes in the data model to defaults and updates
	 * all fields in the UI.
	 */
	public void reset() {
		DataModelInitializer initializer = 
				DataModelInitializerFactory.getDataModelInitializer();
		initializer.initialize(dataModel, this);
		this.updateAllFields();
	}
	
	
	
    /**
     * Read the previous state of the app from the preferences file
     * @param context - The Activity's Context
     */
    public void restoreInstanceState(Context context) {
    	dataModelPersister.restoreState(dataModel, context);
        updateAllFields();
    }

    
	/**
	 * Updates the actual tip percent field with the value given, or if none 
	 * was supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateActualTipPercentText(ActualTipRateUpdate updatedData) {
		BigDecimal rate;
		if (updatedData != null) {
			rate = updatedData.getRate();
		} else {
			rate = this.dataModel.getActualTipRate();
		}
		actualTipPercentText.setText(formatRateToPercent(rate));
	}
	
	
	/**
	 * Updates the actual tip amount field with the value given, or if none was
	 * supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateActualTipAmountText(ActualTipAmountUpdate updatedData) {
		BigDecimal amount;
		if (updatedData != null) {
			amount = updatedData.getAmount();
		} else {
			amount = this.dataModel.getActualTipAmount();
		}
		actualTipAmountText.setText(amount.toPlainString());
	}
	
	
	/**
	 * Updates the values of all UI fields from the data model.
	 */
	public void updateAllFields() {
		updateBillTotalEntry(null);
		updateTaxPercentEntry(null);
		updateTaxAmountEntry(null);
		updateBillSubtotalEntry(null);
		updateDiscountEntry(null);
		updateTippableEntry(null);
		updateTipPercentEntry(null);
		updateTipAmountEntry(null);
		updateSplitBetweenEntry(null);
		updateRoundUpToNearestEntry(null);
		updateBumpsText(null);
		updateActualTipAmountText(null);
		updateActualTipPercentText(null);
		updateTotalDueText(null);
		updateShareDueText(null);
	}
	

	/**
	 * Updates the bill total amount field with the value given, or if none
	 * was supplied, the value from the data model. 
	 * 
	 * Does not update the field if it has the focus, as the user is still
	 * editing it.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateBillTotalEntry(BillTotalUpdate updatedData) {
		if (!billTotalEntry.isFocused() || null == updatedData) {
			BigDecimal newAmount;
			if (updatedData != null) {
				newAmount = updatedData.getAmount();
			} else {
				newAmount = this.dataModel.getBillTotal();
			}
			billTotalEntry.setText(newAmount.toPlainString());
		}
	}


	/**
	 * Updates the bill subtotal amount field with the value given, or if none
	 * was supplied, the value from the data model. 
	 * 
	 * This is not a user-editable field, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateBillSubtotalEntry(BillSubtotalUpdate updatedData) {
		BigDecimal newAmount;
		if (updatedData != null) {
			newAmount = updatedData.getAmount();
		} else {
			newAmount = this.dataModel.getBillSubtotal();
		}
		billSubtotalText.setText(newAmount.toPlainString());
	}


	/**
	 * Updates the bumps field with the value given, or if none was
	 * supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateBumpsText(BumpsUpdate updatedData) {
		int newBumps;
		if (updatedData != null) {
			newBumps = updatedData.getValue();
		} else {
			newBumps = this.dataModel.getBumps();
		}
		String bumpsValue = String.valueOf(newBumps);
		bumpsText.setText(bumpsValue);
	}


	/** Updates the discount field with the value given, or if none was
	 * supplied, the value from the data model. 
	 * 
	 * Does not update the field if it has the focus, as the user is still
	 * editing it.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateDiscountEntry(DiscountUpdate updatedData) {
		if (!discountEntry.isFocused() || null==updatedData) {
			BigDecimal newAmount;
			if (updatedData != null) {
				newAmount = updatedData.getAmount();
			} else {
				newAmount = this.dataModel.getDiscount();
			}
			discountEntry.setText(newAmount.toPlainString());
		}
	}

	
	/** 
	 * Updates the tippable amount field with the value given, or if none
	 * was supplied, the value from the data model. 
	 * 
	 * This is not a user-editable field, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateTippableEntry(TippableAmountUpdate updatedData) {
		BigDecimal newAmount;
		if (updatedData != null) {
			newAmount = updatedData.getAmount();
		} else {
			newAmount = this.dataModel.getTippableAmount();
		}
		tippableText.setText(newAmount.toPlainString());
	}


	/**
	 * Updates the total due field with the value given, or if none was
	 * supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateTotalDueText(TotalDueUpdate updatedData) {
		BigDecimal newAmount;
		if (updatedData != null) {
			newAmount = updatedData.getAmount();
		} else {
			newAmount = this.dataModel.getTotalDue();
		}
		totalDueText.setText(newAmount.toPlainString());
	}


	/**
	 * Updates the share due field with the value given, or if none was
	 * supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateShareDueText(ShareDueUpdate updatedData) {
		BigDecimal newAmount;
		if (updatedData != null) {
			newAmount = updatedData.getAmount();
		} else {
			newAmount = this.dataModel.getShareDue();
		}
		shareDueText.setText(newAmount.toPlainString());
	}
	
	
	/**
	 * Update the tax amount field with the value given, or if none was
	 * supplied, the value from the data model. 
	 * 
	 * Does not update the field if it has the focus, as the user is still
	 * editing it.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateTaxAmountEntry(TaxAmountUpdate updatedData) {
		if (!taxAmountEntry.isFocused() || null==updatedData) {
			BigDecimal newAmount;
			if (updatedData != null) {
				newAmount = updatedData.getAmount();
			} else {
				newAmount = this.dataModel.getTaxAmount();
			}
			taxAmountEntry.setText(newAmount.toPlainString());
		}
	}
	
	
	/**
	 * Updates the tax percentage field with the value given, or if none was
	 * supplied, the value from the data model. 
	 * 
	 * Does not update the field if it has the focus, as the user is still
	 * editing it.
	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateTaxPercentEntry(TaxRateUpdate updatedData) {
		if (!taxPercentEntry.isFocused() || null == updatedData) {
			BigDecimal newRate;
			if (updatedData != null) {
				newRate = updatedData.getRate();
			} else {
				newRate = this.dataModel.getTaxRate();
			}
			taxPercentEntry.setText(formatRateToPercent(newRate));
		}
	}
	

	/**
	 * Updates the value in the Tip percent field.  This field does not
	 * get changed by any means other than user input, so there's no
	 * update notification.
	 */
	private void updateTipPercentEntry(PlannedTipRateUpdate updatedData) {
		if (!tipPercentEntry.isFocused() || null==updatedData) { 
			tipPercentEntry.setText(formatRateToPercent
					(this.dataModel.getPlannedTipRate()));
		}
	}

	
	/**
	 * Updates the tip amount field with the value given, or if none was
	 * supplied, the value from the data model.  
	 * 
	 * This field does not accept user input, so focus is not checked.
 	 * 
	 * @param updatedData updated value.  If this is null, gets the value
	 * from the data model.
	 */
	private void updateTipAmountEntry(PlannedTipAmountUpdate updatedData) {
		BigDecimal amount;
		if (updatedData != null) {
			amount = updatedData.getAmount();
		} else {
			amount = this.dataModel.getPlannedTipAmount();
		}
		tipAmountText.setText(amount.toPlainString());
	}

	
	/**
	 * Updates the value in the Split Between field.
	 */
	private void updateSplitBetweenEntry(SplitBetweenUpdate updatedData) {
		if (!splitBetweenEntry.isFocused() || null==updatedData) { 
			splitBetweenEntry.setText			
				(String.valueOf(this.dataModel.getSplitBetween()));
		}
	}

	
	/**
	 * Updates the value in the Round Up To Nearest spinner.
	 */
	private void updateRoundUpToNearestEntry(RoundUpToNearestUpdate update) {
		BigDecimal roundUpToAmount;
		if (null == update) {
			roundUpToAmount = this.dataModel.getRoundUpToAmount();
		} else {
			roundUpToAmount = update.getAmount();
		}
		
		String selection = spinnerMap.getLabel(roundUpToAmount);
        int itemCount = roundUpToNearestSpinner.getCount();
        for (int position=0; position<itemCount; ++position) {
        	if (roundUpToNearestSpinner.getItemAtPosition(position)
        			.equals(selection)) {
        		roundUpToNearestSpinner.setSelection(position);
        	}
        }		
	}

	
    /**
     * Writes the app's current state to a properties repository.
     * @param context - The Activity's Context
     *
     */
    public void saveInstanceState(Context context) {
    	dataModelPersister.saveState(dataModel, context);
    }

	public void startSetDefaults() {
		Intent intent = new Intent(this, SetDefaultsActivity.class);
		startActivity(intent);
	}

	
}