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
import sk.nuit.blanche.parser.VersionParser;


public class DownloadService extends AbstractDownloadService {

	private final static String BASE_URL = "https://mtsservicedevelop.azurewebsites.net/MtsService.svc";
//	private final static String BASE_URL = "http://mtsservicedemo.azurewebsites.net/MtsService.svc";
	private final static String REGISTRATION_USER = "/registration/clients";
	private final static String DATA_VERSION = "/actualTopologyVersion";
	private final static String TICKET_TYPES = "/ticketTypes";
	private final static String PURCHASED_TICKETS = "/purchasedTickets";
	private final static String ACTIVATE_TICKET = "/purchasedTicketsActivation";
	private final static String TICKET_PURCHASE = "/ticketPurchase";
	private final static String LOGIN = "/login";
	private final static String CARD_REGISTRATION_BASE = "https://secure.ogone.com/ncol/test/orderstandard.asp";
	private final static String CARD_REGISTRATION = "/purchaseTicketNoRegCard";
	private final static String REGISTERED_CARDS = "/getRegisteredCards";
	private final static String CARD_REGISTRATION_WITHOUT_PURCHASE = "/registration/cards";
	private final static String CARD_DELETE = "/deleteRegisteredCard";
	private final static String FLASH_PASS_ANIMATION = "/flashPassAnimation";
	private final static String PRIVATE_KEY = "/actualClientPrivateKey";
	
	public DownloadService() {
		super(DownloadService.class.toString());
	}
	
	public void registerUser(Context context, Callback callback, String email, String username, String password) throws JSONException{
		JSONObject registrationData = new JSONObject();		
		registrationData.put("UserName", username);
		registrationData.put("Email", email);
		registrationData.put("Password", password);
//		GPSTracker gps = ((BaseActivity)context).getApp().getGPSTracker();
//		registrationData.put("Latitude", gps.getLatitude());
//		registrationData.put("Longitude", gps.getLongitude());
		
		addSecurityParams(context, registrationData);
		
		Log.i(registrationData.toString());
		
		new RequestBuilder()
		.setMethod(Methods.POST)
		.setParams(registrationData.toString())
		.setCallback(callback)
//		.setParser(RegistrationParser.class) 
		.setUrl(BASE_URL + REGISTRATION_USER)
		.execute(context, DownloadService.class);
	}
	
	public void checkDataVersion(Context context, Callback callback) throws JSONException{
		JSONObject versionData = new JSONObject();		
		
		addSecurityParams(context, versionData);
		
		new RequestBuilder()
		.setMethod(Methods.POST)
		.setParams(versionData.toString())
		.setCallback(callback)
		.setParser(VersionParser.class) 
		.setUrl(BASE_URL + DATA_VERSION)
		.execute(context, DownloadService.class);
	}
	
	
	
	
	
	private void addSecurityParams(Context context, JSONObject object1) throws JSONException {
		ApplicationStorage appData = new ApplicationStorage(context);
		
		
		JSONObject object = new JSONObject();
		long time = System.currentTimeMillis();
		object.put(Constants.KEYWORD_TIME_STAMP, time);
		object.put(Constants.KEYWORD_TOPOLOGY_ID, Config.TOPOLOGY_ID);
		object.put(Constants.KEYWORD_CONNECTION_KEY, Security.sha1(String.valueOf(time) + Security.SALT));
		// TODO remove test user in future
		object.put(Constants.KEYWORD_DEVICE_NUMBER, Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));
		object1.put(Constants.KEYWORD_HEADER, object);
	}
	
	
	
}
