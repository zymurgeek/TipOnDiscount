package com.itllp.tipOnDiscount.model.persistence;

import android.util.Log;

public class DataModelPersisterFactory {
	private static DataModelPersister dataModelPersister;

	
	public static void setDataModelPersister
	(DataModelPersister newDataModelPersister) {
		if (null == dataModelPersister) {
			dataModelPersister = newDataModelPersister;
			Log.d("TipOnDiscount", "newPersister is " + newDataModelPersister);
		} else {
			Log.d("TipOnDiscount", "newPersister rejected");
		}
	}


	public static DataModelPersister getDataModelPersister() {
		Log.d("TipOnDiscount", "getDataModelPersister is " + dataModelPersister);
		return dataModelPersister;
	}
	

	public static void clearDataModelPersister() {
		Log.d("TipOnDiscount", "cleared DataModelPersister");
		DataModelPersisterFactory.dataModelPersister = null;
	}

}
