package feri.rvir.elocator.android;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.maps.LineItemizedOverlay;
import feri.rvir.elocator.android.maps.MapControls;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UsersResource;

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
	
	private List<User> users;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingdetails);
		thisActivity=this;
		
		Serializer<User> serializer=new Serializer<User>();
		User user=null;
		try {
			user = serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users");
        cr.setRequestEntityBuffering(true);
        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getPassword(), user.getPassword());
        UsersResource resource=cr.wrap(UsersResource.class);
        
        users=null;
        try {
        	users=resource.accept(user.getUsername());
        } catch (RuntimeException e) {
        	
        }
        
        populateSpinner();
		
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
	
	private void populateSpinner() {
		ArrayList<String> usersString=new ArrayList<String>();
		if(users!=null) {
			for(User u:users) {
				usersString.add(u.getUsername());
			}
			
			Spinner spinnerUsers=(Spinner)findViewById(R.id.details_spinnerUsers);
			ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, usersString);
			spinnerUsers.setAdapter(arrayAdapter);
		}
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
