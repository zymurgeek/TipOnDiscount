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
package com.itllp.tipOnDiscount.model.persistence.impl;

import java.math.BigDecimal;

import android.content.Context;
import android.widget.Toast;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersister;
import com.itllp.tipOnDiscount.persistence.Persister;

public class DataModelPersisterImpl implements DataModelPersister {
	private Persister persister;
	
	public DataModelPersisterImpl(Persister persister) {
		this.persister = persister;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.persistence.impl.DataModelPersister#saveState(com.itllp.tipOnDiscount.model.DataModel, com.itllp.tipOnDiscount.persistence.Persister, android.content.Context)
	 */
	@Override
	public void saveState(DataModel model, Context context) {
		persister.beginSave(context);
		try {
			persister.save(DataModel.BILL_TOTAL_KEY, model.getBillTotal());
			if (model.isUsingTaxRate()) {
				persister.save(DataModel.TAX_RATE_KEY, model.getTaxRate());
			} else {
				persister.save(DataModel.TAX_AMOUNT_KEY, model.getTaxAmount());
			}
			persister.save(DataModel.DISCOUNT_KEY, model.getDiscount());
			persister.save(DataModel.PLANNED_TIP_RATE_KEY, 
					model.getPlannedTipRate());
			persister.save(DataModel.SPLIT_BETWEEN_KEY, 
					model.getSplitBetween());
			persister.save(DataModel.ROUND_UP_TO_NEAREST_AMOUNT, 
					model.getRoundUpToAmount());
			persister.save(DataModel.BUMPS_KEY, model.getBumps());
			persister.endSave();
		} catch (Exception e) {
			Toast.makeText(context,
           		 "Failed to save state", Toast.LENGTH_LONG).show();
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.persistence.impl.DataModelPersister#restoreState(com.itllp.tipOnDiscount.model.DataModel, com.itllp.tipOnDiscount.persistence.Persister, android.content.Context)
	 */
	@Override
	public void restoreState(DataModel dataModel, Context context) {
		
		BigDecimal billTotal = persister.retrieveBigDecimal(context, 
				DataModel.BILL_TOTAL_KEY);
		if (null != billTotal) {
			dataModel.setBillTotal(billTotal);
		}
		
		BigDecimal taxRate = persister.retrieveBigDecimal(context, 
				DataModel.TAX_RATE_KEY);
		if (null != taxRate) {
			dataModel.setTaxRate(taxRate);
		}
		
		BigDecimal taxAmount = persister.retrieveBigDecimal(context, 
				DataModel.TAX_AMOUNT_KEY);
		if (null != taxAmount) {
			dataModel.setTaxAmount(taxAmount);
		}
		
		BigDecimal discount = persister.retrieveBigDecimal(context, 
				DataModel.DISCOUNT_KEY);
		if (null != discount) {
			dataModel.setDiscount(discount);
		}
		
		BigDecimal tipRate = persister.retrieveBigDecimal(context, 
				DataModel.PLANNED_TIP_RATE_KEY);
		if (null != tipRate) {
			dataModel.setPlannedTipRate(tipRate);
		}
		
		Integer splits = persister.retrieveInteger(context, 
				DataModel.SPLIT_BETWEEN_KEY);
		if (null != splits) {
			dataModel.setSplitBetween(splits.intValue());
		}
		
		BigDecimal roundUp = persister.retrieveBigDecimal(context, 
				DataModel.ROUND_UP_TO_NEAREST_AMOUNT);
		if (null != roundUp) {
			dataModel.setRoundUpToAmount(roundUp);
		}
		
		Integer bumps = persister.retrieveInteger(context, 
				DataModel.BUMPS_KEY);
		if (null != bumps) {
			dataModel.setBumps(bumps.intValue());
		}
	}
}
