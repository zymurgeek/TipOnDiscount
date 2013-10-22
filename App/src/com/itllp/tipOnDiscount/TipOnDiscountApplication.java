package com.itllp.tipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.impl.DataModelImpl;
import com.itllp.tipOnDiscount.model.persistence.DataModelPersisterFactory;
import com.itllp.tipOnDiscount.model.persistence.impl.DataModelPersisterImpl;
import com.itllp.tipOnDiscount.persistence.PersisterFactory;
import com.itllp.tipOnDiscount.persistence.impl.PreferencesFilePersister;

import android.app.Application;
import android.util.Log;

public class TipOnDiscountApplication extends Application {
	
	public static final String TOD_PREFERENCES_FILE = "TipOnDiscountPrefs";

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d("TipOnDiscount", "Setting production factories");
		DataModelFactory.setDataModel(new DataModelImpl());
		DataModelPersisterFactory.setDataModelPersister(
				new DataModelPersisterImpl());
		PersisterFactory.setPersister(new PreferencesFilePersister(
				TOD_PREFERENCES_FILE));
	}
}
