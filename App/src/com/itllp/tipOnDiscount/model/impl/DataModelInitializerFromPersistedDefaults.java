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
