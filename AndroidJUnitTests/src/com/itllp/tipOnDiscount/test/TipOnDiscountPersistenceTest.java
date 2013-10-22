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
package com.itllp.tipOnDiscount.test;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.test.StubPersister;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class TipOnDiscountPersistenceTest 
extends ActivityInstrumentationTestCase2<TipOnDiscount> {

	private StubDataModel stubDataModel;
	private StubDataModelPersister stubDataModelPersister;
	private StubPersister stubPersister;


	@SuppressWarnings("deprecation")
	public TipOnDiscountPersistenceTest() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        stubDataModel = new StubDataModel();
        DataModelFactory.setDataModel(stubDataModel);
        stubPersister = new StubPersister();
        PersisterFactory.setPersister(stubPersister);
        stubDataModelPersister = new StubDataModelPersister();
		DataModelPersisterFactory.setDataModelPersister(
				stubDataModelPersister);
    }

    
	public void testRestoreInstanceState() {
		// Set up preconditions
		final TipOnDiscount activity = getActivity();
		final Context context = activity;
		
		// Call method under test
    	activity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					activity.restoreInstanceState(context);
    				}
    			}
        	);
    	
    	// Verify postconditions
    	getInstrumentation().waitForIdleSync();
		assertEquals("Incorrect data model restored", stubDataModel, 
				stubDataModelPersister.mock_getLastRestoredDataModel());
		assertEquals("Incorrect persister used to restore",
				stubPersister, 
				stubDataModelPersister.mock_getLastRestoredPersister());
		assertEquals("Incorrect context used to restore", context, 
				stubDataModelPersister.mock_getLastRestoredContext());
	}
	
	
	public void testSaveInstanceState() {
		// Set up preconditions
		final TipOnDiscount activity = getActivity();
		final Context context = activity;
		
		// Call method under test
    	activity.runOnUiThread(
    			new Runnable() {
    				public void run() {
    					activity.saveInstanceState(context);
    				}
    			}
        	);
		
    	// Verify postconditions
    	getInstrumentation().waitForIdleSync();
		assertEquals("Incorrect data model saved", stubDataModel, 
				stubDataModelPersister.mock_getLastSavedDataModel());
		assertEquals("Incorrect persister used to save", stubPersister, 
				stubDataModelPersister.mock_getLastSavedPersister());
		assertEquals("Incorrect context used to save", context, 
				stubDataModelPersister.mock_getLastSavedContext());
		
	}
}
