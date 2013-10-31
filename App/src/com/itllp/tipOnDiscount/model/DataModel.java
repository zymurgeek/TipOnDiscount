// Copyright 2011-2012 David A. Greenbaum
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

package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

public interface DataModel {

	// TODO Move these constants to the DataModelPersister
	public static final String BILL_TOTAL_KEY = "BillTotal";
	public static final String TAX_RATE_KEY = "TaxRate";
	public static final String DISCOUNT_KEY = "Discount";
	public static final String PLANNED_TIP_RATE_KEY = "TipRate";
	public static final String SPLIT_BETWEEN_KEY = "SplitBetween";
	public static final String BUMPS_KEY = "Bumps";
	public static final String ROUND_UP_TO_NEAREST_AMOUNT 
	= "RoundUpToNearestSelectedItem";
	public static final String TAX_AMOUNT_KEY = "TaxAmount";

	/**
	 * Resets all attributes to their default values.  
	 * No update notifications are sent.
	 */
	// TODO Remove this method, as its function is performed by DataModelInitializer
	public void initialize();

	public void addObserver(DataModelObserver observer);
	
	public void bumpDown();
	
	public void bumpUp();
	
	public BigDecimal getActualTipAmount();

	public BigDecimal getActualTipRate();

	public BigDecimal getBillSubtotal();

	public BigDecimal getBillTotal();

	/**
	 * Sets the Bill Total Amount.  This may change the Tax Rate, Tax Amount,
	 * Bill Subtotal, Tip Amount and Share Due.  
	 */
	public void setBillTotal(BigDecimal newAmount);

	/** Gets the number of bumps to apply to each bill share.
	 * 
	 * @return increments of roundUpToAmount that are added to or subtracted
	 * from each bill share.
	 */
	public int getBumps();

	/** Sets the number of bumps to apply to each bill share.
	 * 
	 * This may change Share Due.
	 * 
	 * @param bumps - increments of roundUpToAmount to add to or subtract from 
	 * each bill share.
	 */
	public void setBumps(int bumps);

	public BigDecimal getDiscount();

	/**
	 * Sets the amount of discount included in the bill total.  
	 * This may change Tippable Amount, and Tip Amount.
	 */
	public void setDiscount(BigDecimal newAmount);

	public BigDecimal getPlannedTipAmount();

	public BigDecimal getPlannedTipRate();

	/**
	 * Sets the Planned Tip Rate.  This may change Planned Tip Amount. 
	 */
	public void setPlannedTipRate(BigDecimal newRate);

	/** Sets the amount the bill share is to be rounded up to, i.e.,
	 * round each share up to the nearest "roundUpToAmount".
	 * Note this isn't a half-up round, e.g., for round-up-to $1.00,
	 * $2.15 rounds to $3.00, not $2.00.
	 */
	public BigDecimal getRoundUpToAmount();

	/** Sets the amount the bill share is to be rounded up to, i.e.,
	 * round each share up to the nearest "roundUpToAmount".
	 * This may change Share Due.
	 * @param roundUpToAmount Amount to round up to
	 */
	public void setRoundUpToAmount(BigDecimal roundUpToAmount);

	/** Returns the amount due from each party to cover the cost
	 * of the bill and tip. 
	 */
	public BigDecimal getShareDue();

	/** Returns the numbers of parties splitting the bill.
	 */
	public int getSplitBetween();

	/** Sets the number of parties splitting the bill.
	 * This may change ShareDue.
	 */
	public void setSplitBetween(int parties);

	/** Returns the amount of sales tax on the bill.
	 */
	public BigDecimal getTaxAmount();

	/**
	 * Sets the tax amount.  This may change Tax Rate.
	 */
	public void setTaxAmount(BigDecimal newAmount);

	public BigDecimal getTaxRate();

	/**
	 * Sets the Tax Rate.  This may change Tax Amount and Bill Subtotal.
	 */
	public void setTaxRate(BigDecimal newRate);

	public BigDecimal getTippableAmount();

	public BigDecimal getTotalDue();

	public boolean isUsingTaxRate();

	public String toString();

}