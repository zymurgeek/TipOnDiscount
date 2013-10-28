package com.itllp.tipOnDiscount.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BigDecimalLabelMap {

	private Map<BigDecimal, String> map = new HashMap<BigDecimal, String>();
	
	public BigDecimalLabelMap(String[] valueStrings, String[] labels) {
		if (null == labels) {
			throw new RuntimeException("label array cannot be null");
		}
		if (null == valueStrings) {
			throw new RuntimeException("value string array cannot be null");
		}
		if (labels.length != valueStrings.length) {
			throw new RuntimeException
			("label and value string arrays must be the same length.");
		}
		for (int i=0; i<valueStrings.length; ++i) {
			String valueString = valueStrings[i];
			BigDecimal value = new BigDecimal(valueString);
			map.put(value, labels[i]);
		}
	}

	public String getLabel(BigDecimal value) {
		Set<BigDecimal> valueSet = map.keySet();
		for (BigDecimal aValue: valueSet) {
			if (0 == aValue.compareTo(value)) {
				return map.get(aValue);
			}
		}
		return null;
	}
	
	public Set<BigDecimal> keySet() {
		return map.keySet();
	}

	public Collection<String> values() {
		return map.values();
	}

	public BigDecimal getValue(String label) {
		for (BigDecimal bigDecimal : keySet()) {
			if (getLabel(bigDecimal).equals(label)) {
				return bigDecimal;
			}
		}
		return null;
	}

}
