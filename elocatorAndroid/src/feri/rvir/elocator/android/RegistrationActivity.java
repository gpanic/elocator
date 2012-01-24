package feri.rvir.elocator.android;

import java.io.FileNotFoundException;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.AsyncTaskResult;
import feri.rvir.elocator.android.util.Crypto;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UserResource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity {
	
	private RegistrationActivity thisActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		thisActivity=this;
		
		Button registerButton=(Button)findViewById(R.id.reg_buttonRegister);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText usernameEditText=(EditText)findViewById(R.id.reg_editTextUsername);
				EditText passwordEditText=(EditText)findViewById(R.id.reg_editTextPassword);
				EditText repeatEditText=(EditText)findViewById(R.id.reg_editTextPasswordRepeat);
				
				String username=usernameEditText.getText().toString();
				String password=Crypto.hash(passwordEditText.getText().toString(), "SHA-1");
				String repeat=Crypto.hash(repeatEditText.getText().toString(), "SHA-1");
				if(!(username.equals("")||
						password.equals("")||
						repeat.equals(""))) {
					if(password.equals(repeat)) {
						new RegistrationTask().execute(username,password);
					} else {
						ToastCentered.makeText(thisActivity, "Passwords do not match.").show();
					}
				} else {
					ToastCentered.makeText(thisActivity, "Fill out all the fields.").show();
				}
				

			}
		});	
		
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}
	
	private class RegistrationTask extends AsyncTask<String, Void, Integer> {
		
		private String username;
		private String password;

		@Override
		protected  Integer doInBackground(String... params) {
			username=params[0];
			password=params[1];
			
			System.setProperty("java.net.preferIPv6Addresses", "false");
			ClientResource cr=new ClientResource(getString(R.string.gae_server_address)+"/rest/users/"+username+"/register");
	        cr.setRequestEntityBuffering(true);
	        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "registrator", Crypto.hash(getString(R.string.registrator_password), "SHA-1"));
			try {
				UserResource resource=cr.wrap(UserResource.class);
				resource.accept(new User(username, password));
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
				ToastCentered.makeText(thisActivity, "Registration failed.").show();
				break;
			case AsyncTaskResult.SUCCESSFUL:
				ToastCentered.makeText(thisActivity, "Registration successful.").show();
				Serializer<User> serializer=new Serializer<User>();
				try {
					serializer.serialize(thisActivity.openFileOutput(thisActivity.getString(R.string.user_data_store), Context.MODE_PRIVATE), new User(username, password));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Intent i=new Intent(thisActivity, TabMenuActivity.class);
				thisActivity.startActivity(i);
				break;
			default:
				ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
				break;
			}
		}
	}

}
