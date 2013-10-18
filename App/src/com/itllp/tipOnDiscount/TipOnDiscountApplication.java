package com.itllp.tipOnDiscount;
import com.itllp.tipOnDiscount.model.DataModel;
import com.itllp.tipOnDiscount.model.DataModelFactory;
import com.itllp.tipOnDiscount.model.impl.DataModelImpl;
import android.app.Application;
import android.util.Log;

public class TipOnDiscountApplication extends Application {
	
	public static final String PREFERENCES_FILE = "TipOnDiscountPrefs";

	@Override
	public void onCreate() {
		super.onCreate();

		DataModel dataModel = new DataModelImpl();
		Log.d("TipOnDiscount", "Setting production data model");
		DataModelFactory.setDataModel(dataModel);
	}
}
