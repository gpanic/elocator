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
import feri.rvir.elocator.android.util.AsyncTaskResult;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UsersResource;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TrackingDetailsActivity extends MapActivity {
	
	private TrackingDetailsActivity thisActivity;
	private LineItemizedOverlay itemizedOverlay;
	private Drawable drawableInfo;
	
	private MapView mapView;
	private List<Overlay> mapOverlays;
	
	private ArrayList<User> children;
	private ArrayList<String> childrenString;
	
	private User user=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingdetails);
		thisActivity=this;
		
		Serializer<User> serializer=new Serializer<User>();
		try {
			user = serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		populateSpinner();
		
		new GetTrackingTask().execute(user.getUsername(), user.getPassword());
		
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
	
	private void populateChildrenStringArray() {
		childrenString=new ArrayList<String>();
			if(children!=null) {
			for(User u:children) {
				childrenString.add(u.getUsername());
			}
		}
	}
	
	private void populateSpinner() {
		populateChildrenStringArray();
		Spinner spinnerUsers=(Spinner)findViewById(R.id.details_spinnerUsers);
		if(children!=null) {
			if(!children.isEmpty()){
				ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, childrenString);
				spinnerUsers.setAdapter(arrayAdapter);
				spinnerUsers.setEnabled(true);
			} else {
				spinnerUsers.setEnabled(false);
			}
		} else {
			spinnerUsers.setEnabled(false);
		}

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MapControls.adjustZoom(itemizedOverlay.getOverlayItems(), mapView);
		new GetTrackingTask().execute(user.getUsername(), user.getPassword());
		populateSpinner();
	}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private class GetTrackingTask extends AsyncTask<String, Void, Integer> {
		
		private String username;
		private String password;
		
		private ArrayList<User> children;
		
		@Override
		protected  Integer doInBackground(String... params) {
			username=params[0];
			password=params[1];
			ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users");
	        cr.setRequestEntityBuffering(true);
	        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
			try {
				UsersResource resource=cr.wrap(UsersResource.class);
				children=resource.accept(username);
	        	return AsyncTaskResult.SUCCESSFUL;
			} catch (RuntimeException e) {
				if(cr.getStatus().equals(org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
					return AsyncTaskResult.UNAUTHORIZED;
				} else if(!cr.getStatus().isSuccess()) {
					return AsyncTaskResult.CONNECTION_FAILED;
				}
			}
			return AsyncTaskResult.CONNECTION_FAILED;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			
			switch (result) {
			case AsyncTaskResult.UNAUTHORIZED:
				ToastCentered.makeText(thisActivity, "Unauthorized.").show();
				break;
			case AsyncTaskResult.SUCCESSFUL:
				thisActivity.children=children;
				populateSpinner();
				break;
			default:
				ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
				break;
			}
		}
	}

}
