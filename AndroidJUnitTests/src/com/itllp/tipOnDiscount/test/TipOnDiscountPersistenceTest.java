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
import android.util.Log;

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
        
        Log.d("TipOnDiscount", "Setting up mock classes in factories");
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
