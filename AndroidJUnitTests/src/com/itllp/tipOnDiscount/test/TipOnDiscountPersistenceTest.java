package com.itllp.tipOnDiscount.test;

import com.itllp.tipOnDiscount.TipOnDiscount;
import com.itllp.tipOnDiscount.TipOnDiscountApplication;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.test.MockDataModelPersister;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.impl.PreferencesFilePersister;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class TipOnDiscountPersistenceTest 
extends ActivityInstrumentationTestCase2<TipOnDiscount> {

	
	@SuppressWarnings("deprecation")
	public TipOnDiscountPersistenceTest() {
		// Using the non-deprecated version of this constructor requires API 8
		super("com.itllp.tipOnDiscount", TipOnDiscount.class);
	}

	
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PreferencesFilePersister appPrefs = new PreferencesFilePersister(
        		TipOnDiscountApplication.APP_PREFERENCES_FILE);
        appPrefs.beginSave(this.getInstrumentation().getTargetContext());
        try {
        	appPrefs.save(TipOnDiscountApplication.UNIT_TEST_KEY, true);
        	appPrefs.endSave();
        } catch (Exception e) {
        	fail("Could not set unit test mode");
        }
        getInstrumentation().waitForIdleSync();
        Log.d("TipOnDiscount", "Setting mock data model persister");
        DataModelPersisterFactory.clearDataModelPersister();
		DataModelPersisterFactory.setDataModelPersister(new MockDataModelPersister());

    }

    
    @Override
    protected void tearDown() throws Exception {
        PreferencesFilePersister appPrefs = new PreferencesFilePersister(
        		TipOnDiscountApplication.APP_PREFERENCES_FILE);
        appPrefs.beginSave(this.getInstrumentation().getTargetContext());
        try {
        	appPrefs.save(TipOnDiscountApplication.UNIT_TEST_KEY, false);
        	appPrefs.endSave();
        } catch (Exception e) {
        	fail("Could not unset unit test mode");
        }
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
    	MockDataModelPersister mockDataModelPersister = (MockDataModelPersister)
    			DataModelPersisterFactory.getDataModelPersister();
		assertEquals("Incorrect data model restored", 
				DataModelFactory.getDataModel(), 
				mockDataModelPersister.mock_getLastRestoredDataModel());
		assertEquals("Incorrect persister used to restore",
				PersisterFactory.getPersister(), 
				mockDataModelPersister.mock_getLastRestoredPersister());
		assertEquals("Incorrect context used to restore", context, 
				mockDataModelPersister.mock_getLastRestoredContext());
	}
}
