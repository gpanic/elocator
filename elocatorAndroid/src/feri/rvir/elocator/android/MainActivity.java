package feri.rvir.elocator.android;

import java.io.FileNotFoundException;
import java.util.Calendar;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.AsyncTaskResult;
import feri.rvir.elocator.android.util.Crypto;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.dbhelper.DBHelper;
import feri.rvir.elocator.rest.resource.user.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MainActivity thisActivity;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		thisActivity = this;
		
		DBHelper db=new DBHelper(getApplicationContext());
		db.open();
		db.addRow("t", "16.5", "16.5", Calendar.getInstance().getTime().toString());
		System.out.println("DODAL");
		db.close();
		
		if (isSignedIn()) {
			Intent i = new Intent(thisActivity, TabMenuActivity.class);
			startActivity(i);
		} else {

			Button signInButton = (Button) findViewById(R.id.main_buttonSignIn);
			signInButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					EditText usernameEditText = (EditText) findViewById(R.id.main_editTextUsername);
					EditText passwordEditText = (EditText) findViewById(R.id.main_editTextPassword);

					String username = usernameEditText.getText().toString();
					String password = Crypto.hash(passwordEditText.getText()
							.toString(), "SHA-1");

					if (!(username.equals("") || password.equals(Crypto.hash(
							"", "SHA-1")))) {
						new SignInTask().execute(username, password);
					} else {
						ToastCentered.makeText(thisActivity,
								"Fill out all the fields.").show();
					}
				}

			});

			TextView registerLink = (TextView) findViewById(R.id.main_textViewRegister);
			registerLink.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(v.getContext(),
							RegistrationActivity.class);
					startActivity(i);
				}
			});

		}
	}

	private boolean isSignedIn() {
		Serializer<User> seralizer = new Serializer<User>();
		try {
			User u = seralizer
					.unserialize(openFileInput(getString(R.string.user_data_store)));
			if (u != null) {
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	private class SignInTask extends AsyncTask<String, Void, Integer> {

		private String username;
		private String password;

		@Override
		protected Integer doInBackground(String... params) {
			username = params[0];
			password = params[1];
			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr = new ClientResource(
					getString(R.string.gae_server_address) + "/rest/users/"
							+ username);
			cr.setRequestEntityBuffering(true);
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username,
					password);
			try {
				cr.get();
				return AsyncTaskResult.AUTHORIZED;
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
				ToastCentered.makeText(thisActivity,
						"Credentials do not match.").show();
				break;
			case AsyncTaskResult.AUTHORIZED:
				ToastCentered.makeText(thisActivity,
						"Signed in as: " + username + ".").show();
				Serializer<User> serializer = new Serializer<User>();
				try {
					serializer
							.serialize(thisActivity.openFileOutput(thisActivity
									.getString(R.string.user_data_store),
									Context.MODE_PRIVATE), new User(username,
									password));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Intent i = new Intent(thisActivity, TabMenuActivity.class);
				thisActivity.startActivity(i);
				break;
			default:
				ToastCentered.makeText(thisActivity,
						"Connection to server failed.").show();
				break;
			}
		}
	}

}