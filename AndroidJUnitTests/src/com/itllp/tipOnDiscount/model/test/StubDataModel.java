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

package com.itllp.tipOnDiscount.model.test;

import java.math.BigDecimal;
import java.util.Observable;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelObserver;

public class StubDataModel extends Observable implements DataModel {
	private BigDecimal actualTipAmount;
	private BigDecimal actualTipRate;
	private BigDecimal billSubtotal;
	private BigDecimal billTotal;
	private int bumpDownCallCount;
	private int bumpUpCallCount;
	private int bumps;
	private BigDecimal discount;
	private BigDecimal plannedTipAmount;
	private BigDecimal plannedTipRate;
	private BigDecimal roundUpToAmount;
	private BigDecimal shareDue;
	private int splitBetween;
	private BigDecimal taxAmount;
	private BigDecimal taxRate;
	private BigDecimal tippableAmount;
	private BigDecimal totalDue;
	private boolean usingTaxRate;
	private boolean dataModelWasSaved;
	private boolean dataModelWasRestored;
	public static final BigDecimal INITIAL_ACTUAL_TIP_AMOUNT = new BigDecimal("1.23");
	public static final BigDecimal INITIAL_ACTUAL_TIP_RATE = new BigDecimal("0.1234");
	public static final BigDecimal INITIAL_BILL_SUBTOTAL = new BigDecimal("2.34");
	public static final BigDecimal INITIAL_BILL_TOTAL = new BigDecimal("3.45");
	public static final int INITIAL_BUMPS = 7;
	public static final BigDecimal INITIAL_DISCOUNT = new BigDecimal("5.67");
	public static final BigDecimal INITIAL_PLANNED_TIP_AMOUNT = new BigDecimal("6.78");
	public static final BigDecimal INITIAL_PLANNED_TIP_RATE = new BigDecimal("0.23456");
	public static final BigDecimal INITIAL_ROUND_UP_TO_AMOUNT = new BigDecimal("2.00");
	public static final BigDecimal INITIAL_SHARE_DUE = new BigDecimal("8.90");
	public static final int INITIAL_SPLIT_BETWEEN = 8;
	public static final BigDecimal INITIAL_TAX_AMOUNT = new BigDecimal("10.12");
	public static final BigDecimal INITIAL_TAX_RATE = new BigDecimal("0.56789");
	public static final BigDecimal INITIAL_TIPPABLE_AMOUNT = new BigDecimal("11.23");
	public static final BigDecimal INITIAL_TOTAL_DUE = new BigDecimal("12.34");
	public static final boolean INITIAL_USING_TAX_RATE = true;
	
	
	public StubDataModel() {
		initialize();
	}
	
	@Override
	public void addObserver(DataModelObserver observer) {
	}

	public void bumpDown() {
		setBumps(getBumps() - 1);
		++bumpDownCallCount;
	}

	public void bumpUp() {
		setBumps(getBumps() + 1);
		++bumpUpCallCount;
	}

	public BigDecimal getActualTipAmount() {
		return actualTipAmount;
	}

	public BigDecimal getActualTipRate() {
		return this.actualTipRate;
	}

	public BigDecimal getBillSubtotal() {
		return this.billSubtotal;
	}

	public BigDecimal getBillTotal() {
		return this.billTotal;
	}

	public int getBumpDownCallCount() {
		return this.bumpDownCallCount;
	}

	public int getBumpUpCallCount() {
		return this.bumpUpCallCount;
	}

	public int getBumps() {
		return this.bumps;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public BigDecimal getPlannedTipAmount() {
		return this.plannedTipAmount;
	}

	public BigDecimal getPlannedTipRate() {
		return this.plannedTipRate;
	}

	public BigDecimal getRoundUpToAmount() {
		return this.roundUpToAmount;
	}

	public BigDecimal getShareDue() {
		return shareDue;
	}

	public int getSplitBetween() {
		return this.splitBetween;
	}

	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}

	public BigDecimal getTaxRate() {
		return this.taxRate;
	}

	public BigDecimal getTippableAmount() {
		return this.tippableAmount;
	}

	public BigDecimal getTotalDue() {
		return this.totalDue;
	}

	public void initialize() {
		actualTipAmount = INITIAL_ACTUAL_TIP_AMOUNT;
		actualTipRate = INITIAL_ACTUAL_TIP_RATE;
		billSubtotal = INITIAL_BILL_SUBTOTAL;
		billTotal = INITIAL_BILL_TOTAL;
		bumpDownCallCount = 0;
		bumpUpCallCount = 0;
		bumps = INITIAL_BUMPS;
		discount = INITIAL_DISCOUNT;
		plannedTipAmount = INITIAL_PLANNED_TIP_AMOUNT;
		plannedTipRate = INITIAL_PLANNED_TIP_RATE;
		roundUpToAmount = INITIAL_ROUND_UP_TO_AMOUNT;
		shareDue = INITIAL_SHARE_DUE;
		splitBetween = INITIAL_SPLIT_BETWEEN;
		taxAmount = INITIAL_TAX_AMOUNT;
		taxRate = INITIAL_TAX_RATE;
		tippableAmount = INITIAL_TIPPABLE_AMOUNT;
		totalDue = INITIAL_TOTAL_DUE;
		usingTaxRate = INITIAL_USING_TAX_RATE;
		dataModelWasRestored = false;
		dataModelWasSaved = false;
	}

	public boolean isUsingTaxRate() {
		return this.usingTaxRate;
	}

	@Override
	public void restoreState() {
		this.dataModelWasRestored = true;
	}

	@Override
	public void saveState() {
		this.dataModelWasSaved = true;
	}

	public void setActualTipAmount(BigDecimal amount) {
		this.actualTipAmount = amount;
	}

	public void setActualTipRate(BigDecimal rate) {
		this.actualTipRate = rate;
	}

	public void setBillSubtotal(BigDecimal amount) {
		this.billSubtotal = amount;
	}

	public void setBillTotal(BigDecimal newAmount) {
		this.billTotal = newAmount;
	}

	public void setBumps(int bumps) {
		this.bumps = bumps;
	}

	public void setDiscount(BigDecimal newAmount) {
		this.discount = newAmount;
	}

	public void setPlannedTipRate(BigDecimal newRate) {
		this.plannedTipRate = newRate;
	}

	public void setPlannedTipAmount(BigDecimal amount) {
		this.plannedTipAmount = amount;
	}

	public void setRoundUpToAmount(BigDecimal roundUpToAmount) {
		this.roundUpToAmount = roundUpToAmount;
	}

	public void setShareDue(BigDecimal amount) {
		shareDue = amount;	
	}

	public void setSplitBetween(int parties) {
		this.splitBetween = parties;
	}

	public void setTaxAmount(BigDecimal newAmount) {
		this.taxAmount = newAmount;
		this.usingTaxRate = false;
	}

	public void setTaxRate(BigDecimal newRate) {
		this.taxRate = newRate;
		this.usingTaxRate = true;
	}

	public void setTippableAmount(BigDecimal amount) {
		this.tippableAmount = amount;
	}

	public void setTotalDue(BigDecimal amount) {
		this.totalDue = amount;
	}

	public boolean wasDataModelSaved() {
		return this.dataModelWasSaved;
	}

	public boolean wasDataModelRestored() {
		return this.dataModelWasRestored;
	}

}
