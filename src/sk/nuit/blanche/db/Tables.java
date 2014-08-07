package sk.nuit.blanche.db;

import sk.nuit.blanche.core.Constants;


public class Tables{
	
	public static class dbBoolean{
		public static final int TRUE  = 1;
		public static final int FALSE = 0;
	}
	
	public static class Artists
	{
		public static final String	TABLE_NAME			= "Tickets";
		
		public static final String	NAME				= TABLE_NAME + "_" + Constants.KEYWORD_NAME;
		public static final String	WORK				= TABLE_NAME + "_" + Constants.KEYWORD_WORK;
		public static final String  IMAGE				= TABLE_NAME + "_" + Constants.KEYWORD_IMAGE;
		public static final String	PLACE				= TABLE_NAME + "_" + Constants.KEYWORD_PLACE;
		public static final String	COUNTRY				= TABLE_NAME + "_" + Constants.KEYWORD_COUNTRY;
		public static final String	TYPE				= TABLE_NAME + "_" + Constants.KEYWORD_TYPE;
		public static final String	DESCRIPTION			= TABLE_NAME + "_" + Constants.KEYWORD_DESCRIPTION;
		public static final String	FOR_CHILDREN		= TABLE_NAME + "_" + Constants.KEYWORD_FORCHILDREN;
		public static final String	LATITUDE			= TABLE_NAME + "_" + Constants.KEYWORD_LATITUDE;
		public static final String	LONGITUDE			= TABLE_NAME + "_" + Constants.KEYWORD_LONGITUDE;
		
		public static final String	CREATE_TABLE		= "CREATE TABLE " + TABLE_NAME + " (" + 
																							NAME + " TEXT, "+ 
																							WORK + " TEXT, "+
																							IMAGE + " TEXT, " +
																							PLACE + " TEXT, "+
																							COUNTRY + " TEXT, "+
																							TYPE + " TEXT, "+
																							DESCRIPTION + " TEXT, "+
																							FOR_CHILDREN + " INTEGER, "+
																							LATITUDE + " TEXT, "+
																							LONGITUDE + " TEXT"+
																						");";
	}
	
	
	
}
