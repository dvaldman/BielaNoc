package sk.nuit.blanche.core;

import java.util.Calendar;
import java.util.Date;

import sk.nuit.blanche.utils.Security;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class ApplicationStorage {
    private static final String APP_SHARED_PREFS = ApplicationStorage.class.getSimpleName(); //  Name of the file -.xml
    
    private static final String TAG_LOGGED_IN				  = Security.sha1("logged_in");
    private static final String TAG_REGISTERED 				  = Security.sha1("registered");
    private static final String TAG_EMAIL 					  = Security.sha1("email");
    private static final String TAG_USERNAME 				  = Security.sha1("username");
    private static final String TAG_PASSWORD 				  = Security.sha1("password");
    private static final String TAG_USER_ID 				  = Security.sha1("user_id");
    
    private static final String TAG_INACTIVE_TAB_CHANGED 	  =	Security.sha1("inactive_tab_changed");
    private static final String TAG_ACTIVE_TAB_CHANGED 		  = Security.sha1("active_tab_changed");
    private static final String TAG_EXPIRED_TAB_CHANGED 	  =	Security.sha1("expired_tab_changed");
    
    public static final String TAG_LATEST_TICKET_LIST_VERSION = Security.sha1("latest_ticket_list");
    public static final String TAG_LATEST_PRIVATE_KEY_VERSION = Security.sha1("latest_private_key");
    
    public static final String TAG_LAST_FLASH_PASS_ANIMS_UPDATE_DATE = Security.sha1("fp_anim_updated");
    
    private SharedPreferences sharedPrefs;
    private Editor prefsEditor;

    public ApplicationStorage(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }
    
    public void setIsLoggedIn(boolean isLoggedIn){
    	prefsEditor.putString(TAG_LOGGED_IN, Security.sha1(isLoggedIn));
    	prefsEditor.commit();
    }
    
   
    
    public boolean isLoggedIn(){
    	return sharedPrefs.getString(TAG_LOGGED_IN,"")
    				.equals(Security.sha1(true));    	
    }   
    
    public boolean isRegistered(){
    	return sharedPrefs.getString(TAG_REGISTERED,"")
    				.equals(Security.sha1(true));    	
    }   
    
    
   
    public boolean checkLatestConfigDataVersion(int actualVersion) {
    	boolean result = false;
    	int latestVersion = -1;
    	if(sharedPrefs.contains(TAG_LATEST_TICKET_LIST_VERSION))
    		latestVersion = sharedPrefs.getInt(TAG_LATEST_TICKET_LIST_VERSION, -1);
    	
    	if(latestVersion < actualVersion)
    		result = true;
//    	prefsEditor.putInt(TAG_LATEST_TICKET_LIST_VERSION, actualVersion);
//    	prefsEditor.commit();
    	
    	return result;
    }
    
    public boolean checkLatestPrivateKeyVersion(int actualVersion) {
    	boolean result = false;
    	int latestVersion = -1;
    	if(sharedPrefs.contains(TAG_LATEST_PRIVATE_KEY_VERSION))
    		latestVersion = sharedPrefs.getInt(TAG_LATEST_PRIVATE_KEY_VERSION, -1);
    	
    	if(latestVersion < actualVersion)
    		result = true;
//    	prefsEditor.putInt(TAG_LATEST_PRIVATE_KEY_VERSION, actualVersion);
//    	prefsEditor.commit();
    	
    	return result;
    }
    
    /**
     * Will check if flash pass animation update is necessary to do.
     * If returning true, todays date will be written into file, and will return false for rest of the day
     * @return will return false if flash pass animation was downloaded today, true otherwise 
     */
    public boolean haveToUpdateFlashPass() {
    	String lastHashed = loadString(TAG_LAST_FLASH_PASS_ANIMS_UPDATE_DATE);
    	
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	int todayInYear = c.get(Calendar.DAY_OF_YEAR);
    	int todaysYear = c.get(Calendar.YEAR);
    	
    	String hashedToday = Security.sha1((todayInYear+todaysYear)+"");
    	
    	boolean haveToUpdate = !hashedToday.equals(lastHashed);
    	
    	if(haveToUpdate) {
    		saveValue(TAG_LAST_FLASH_PASS_ANIMS_UPDATE_DATE, hashedToday);
    	}
    	return haveToUpdate;
    }
    
    
    public void saveValue(String key,String value){
    	prefsEditor.putString(key, value);
    	prefsEditor.commit();
    }
    
    public void saveValue(String key,int value){
    	prefsEditor.putInt(key, value);
    	prefsEditor.commit();
    }
    
    public void saveValue(String key,long value){
    	prefsEditor.putLong(key, value);
    	prefsEditor.commit();
    }
    
    public void saveValue(String key,boolean value){
    	prefsEditor.putBoolean(key, value);
    	prefsEditor.commit();
    }
    
    public void saveValue(String key,float value){
    	prefsEditor.putFloat(key, value);
    	prefsEditor.commit();
    }
    
    
    public String loadString(String key){
    	return sharedPrefs.getString(key,null);
    }
    
    public int loadInt(String key){
    	return sharedPrefs.getInt(key,0);
    }
    
    public long loadLong(String key){
    	return sharedPrefs.getLong(key,0);
    }
    
    public boolean loadBoolean(String key){
    	return sharedPrefs.getBoolean(key,false);
    }
    
    public float loadFloat(String key){
    	return sharedPrefs.getFloat(key,0);
    }
    
    public String loadString(String key, String defVal){
    	return sharedPrefs.getString(key,defVal);
    }
    
    public int loadInt(String key, int defVal){
    	return sharedPrefs.getInt(key,defVal);
    }
    
    public long loadLong(String key, Long defVal){
    	return sharedPrefs.getLong(key,defVal);
    }
    
    public boolean loadBoolean(String key, boolean defVal){
    	return sharedPrefs.getBoolean(key,defVal);
    }
    
    public float loadFloat(String key, float defVal){
    	return sharedPrefs.getFloat(key,defVal);
    }
    
    public boolean contains(String key){
    	return sharedPrefs.contains(key);
    }
}
