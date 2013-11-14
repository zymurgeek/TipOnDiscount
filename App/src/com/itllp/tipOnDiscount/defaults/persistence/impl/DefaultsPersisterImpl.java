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
package com.itllp.tipOnDiscount.defaults.persistence.impl;

import java.math.BigDecimal;

import android.content.Context;
import android.widget.Toast;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;
import com.itllp.tipOnDiscount.persistence.Persister;

public class DefaultsPersisterImpl implements DefaultsPersister {
	private Persister persister;
	
	public DefaultsPersisterImpl(Persister persister) {
		this.persister = persister;
	}
	
	@Override
	public void saveState(Defaults defaults, Context context) {
		persister.beginSave(context);
		try {
			persister.save(Defaults.TAX_PERCENT_KEY, defaults.getTaxPercent());
			persister.save(Defaults.TIP_PERCENT_KEY, defaults.getTipPercent());
			persister.save(Defaults.ROUND_UP_TO_AMOUNT_KEY, 
					defaults.getRoundUpToAmount());
			persister.endSave();
		} catch (Exception e) {
			Toast.makeText(context,
	           		 "Failed to save defaults", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void restoreState(Defaults defaults, Context context) {
		BigDecimal taxPercent = persister.retrieveBigDecimal(context, 
				Defaults.TAX_PERCENT_KEY);
		if (null != taxPercent) {
			defaults.setTaxPercent(taxPercent);
		}
		
		BigDecimal tipPercent = persister.retrieveBigDecimal(context, 
				Defaults.TIP_PERCENT_KEY);
		if (null != tipPercent) {
			defaults.setTipPercent(tipPercent);
		}
		
		
		BigDecimal roundUpToAmount = persister.retrieveBigDecimal(context, 
				Defaults.ROUND_UP_TO_AMOUNT_KEY);
		if (null != roundUpToAmount) {
			defaults.setRoundUpToAmount(roundUpToAmount);
		}
	}

}
