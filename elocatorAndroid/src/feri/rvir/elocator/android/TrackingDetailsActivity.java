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
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.location.LocationResource;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UsersResource;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TrackingDetailsActivity extends MapActivity {
	
	private TrackingDetailsActivity thisActivity;
	private LineItemizedOverlay itemizedOverlay;
	
	private Drawable drawableInfo;
	private Drawable drawableStart;
	private Drawable drawableFinish;
	
	private MapView mapView;
	private List<Overlay> mapOverlays;
	
	private ArrayList<User> children;
	private ArrayList<String> childrenString;
	private ArrayList<Location> locationsOfSelectedUser=new ArrayList<Location>();
	private User selectedUser=null;
	
	private Spinner spinnerUsers;
	
	private User user=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackingdetails);
		thisActivity=this;
		
		drawableInfo=getResources().getDrawable(R.drawable.ic_marker_info);
		drawableInfo.setBounds(-drawableInfo.getIntrinsicWidth()/2, -drawableInfo.getIntrinsicHeight(), drawableInfo.getIntrinsicWidth()/2, 0);
		drawableStart=getResources().getDrawable(R.drawable.ic_marker_start);
		drawableStart.setBounds(-drawableStart.getIntrinsicWidth()/2, -drawableStart.getIntrinsicHeight(), drawableStart.getIntrinsicWidth()/2, 0);
		drawableFinish=getResources().getDrawable(R.drawable.ic_marker_finish);
		drawableFinish.setBounds(-drawableFinish.getIntrinsicWidth()/2, -drawableFinish.getIntrinsicHeight(), drawableFinish.getIntrinsicWidth()/2, 0);
		
		Serializer<User> serializer=new Serializer<User>();
		try {
			user = serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		spinnerUsers=(Spinner)findViewById(R.id.details_spinnerUsers);
		spinnerUsers.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				System.out.println("ON ITEM SELECTED");
				selectedUser=children.get(arg2);
				new GetLocationsTask().execute(user.getUsername(), user.getPassword(), selectedUser.getUsername());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		populateSpinner();
		
		new GetTrackingTask().execute(user.getUsername(), user.getPassword());
		
		mapView=(MapView)findViewById(R.id.details_mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays=mapView.getOverlays();
		
	}
	
	private void populateOverlays() {
		mapOverlays.remove(itemizedOverlay);
		itemizedOverlay=new LineItemizedOverlay(drawableInfo, thisActivity);
		for(int i=0;i<locationsOfSelectedUser.size();i++) {
			Location l=locationsOfSelectedUser.get(i);
			OverlayItem oi=new OverlayItem(new GeoPoint((int)(l.getLatitude()*1E6),(int)(l.getLongitude()*1E6)), selectedUser.getUsername(), l.getTimestamp().toGMTString());
			if(i==0) {
				oi.setMarker(drawableStart);
			} else if(i==locationsOfSelectedUser.size()-1) {
				oi.setMarker(drawableFinish);
			}
			itemizedOverlay.addOverlay(oi);
		}
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
		spinnerUsers=(Spinner)findViewById(R.id.details_spinnerUsers);
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
		/*
		MapControls.adjustZoom(itemizedOverlay.getOverlayItems(), mapView);
		new GetTrackingTask().execute(user.getUsername(), user.getPassword());
		populateSpinner();*/
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
			System.out.println("GET TRACKING");
			username=params[0];
			password=params[1];
			ClientResource cr=new ClientResource(getString(R.string.gae_server_address)+"/rest/users");
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
	
	private class GetLocationsTask extends AsyncTask<String, Void, Integer> {
		
		private String username;
		private String password;
		private String childUsername;
		private ArrayList<Location> locationsOfSelectedUser=new ArrayList<Location>();
		
		@Override
		protected  Integer doInBackground(String... params) {
			System.out.println("GET LOCATIONS");
			username=params[0];
			password=params[1];
			childUsername=params[2];
			System.out.println(username);
			System.out.println(childUsername);
			ClientResource cr=new ClientResource(getString(R.string.gae_server_address)+"/rest/users/"+username+"/location");
	        cr.setRequestEntityBuffering(true);
	        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
			try {
				LocationResource lr=cr.wrap(LocationResource.class);
				locationsOfSelectedUser=lr.accept(childUsername);
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
				thisActivity.locationsOfSelectedUser=locationsOfSelectedUser;
				populateOverlays();
				break;
			default:
				ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
				break;
			}
		}
	}

}
