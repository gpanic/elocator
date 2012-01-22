package feri.rvir.elocator.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.AsyncTaskResult;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.RestletErrorMessage;
import feri.rvir.elocator.rest.resource.tracking.TrackingResource;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UsersResource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.EditText;

public class PreferencesActivity extends PreferenceActivity {
	
	private static final int DIALOG_ADDTRACKEDUSER=0;
	
	private PreferenceActivity thisActivity;
	
	private User user=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisActivity=this;
		
		StringBuilder sb=new StringBuilder();
		Serializer<User> serializer=new Serializer<User>();
		try {
			user = serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
			sb.append("Signed in as: ");
			sb.append(user.getUsername());
			sb.append(". ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sb.append("Click here to sign out.");
		
		addPreferencesFromResource(R.xml.preferences);
		
		Preference prefSignOut=(Preference)findPreference("prefSignOut");
		prefSignOut.setSummary(sb.toString());
		prefSignOut.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				File dir=getFilesDir();
				File file=new File(dir,getString(R.string.user_data_store));
				if(file.delete()) {
					ToastCentered.makeText(thisActivity, "Signed out.").show();
					Intent i=new Intent(thisActivity, MainActivity.class);
					startActivity(i);
					return true;
				} else {
					ToastCentered.makeText(thisActivity, "Sign out failed.").show();
					return false;
				}
			}
		});
		
		Preference prefAddTracked=(Preference)findPreference("prefAddTracked");
		prefAddTracked.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				showDialog(DIALOG_ADDTRACKEDUSER);
				return true;
			}
		});
		
		Preference prefSync=(Preference)findPreference("prefSync");
		prefSync.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				return false;
			}
		});
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_ADDTRACKEDUSER:
			final EditText text=new EditText(thisActivity);
			dialog=new AlertDialog.Builder(thisActivity)
				.setTitle("Enter username")
				.setView(text)
				.setPositiveButton("Add", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String trackedUser=text.getText().toString();
						new AddTrackedUserTask().execute(user.getUsername(),user.getPassword(),trackedUser);
					}
				})
				.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.create();
			break;
		default:
			dialog=null;
			break;
		}
		return dialog;
	}
	
	private class AddTrackedUserTask extends AsyncTask<String, Void, Integer> {
			
			private String username;
			private String password;
			
			private String trackedUser;
			private RestletErrorMessage rem;
			
			@Override
			protected  Integer doInBackground(String... params) {
				username=params[0];
				password=params[1];
				trackedUser=params[2];
				
				System.out.println(username+password+trackedUser);
				ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/"+username+"/tracking");
		        cr.setRequestEntityBuffering(true);
		        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
				try {
					TrackingResource r=cr.wrap(TrackingResource.class);
					rem=r.accept(username, trackedUser);
		        	return AsyncTaskResult.SUCCESSFUL;
				} catch (RuntimeException e) {
					if(cr.getStatus().equals(org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
						return AsyncTaskResult.UNAUTHORIZED;
					} else if(!cr.getStatus().isSuccess()) {
						System.out.println("TEST 1");
						return AsyncTaskResult.CONNECTION_FAILED;
					}
				}
				System.out.println("TEST 2");
				return AsyncTaskResult.CONNECTION_FAILED;
			}
			
			@Override
			protected void onPostExecute(Integer result) {
				
				switch (result) {
				case AsyncTaskResult.UNAUTHORIZED:
					ToastCentered.makeText(thisActivity, "Unauthorized.").show();
					break;
				case AsyncTaskResult.SUCCESSFUL:
					ToastCentered.makeText(thisActivity, rem.getMessage()).show();
					break;
				default:
					ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
					break;
				}
			}
	}

}
