package com.itllp.tipOnDiscount.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class StubResultReceiver extends ResultReceiver {

	public static final int NO_CODE = -1;
	private int stub_lastResult = NO_CODE;
	
	public StubResultReceiver(Handler handler) {
		super(handler);
	}
	
	protected void onReceiveResult (int resultCode, Bundle resultData) {
		stub_lastResult = resultCode;
	}
	
	public int stub_getLastResultCode() {
		return stub_lastResult;
	}

}
