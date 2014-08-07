package sk.nuit.blanche.parser;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sk.nuit.blanche.core.Constants;
import sk.nuit.blanche.db.DBHelper;
import sk.nuit.blanche.db.Tables;
import sk.nuit.blanche.model.BaseResponse;
import sk.nuit.blanche.net.DownloadUtils;
import sk.nuit.blanche.net.DownloadUtils.Parser;
import sk.nuit.blanche.net.DownloadUtils.Status;
import sk.nuit.blanche.utils.Log;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;



public class ArtistsParser extends Parser {
	
	private static final long serialVersionUID = -1146044586601432066L;

	@Override
	public int parse(Context context, InputStream inputStream, Bundle results) {
		try {
			String response = DownloadUtils.convertStreamToString(inputStream);
			
			Log.i("verion Response: "+response);
			
			JSONObject json = new JSONObject(response);
			
			parseArtists(json.getJSONArray(Constants.KEYWORD_ARTISTS),context);
//			VersionHolder versionHolder = new VersionHolder();
//			versionHolder.setTopologyVersionID(json.getInt(Constants.KEYWORD_VERSION_ID));
//			versionHolder.setRSAKeyId(json.getInt(Constants.KEYWORD_VERSION_ID));
//			baseResponse.setResponseBody(versionHolder);
			
//			results.putSerializable("response", baseResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return Status.EXCEPTION;
		}
		return Status.OK;
	}
	
	private void parseArtists(JSONArray artistsArray,Context context) throws JSONException{
		DBHelper database = DBHelper.getInstance(context); 
		database.deleteValuesFromTable(Tables.Artists.TABLE_NAME, null, null, null);
		ContentValues values = new ContentValues();
		
		for (int i = 0; i< artistsArray.length(); i++){
			JSONObject artistJsonItem = artistsArray.getJSONObject(i);
			values.clear();
			
			
			values.put(Tables.Artists.NAME, artistJsonItem.getString(Constants.KEYWORD_NAME));
			values.put(Tables.Artists.WORK, artistJsonItem.getString(Constants.KEYWORD_WORK));
			values.put(Tables.Artists.IMAGE, artistJsonItem.getString(Constants.KEYWORD_IMAGE));
			values.put(Tables.Artists.PLACE, artistJsonItem.getString(Constants.KEYWORD_PLACE));
			values.put(Tables.Artists.COUNTRY, artistJsonItem.getString(Constants.KEYWORD_COUNTRY));
			values.put(Tables.Artists.TYPE, artistJsonItem.getString(Constants.KEYWORD_TYPE));
			values.put(Tables.Artists.DESCRIPTION, artistJsonItem.getString(Constants.KEYWORD_DESCRIPTION));
			values.put(Tables.Artists.FOR_CHILDREN, (artistJsonItem.getBoolean(Constants.KEYWORD_FORCHILDREN)?Tables.dbBoolean.TRUE:Tables.dbBoolean.FALSE));
			values.put(Tables.Artists.LATITUDE, Long.toString(artistJsonItem.getLong(Constants.KEYWORD_LATITUDE)));
			values.put(Tables.Artists.LONGITUDE, Long.toString(artistJsonItem.getLong(Constants.KEYWORD_LONGITUDE)));
			
			database.insertValuesIntoTable(Tables.Artists.TABLE_NAME, values);
			

		}
	}

}
