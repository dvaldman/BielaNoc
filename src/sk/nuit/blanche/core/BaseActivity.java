package sk.nuit.blanche.core;

import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	
	public ThisApplication getApp(){
		return (ThisApplication) getApplication();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		getApp().getGPSTracker().stopUsingGPS();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		
	}

}
