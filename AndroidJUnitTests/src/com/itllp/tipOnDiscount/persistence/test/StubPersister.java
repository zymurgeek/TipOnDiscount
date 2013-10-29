package com.itllp.tipOnDiscount.persistence.test;

import java.math.BigDecimal;

import android.content.Context;

import com.itllp.tipOnDiscount.persistence.Persister;

public class StubPersister implements Persister {

	@Override
	public void beginSave(Context context) {
	}

	@Override
	public void save(String key, BigDecimal value) throws Exception {
	}

	@Override
	public void save(String key, boolean value) throws Exception {
	}

	@Override
	public void save(String key, int value) throws Exception {
	}

	@Override
	public void endSave() throws Exception {
	}

	@Override
	public BigDecimal retrieveBigDecimal(Context context, String key) {
		return null;
	}

	@Override
	public Integer retrieveInteger(Context context, String key) {
		return null;
	}

	public Boolean retrieveBoolean(Context context, String key) {
		return null;
	}

}
