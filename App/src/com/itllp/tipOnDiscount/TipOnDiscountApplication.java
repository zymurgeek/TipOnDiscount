package com.itllp.tipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.impl.DataModelImpl;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.impl.DataModelPersisterImpl;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.impl.PreferencesFilePersister;

import android.app.Application;

public class TipOnDiscountApplication extends Application {
	
	public static final String TOD_PREFERENCES_FILE = "TipOnDiscountPrefs";

	@Override
	public void onCreate() {
		super.onCreate();

		DataModelFactory.setDataModel(new DataModelImpl());
		DataModelPersisterFactory.setDataModelPersister(
				new DataModelPersisterImpl());
		PersisterFactory.setPersisterForApp(new PreferencesFilePersister(
				TOD_PREFERENCES_FILE));
	}
}
