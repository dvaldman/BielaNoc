package sk.nuit.blanche.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DrawableManager {
    private static final String APP_SHARED_PREFS = DrawableManager.class.getSimpleName() + "_resources"; //  Name of the file -.xml
    private static SharedPreferences sharedPrefs;
    private static Editor prefsEditor;
    private static Context context;
    private static String TAG = "drawable resources";
    private static File mediaStorageDir;
    
    private static Map<String, Drawable> drawableMap;
    
    private static DrawableManager instance;

    private DrawableManager(Context context){
    	this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
        this.context = context;
        drawableMap = new HashMap<String, Drawable>();
        
        mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files"); 
    }
    
    public static DrawableManager getInstance(Context cont){
    	if(instance == null)
    		instance = new DrawableManager(cont);
    	return instance;
    }
	
	public Bitmap getBitmap(String key){
		String fileName = sharedPrefs.getString(key, null);
		Log.i("filename", "> " + fileName);
		if(fileName == null)
			return null;
		return BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator + fileName);
	}
	
	public void setDrawable(String key, String fileName, Bitmap bitmap){
		storeImage(bitmap, fileName);
		prefsEditor.putString(key, fileName);
		prefsEditor.commit();
	}
	
	private void storeImage(Bitmap image, String fileName) {
	    File pictureFile = getOutputMediaFile(fileName);
	    if (pictureFile == null) {
	        Log.d(TAG,
	                "Error creating media file, check storage permissions: ");// e.getMessage());
	        return;
	    } 
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.d(TAG, "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d(TAG, "Error accessing file: " + e.getMessage());
	    }  
	}
	
	private  File getOutputMediaFile(String fileName){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this. 
	    

	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    } 
	    // Create a media file name
	    File mediaFile;
	        String mImageName = fileName;
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    return mediaFile;
	} 
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void addImageToItemLeft(final String urlString, final int imgId, final ImageView imageView,final ProgressBar prog){
		if(drawableMap.containsKey(Integer.toString(imgId))){
			imageView.setBackgroundDrawable(drawableMap.get(Integer.toString(imgId)));
            return;
		}
		
		Bitmap bmp = getBitmap(Integer.toString(imgId));
		
		if (bmp != null) {
			Drawable d =(Drawable)new BitmapDrawable(context.getResources(), bmp);
			drawableMap.put(Integer.toString(imgId), d);
        	imageView.setBackgroundDrawable(d);
            return;
        }
		if(prog != null)
        	prog.setVisibility(View.VISIBLE);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	
            	if(message.obj != null){
            		Drawable d =(Drawable)new BitmapDrawable(context.getResources(), (Bitmap) message.obj);
            		imageView.setBackgroundDrawable(d);
            		setDrawable(Integer.toString(imgId), "file"+imgId+".jpg", (Bitmap) message.obj);
            		drawableMap.put(Integer.toString(imgId), d);
            	}
            	if(prog != null)
            		prog.setVisibility(View.INVISIBLE);
                
            }
        };
        
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bmp =  getBitmapFromURL(urlString);
                Message message = handler.obtainMessage(1, bmp);
                handler.sendMessage(message);
            }
        };
        thread.start();
	}
	
	
	public void addImageToItemRight(final String urlString, final int imgId, final ImageView imageView,final ProgressBar prog){
		if(drawableMap.containsKey(Integer.toString(imgId))){
			imageView.setBackgroundDrawable(drawableMap.get(Integer.toString(imgId)));
            return;
		}
		
		Bitmap bmp = getBitmap(Integer.toString(imgId));
		
		if (bmp != null) {
			Drawable d =(Drawable)new BitmapDrawable(context.getResources(), bmp);
			drawableMap.put(Integer.toString(imgId), d);
        	imageView.setBackgroundDrawable(d);
            return;
        }
        prog.setVisibility(View.VISIBLE);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	
            	if(message.obj != null){
            		Drawable d =(Drawable)new BitmapDrawable(context.getResources(), (Bitmap) message.obj);
            		imageView.setBackgroundDrawable(d);
            		setDrawable(Integer.toString(imgId), "file"+imgId+".jpg", (Bitmap) message.obj);
            		drawableMap.put(Integer.toString(imgId), d);
            	}
            		
            	prog.setVisibility(View.INVISIBLE);
                
            }
        };
        
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bmp =  getBitmapFromURL(urlString);
                Message message = handler.obtainMessage(1, bmp);
                handler.sendMessage(message);
            }
        };
        thread.start();
	}
	
	
}
