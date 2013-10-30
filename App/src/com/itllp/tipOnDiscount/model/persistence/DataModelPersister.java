package com.itllp.tipOnDiscount.model.persistence;

import android.content.Context;

import com.itllp.tipOnDiscount.model.DataModel;

public interface DataModelPersister {
	public abstract void saveState(DataModel model, Context context);

	public abstract void restoreState(DataModel dataModel, Context context);

}