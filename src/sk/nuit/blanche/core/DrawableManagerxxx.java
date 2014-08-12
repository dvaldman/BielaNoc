//package sk.nuit.blanche.core;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.ByteArrayBuffer;
//
//import sk.nuit.blanche.R.drawable;
//import sk.nuit.blanche.model.Artist;
//import sk.nuit.blanche.model.ContentHolder;
//import sk.nuit.blanche.utils.Log;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//public class DrawableManager {
//    private static Map<String, Drawable> drawableMap;
//    
//    private static DrawableManager instance;
//	private static Context context;
//	private static ApplicationStorage storage;
//	
//	
//	private DrawableManager(Context context){
//		DrawableManager.context = context;
//		drawableMap = new HashMap<String, Drawable>();
//		storage = new ApplicationStorage(context);
//		
//	}
//	
//	public static DrawableManager getInstance(Context contect){
//		if(instance == null)
//			instance = new DrawableManager(contect);
//		initDrawableMap();
//		return instance;
//	}
//	
//	private static void initDrawableMap(){
//		for(String img:storage.getDownloadedImages())
//			drawableMap.put(img, loadDrawableInternalStorage(img));
//	}
//	
//	
//	private void storeImage(Bitmap image, String fileName) {
//	    File pictureFile = getOutputMediaFile(fileName);
//	    if (pictureFile == null) {
//	        return;
//	    } 
//	    try {
//	        FileOutputStream fos = new FileOutputStream(pictureFile);
//	        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
//	        fos.close();
//	    } catch (FileNotFoundException e) {
//	        
//	    } catch (IOException e) {
//	        
//	    }  
//	}
//	
//	private  File getOutputMediaFile(String fileName){
//	    // To be safe, you should check that the SDCard is mounted
//	    // using Environment.getExternalStorageState() before doing this. 
//	    
//
//	    // This location works best if you want the created images to be shared
//	    // between applications and persist after your app has been uninstalled.
//
//	    // Create the storage directory if it does not exist
//	    if (! mediaStorageDir.exists()){
//	        if (! mediaStorageDir.mkdirs()){
//	            return null;
//	        }
//	    } 
//	    // Create a media file name
//	    File mediaFile;
//	        String mImageName = fileName;
//	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
//	    return mediaFile;
//	} 
//	
//   
//    public Drawable fetchDrawable(String urlString,String id) {
//        if (drawableMap.containsKey(id)) {
//            return drawableMap.get(id);
//        }
//
//        try {
//        	getBitmapFromURL(urlString)
//            Drawable drawable = Drawable.createFromStream(is, "src");
//
//            if (drawable != null) {
//            	saveStreamToInternalStorage(is,id);
//            	storage.addImage(id);
//                drawableMap.put(id, drawable);
//                
//            } else {
//              Log.e("could not get thumbnail");
//            }
//
//            return drawable;
//        } catch (MalformedURLException e) {
//            Log.e("fetchDrawable failed" +e);
//            return null;
//        } catch (IOException e) {
//            Log.e("fetchDrawable failed"+ e);
//            return null;
//        }
//    }
//
//    public void fetchScaledDrawableOnThread(final String urlString, final int imgId, final ImageView imageView,final ProgressBar prog, final OnDownloadFinishedListener listener) {
//
//        if (drawableMap.containsKey(Integer.toString(imgId))) {
//        	imageView.setBackgroundDrawable(drawableMap.get(Integer.toString(imgId)));
//            return;
//        }
//        prog.setVisibility(View.VISIBLE);
//
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message message) {
//            	if(message.obj != null)
//            		imageView.setBackgroundDrawable((Drawable) message.obj);
//            	prog.setVisibility(View.INVISIBLE);
//                if(listener!=null) listener.onFinish();
//            }
//        };
//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Drawable drawable = fetchDrawable(urlString,Integer.toString(imgId));
//                Message message = handler.obtainMessage(1, drawable);
//                handler.sendMessage(message);
//            }
//        };
//        thread.start();
//    }
//
//    public static Bitmap getBitmapFromURL(String src) {
//	    try {
//	        URL url = new URL(src);
//	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	        connection.setDoInput(true);
//	        connection.connect();
//	        InputStream input = connection.getInputStream();
//	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
//	        return myBitmap;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        return null;
//	    }
//	}
//    
//    public interface OnDownloadFinishedListener {
//    	public void onFinish();
//    }
//}
