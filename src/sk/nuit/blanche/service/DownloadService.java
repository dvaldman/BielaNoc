package sk.nuit.blanche.service;

import org.json.JSONException;
import org.json.JSONObject;

import sk.nuit.blanche.core.ApplicationStorage;
import sk.nuit.blanche.core.Config;
import sk.nuit.blanche.core.Constants;
import sk.nuit.blanche.core.GPSTracker;
import sk.nuit.blanche.net.DownloadUtils.AbstractDownloadService;
import sk.nuit.blanche.net.DownloadUtils.Callback;
import sk.nuit.blanche.net.DownloadUtils.Methods;
import sk.nuit.blanche.utils.Log;
import sk.nuit.blanche.utils.Security;
import android.R.anim;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.provider.Settings.Secure;
import sk.nuit.blanche.net.DownloadUtils.RequestBuilder;
import sk.nuit.blanche.parser.ArtistsParser;


public class DownloadService extends AbstractDownloadService {

	private final static String BASE_URL = "http://bielanoc.sk/";
	private final static String ARTISTS = "artists.json";
	
	public DownloadService() {
		super(DownloadService.class.toString());
	}
	
	public void getArtists(Context context, Callback callback) throws JSONException{
		Log.i("downloading artists");
		new RequestBuilder()
		.setMethod(Methods.GET)
		.setCallback(callback)
		.setParser(ArtistsParser.class) 
		.setUrl(BASE_URL + ARTISTS)
		.execute(context, DownloadService.class);
		
	}
	
}
