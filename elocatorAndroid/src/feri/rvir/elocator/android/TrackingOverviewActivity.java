package feri.rvir.elocator.android;

import java.util.Calendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.maps.TrackingItemizedOverlay;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class TrackingOverviewActivity extends MapActivity {
	
	private List<Overlay> mapOverlays;
	private TrackingItemizedOverlay itemizedOverlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingoverview);
		
		MapView mapView=(MapView)findViewById(R.id.trackingoverview_mapview);
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_marker);
		itemizedOverlay = new TrackingItemizedOverlay(drawable,this);
		
		addOverlayItem(new GeoPoint(19240000,-99120000), "gregor.panic", Calendar.getInstance());
		addOverlayItem(new GeoPoint(20000000,-99120000), "jernej.legvart", Calendar.getInstance());
		
	}
	
	private void addOverlayItem(GeoPoint point, String username, Calendar date) {
		OverlayItem overlayItem = new OverlayItem(point, username, date.getTime().toGMTString());
		
		itemizedOverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
