package com.itllp.tipOnDiscount.model.test;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelInitializer;

public class StubDataModelInitializer implements DataModelInitializer {

	private boolean wasInitializeCalled = false;
	
	public boolean stub_wasInitializeCalled() {
		return wasInitializeCalled;
	}

	@Override
	public void initialize(DataModel dataModel) {
		wasInitializeCalled = true;
	}

}
