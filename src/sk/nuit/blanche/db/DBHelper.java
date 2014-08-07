package sk.nuit.blanche.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper
{
	
	private static final String DATABASE_NAME 			= "mticketing-crypt.db";;
	private static final String LEGACY_DATABASE_NAME	= "mticketing.db";
	private static final int VERSION		= 1;
	private static final String PASSPHRASE = "setPassphrase";
	
	private static DBHelper	instance	= null;
	
	public class EqualitySymbols{
		public static final String LESS_THAN 	 = " < ?";
		public static final String LESS_OR_EQUAL = " <= ?";
		public static final String MORE_THAN 	 = " > ?";
		public static final String MORE_OR_EQUAL = " >= ?";
		public static final String EQUAL 	 	 = " = ?";
		public static final String LIKE 		 = " like ?";
	}
	
	public static DBHelper getInstance(Context context){
		if (instance == null)
			instance = new DBHelper(context);
		return instance;
	}
	
	private DBHelper(Context context){
		super(context, DATABASE_NAME, null, VERSION);
	}
	 
	
	
	
	private void executeSQL(SQLiteDatabase db, String sql){
		try{
			db.beginTransaction();
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}
		catch (SQLiteException e){
//			Log.i("db error "+e.getMessage());
		}
		finally {
		      db.endTransaction();
		}
	}
	
	public int insertValuesIntoTable(String table, ContentValues vals){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.beginTransaction();
		int result = (int) db.insertWithOnConflict(table, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		return result;
	}
	
	public int updateRow(String table, ContentValues vals, String where, String[] whereArgs) {
		SQLiteDatabase db = this.getWritableDatabase();
		int result = db.updateWithOnConflict(table, vals, where, whereArgs, SQLiteDatabase.CONFLICT_REPLACE);
		db.close();
		
		return result;
	}
	
	
	public int deleteValuesFromTable(String table, String[] whereThese, String[] areEquealThese,String[] equality){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.beginTransaction();
		String where = "";
		
		if(whereThese != null){ 
			for(int i=0; i<whereThese.length;i++){
				if(equality == null)
					where = where + whereThese[i] +EqualitySymbols.EQUAL;
				else
					where = where + whereThese[i] +equality[i];
				if(i+1 != whereThese.length)
					where = where + " AND ";
			}
		}
		int result = (int) db.delete(table, where, areEquealThese);
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		return result;
	}
	
	public Cursor getResultsFromSingleTable(String fromTable, String[] whereThese, String[] areEquealThese,String[] equality){
		return getResultsFromSingleTable("*",fromTable,whereThese,areEquealThese,equality);
	}
	
	public Cursor getResultsFromSingleTable(String fromTable, String[] whereThese, String[] areEquealThese){
		return getResultsFromSingleTable("*",fromTable,whereThese,areEquealThese,null);
	}
	
	public Cursor getResultsFromSingleTable(String giveMeColumns, String fromTable, String[] whereThese, String[] areEquealThese){
		return getResultsFromSingleTable(giveMeColumns,fromTable,whereThese,areEquealThese,null);
	}
	
	public Cursor getResultsFromSingleTable(String giveMeColumns, String fromTable, String[] whereThese, String[] areEquealThese,String[] equality){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "SELECT "+giveMeColumns+" FROM "+fromTable;
		
		if(whereThese != null){ 
			sql = sql +" WHERE ";
			for(int i=0; i<whereThese.length;i++){
				if(equality == null)
					sql = sql + whereThese[i] +EqualitySymbols.EQUAL;
				else
					sql = sql + whereThese[i] +equality[i];
				if(i+1 != whereThese.length)
					sql = sql + " AND ";
			}
		}
		
		Cursor c = db.rawQuery(sql,areEquealThese);
		return c;
	}
	
	public Cursor getResultsForCustomSelect(String select){
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor c = db.rawQuery(select,null);
		return c;
	}


	public boolean hasTableData(String tableName){
		
		boolean result = true;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "+tableName,null);
		
		if (!cursor.moveToFirst())
	    	result = false;
		
	    cursor.close();
	    return result;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		executeSQL(db,Tables.Artists.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
