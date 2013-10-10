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

/** This JUnit test harness check that the DataModelObservable
 * class sends update notifications to all registered observers.
 */

package com.itllp.tipOnDiscount.model;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.itllp.tipOnDiscount.model.update.BillTotalUpdate;
import com.itllp.tipOnDiscount.model.update.Update;
import com.itllp.tipOnDiscount.modelimpl.DataModelImpl;

public class DataModelObservableTests {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private DataModelObservable observable = null;

	
	@Before
	public void initialize() {
		observable = new DataModelObservable();
	}

	
	@Test 
	public void testUpdateOfOneObserver() {
		// Preconditions
		final DataModelObserver observer = context.mock(DataModelObserver.class);
		final DataModel model = new DataModelImpl();
		final Update update = new BillTotalUpdate(new BigDecimal("0.00"));
		observable.addObserver(observer);
		
		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer).update(model, update);			
		}});
		
		// Method under test
		observable.notifyObservers(model, update);
	}

	
	@Test 
	public void testUpdateOfDeregisteredObserver() {
		// Preconditions
		final DataModelObserver observer = context.mock(DataModelObserver.class);
		final Update update = new BillTotalUpdate(new BigDecimal("0.00"));
		observable.addObserver(observer);
		observable.deleteObserver(observer);
		
		// Postconditions
		context.checking(new Expectations() {{
			never (observer).update(with(any(DataModelImpl.class)), with(equal(update)));			
		}});
		
		// Method under test
		observable.notifyObservers(null, update);
	}

	
	@Test 
	public void testUpdateOfTwoObservers() {
		// Preconditions
		final DataModelObserver observer1 = context.mock(DataModelObserver.class, "1");
		final DataModelObserver observer2 = context.mock(DataModelObserver.class, "2");
		final DataModel model = new DataModelImpl();
		final Update update = new BillTotalUpdate(new BigDecimal("0.00"));
		observable.addObserver(observer1);
		observable.addObserver(observer2);
		
		// Postconditions
		context.checking(new Expectations() {{
			oneOf (observer1).update(model, update);			
			oneOf (observer2).update(model, update);			
		}});
		
		// Method under test
		observable.notifyObservers(model, update);
	}

	
	
}