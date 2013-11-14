package com.itllp.tipOnDiscount.defaults;

import java.math.BigDecimal;

import com.itllp.tipOnDiscount.defaults.Defaults;

public class DefaultsImpl implements Defaults {

	private BigDecimal taxPercent;
	private BigDecimal tipPercent;
	private BigDecimal roundUpToAmount;
	public static final String DEFAULT_PLANNED_TIP_PERCENT = "15";
	public static final String DEFAULT_TAX_PERCENT = "0";
	public static final String DEFAULT_ROUND_UP_TO_AMOUNT = ".01";
	
	public DefaultsImpl() {
		taxPercent = new BigDecimal(DEFAULT_TAX_PERCENT);
		tipPercent = new BigDecimal(DEFAULT_PLANNED_TIP_PERCENT);
		roundUpToAmount = new BigDecimal(DEFAULT_ROUND_UP_TO_AMOUNT);
	}
	@Override
	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	@Override
	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}

	@Override
	public void setTipPercent(BigDecimal tipPercent) {
		this.tipPercent = tipPercent;
	}

	@Override
	public BigDecimal getTipPercent() {
		return tipPercent;
	}

	@Override
	public void setRoundUpToAmount(BigDecimal roundUpToAmount) {
		this.roundUpToAmount = roundUpToAmount;
	}

	@Override
	public BigDecimal getRoundUpToAmount() {
		return roundUpToAmount;
	}

}
