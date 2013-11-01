// Copyright 2011-2013 David A. Greenbaum
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
import java.math.RoundingMode;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelObservable;
import com.itllp.tipOnDiscount.model.DataModelObserver;
import com.itllp.tipOnDiscount.model.update.ActualTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.ActualTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.BillSubtotalUpdate;
import com.itllp.tipOnDiscount.model.update.BillTotalUpdate;
import com.itllp.tipOnDiscount.model.update.BumpsUpdate;
import com.itllp.tipOnDiscount.model.update.DiscountUpdate;
import com.itllp.tipOnDiscount.model.update.RoundUpToNearestUpdate;
import com.itllp.tipOnDiscount.model.update.ShareDueUpdate;
import com.itllp.tipOnDiscount.model.update.SplitBetweenUpdate;
import com.itllp.tipOnDiscount.model.update.TaxAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TaxRateUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipAmountUpdate;
import com.itllp.tipOnDiscount.model.update.PlannedTipRateUpdate;
import com.itllp.tipOnDiscount.model.update.TippableAmountUpdate;
import com.itllp.tipOnDiscount.model.update.TotalDueUpdate;
import com.itllp.tipOnDiscount.model.update.Update;
import com.itllp.tipOnDiscount.model.update.UpdateSet;
import com.itllp.tipOnDiscount.util.EqualsUtil;

public class DataModelImpl implements DataModel {

	// TODO Support tip included, maybe with help screen advice

	DataModelObservable observable = new DataModelObservable();
	/* 
	 * The following data is calculated in the order it appears here.
	 * When updating these items in the update methods, do not use a later 
	 * item in a calculation, as it may not yet be updated.  Note the update
	 * methods recalculate their respective field and call any update
	 * methods that are affect by their calculation.  Change observers
	 * are only notified if a value changes. 
	 */
	private int splitBetween = 1;
	private BigDecimal roundUpToAmount = BigDecimal.ZERO.setScale(2);
	private int bumps = 0;
	private BigDecimal billTotal = BigDecimal.ZERO.setScale(2);
	private boolean usingTaxRate = true;
	private BigDecimal taxRate = BigDecimal.ZERO.setScale(5);
	private BigDecimal taxAmount = BigDecimal.ZERO.setScale(2);
	private BigDecimal billSubtotal = BigDecimal.ZERO.setScale(2);
	private BigDecimal discount = BigDecimal.ZERO.setScale(2);
	private BigDecimal tippableAmount = BigDecimal.ZERO.setScale(2);
	private BigDecimal plannedTipRate = BigDecimal.ZERO.setScale(5);
	private BigDecimal plannedTipAmount = BigDecimal.ZERO.setScale(2);
	private BigDecimal shareDue = BigDecimal.ZERO.setScale(2);
	private BigDecimal totalDue = BigDecimal.ZERO.setScale(2);
	private BigDecimal actualTipAmount = BigDecimal.ZERO.setScale(2);
	private BigDecimal actualTipRate = BigDecimal.ZERO.setScale(5);

	
	public DataModelImpl() {
		initialize();
	}
	
	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#initialize()
	 */
	//TODO Move this to DataModelInitializer
	public void initialize() {
		setRoundUpToAmount(new BigDecimal("0.01"));
		setBillTotal(BigDecimal.ZERO.setScale(2));
		setTaxRate(BigDecimal.ZERO.setScale(5)); // sets usingTaxRate=true
		setDiscount(BigDecimal.ZERO.setScale(2));
		setPlannedTipRate(new BigDecimal("0.15000"));
		setSplitBetween(1);
		setBumps(0);
	}
	
	
	public void addObserver(DataModelObserver observer) {
		this.observable.addObserver(observer);
	}

	
	@Override
	public void bumpDown() {
		setBumps(getBumps() - 1);
	}
	

	@Override
	public void bumpUp() {
		setBumps(getBumps() + 1);
	}
	

	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getActualTipAmount()
	 */
	public BigDecimal getActualTipAmount() {
		return this.actualTipAmount;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getActualTipRate()
	 */
	public BigDecimal getActualTipRate() {
		return this.actualTipRate;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getBillSubtotal()
	 */
	public BigDecimal getBillSubtotal() {
		return this.billSubtotal;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getBillTotal()
	 */
	public BigDecimal getBillTotal() {
		return this.billTotal;
	}
	
	
	/** Sends out an update notification for each item in the update set.
	 * @param updateSet
	 */
	private void notifyObservers(UpdateSet updateSet) {
		for (Update item: updateSet) {
			this.observable.notifyObservers(this, item);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setBillTotal(java.math.BigDecimal)
	 */
	public void setBillTotal(BigDecimal newAmount) {
		if (null == newAmount) return;
		
		BigDecimal formerBillTotal = this.billTotal;
		
		this.billTotal = newAmount;
		
		if (!EqualsUtil.areEqual(formerBillTotal, 
				this.billTotal)) {
			UpdateSet us = new UpdateSet();
			BillTotalUpdate billTotalUpdate 
				= new BillTotalUpdate(this.billTotal);
			us.add(billTotalUpdate);
			updateTax(us);
			this.notifyObservers(us);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getBumps()
	 */
	public int getBumps() {
		return this.bumps;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setBumps(int)
	 */
	public void setBumps(int bumps) {
		if (!EqualsUtil.areEqual(this.bumps, bumps)) {
			this.bumps = bumps;
		
			UpdateSet updateSet = new UpdateSet();
			BumpsUpdate bumpsUpdate = new BumpsUpdate(this.bumps);
			updateSet.add(bumpsUpdate);
			
			updateShareDue(updateSet);
			
			this.notifyObservers(updateSet);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getDiscount()
	 */
	public BigDecimal getDiscount() {
		return this.discount;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setDiscount(java.math.BigDecimal)
	 */
	public void setDiscount(BigDecimal newAmount) {
		if (null == newAmount) return;
		
		if (!EqualsUtil.areEqual(this.discount, newAmount)) { 
			this.discount = newAmount;
			UpdateSet updateSet = new UpdateSet();
			DiscountUpdate discountUpdate 
				= new DiscountUpdate(this.discount);
			updateSet.add(discountUpdate);
			updateTippableAmount(updateSet);

			this.notifyObservers(updateSet);
		}
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getRoundUpToAmount()
	 */
	public BigDecimal getRoundUpToAmount() {
		return this.roundUpToAmount;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setRoundUpToAmount(java.math.BigDecimal)
	 */
	public void setRoundUpToAmount(BigDecimal roundUpToAmount) {
		if (null == roundUpToAmount) return;
		
		if (roundUpToAmount.compareTo(new BigDecimal("0.00")) < 1) {
			// Do not allow roundUpToAmount <= 0
			return;
		}
		if (!EqualsUtil.areEqual(this.roundUpToAmount, roundUpToAmount)) {
			this.roundUpToAmount = roundUpToAmount;
		
			UpdateSet updateSet = new UpdateSet();
			RoundUpToNearestUpdate roundUpToNearestUpdate = 
					new RoundUpToNearestUpdate(this.roundUpToAmount);
			updateSet.add(roundUpToNearestUpdate);
			
			updateShareDue(updateSet);
			
			this.notifyObservers(updateSet);
		}

	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getShareDue()
	 */
	public BigDecimal getShareDue() {
		return this.shareDue;
	}
	

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getSplitBetween()
	 */
	public int getSplitBetween() {
		return this.splitBetween;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setSplitBetween(int)
	 */
	public void setSplitBetween(int parties) {
		if (!EqualsUtil.areEqual(this.splitBetween, parties)) {
			this.splitBetween = parties;
		
			UpdateSet updateSet = new UpdateSet();
			SplitBetweenUpdate splitBetweenUpdate = new 
					SplitBetweenUpdate(this.splitBetween);
			updateSet.add(splitBetweenUpdate);
			
			updateShareDue(updateSet);
			
			this.notifyObservers(updateSet);
		}

	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTaxAmount()
	 */
	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setTaxAmount(java.math.BigDecimal)
	 */
	public void setTaxAmount(BigDecimal newAmount) {
		if (null == newAmount) return;
		
		if (!EqualsUtil.areEqual(this.taxAmount, newAmount)) {
			this.taxAmount = newAmount;
			this.usingTaxRate = false;
			
			UpdateSet updateSet = new UpdateSet();
			TaxAmountUpdate taxAmount = new TaxAmountUpdate(this.taxAmount);
			updateSet.add(taxAmount);
			
			updateTax(updateSet);
			
			this.notifyObservers(updateSet);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTaxRate()
	 */
	public BigDecimal getTaxRate() {
		return this.taxRate;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setTaxRate(java.math.BigDecimal)
	 */
	public void setTaxRate(BigDecimal newRate) {
		if (null == newRate) return;
		
		if (!EqualsUtil.areEqual(this.taxRate, newRate)) {
			this.taxRate = newRate;
			this.usingTaxRate = true;
			
			UpdateSet updateSet = new UpdateSet();
			TaxRateUpdate taxRateUpdate = new TaxRateUpdate(this.taxRate);
			updateSet.add(taxRateUpdate);
			
			updateTax(updateSet);
			
			this.notifyObservers(updateSet);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTippableAmount()
	 */
	public BigDecimal getTippableAmount() {
		return this.tippableAmount;
	}
	
	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTipAmount()
	 */
	public BigDecimal getPlannedTipAmount() {
		return this.plannedTipAmount;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTipRate()
	 */
	public BigDecimal getPlannedTipRate() {
		return this.plannedTipRate;
	}
	
	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#setTipRate(java.math.BigDecimal)
	 */
	public void setPlannedTipRate(BigDecimal newRate) {
		if (null == newRate) return;
		
		if (!EqualsUtil.areEqual(this.plannedTipRate, newRate)) {
			this.plannedTipRate = newRate;
			UpdateSet us = new UpdateSet();
			PlannedTipRateUpdate tipRateUpdate	= new PlannedTipRateUpdate(this.plannedTipRate);
			us.add(tipRateUpdate);
			updateTipAmount(us);
			this.notifyObservers(us);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#getTotalDue()
	 */
	public BigDecimal getTotalDue() {
		return this.totalDue;
	}


	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#isUsingTaxRate()
	 */
	public boolean isUsingTaxRate() {
		return this.usingTaxRate;
	}

	
	/* (non-Javadoc)
	 * @see com.itllp.tipOnDiscount.model.DataModel#toString()
	 */
	public String toString() {
		return "Model{"
			+ "billTotal("
			+ billTotal + "), "
			+ "taxRate("+ taxRate + "), "
			+ "taxAmount(" + taxAmount + "), "
			+ "usingTaxRate(" + Boolean.toString(usingTaxRate) + "), "
			+ "billSubtotal(" + billSubtotal + "), "
			+ "discount(" + discount + "), "
			+ "tippableAmount(" + tippableAmount + "), "
			+ "plannedTipRate(" + plannedTipRate + "), "
			+ "plannedTipAmount(" + plannedTipAmount + "), "
			+ "splitBetweenParties(" + this.splitBetween + "), "
			+ "roundUpToAmount(" + roundUpToAmount + "), "
			+ "bumps(" + this.bumps + "), "
			+ "actualTipRate(" + actualTipRate + "), "
			+ "actualTipAmount(" + actualTipAmount + "), "
			+ "shareAmount(" + shareDue + ")"
			+ "}";
	}
	
	
	/**
	 * Recalculates the actual tip amount.  This may change Actual Tip Rate.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateActualTipAmount(UpdateSet updateSet) {
		final BigDecimal formerActualTipAmount = this.actualTipAmount;
		
		this.actualTipAmount = this.totalDue.subtract(this.billTotal); 
		
		if (!EqualsUtil.areEqual(formerActualTipAmount, this.actualTipAmount)) {
			ActualTipAmountUpdate actualTipUpdate 
				= new ActualTipAmountUpdate(this.actualTipAmount);
			updateSet.add(actualTipUpdate);
		}
		updateActualTipRate(updateSet);
		
	}


	/**
	 * Recalculates the actual tip rate. 
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateActualTipRate(UpdateSet updateSet) {
		final BigDecimal formerActualTipRate = this.actualTipRate;

		if (this.tippableAmount.floatValue() != 0f) {
			this.actualTipRate = this.actualTipAmount.divide(
					this.tippableAmount, 5, RoundingMode.HALF_UP);
		} else {
			this.actualTipRate = new BigDecimal(".00000");
		}
		
		if (!EqualsUtil.areEqual(formerActualTipRate, this.actualTipRate)) {
			ActualTipRateUpdate actualTipRate = new ActualTipRateUpdate(this.actualTipRate);
			updateSet.add(actualTipRate);
		}
	}

	
	/**
	 * Recalculates the Bill Subtotal.  This may change Tippable Amount.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateBillSubtotal(UpdateSet updateSet) {
		final BigDecimal formerBillSubtotal = this.billSubtotal;

		this.billSubtotal = this.billTotal.subtract(this.taxAmount); 
		
		if (!EqualsUtil.areEqual(formerBillSubtotal, 
				this.billSubtotal)) {
			BillSubtotalUpdate billSubtotalUpdate 
				= new BillSubtotalUpdate(this.billSubtotal);
			updateSet.add(billSubtotalUpdate);
		}
		updateTippableAmount(updateSet);
	}


	/**
	 * Recalculate the Tippable Amount.  This may change Tip Amount.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateTippableAmount(UpdateSet updateSet) {
		final BigDecimal formerTippableAmount = this.tippableAmount;

		this.tippableAmount = this.billTotal.subtract(this.taxAmount) 
			.add(this.discount); 
		if (!EqualsUtil.areEqual(formerTippableAmount, 
				this.tippableAmount)) {
			TippableAmountUpdate tippableAmountUpdate 
				= new TippableAmountUpdate(this.tippableAmount);
			updateSet.add(tippableAmountUpdate);
		}
		this.updateTipAmount(updateSet);
	}

	
	/**
	 * Recalculate the Tip Amount.  This may change Share Due.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateTipAmount(UpdateSet updateSet) {
		final BigDecimal formerTipAmount = this.plannedTipAmount;
		
		this.plannedTipAmount = this.billSubtotal.add(this.discount) 
			.multiply(this.plannedTipRate).setScale(2, RoundingMode.HALF_UP);
		
		if (!EqualsUtil.areEqual(formerTipAmount, this.plannedTipAmount)) {
			PlannedTipAmountUpdate tipAmountUpdate = new PlannedTipAmountUpdate(this.plannedTipAmount);
			updateSet.add(tipAmountUpdate);
		}
		
		updateShareDue(updateSet);
	}

	
	/**
	 * Recalculates the amount of the bill due by each party.
	 * This may change Total Due.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateShareDue(UpdateSet updateSet) {
		BigDecimal formerShareDue = this.shareDue;
		
		BigDecimal calcTotalDue = this.billTotal.add(this.plannedTipAmount);
		BigDecimal splitBetween = new BigDecimal(this.splitBetween);
		BigDecimal splitTotalDue = calcTotalDue.divide(splitBetween,
				2, RoundingMode.HALF_UP);
		BigDecimal roundMults = splitTotalDue.divide(this.roundUpToAmount, 0, 
				RoundingMode.UP); 
		BigDecimal roundedSplitTotalDue 
			= this.roundUpToAmount.multiply(roundMults);
		BigDecimal bumpAmount = this.roundUpToAmount.multiply(
			new BigDecimal(this.bumps));
		this.shareDue = roundedSplitTotalDue.add(bumpAmount);
		
		if (!EqualsUtil.areEqual(formerShareDue, this.shareDue)) {
			ShareDueUpdate shareDueUpdate = new ShareDueUpdate(this.shareDue);
			updateSet.add(shareDueUpdate);
		}

		updateTotalDue(updateSet);
	}
	
	
	/** Recalculates the tax rate or tax amount.
	 * 
	 * If the tax rate is being used, updates the tax amount based
	 * on the tax rate, otherwise updates the tax rate based on the tax
	 * amount.
	 * 
	 * This may change the bill subtotal.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateTax(UpdateSet updateSet) {
		if (this.usingTaxRate) {
			// Using Tax Rate, calculate Tax Amount
			BigDecimal formerTaxAmount = this.taxAmount;
			
			BigDecimal taxRatio = new BigDecimal("1.00000")
				.add(this.taxRate);
			if (taxRatio.floatValue() != 0f) {
				BigDecimal bs = this.billTotal
					.divide(taxRatio, 2, RoundingMode.HALF_UP);
				this.taxAmount = this.billTotal.subtract(bs);
			} else {
				this.taxAmount = BigDecimal.ZERO.setScale(2);
			}
			
			if (!EqualsUtil.areEqual(formerTaxAmount, this.taxAmount)) {
				TaxAmountUpdate taxAmountUpdate 
					= new TaxAmountUpdate(this.taxAmount);
				updateSet.add(taxAmountUpdate);
			}
		} else {  
			// using Tax Amount, calculate Tax Rate
			
			BigDecimal formerTaxRate = this.taxRate;
			
			BigDecimal bs = this.billTotal.subtract(this.taxAmount);
			if (bs.floatValue() != 0f) {
				this.taxRate = this.taxAmount.divide(bs,
					5, RoundingMode.HALF_UP);
			} else {
				this.taxRate = BigDecimal.ZERO.setScale(5);
			}
			
			if (!EqualsUtil.areEqual(formerTaxRate, this.taxRate)) {
				TaxRateUpdate taxRate = new TaxRateUpdate(this.taxRate);
				updateSet.add(taxRate);
			}
		}

		updateBillSubtotal(updateSet);
	}

	
	/**
	 * Recalculates the amount of the total bill due, including tax and tip.
	 * 
	 * This may change the Actual Tip Amount.
	 * 
	 * @param updateSet The set of update notifications.
	 */
	private void updateTotalDue(UpdateSet updateSet) {
		BigDecimal formerTotalDue = this.totalDue;
		this.totalDue = this.shareDue
			.multiply(new BigDecimal(this.splitBetween));
		
		if (!EqualsUtil.areEqual(formerTotalDue, this.totalDue)) {
			TotalDueUpdate tdUpdate = new TotalDueUpdate(this.totalDue);
			updateSet.add(tdUpdate);
		}
		
		this.updateActualTipAmount(updateSet);
	}


}
