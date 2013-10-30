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

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.DefaultsImpl;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.defaults.persistence.test.StubDefaultsPersister;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;

public class SetDefaultsActionTest 
extends ActivityInstrumentationTestCase2<TipOnDiscount> {

	private TipOnDiscount tipOnDiscount;
	private Instrumentation instrumentation;
	
	@SuppressWarnings("deprecation")
	public SetDefaultsActionTest() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
        
		setActivityInitialTouchMode(false);  // Enable sending key events
	}

	
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        
		DefaultsFactory.setDefaults(new DefaultsImpl());
		DefaultsPersisterFactory.setDefaultsPersister(
				new StubDefaultsPersister());

        DataModelPersisterFactory.setDataModelPersister(new StubDataModelPersister());
        StubDataModel stubDataModel = new StubDataModel();
        DataModelFactory.setDataModel(stubDataModel);
        
       instrumentation = getInstrumentation();
        tipOnDiscount = (TipOnDiscount)getActivity();
    }

	
	public void testStartSetDefaults() {
		// register next activity that need to be monitored.
		ActivityMonitor activityMonitor = instrumentation.addMonitor
				(SetDefaultsActivity.class.getName(), null, false);
		
		// Call method under test
		tipOnDiscount.startSetDefaults();
		
		// Verify postconditions
		SetDefaultsActivity newActivity = (SetDefaultsActivity)instrumentation
				.waitForMonitorWithTimeout(activityMonitor, 5000);
		assertNotNull("Activity did not start", newActivity);
		newActivity.finish();
	}
}
