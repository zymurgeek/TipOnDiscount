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
	
	@Override
	public BigDecimal getTaxRate(Context context) {
		defaultsPersister.restoreState(defaults, context);
		
		BigDecimal taxPercent = defaults.getTaxPercent();
		BigDecimal taxRate;
		if (null != taxPercent) {
			taxRate = taxPercent.multiply(percentToRateMultiplicand);
		} else {
			taxRate = super.getTaxRate(context);
		}
		return taxRate;
	}
	
	@Override
	public BigDecimal getTipRate(Context context) {
		defaultsPersister.restoreState(defaults, context);
		
		BigDecimal tipPercent = defaults.getTipPercent();
		BigDecimal tipRate;
		if (null != tipPercent) {
			tipRate = tipPercent.multiply(percentToRateMultiplicand);
		} else {
			tipRate = super.getTipRate(context);
		}
		return tipRate;
	}
	
	@Override
	public BigDecimal getRoundUpToAmount(Context context) {
		defaultsPersister.restoreState(defaults, context);
		
		BigDecimal roundUpToAmount = defaults.getRoundUpToAmount();
		if (null == roundUpToAmount) {
			roundUpToAmount = super.getRoundUpToAmount(context);
		}
		return roundUpToAmount;
	}
}
