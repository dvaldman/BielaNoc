package sk.nuit.blanche.parser;

import java.io.InputStream;

import org.json.JSONObject;

import sk.nuit.blanche.model.BaseResponse;
import sk.nuit.blanche.net.DownloadUtils;
import sk.nuit.blanche.net.DownloadUtils.Parser;
import sk.nuit.blanche.net.DownloadUtils.Status;
import sk.nuit.blanche.utils.Log;
import android.content.Context;
import android.os.Bundle;



public class VersionParser extends Parser {
	
	private static final long serialVersionUID = -1146044586601432066L;

	@Override
	public int parse(Context context, InputStream inputStream, Bundle results) {
		try {
			String response = DownloadUtils.convertStreamToString(inputStream);
			
			Log.i("verion Response: "+response);
			
			JSONObject json = new JSONObject(response);
			BaseResponse baseResponse = parseBaseResponse(json);			
			if(!baseResponse.isSuccess()){
				return errorResponse(results, baseResponse);
			}
			
//			VersionHolder versionHolder = new VersionHolder();
//			versionHolder.setTopologyVersionID(json.getInt(Constants.KEYWORD_VERSION_ID));
//			versionHolder.setRSAKeyId(json.getInt(Constants.KEYWORD_VERSION_ID));
//			baseResponse.setResponseBody(versionHolder);
			
			results.putSerializable("response", baseResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return Status.EXCEPTION;
		}
		return Status.OK;
	}

}
