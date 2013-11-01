package com.itllp.tipOnDiscount.model.test;

import java.math.BigDecimal;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelInitializer;

public class StubDataModelInitializer implements DataModelInitializer {

	private static final BigDecimal INITIAL_DISCOUNT = new BigDecimal("5.67");

	private boolean wasInitializeCalled = false;
	
	public boolean stub_wasInitializeCalled() {
		return wasInitializeCalled;
	}

	@Override
	public void initialize(DataModel dataModel, Context dummyContext) {
		wasInitializeCalled = true;
	}

	@Override
	public BigDecimal getDiscount() {
		return INITIAL_DISCOUNT;
	}

	@Override
	public BigDecimal getBillTotal() {
		return null;
	}

	@Override
	public BigDecimal getTaxRate(Context context) {
		return null;
	}

	@Override
	public BigDecimal getTipRate(Context context) {
		return null;
	}

	@Override
	public int getSplitBetween() {
		return 0;
	}

	@Override
	public BigDecimal getRoundUpToAmount(Context context) {
		return null;
	}

	@Override
	public int getBumps() {
		return 0;
	}

}
