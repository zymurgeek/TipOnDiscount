package com.itllp.tipOnDiscount.model.test;

import java.math.BigDecimal;

import android.content.Context;

import com.itllp.tipOnDiscount.persistence.Persister;

public class StubDataModelPersister implements Persister {

	@Override
	public void save(String key, BigDecimal value) {
	}

	@Override
	public void save(String key, int value) {
	}

	@Override
	public BigDecimal retrieveBigDecimal(Context context, String key) {
		return null;
	}

	@Override
	public Integer retrieveInteger(Context context, String key) {
		return null;
	}

	@Override
	public void beginSave(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endSave() {
		// TODO Auto-generated method stub
		
	}

}
