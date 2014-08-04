package sk.nuit.blanche.core;

import android.app.Application;


public class ThisApplication extends Application {
	private ApplicationStorage applicationStorage;
	
	private GPSTracker gpsTracker;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		applicationStorage = new ApplicationStorage(getBaseContext());
		gpsTracker = new GPSTracker(getBaseContext());
	}
	
	
	public GPSTracker getGPSTracker(){
		return this.gpsTracker;
	}
	
	
	public ApplicationStorage getAppStorage(){
		return applicationStorage;
	}	
}
