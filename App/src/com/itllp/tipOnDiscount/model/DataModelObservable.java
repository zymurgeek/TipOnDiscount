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

import java.util.HashSet;

import com.itllp.tipOnDiscount.model.update.Update;

public class DataModelObservable {
	private HashSet<DataModelObserver> observerSet = new HashSet<DataModelObserver>();
	
	public void addObserver(DataModelObserver o) {
		observerSet.add(o);
	}
	
	public void deleteObserver(DataModelObserver o) {
		observerSet.remove(o);
	}

	public void notifyObservers(DataModel model, Update item) {
		for (DataModelObserver observer: observerSet) {
			observer.update(model, item);
		}
	}

}
