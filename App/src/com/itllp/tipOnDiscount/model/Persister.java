package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

public interface Persister {

	public static final String BILL_TOTAL_KEY = "BillTotal";
	public static final String TAX_RATE_KEY = "TaxRate";
	public static final String DISCOUNT_KEY = "Discount";
	public static final String PLANNED_TIP_RATE_KEY = "TipRate";
	public static final String SPLIT_BETWEEN_KEY = "SplitBetween";
	public static final String BUMPS_KEY = "Bumps";
	public static final String ROUND_UP_TO_NEAREST_AMOUNT 
	= "RoundUpToNearestSelectedItem";
	public static final String TAX_AMOUNT_KEY = "TaxAmount";
	public void save(String key, BigDecimal value);
	public void save(String key, int value);

}
