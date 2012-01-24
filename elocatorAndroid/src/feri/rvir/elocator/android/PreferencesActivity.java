package feri.rvir.elocator.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.AsyncTaskResult;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.dbhelper.DBHelper;
import feri.rvir.elocator.rest.resource.RestletErrorMessage;
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.location.LocationResource;
import feri.rvir.elocator.rest.resource.tracking.TrackingResource;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UsersResource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.EditText;

public class PreferencesActivity extends PreferenceActivity {

	private static final int DIALOG_ADDTRACKEDUSER = 0;
	private static final int DIALOG_REMOVETRACKEDUSER = 1;
	private static DBHelper db = null;
	Cursor cursor = null;

	private PreferencesActivity thisActivity;

	private User user = null;

	private ArrayList<User> children = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisActivity = this;

		db = new DBHelper(getApplicationContext()); // new database helper
		db.open();

		try {
			cursor = db.getRowRaw("username");
			System.out.println("ni napake");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
//		while (!cursor.isAfterLast()) {
//			System.out.println("Loopam " + cursor.getString(1));
//			cursor.moveToNext();
//		}
//		
//

		StringBuilder sb = new StringBuilder();
		Serializer<User> serializer = new Serializer<User>();
		try {
			user = serializer
					.unserialize(openFileInput(getString(R.string.user_data_store)));
			sb.append("Signed in as: ");
			sb.append(user.getUsername());
			sb.append(". ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sb.append("Click here to sign out.");

		addPreferencesFromResource(R.xml.preferences);

		Preference prefSignOut = (Preference) findPreference("prefSignOut");
		prefSignOut.setSummary(sb.toString());
		prefSignOut
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						File dir = getFilesDir();
						File file = new File(dir,
								getString(R.string.user_data_store));
						if (file.delete()) {
							ToastCentered.makeText(thisActivity, "Signed out.")
									.show();
							Intent i = new Intent(thisActivity,
									MainActivity.class);
							startActivity(i);
							return true;
						} else {
							ToastCentered.makeText(thisActivity,
									"Sign out failed.").show();
							return false;
						}
					}
				});

		Preference prefAddTracked = (Preference) findPreference("prefAddTracked");
		prefAddTracked
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						showDialog(DIALOG_ADDTRACKEDUSER);
						return true;
					}
				});

		Preference prefRemoveTracked = (Preference) findPreference("prefRemoveTracked");
		prefRemoveTracked
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						new GetTrackingTask().execute(user.getUsername(),
								user.getPassword());
						return true;
					}
				});

		Preference prefSync = (Preference) findPreference("prefSync"); // baza
																		// sync
		prefSync.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				System.out.println("Zacetek");
				new SyncToDatabase().execute(user.getUsername(),
						user.getPassword());
				return true;
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_ADDTRACKEDUSER:
			final EditText text = new EditText(thisActivity);
			dialog = new AlertDialog.Builder(thisActivity)
					.setTitle("Enter username").setView(text)
					.setPositiveButton("Add", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String trackedUser = text.getText().toString();
							System.out.println(trackedUser);
							new AddTrackedUserTask().execute(
									user.getUsername(), user.getPassword(),
									trackedUser);
						}
					}).setNegativeButton("Cancel", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).create();
			break;
		case DIALOG_REMOVETRACKEDUSER:
			ArrayList<String> childrenString = new ArrayList<String>();
			if (children != null) {
				for (User u : children) {
					childrenString.add(u.getUsername());
				}
			}

			dialog = new AlertDialog.Builder(thisActivity)
					.setTitle("Select user")
					.setItems(
							childrenString.toArray(new CharSequence[childrenString
									.size()]), new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									new RemoveTrackedUserTask().execute(
											user.getUsername(),
											user.getPassword(),
											children.get(which).getUsername());
								}
							}).create();
			break;
		default:
			dialog = null;
			break;
		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_REMOVETRACKEDUSER:
			removeDialog(id);
			break;

		default:
			break;
		}
	}

	private class GetTrackingTask extends AsyncTask<String, Void, Integer> {

		private String username;
		private String password;

		private ArrayList<User> children;

		@Override
		protected Integer doInBackground(String... params) {
			username = params[0];
			password = params[1];
			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr = new ClientResource(
					getString(R.string.gae_server_address) + "/rest/users");
			cr.setRequestEntityBuffering(true);
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username,
					password);
			try {
				UsersResource resource = cr.wrap(UsersResource.class);
				children = resource.accept(username);
				return AsyncTaskResult.SUCCESSFUL;
			} catch (RuntimeException e) {
				if (cr.getStatus().equals(
						org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
					return AsyncTaskResult.UNAUTHORIZED;
				} else if (!cr.getStatus().isSuccess()) {
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
				thisActivity.children = children;
				showDialog(DIALOG_REMOVETRACKEDUSER);
				break;
			default:
				ToastCentered.makeText(thisActivity,
						"Connection to server failed.").show();
				break;
			}
		}
	}

	private class SyncToDatabase extends AsyncTask<String, Void, Integer> {
		String username;
		String password;

		@Override
		protected Integer doInBackground(String... params) {
			username = params[0];
			password = params[1];

			System.out.println("Elementov " + cursor.getCount());

			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr = new ClientResource(
					getString(R.string.gae_server_address) + "/rest/users/"
							+ username + "/location");
			cr.setRequestEntityBuffering(true);
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username,
					password);
			try {
				LocationResource r = cr.wrap(LocationResource.class);
				System.out.println("check");

				if (cursor.getCount() == 0)
					System.out.print("nothing in cursor");

				Location temp = null;
				Date timestamp = null;

				while (!cursor.isAfterLast()) {
					timestamp = new Date(cursor.getString(3));
					temp = new Location(Long.parseLong("1"), timestamp,
							Double.parseDouble(cursor.getString(1)),
							Double.parseDouble(cursor.getString(2)));
					r.store(temp);
					System.out.println("Poslal sem lokacijo");
					cursor.moveToNext();
				}
				cursor.close();
				db.deleteAllLocations("username");
				Cursor ostale = db.getRowRaw("username");
				ostale.close();
				System.out.println("brisano. ostane =  " + ostale.getCount());
				db.close();
				return AsyncTaskResult.SUCCESSFUL;
			} catch (RuntimeException e) {
				if (cr.getStatus().equals(
						org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
					return AsyncTaskResult.UNAUTHORIZED;
				} else if (!cr.getStatus().isSuccess()) {
					System.out.println("TEST 1");
					return AsyncTaskResult.CONNECTION_FAILED;
				}
			}
			System.out.println("Po�iljanje lokacije parenta");
			return AsyncTaskResult.CONNECTION_FAILED;
		}

		@Override
		protected void onPostExecute(Integer result) {

			switch (result) {
			case AsyncTaskResult.UNAUTHORIZED:
				ToastCentered.makeText(thisActivity, "Unauthorized.").show();
				break;
			case AsyncTaskResult.SUCCESSFUL:
				ToastCentered.makeText(thisActivity, "Synchronized.").show();
				break;
			default:
				ToastCentered.makeText(thisActivity,
						"Connection to server failed.").show();
				break;
			}
		}

	}

	private class AddTrackedUserTask extends AsyncTask<String, Void, Integer> {

		private String username;
		private String password;

		private String trackedUser;
		private RestletErrorMessage rem;

		@Override
		protected Integer doInBackground(String... params) {
			username = params[0];
			password = params[1];
			trackedUser = params[2];

			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr = new ClientResource(getString(R.string.gae_server_address) + "/rest/users/"+ username + "/tracking/add");
			cr.setRequestEntityBuffering(true);
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
			try {
				TrackingResource r = cr.wrap(TrackingResource.class);
				rem = r.accept(trackedUser);
				return AsyncTaskResult.SUCCESSFUL;
			} catch (RuntimeException e) {
				if (cr.getStatus().equals(
						org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
					return AsyncTaskResult.UNAUTHORIZED;
				} else if (!cr.getStatus().isSuccess()) {
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
				ToastCentered.makeText(thisActivity,
						"Connection to server failed.").show();
				break;
			}
		}
	}

	private class RemoveTrackedUserTask extends
			AsyncTask<String, Void, Integer> {

		private String username;
		private String password;

		private String trackedUser;
		private RestletErrorMessage rem;

		@Override
		protected Integer doInBackground(String... params) {
			username = params[0];
			password = params[1];
			trackedUser = params[2];

			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr = new ClientResource(getString(R.string.gae_server_address) + "/rest/users/"+ username + "/tracking/remove");
			cr.setRequestEntityBuffering(true);
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username,
					password);
			try {
				TrackingResource r = cr.wrap(TrackingResource.class);
				rem = r.accept(trackedUser);
				return AsyncTaskResult.SUCCESSFUL;
			} catch (RuntimeException e) {
				if (cr.getStatus().equals(
						org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
					return AsyncTaskResult.UNAUTHORIZED;
				} else if (!cr.getStatus().isSuccess()) {
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
				ToastCentered.makeText(thisActivity,
						"Connection to server failed.").show();
				break;
			}
		}
	}

}
