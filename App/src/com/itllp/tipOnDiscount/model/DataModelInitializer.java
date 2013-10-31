package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

import android.content.Context;

public interface DataModelInitializer {

	void initialize(DataModel dataModel, Context context);

	BigDecimal getDiscount();

}
