package com.itllp.tipOnDiscount.persistence;

import java.math.BigDecimal;

import android.content.Context;

public interface Persister {

	public void beginSave(Context context);
	public void save(String key, BigDecimal value) throws Exception;
	public void save(String key, boolean value) throws Exception;
	public void save(String key, int value) throws Exception;
	public void endSave() throws Exception;
	public BigDecimal retrieveBigDecimal(Context context, String key);
	public Integer retrieveInteger(Context context, String key);
}
