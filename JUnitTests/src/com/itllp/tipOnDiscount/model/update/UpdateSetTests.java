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

/**
 * This JUnit test harness verifies that the UpdateSet will store 
 * update notifications and that there can be only one notification
 * of each type. 
 */
package com.itllp.tipOnDiscount.model.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;


public class UpdateSetTests {
	private UpdateSet us;
	BigDecimal oneDollar;
	BigDecimal twoDollars;

	
	@Before
	public void initialize() {
		us = new UpdateSet();
		oneDollar = new BigDecimal("1.00");
		twoDollars = new BigDecimal("2.00");
	}

	
	/* Tests initialization of the update set.
	 */
	@Test
	public void testInitialization() {
		assertEquals("New update set should be empty", 0, us.size());
	}
	
	
	/* Test that the update set does not allow more than one item of each
	 * update type. 
	 */
	@Test
	public void testDuplicateAddition() {
		BillTotalUpdate bt1 = new BillTotalUpdate(oneDollar);
		us.add(bt1);
		
		BillTotalUpdate bt2 = new BillTotalUpdate(twoDollars);
		us.add(bt2);
		
		assertEquals("Update set should have one item", 1, us.size());
		assertTrue("Update set does not contain a bt item", us.contains(bt1));
		BillTotalUpdate btTemp = (BillTotalUpdate) us.remove(bt2);
		assertEquals("Update set does not contain correct bt item", 
				bt2, btTemp);
	}
	
	

	/* Test that the update set contains only the last added update
	 * each type of update. 
	 */
	@Test
	public void testNewSingleAddition() {
		BillTotalUpdate bt = new BillTotalUpdate(oneDollar);
		us.add(bt);
		assertEquals("Update set should have one item", 1, us.size());
		assertTrue("Update set does not contain item", us.contains(bt));
	}
	
	
	/* Test that the update set contains only the last added update
	 * each type of update. 
	 */
	@Test
	public void testNewMultipleAdditions() {
		BillTotalUpdate bt = new BillTotalUpdate(oneDollar);
		us.add(bt);
		BillSubtotalUpdate bs = new BillSubtotalUpdate(oneDollar);
		us.add(bs);
		assertEquals("Update set should have two items", 2, us.size());
		assertTrue("Update set does not contain bt item", us.contains(bt));
		assertTrue("Update set does not contain bs item", us.contains(bs));
	}
	
	

}
