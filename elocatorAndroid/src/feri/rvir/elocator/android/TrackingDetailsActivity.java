package feri.rvir.elocator.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.maps.LineItemizedOverlay;
import feri.rvir.elocator.android.maps.MapControls;
import feri.rvir.elocator.rest.resource.user.User;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TrackingDetailsActivity extends MapActivity {
	
	private TrackingDetailsActivity thisActivity;
	private LineItemizedOverlay itemizedOverlay;
	private Drawable drawableInfo;
	
	private MapView mapView;
	private List<Overlay> mapOverlays;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingdetails);
		thisActivity=this;
		
		ArrayList<User> users=new ArrayList<User>();
		users.add(new User("gregor.panic", "asdf"));
		users.add(new User("jernej.legvart", "asdf"));
		
		ArrayList<String> usersString=new ArrayList<String>();
		for(User u:users) {
			usersString.add(u.getUsername());
		}
		
		Spinner spinnerUsers=(Spinner)findViewById(R.id.details_spinnerUsers);
		ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, usersString);
		spinnerUsers.setAdapter(arrayAdapter);
		
		mapView=(MapView)findViewById(R.id.details_mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays=mapView.getOverlays();
		drawableInfo=getResources().getDrawable(R.drawable.ic_marker_info);
		
		OverlayItem item1=new OverlayItem(new GeoPoint(19240000,-99120000), "gregor.panic", Calendar.getInstance().getTime().toGMTString());
		OverlayItem item2=new OverlayItem(new GeoPoint(25040000,-93120000), "gregor.panic", Calendar.getInstance().getTime().toGMTString());
		OverlayItem item3=new OverlayItem(new GeoPoint(20040000,-89120000), "gregor.panic", Calendar.getInstance().getTime().toGMTString());
		
		itemizedOverlay=new LineItemizedOverlay(drawableInfo, thisActivity);
		itemizedOverlay.addOverlay(item1);
		itemizedOverlay.addOverlay(item2);
		itemizedOverlay.addOverlay(item3);
		
		mapOverlays.add(itemizedOverlay);
		MapControls.adjustZoom(itemizedOverlay.getOverlayItems(), mapView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MapControls.adjustZoom(itemizedOverlay.getOverlayItems(), mapView);
	}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
