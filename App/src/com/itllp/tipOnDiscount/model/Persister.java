package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

public interface Persister {

	public void save(String key, BigDecimal value);
	public void save(String key, int value);
	public BigDecimal retrieveBigDecimal(String key);
	public Integer retrieveInt(String key);
}
