package sk.nuit.blanche.core;


import java.util.ArrayList;
import java.util.List;

import sk.nuit.blanche.utils.Log;
import sk.nuit.blanche.utils.Security;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class ApplicationStorage {
    private static final String APP_SHARED_PREFS = ApplicationStorage.class.getSimpleName(); //  Name of the file -.xml
    
    private static final String IMAGES_COUNT			  = Security.sha1("images_count");
    private static final String IMAGE_PREFIX			  = Security.sha1("images_prefix");
   
    
    private SharedPreferences sharedPrefs;
    private Editor prefsEditor;

    public ApplicationStorage(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }
    
//    public void setIsLoggedIn(boolean isLoggedIn){
//    	prefsEditor.putString(TAG_LOGGED_IN, Security.sha1(isLoggedIn));
//    	prefsEditor.commit();
//    }
//    
//    
//    public boolean isLoggedIn(){
//    	return sharedPrefs.getString(TAG_LOGGED_IN,"")
//    				.equals(Security.sha1(true));    	
//    }   
//    
//    public boolean isRegistered(){
//    	return sharedPrefs.getString(TAG_REGISTERED,"")
//    				.equals(Security.sha1(true));    	
//    }   
    
    public void addImage(String id){
//    	Log.i("adding image");
    	int count = loadInt(IMAGES_COUNT)+1;
    	saveValue(IMAGES_COUNT, count);
    	saveValue(IMAGE_PREFIX+count, id);
    	Log.i("images "+loadInt(IMAGES_COUNT));
    }
    
    public List<String> getDownloadedImages(){
    	List<String> tmp = new ArrayList<String>();
    	int size = loadInt(IMAGES_COUNT);
    	
    	for(int i=1;i<=size;i++){
    		tmp.add(loadString(IMAGE_PREFIX+i));
    	}
    	return tmp;
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
