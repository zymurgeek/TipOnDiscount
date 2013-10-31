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

	@Override
	public void initialize(DataModel dataModel, Context unused) {
		dataModel.setBillTotal(getBillTotal());
		dataModel.setTaxRate(getTaxRate());
		dataModel.setDiscount(getDiscount());
		dataModel.setPlannedTipRate(getTipRate());
		dataModel.setSplitBetween(getSplitBetween());
		dataModel.setRoundUpToAmount(getRoundUpToAmount());
		dataModel.setBumps(getBumps());
	}

	@Override
	public BigDecimal getDiscount() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBillTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getTaxRate() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getTipRate() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSplitBetween() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BigDecimal getRoundUpToAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getBumps() {
		// TODO Auto-generated method stub
		return 0;
	}

}
