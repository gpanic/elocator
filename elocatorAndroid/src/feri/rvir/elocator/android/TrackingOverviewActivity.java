package feri.rvir.elocator.android;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.maps.TrackingItemizedOverlay;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.rest.resource.user.User;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class TrackingOverviewActivity extends MapActivity {
	
	private TrackingOverviewActivity thisActivity;
	
	MapView mapView;
	private List<Overlay> mapOverlays;
	private TrackingItemizedOverlay itemizedOverlayHome;
	private TrackingItemizedOverlay itemizedOverlayTracking;
	private Drawable drawableHome;
	private Drawable drawableUser;
	
	private User user;
	private boolean gotFirstLocation=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingoverview);
		thisActivity=this;

		Serializer<User> serializer=new Serializer<User>();
		try {
			user=serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
		} catch (FileNotFoundException e) {
			user=null;
			e.printStackTrace();
		}
		
		mapView=(MapView)findViewById(R.id.trackingoverview_mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();
		drawableHome=getResources().getDrawable(R.drawable.ic_marker_home);
		drawableUser=getResources().getDrawable(R.drawable.ic_marker_user);
		
		LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener=new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			
			@Override
			public void onProviderEnabled(String provider) {
			}
			
			@Override
			public void onProviderDisabled(String provider) {
			}
			
			@Override
			public void onLocationChanged(Location location) {
				System.out.println("LOCATION CHANGED");
				GeoPoint point=new GeoPoint((int)(location.getLatitude()*1e6), (int)(location.getLongitude()*1e6));
				OverlayItem overlayItem=new OverlayItem(point, user.getUsername(), Calendar.getInstance().getTime().toGMTString());

				if(!gotFirstLocation) {
					System.out.println("GOT LOCATION FIRST TIME");
					itemizedOverlayHome=new TrackingItemizedOverlay(drawableHome, thisActivity);
					itemizedOverlayHome.addOverlay(overlayItem);
					mapOverlays.add(itemizedOverlayHome);
					gotFirstLocation=true;
					mapView.invalidate();
					adjustZoom();
				} else {
					int pos=mapOverlays.indexOf(itemizedOverlayHome);
					itemizedOverlayHome=new TrackingItemizedOverlay(drawableHome, thisActivity);
					itemizedOverlayHome.addOverlay(overlayItem);
					mapOverlays.set(pos, itemizedOverlayHome);
					mapView.invalidate();
					adjustZoom();
				}
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		OverlayItem overlayItem1=new OverlayItem(new GeoPoint(19240000,-99120000), "gregor.panic", Calendar.getInstance().getTime().toGMTString());
		OverlayItem overlayItem2=new OverlayItem(new GeoPoint(25000000,-99120000), "jernej.legvart", Calendar.getInstance().getTime().toGMTString());
		itemizedOverlayTracking=new TrackingItemizedOverlay(drawableUser, thisActivity);
		itemizedOverlayTracking.addOverlay(overlayItem1);
		itemizedOverlayTracking.addOverlay(overlayItem2);
		
		mapOverlays.add(itemizedOverlayTracking);
		adjustZoom();
		
	}
	
	private void adjustZoom() {
		ArrayList<OverlayItem> overlayItems=new ArrayList<OverlayItem>();
		overlayItems.addAll(itemizedOverlayTracking.getOverlayItems());
		if(itemizedOverlayHome!=null) {
			overlayItems.addAll(itemizedOverlayHome.getOverlayItems());
		}
		
		int maxLat=Integer.MIN_VALUE;
		int minLat=Integer.MAX_VALUE;
		int maxLon=Integer.MIN_VALUE;
		int minLon=Integer.MAX_VALUE;
		
		for(OverlayItem o:overlayItems) {
			int lat=o.getPoint().getLatitudeE6();
			int lon=o.getPoint().getLongitudeE6();
			
			maxLat=Math.max(lat,maxLat);
			minLat=Math.min(lat, minLat);
			maxLon=Math.max(lon, maxLon);
			minLon=Math.min(lon, minLon);
		}
		
		mapView.getController().zoomToSpan(Math.abs(maxLat-minLat), Math.abs(maxLon-minLon));
		mapView.getController().animateTo(new GeoPoint((maxLat+minLat)/2, (maxLon+minLon)/2));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
