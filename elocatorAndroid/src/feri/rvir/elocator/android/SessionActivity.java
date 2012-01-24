package feri.rvir.elocator.android;

import java.io.FileNotFoundException;
import java.util.List;

import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.maps.LineItemizedOverlay;
import feri.rvir.elocator.android.maps.MapControls;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.dbhelper.DBHelper;
import feri.rvir.elocator.rest.resource.user.User;

public class SessionActivity extends MapActivity {
	
	private SessionActivity thisActivity;
	
	private LineItemizedOverlay itemizedOverlay;
	
	private User user=null;
	private MapView mapView;
	private List<Overlay> mapOverlays;
	
	private Drawable drawableInfo;
	private Drawable drawableStart;
	private Drawable drawableFinish;
	
	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session);
		
		thisActivity=this;
		
		drawableInfo=getResources().getDrawable(R.drawable.ic_marker_info);
		drawableInfo.setBounds(-drawableInfo.getIntrinsicWidth()/2, -drawableInfo.getIntrinsicHeight(), drawableInfo.getIntrinsicWidth()/2, 0);
		drawableStart=getResources().getDrawable(R.drawable.ic_marker_start);
		drawableStart.setBounds(-drawableStart.getIntrinsicWidth()/2, -drawableStart.getIntrinsicHeight(), drawableStart.getIntrinsicWidth()/2, 0);
		drawableFinish=getResources().getDrawable(R.drawable.ic_marker_finish);
		drawableFinish.setBounds(-drawableFinish.getIntrinsicWidth()/2, -drawableFinish.getIntrinsicHeight(), drawableFinish.getIntrinsicWidth()/2, 0);
		
		Serializer<User> serializer=new Serializer<User>();
		try {
			user=serializer.unserialize(openFileInput(getResources().getString(R.string.user_data_store)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		mapView=(MapView)findViewById(R.id.details_mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays=mapView.getOverlays();
		
		populateOverlays();
		
	}
	
	private void populateOverlays() {
		itemizedOverlay=new LineItemizedOverlay(drawableInfo, thisActivity);
		DBHelper db=new DBHelper(getApplicationContext());
		db.open();
		try {
			cursor=db.getRowRaw(user.getUsername());
			while(!cursor.isAfterLast()) {
				String lat=cursor.getString(cursor.getColumnIndex("latitude"));
				String lon=cursor.getString(cursor.getColumnIndex("longitude"));
				double latDouble=Double.parseDouble(lat);
				double lonDouble=Double.parseDouble(lon);
				String date=cursor.getString(cursor.getColumnIndex("timestamp"));
				OverlayItem oi=new OverlayItem(new GeoPoint((int)(latDouble*1E6), (int)(lonDouble*1E6)), user.getUsername(), date);
				if(cursor.isFirst()) {
					oi.setMarker(drawableStart);
				} else if(cursor.isLast()) {
					oi.setMarker(drawableFinish);
				}
				itemizedOverlay.addOverlay(oi);
				cursor.moveToNext();
			}
			cursor.close();
			mapOverlays.add(itemizedOverlay);
			MapControls.adjustZoom(itemizedOverlay.getOverlayItems(), mapView);
		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
