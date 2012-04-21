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

package com.itllp.tipOnDiscount.model.update;

import java.util.HashSet;
import java.util.Iterator;

public class UpdateSet extends HashSet<Update> {

	private static final long serialVersionUID = 1L;


	/** Adds an update item to the update set.  Only one type of update
	 * can belong to the set.  The most recent update is retained.
	 * @param item An update item.
	 */
	public boolean add(Update item) {
		if (contains(item)) {
			this.remove(item);
		}
		return super.add(item);
	}

	
	/** Tells if an update item is in the set.
	 * @param item to check for membership
	 * @return true if the item is in the set, false otherwise.
	 */
	public boolean contains(Update item) {
		Class<? extends Update> itemClass = item.getClass();
		for (Iterator<?> i = iterator(); i.hasNext(); ) {
			if (itemClass == i.next().getClass()) {
				return true;
			}
		}
		return false;
	}
	
	
	/** Removes an item from the set if it is of the same update type
	 * as the given item.
	 */
	public Update remove(Update item) {
		Class<? extends Update> itemClass = item.getClass();
		for (Iterator<?> i = iterator(); i.hasNext(); ) {
			Update thisItem = (Update)i.next();
			if (itemClass == thisItem.getClass()) {
				super.remove(thisItem);
				return thisItem;
			}
		}
		return null;
		
	}
}
