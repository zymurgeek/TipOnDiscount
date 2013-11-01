package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

import android.content.Context;

public interface DataModelInitializer {

	void initialize(DataModel dataModel, Context context);

	BigDecimal getBillTotal();

	BigDecimal getDiscount();

	BigDecimal getTaxRate(Context context);

	BigDecimal getTipRate();

	int getSplitBetween();

	BigDecimal getRoundUpToAmount();

	int getBumps();

}
