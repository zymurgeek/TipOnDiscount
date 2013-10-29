// Copyright 2013 David A. Greenbaum
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
package com.itllp.tipOnDiscount.defaults;

import java.math.BigDecimal;

public interface Defaults {

	public abstract void setTaxPercent(BigDecimal taxPercent);

	public abstract BigDecimal getTaxPercent();

	public abstract void setTipPercent(BigDecimal tipPercent);

	public abstract BigDecimal getTipPercent();

	public abstract void setRoundUpToAmount(BigDecimal roundUpToAmount);

	public abstract BigDecimal getRoundUpToAmount();
}
