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

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelInitializer;

public class SimpleDataModelInitializer implements DataModelInitializer {

	private static final BigDecimal ROUND_UP_TO_AMOUNT = new BigDecimal("0.01");

	@Override
	public void initialize(DataModel dataModel, Context context) {
		dataModel.setBillTotal(getBillTotal());
		dataModel.setTaxRate(getTaxRate(context));
		dataModel.setDiscount(getDiscount());
		dataModel.setPlannedTipRate(getTipRate());
		dataModel.setSplitBetween(getSplitBetween());
		dataModel.setRoundUpToAmount(getRoundUpToAmount());
		dataModel.setBumps(getBumps());
	}

	@Override
	public BigDecimal getDiscount() {
		return BigDecimal.ZERO.setScale(2);
	}

	@Override
	public BigDecimal getBillTotal() {
		return BigDecimal.ZERO.setScale(2);
	}

	@Override
	public BigDecimal getTaxRate(Context unused) {
		return BigDecimal.ZERO.setScale(5);
	}

	@Override
	public BigDecimal getTipRate() {
		return BigDecimal.ZERO.setScale(5);
	}

	@Override
	public int getSplitBetween() {
		return 1;
	}

	@Override
	public BigDecimal getRoundUpToAmount() {
		return ROUND_UP_TO_AMOUNT;
	}

	@Override
	public int getBumps() {
		return 0;
	}

}
