package sk.nuit.blanche.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import sk.nuit.blanche.core.Constants;
import sk.nuit.blanche.model.BaseResponse;
import sk.nuit.blanche.model.ResponseError;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;

public abstract class DownloadUtils {
	private static final int TIMEOUT_MS = 10000;
	public static final String ERROR_CODE = "error_code";
	public static final String ERROR_MESSAGE = "error_message";
	
	public static abstract class AbstractDownloadService extends IntentService {
		private static final String ACTION = "com.webservice.AbstractDownloadService";
		
		private static final String EXTRA_PARAMS = "params";
		private static final String EXTRA_CALLBACK = "receiver";
		private static final String EXTRA_METHOD = "method";
		private static final String EXTRA_URL = "url";
		private static final String EXTRA_PARSER = "parser";
		
		private ResultReceiver callback;
		
		public AbstractDownloadService(String name) {
			super(name);		
		}
		
		@Override
		protected void onHandleIntent(Intent intent) {
			callback = intent.getParcelableExtra(EXTRA_CALLBACK);
			int method = intent.getIntExtra(EXTRA_METHOD, Methods.GET);
			String url = intent.getStringExtra(EXTRA_URL);
			String params = intent.getStringExtra(EXTRA_PARAMS);
			Parser parser = (Parser) intent.getSerializableExtra(EXTRA_PARSER);
			
			callback.send(Status.RUNNING, Bundle.EMPTY);
			
			try {
				Bundle result = new Bundle();
				//
				int resultCode = DownloadUtils.execute(this, method, url, params, result, parser);
				//
				if (callback != null) {
					callback.send(resultCode, result);
				}

			} 
			catch (ConnectException e) {
				if(callback != null) callback.send(Status.ERROR, createErrorResult(Error.INTERNET_ERROR, e.getMessage()));
			}
			catch (IOException e) {		
				if(callback != null) callback.send(Status.ERROR, createErrorResult(Error.SERVER_ERROR, e.getMessage()));
			}
			catch (Exception e) {
				if (callback != null) {
					if(callback != null) callback.send(Status.EXCEPTION, Bundle.EMPTY);
				}
			}
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			
			callback = null;
		}
	}
	
	public static int post(Context context, String endpoint, String params, Bundle results, Parser parser) throws Exception {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid url: " + endpoint);
		}

		int resultCode = Status.OK;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("Content-Type", "application/json");

			if (params != null) {
				conn.setDoOutput(true);
				conn.setConnectTimeout(TIMEOUT_MS);
				conn.setRequestProperty("Content-Length", Integer.toString(params.length()));

				// Post the request
				OutputStream out = conn.getOutputStream();
				out.write(params.getBytes());
				out.close();
			}

			// Handle the response code
			 int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
			
			resultCode = parser.parse(context, conn.getInputStream(), results);

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return resultCode;

	}

	public static int get(Context context, String endpoint, String params, Bundle results, Parser parser) throws IOException, Exception {
		URL url;
		try {
			url = params != null ? new URL(endpoint + "?" + params) : new URL(endpoint);

		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}

		int resultCode = Status.OK;
		HttpURLConnection conn = null;
		try {
			// Make connection
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(TIMEOUT_MS);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			results.putString("params", params);
			resultCode = parser.parse(context, conn.getInputStream(), results);

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultCode;
	}

	public static int execute(Context context, int method, String url, String params, Bundle results, Parser parser)
			throws Exception {
		Thread.sleep(1500);
		if (method == Methods.GET) {
			return get(context, url, params, results,parser);
		} else if (method == Methods.POST) {
			return post(context, url, params, results, parser);
		}
		return -1;
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;

	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }

	    is.close();

	    return sb.toString();
	}
	
	public static Bundle createErrorResult(int code, String message){
		Bundle bundle = new Bundle();
		bundle.putInt(ERROR_CODE, Error.INTERNET_ERROR);
		bundle.putString(ERROR_MESSAGE, message);

		return bundle;
	}
	
	public static abstract class Parser implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		protected int errorResponse(Bundle bundle, BaseResponse response){
			bundle.putInt(DownloadUtils.ERROR_CODE, response.getError(0).getErrorCode());
			bundle.putString(DownloadUtils.ERROR_MESSAGE, response.getError(0).getErrorMessage());
			
			return Status.ERROR;
		}
		
		public abstract int parse(Context context, InputStream inputStream, Bundle results);
	}
	
	public static class Error {
		public static final int INTERNET_ERROR = 0;
		public static final int SERVER_ERROR = 1;
	}
	
	public static class Status {
		public static final int ERROR = -1;
		public static final int OK = 0;
		public static final int RUNNING = 1;
		public static final int EXCEPTION = 2;
		
	}
	
	public static class Methods {
		public static final int GET = 0;
		public static final int POST = 1;
	}
	
	public static abstract class Callback extends ResultReceiver {

		public Callback() {
			super(putHandlerIfNeccessary());
		}

		private static Handler putHandlerIfNeccessary() {
			if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
				return new Handler();
			} else {
				return null;
			}
		}
		
		public int getId() {
			return 0;
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			switch (resultCode) {
				case Status.RUNNING: {
					onStarted();
					break;
				}
				case Status.OK: {
					onSuccess(resultData);
					break;
				}
				case Status.ERROR: {
					int errorCode = resultData.getInt(ERROR_CODE);
					String errorMessage = resultData.getString(ERROR_MESSAGE);
					onError(errorCode, errorMessage);
					break;
				}
				case Status.EXCEPTION: {
					onException();
					break;
				}
			}
		}

		public abstract void onSuccess(Bundle data);
		public abstract void onStarted();
		public abstract void onError(int code, String message);
		public abstract void onException();

	}
	
	public static class RequestBuilder {
		private int mMethod;
		private String mUrl;
		private String mParams;
		private Callback mCallback;
		private Parser mParser;

		public RequestBuilder setMethod(int method) {
			mMethod = method;
			return this;
		}

		public RequestBuilder setUrl(String url) {
			mUrl = url;
			return this;
		}

		public RequestBuilder setParams(String params) {
			mParams = params;
			return this;
		}
		
		public RequestBuilder setParser (Class <? extends Parser> clazz) {
			Parser parser;
			try {
				parser = clazz.newInstance();
				this.mParser = parser;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return this;
		}

		public RequestBuilder setCallback(Callback callback) {
			mCallback = callback;
			return this;
		}

		public void execute(Context context, Class<? extends AbstractDownloadService> serviceClass) {
			if (mUrl == null) {
				return;
			}

			Intent intent = new Intent(context, serviceClass)
					.setAction(AbstractDownloadService.ACTION)
					.putExtra(AbstractDownloadService.EXTRA_METHOD, mMethod)
					.putExtra(AbstractDownloadService.EXTRA_URL, mUrl)
					.putExtra(AbstractDownloadService.EXTRA_PARAMS, mParams)
					.putExtra(AbstractDownloadService.EXTRA_CALLBACK, mCallback)
					.putExtra(AbstractDownloadService.EXTRA_PARSER, mParser);

			context.startService(intent);
		}
	}
	
	public static class ParamBuilder {

		private HashMap<String, String> mParams;

		public ParamBuilder() {
			mParams = new HashMap<String, String>();
		}

		public ParamBuilder addParam(String param, String value) {
			mParams.put(param, value);
			return this;
		}

		public String build() {
			StringBuilder sb = new StringBuilder();
			Iterator<Entry<String, String>> iterator = mParams.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();

				sb.append(param.getKey())
						.append('=')
						.append(param.getValue());

				if (iterator.hasNext()) {
					sb.append('&');
				}
			}

			return sb.toString();
		}
	}
}
