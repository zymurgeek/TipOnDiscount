package com.itllp.tipOnDiscount.model.impl;

import java.math.BigDecimal;

import android.content.Context;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelInitializer;

public class DataModelInitializerFromPersistedDefaults implements
		DataModelInitializer {

	public static final BigDecimal DEFAULT_BILL_TOTAL = BigDecimal.ZERO.setScale(2);
	public static final int DEFAULT_SPLIT_BETWEEN = 1;
	public static final int DEFAULT_BUMPS = 0;
	private DefaultsPersister defaultsPersister;
	private Defaults defaults;
	
	
	public DataModelInitializerFromPersistedDefaults(
			DefaultsPersister dp, Defaults d) {
		defaultsPersister = dp;
		defaults = d;
	}
	
	
	@Override
	public void initialize(DataModel dataModel, Context context) {
		defaultsPersister.restoreState(defaults, context);
		final BigDecimal percentToRateMultiplicand = new BigDecimal(".01");
		
		dataModel.setBillTotal(DEFAULT_BILL_TOTAL);
		BigDecimal taxRate = defaults.getTaxPercent()
				.multiply(percentToRateMultiplicand);
		dataModel.setTaxRate(taxRate);
		dataModel.setDiscount(getDiscount());
		BigDecimal tipRate = defaults.getTipPercent()
				.multiply(percentToRateMultiplicand);
		dataModel.setPlannedTipRate(tipRate);
		dataModel.setSplitBetween(DEFAULT_SPLIT_BETWEEN);
		BigDecimal roundUpToAmount = defaults.getRoundUpToAmount();
		dataModel.setRoundUpToAmount(roundUpToAmount);
		dataModel.setBumps(DEFAULT_BUMPS);
	}


	@Override
	public BigDecimal getDiscount() {
		return BigDecimal.ZERO.setScale(2);
	}

}
