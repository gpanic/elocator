package feri.rvir.elocator.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
	private SQLiteDatabase db; // a reference do baze
	private final String DB_NAME = "elocator_database"; // ime baze
	private final int DB_VERSION = 1; // verzija baze

	// imena stolpcev v bazi
	private final String TABELA_IME = "lokacija";
	private final String TABELA_STOLPEC_ID = "id";
	private final String TABELA_STOLPEC_USER = "username";
	private final String TABELA_STOLPEC_LAT = "latitude";
	private final String TABELA_STOLPEC_LONG = "longitude";
	private final String TABELA_STOLPEC_DATE="timestamp";
	Context context;

	class DBmanager extends SQLiteOpenHelper {

		public DBmanager(Context context) {
			// super(context, name, factory, version);
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				//CREATE TABLE database_tabela ( id int primary key,tabela_stolpec_ena int,tabela_stolpec_dva text);
				String table="create table " + TABELA_IME + " ("+TABELA_STOLPEC_ID+" integer primary key autoincrement,"
				+ TABELA_STOLPEC_USER + " text,"
				+ TABELA_STOLPEC_LAT + " text," + TABELA_STOLPEC_LONG
				+ " text,"+TABELA_STOLPEC_DATE + " text)";
				
				db.execSQL(table);
			} catch (SQLException e) {
				Log.e("DBHelper onCreate  ","Error creating table "+e.getMessage());
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table "+TABELA_IME);
			this.onCreate(db);
		}

		
	}
	
	
	private  DBmanager dbManager; //obstaja naj le ena instanca baze

	public DBHelper(Context context) {
		this.context=context;
		
	}
	
	public void close(){ //poskrbimo za zapiranje baze
		this.dbManager.close();
	}
	
	public void open(){
		
		try {
			this.dbManager = new DBmanager(context);
			db = this.dbManager.getWritableDatabase();
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("DBHelper ","Error openning db "+e.getMessage());
		}
	}
	
	public Boolean addRow(String username,String latitude, String longitude, String datum){
	
		ContentValues initialValues = new ContentValues();
		initialValues.put(TABELA_STOLPEC_USER, username);
		initialValues.put(TABELA_STOLPEC_LAT,latitude);
		initialValues.put(TABELA_STOLPEC_LONG,longitude);	
		initialValues.put(TABELA_STOLPEC_DATE,datum);	
		System.out.println("New location saved");
		return db.insert(TABELA_IME,null, initialValues)>0;
		
	}
	
	public boolean deleteRow(int rowId){
		
		return db.delete(TABELA_IME,TABELA_STOLPEC_ID+"="+rowId,null)>0;
		
	}
	
	public boolean updateRow(int rowId,String latitude,String longitude, String date){
		
		ContentValues newValues = new ContentValues();
		newValues.put(TABELA_STOLPEC_LAT, latitude);
		newValues.put(TABELA_STOLPEC_LONG,longitude);
		newValues.put(TABELA_STOLPEC_DATE,date);
		return db.update(TABELA_IME,newValues,TABELA_STOLPEC_ID+"="+rowId,null)>0;
		
	}
	
	public void deleteAllLocations(String username) {
		db.delete(TABELA_IME, TABELA_STOLPEC_USER + "=" + username, null);
	}
	
	public Cursor getRow(int rowId) throws SQLException{
		
		Cursor cursor=db.query(TABELA_IME,
				new String[]{TABELA_STOLPEC_USER,TABELA_STOLPEC_LAT, TABELA_STOLPEC_LONG, TABELA_STOLPEC_DATE},
				TABELA_STOLPEC_ID+"="+rowId,
				null,//selectionArgs,
				null,//groupBy,
				null,//having,
				null//orderBy
				);
		
		if (cursor != null) {
            cursor.moveToFirst();
        }			
		return cursor;	
	}
	
	public Cursor getRowRaw(String username) throws SQLException{
		
		String rawQueryString="SELECT " + TABELA_STOLPEC_ID + ","+ TABELA_STOLPEC_LAT +","+TABELA_STOLPEC_LONG+","+TABELA_STOLPEC_DATE+" FROM " +TABELA_IME
		+" WHERE "+TABELA_STOLPEC_USER+"="+username;
		
		Cursor cursor=db.rawQuery(rawQueryString, null);
		if (cursor != null) {
            cursor.moveToFirst();
        }			
		return cursor;
		
	}
	}
