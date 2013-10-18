package com.itllp.tipOnDiscount.model.persistence;

import java.math.BigDecimal;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.persistence.Persister;

public class DataModelPersister {
	public void saveState(DataModel model, Persister persister, Context context) {
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
		} catch (Exception e) {
			// TODO toast if failed
		}
		try {
			persister.endSave();
		} catch (Exception e) {
			// TODO toast if failed
		}
	}

	
	public void restoreState(DataModel dataModel, Persister persister,
			Context context) {
		
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
