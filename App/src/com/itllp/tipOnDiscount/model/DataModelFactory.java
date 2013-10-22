package com.itllp.tipOnDiscount.model;

import android.util.Log;

public class DataModelFactory {
	private static DataModel dataModel;
	
	public static void setDataModel(DataModel newDataModel) {
		Log.d("TipOnDiscount", "setDataModel() was: " + getDataModelDescription(dataModel));
		dataModel = newDataModel;
		Log.d("TipOnDiscount", "setDataModel() now is: " + getDataModelDescription(dataModel));
	}

	private static String getDataModelDescription(DataModel dataModel) {
		String dataModelDescription = "null";
		if (null != dataModel) {
			dataModelDescription = dataModel.getClass().toString() + dataModel.toString();
		}
		return dataModelDescription;
	}

	public static DataModel getDataModel() {
		Log.d("TipOnDiscount", "getDataModel(): " + getDataModelDescription(dataModel));
		return dataModel;
	}
	

}
