package com.itllp.tipOnDiscount.model.persistence;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.persistence.Persister;

public interface DataModelPersister {

	public abstract void saveState(DataModel model, Persister persister,
			Context context);

	public abstract void restoreState(DataModel dataModel, Persister persister,
			Context context);

}