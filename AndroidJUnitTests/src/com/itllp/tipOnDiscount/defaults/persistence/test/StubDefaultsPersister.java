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
package com.itllp.tipOnDiscount.defaults.persistence.test;

import android.content.Context;

import com.itllp.tipOnDiscount.defaults.Defaults;
import com.itllp.tipOnDiscount.defaults.persistence.DefaultsPersister;

public class StubDefaultsPersister implements DefaultsPersister {

	private Context stub_lastSavedContext;
	private Defaults stub_lastSavedDefaults;
	private Context stub_lastRestoredContext;
	private Defaults stub_lastRestoredDefaults;
	
	public void mock_reset() {
		stub_lastSavedDefaults = null;
		stub_lastSavedContext = null;
		stub_lastRestoredDefaults = null;
		stub_lastRestoredContext = null;
		
	}
	
	@Override
	public void saveState(Defaults defaults, Context context) {
		stub_lastSavedDefaults = defaults;
		stub_lastSavedContext = context;
	}

	
	public Defaults stub_getLastSavedDefaults() {
		return stub_lastSavedDefaults;
	}
	
	
	public Context stub_getLastSavedContext() {
		return stub_lastSavedContext;
	}
	
	
	@Override
	public void restoreState(Defaults defaults, Context context) {
		stub_lastRestoredDefaults = defaults;
		stub_lastRestoredContext = context;
	}

	
	public Defaults stub_getLastRestoredDefaults() {
		return stub_lastRestoredDefaults;
	}
	
	
	public Context stub_getLastRestoredContext() {
		return stub_lastRestoredContext;
	}
	
	
}
