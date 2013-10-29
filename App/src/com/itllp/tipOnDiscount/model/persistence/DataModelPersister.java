package com.itllp.tipOnDiscount.model.persistence;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.persistence.Persister;

public interface DataModelPersister {
//TODO Pass Persister to constructor rather than method calls
	public abstract void saveState(DataModel model, Persister persister,
			Context context);

	public abstract void restoreState(DataModel dataModel, Persister persister,
			Context context);

}