// Copyright 2011-2013 David A. Greenbaum
/*
This file is part of Tip On Discount.

Tip On Discount is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Tip On Discount is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with Tip On Discount.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.itllp.tipOnDiscount.model.update;

import com.itllp.tipOnDiscount.util.EqualsUtil;

public class SplitBetweenUpdate extends Update {
	private int value = 0;
	
	public SplitBetweenUpdate(int newValue) {
		this.value = newValue;
	}
	
	@Override 
	public boolean equals(Object other) {
		//check for self-comparison
		if ( this == other ) return true;

		// use instanceof instead of getClass here for two reasons
		// 1. if need be, it can match any supertype, and not just one class;
		// 2. it renders an explict check for "that == null" redundant, since
		// it does the check for null already - "null instanceof [type]" always
		// returns false. (See Effective Java by Joshua Bloch.)
		if ( !(other instanceof SplitBetweenUpdate) ) return false;

		// cast to native object is now safe
		SplitBetweenUpdate otherAT = (SplitBetweenUpdate)other;

	    // now a proper field-by-field evaluation can be made
		return EqualsUtil.areEqual(this.value, otherAT.value);
	}

	
	
	public int getValue() {
		return this.value;
	}

	public String toString() {
		return "SplitBetween(" + this.value + ")";
	}
}
