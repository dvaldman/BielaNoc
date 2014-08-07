package sk.nuit.blanche.utils;


public class Log {
	private static boolean isDebugEnabled;
	private static final String TAG = "BN";
	
	public  static void initialize(boolean isDebugEnabled){
		Log.isDebugEnabled = isDebugEnabled;
	}
	
	public static void i(String tag, String msg){
		if(isDebugEnabled)
			android.util.Log.i(tag, msg);
	}
	
	public static void i(String msg){
		i(TAG, msg);
	}
	
	public static void e(String tag, String msg){
		if(isDebugEnabled)
			android.util.Log.e(tag, msg);
	}
	
	public static void e(String msg){
		e(TAG, msg);
	}
}
