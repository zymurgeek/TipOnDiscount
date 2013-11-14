package com.itllp.tipOnDiscount.model;


public class DataModelInitializerFactory {

	private static DataModelInitializer dataModelInitializer;
	
	public static void setDataModelInitializer(
			DataModelInitializer initializer) {
		dataModelInitializer = initializer;
	}

	public static DataModelInitializer getDataModelInitializer() {
		return dataModelInitializer;
	}
}
