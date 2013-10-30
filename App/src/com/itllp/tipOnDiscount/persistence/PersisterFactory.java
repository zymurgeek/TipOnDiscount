package com.itllp.tipOnDiscount.persistence;

// TODO Remove this factory.  Have Persister passed in to the DataModelPersister constructor
public class PersisterFactory {
	private static Persister appPersister;
	private static Persister defaultsPersister;

	
	public static void setPersisterForApp(Persister persister) {
		appPersister = persister;
	}


	public static Persister getPersisterForApp() {
		return appPersister;
	}


	public static void setPersisterForDefaults(Persister persister) {
		defaultsPersister = persister;
	}
	

	public static Persister getPersisterForDefaults() {
		return defaultsPersister;
	}


}
