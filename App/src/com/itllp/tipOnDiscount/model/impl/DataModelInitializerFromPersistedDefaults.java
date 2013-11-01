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

public class DataModelInitializerFromPersistedDefaults 
extends SimpleDataModelInitializer 
implements DataModelInitializer {

	private final static BigDecimal percentToRateMultiplicand = new BigDecimal(".01");
	private DefaultsPersister defaultsPersister;
	private Defaults defaults;
	
	
	public DataModelInitializerFromPersistedDefaults(
			DefaultsPersister dp, Defaults d) {
		defaultsPersister = dp;
		defaults = d;
	}
	
	//TODO delete me
//	@Override
//	public void initialize(DataModel dataModel, Context context) {
//		//TODO update test so restoreState is called from each of the get methods in this class
//		defaultsPersister.restoreState(defaults, context);
//
//		super.initialize(dataModel, context);
//	}

	@Override
	public BigDecimal getTaxRate(Context context) {
		defaultsPersister.restoreState(defaults, context);
		BigDecimal taxRate = defaults.getTaxPercent()
				.multiply(percentToRateMultiplicand);
		return taxRate;
	}
	
	@Override
	public BigDecimal getTipRate() {
		BigDecimal tipRate = defaults.getTipPercent()
				.multiply(percentToRateMultiplicand);
		return tipRate;
	}
	
	@Override
	public BigDecimal getRoundUpToAmount() {
		BigDecimal roundUpToAmount = defaults.getRoundUpToAmount();
		return roundUpToAmount;
	}
}
