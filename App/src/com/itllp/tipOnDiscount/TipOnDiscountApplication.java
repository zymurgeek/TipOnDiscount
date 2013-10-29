// Copyright 2013 David A. Greenbaum
/*
This file is part of Tip On Discount.

Tip On Discount is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Tip On Discount is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with Tip On Discount.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.itllp.tipOnDiscount;
import com.itllp.tipOnDiscount.defaults.DefaultsFactory;
import com.itllp.tipOnDiscount.defaults.DefaultsImpl;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersisterFactory;
import com.itllp.tipOnDiscount.defaults.persistence.impl.DefaultsPersisterImpl;
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

		DefaultsFactory.setDefaults(new DefaultsImpl());
		DefaultsPersisterFactory.setDefaultsPersister(
				new DefaultsPersisterImpl());
		
		DataModelFactory.setDataModel(new DataModelImpl());
		DataModelPersisterFactory.setDataModelPersister(
				new DataModelPersisterImpl());
		PersisterFactory.setPersisterForApp(new PreferencesFilePersister(
				TOD_PREFERENCES_FILE));
	}
}
