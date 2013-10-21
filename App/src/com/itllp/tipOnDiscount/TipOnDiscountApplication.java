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
	public static final String APP_PREFERENCES_FILE = "ApplicationPrefs";
	public static final String UNIT_TEST_KEY = "isDebug";

	@Override
	public void onCreate() {
		super.onCreate();

		PreferencesFilePersister appPrefs = new PreferencesFilePersister
				(APP_PREFERENCES_FILE);
		Boolean isUnitTest = appPrefs.retrieveBoolean(getApplicationContext(),
				UNIT_TEST_KEY);
		if (null == isUnitTest || isUnitTest.booleanValue() == false) {
			Log.d("TipOnDiscount", "Setting production factories");
			DataModelFactory.clearDataModel();
			DataModelFactory.setDataModel(new DataModelImpl());
			DataModelPersisterFactory.clearDataModelPersister();
			DataModelPersisterFactory.setDataModelPersister(
					new DataModelPersisterImpl());
			PersisterFactory.clearPersister();
			PersisterFactory.setPersister(new PreferencesFilePersister(
					TOD_PREFERENCES_FILE));
		}
	}
}
