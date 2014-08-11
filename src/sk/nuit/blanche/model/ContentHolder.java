package sk.nuit.blanche.model;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import sk.nuit.blanche.db.DBHelper;
import sk.nuit.blanche.db.Tables;
import sk.nuit.blanche.utils.Log;
import android.content.Context;
import android.database.Cursor;

public class ContentHolder {
	
	private static ContentHolder instance;
	private static List<Artist> artists;
	private static Context context;
	
	private ContentHolder(Context context){
		ContentHolder.context = context;
	}
	
	public static ContentHolder getInstance(Context contect){
		if(instance == null)
			instance = new ContentHolder(contect);
		return instance;
	}
	
	
	public List<Artist> getArtists(){
		if(artists == null)
			artists = initArtists();
		return artists;
	}
	
	private List<Artist> initArtists(){
		List<Artist> tmp = new ArrayList<Artist>();
		
		Cursor c = DBHelper.getInstance(context).getResultsFromSingleTable(Tables.Artists.TABLE_NAME);
		
		Artist art = null;
		
		for(int repeat = 0; repeat < 15; repeat++)
        if(c.moveToFirst()){
        	do{
	        	art = new Artist();
	        	art.setId(repeat+c.getInt(c.getColumnIndex(Tables.Artists.ID)));
	        	art.setName(c.getString(c.getColumnIndex(Tables.Artists.NAME)));
	        	art.setWork(c.getString(c.getColumnIndex(Tables.Artists.WORK)));
	        	art.setImage(c.getString(c.getColumnIndex(Tables.Artists.IMAGE)));
	        	art.setPlace(c.getString(c.getColumnIndex(Tables.Artists.PLACE)));
	        	art.setCountry(c.getString(c.getColumnIndex(Tables.Artists.COUNTRY)));
	        	art.setType(c.getString(c.getColumnIndex(Tables.Artists.TYPE)));
	        	art.setDesc(c.getString(c.getColumnIndex(Tables.Artists.DESCRIPTION)));
	        	art.setForKids((c.getInt(c.getColumnIndex(Tables.Artists.FOR_CHILDREN)) == Tables.dbBoolean.TRUE) ? true:false);
	        	art.setLatitude(Double.parseDouble(c.getString(c.getColumnIndex(Tables.Artists.LATITUDE))));
	        	art.setLongitude(Double.parseDouble(c.getString(c.getColumnIndex(Tables.Artists.LONGITUDE))));
	        	
	        	Log.i("place: "+art.getLatitude() + ", "+art.getLongitude());
	        	
	        	tmp.add(art);
	        	
	        	
	        	
        	}while(c.moveToNext());
    	}
		
		return tmp;
	}
}
