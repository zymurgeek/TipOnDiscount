package com.itllp.tipOnDiscount.persistence;

public class PersisterFactory {
	private static Persister persister;

	
	public static void setPersister(Persister newPersister) {
		persister = newPersister;
	}


	public static Persister getPersister() {
		return persister;
	}
	

}
