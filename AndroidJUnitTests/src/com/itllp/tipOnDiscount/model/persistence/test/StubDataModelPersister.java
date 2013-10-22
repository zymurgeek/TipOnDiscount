package com.itllp.tipOnDiscount.model.persistence.test;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersister;
import com.itllp.tipOnDiscount.persistence.Persister;

public class StubDataModelPersister implements DataModelPersister {

	private DataModel mock_lastSavedDataModel;
	private Persister mock_lastSavedPersister;
	private Context mock_lastSavedContext;
	private DataModel mock_lastRestoredDataModel;
	private Persister mock_lastRestoredPersister;
	private Context mock_lastRestoredContext;
	
	public void mock_reset() {
		mock_lastSavedDataModel = null;
		mock_lastSavedPersister = null;
		mock_lastSavedContext = null;
		mock_lastRestoredDataModel = null;
		mock_lastRestoredPersister = null;
		mock_lastRestoredContext = null;
		
	}
	
	@Override
	public void saveState(DataModel model, Persister persister, Context context) {
		mock_lastSavedDataModel = model;
		mock_lastSavedPersister = persister;
		mock_lastSavedContext = context;
	}

	
	public DataModel mock_getLastSavedDataModel() {
		return mock_lastSavedDataModel;
	}
	
	
	public Persister mock_getLastSavedPersister() {
		return mock_lastSavedPersister;
	}
	
	
	public Context mock_getLastSavedContext() {
		return mock_lastSavedContext;
	}
	
	
	@Override
	public void restoreState(DataModel dataModel, Persister persister,
			Context context) {
		mock_lastRestoredDataModel = dataModel;
		mock_lastRestoredPersister = persister;
		mock_lastRestoredContext = context;
	}

	
	public DataModel mock_getLastRestoredDataModel() {
		return mock_lastRestoredDataModel;
	}
	
	
	public Persister mock_getLastRestoredPersister() {
		return mock_lastRestoredPersister;
	}
	
	
	public Context mock_getLastRestoredContext() {
		return mock_lastRestoredContext;
	}
	
	
}
