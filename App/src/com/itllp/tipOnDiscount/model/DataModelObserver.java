// Copyright 2011-2012 David A. Greenbaum
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

package com.itllp.tipOnDiscount.model;

import com.itllp.tipOnDiscount.model.update.Update;

public interface DataModelObserver {
    /**
     * Processes an update from the data model.
     * Each attribute of the data model has its own data type to identify 
     * which field in the UI needs to be updated.
     * @param updatedModel should always be our data model
     * @param updatedData contains the updated value
     */
	public void update(DataModel updatedModel, Update updatedData);
}
