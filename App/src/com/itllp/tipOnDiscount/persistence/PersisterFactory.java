package com.itllp.tipOnDiscount.persistence;

public class PersisterFactory {
	private static Persister persister;

	
	public static void setPersister(Persister newPersister) {
		if (null == persister) {
			persister = newPersister;
		}
	}


	public static Persister getPersister() {
		return persister;
	}
	

	public static void clearPersister() {
		PersisterFactory.persister = null;
	}
}
