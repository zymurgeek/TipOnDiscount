package com.itllp.tipOnDiscount.test;

import com.itllp.tipOnDiscount.SetDefaultsActivity;
import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.StubDataModelPersister;
import com.itllp.tipOnDiscount.model.test.StubDataModel;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;

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
	}
}
