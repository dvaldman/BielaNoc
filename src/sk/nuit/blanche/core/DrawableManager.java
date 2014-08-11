package sk.nuit.blanche.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import sk.nuit.blanche.R.drawable;
import sk.nuit.blanche.model.Artist;
import sk.nuit.blanche.model.ContentHolder;
import sk.nuit.blanche.utils.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DrawableManager {
    private static Map<String, Drawable> drawableMap;
    
    private static DrawableManager instance;
	private static Context context;
	private static ApplicationStorage storage;
	
	
	private DrawableManager(Context context){
		DrawableManager.context = context;
		drawableMap = new HashMap<String, Drawable>();
		storage = new ApplicationStorage(context);
		
	}
	
	public static DrawableManager getInstance(Context contect){
		if(instance == null)
			instance = new DrawableManager(contect);
		initDrawableMap();
		return instance;
	}
	
	private static void initDrawableMap(){
//		Log.i("INIT _______________" + storage.getDownloadedImages().size());
		for(String img:storage.getDownloadedImages())
			drawableMap.put(img, loadDrawableInternalStorage(img));
		
//		Log.i("INIT end_______________" + drawableMap.size());
	}
	
	
	
	private void saveStreamToInternalStorage(InputStream input,String id) {
		
		
		File assets = new File(Environment.getExternalStorageDirectory().getPath()+"/BN_KE_14/");//"/sdcard/TSBValidator/");
		assets.mkdirs();
		
		File file = new File(assets, "file"+id+".res");
		
		try {
			input.reset();
		     final OutputStream output = new FileOutputStream(file);
		    try {
		        try {
		            final byte[] buffer = new byte[1024];
		            int read;

		            while ((read = input.read(buffer)) != -1)
		                output.write(buffer, 0, read);

		            output.flush();
		        } finally {
		            output.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
		    try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static Drawable loadDrawableInternalStorage(String id) {
		try {

			String pathName = Environment.getExternalStorageDirectory().getPath()+"/BN_KE_14/file"+id+".res"; 
			FileInputStream is = new FileInputStream(pathName);
			
			Drawable d = Drawable.createFromStream(is, "src");
			if(d==null)
			Log.i("drawable is null "+pathName);
			else
				Log.i("drawable is NOT null");
			return d;

		} catch (Exception e) {
		Log.i("load exc "+e.getMessage());
		}
		
		return null;
	}

   
    public Drawable fetchDrawable(String urlString,String id) {
        if (drawableMap.containsKey(id)) {
            return drawableMap.get(id);
        }

        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");

            if (drawable != null) {
            	saveStreamToInternalStorage(is,id);
            	storage.addImage(id);
                drawableMap.put(id, drawable);
                
            } else {
              Log.e("could not get thumbnail");
            }

            return drawable;
        } catch (MalformedURLException e) {
            Log.e("fetchDrawable failed" +e);
            return null;
        } catch (IOException e) {
            Log.e("fetchDrawable failed"+ e);
            return null;
        }
    }

    public void fetchScaledDrawableOnThread(final String urlString, final int imgId, final ImageView imageView,final ProgressBar prog, final OnDownloadFinishedListener listener) {
    	Log.i("draw map size "+drawableMap.size());
        if (drawableMap.containsKey(Integer.toString(imgId))) {
//            imageView.setImageDrawable(drawableMap.get(Integer.toString(imgId)));
        	imageView.setBackgroundDrawable(drawableMap.get(Integer.toString(imgId)));
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)drawableMap.get(Integer.toString(imgId))).getBitmap(), 150, 150, false));
            
            return;
        }
        prog.setVisibility(View.VISIBLE);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	if(message.obj != null)
            		imageView.setBackgroundDrawable((Drawable) message.obj);
            	prog.setVisibility(View.INVISIBLE);
                if(listener!=null) listener.onFinish();
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                Drawable drawable = fetchDrawable(urlString,Integer.toString(imgId));
                Message message = handler.obtainMessage(1, drawable);
                handler.sendMessage(message);
            }
        };
        thread.start();
    }

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }
    
    public interface OnDownloadFinishedListener {
    	public void onFinish();
    }
}
