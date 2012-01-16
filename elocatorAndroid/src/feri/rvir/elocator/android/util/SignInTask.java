package feri.rvir.elocator.android.util;

import java.io.FileNotFoundException;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.R;
import feri.rvir.elocator.android.TabMenuActivity;
import feri.rvir.elocator.rest.resource.user.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class SignInTask extends AsyncTask<String, Void, Integer> {
	
	private final int UNAUTHORIZED=0;
	private final int CONNECTION_FAILED=1;
	private final int AUTHORIZED=2;
	
	private String username;
	private String password;
	
	private Activity thisActivity;
	
	public SignInTask(Activity thisActivity) {
		this.thisActivity=thisActivity;
	}

	@Override
	protected  Integer doInBackground(String... params) {
		username=params[0];
		password=params[1];
		ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/"+username);
        cr.setRequestEntityBuffering(true);
        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
		try {
        	cr.get();
        	return AUTHORIZED;
		} catch (RuntimeException e) {
			if(cr.getStatus().equals(org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED)) {
				return UNAUTHORIZED;
			} else if(!cr.getStatus().isSuccess()) {
				return CONNECTION_FAILED;
			}
		}
		return CONNECTION_FAILED;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		
		switch (result) {
		case UNAUTHORIZED:
			ToastCentered.makeText(thisActivity, "Credentials do not match.").show();
			break;
		case AUTHORIZED:
			ToastCentered.makeText(thisActivity, "Signed in as: "+username+".").show();
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
