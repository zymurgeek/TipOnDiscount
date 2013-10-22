package com.itllp.tipOnDiscount.model.persistence;

import android.util.Log;

public class DataModelPersisterFactory {
	private static DataModelPersister dataModelPersister;

	
	public static void setDataModelPersister
	(DataModelPersister newDataModelPersister) {
		dataModelPersister = newDataModelPersister;
		Log.d("TipOnDiscount", "newPersister is " + newDataModelPersister);
	}


	public static DataModelPersister getDataModelPersister() {
		Log.d("TipOnDiscount", "getDataModelPersister is " + dataModelPersister);
		return dataModelPersister;
	}
	

}
