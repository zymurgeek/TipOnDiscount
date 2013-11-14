package com.itllp.tipOnDiscount.model.persistence.test;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersister;

public class StubDataModelPersister implements DataModelPersister {

	private DataModel mock_lastSavedDataModel;
	private Context mock_lastSavedContext;
	private DataModel mock_lastRestoredDataModel;
	private Context mock_lastRestoredContext;
	
	public void mock_reset() {
		mock_lastSavedDataModel = null;
		mock_lastSavedContext = null;
		mock_lastRestoredDataModel = null;
		mock_lastRestoredContext = null;
		
	}
	
	@Override
	public void saveState(DataModel model, Context context) {
		mock_lastSavedDataModel = model;
		mock_lastSavedContext = context;
	}

	
	public DataModel mock_getLastSavedDataModel() {
		return mock_lastSavedDataModel;
	}
	
	
	public Context mock_getLastSavedContext() {
		return mock_lastSavedContext;
	}
	
	
	@Override
	public void restoreState(DataModel dataModel, Context context) {
		mock_lastRestoredDataModel = dataModel;
		mock_lastRestoredContext = context;
	}

	
	public DataModel mock_getLastRestoredDataModel() {
		return mock_lastRestoredDataModel;
	}
	
	
	public Context mock_getLastRestoredContext() {
		return mock_lastRestoredContext;
	}
	
	
}
