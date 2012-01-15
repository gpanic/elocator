package feri.rvir.elocator.android.maps;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapControls {
	
	public static void adjustZoom(ArrayList<OverlayItem> overlayItems, MapView mapView) {
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

}
